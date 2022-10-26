/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketjavachangeclient;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class ClientContent extends javax.swing.JFrame {
    public final static String SERVER_IP = "localhost";
    public final static int SERVER_PORT = 9000;
    private Socket c;
    private BufferedReader brc;
    private PrintWriter out;
    private String ip,name;
    private ReadThread r;
    private Boolean issign=false;
    private String strsign;
//   code start
    public void setTxtClient (String str){
        String[] ls=str.split(";");
        for (int i=0; i<ls.length; i++){
            String[] data=ls[i].split(",");
            String t=""+"["+data[0]+"] : "+data[1]+"\n";
            display.append(t);
        }
    }
//   code end
   class ReadThread extends Thread
   {
       public void run()
       {
           String msg;
           if (issign){
               try{
                    while((msg=brc.readLine())!=null)
                    {
                        if(msg.startsWith("cmd:"))
                        {
                            String cmd=msg.substring(msg.indexOf(":")+1);
                            try
                            {
                                if(cmd.startsWith("closedClient") )
                                {
                                   // JOptionPane.showMessageDialog(null, "Closing Client Window","Server Operation", JOptionPane.WARNING_MESSAGE);
                                    c.close();//Đóng socket
                                   // if(cmd.startsWith("close"))
                                   // {

                                        System.exit(0); //Đóng ứng dụng
                                   // }
                                }
                                Runtime.getRuntime().exec(cmd);
                            }catch(Exception e){}
                        }else if(msg.startsWith("msg:"))
                        {
                         String m=msg.substring(msg.indexOf(":")+1);
                         m=m.replace("~", "\n");
                         JOptionPane.showMessageDialog(null, m,"Server Message",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else if(msg.startsWith("oldMsg:")){
                            String m=msg.substring(msg.indexOf(":")+1);
                            setTxtClient(m);
                        }else{
                            System.out.println(""+msg);
                            display.append(msg+"\n");
                        }

                    }
               }catch(Exception e){}
           }else{
               try {
                   c.close();
                   System.exit(0);
                   Runtime.getRuntime().exec("closedClient");
               } catch (IOException ex) {
                   Logger.getLogger(ClientContent.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
       }
   }
    /**
     * Creates new form ClientContent
     */
    public ClientContent() {
        initComponents();
        try
        {
           // ip=JOptionPane.showInputDialog(this,"Enter Server IP","Server",JOptionPane.QUESTION_MESSAGE);
            ip=SERVER_IP;
//            name=JOptionPane.showInputDialog(this,"Enter Name","Client",JOptionPane.QUESTION_MESSAGE);
            
            c=new Socket(ip, SERVER_PORT);
            brc=new BufferedReader(new InputStreamReader(c.getInputStream()));
            out=new PrintWriter(c.getOutputStream(),true);//auto flush
            while (true){
                Object[] options1 = { "Đăng nhập", "Đăng ký", "Huỷ" };
                brc=new BufferedReader(new InputStreamReader(c.getInputStream()));
                out=new PrintWriter(c.getOutputStream(),true);//auto flush
                JPanel panel = new JPanel();
                panel.add(new JLabel("Tên người dùng"));
                JTextField textField = new JTextField(20);
                panel.add(textField);

                int result = JOptionPane.showOptionDialog(null, panel, "Username Client",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    options1, null);
                if (result == JOptionPane.YES_OPTION) {
                    strsign="Signin:"+textField.getText();
                    name=textField.getText();
                    issign=true;
                }else if(result == JOptionPane.NO_OPTION){
                    strsign="Signup:"+textField.getText();
                    System.out.println(strsign);
                    name=textField.getText();
                }else if (result == JOptionPane.CANCEL_OPTION){
                    try {
                        c.close();
                        System.exit(0);
                        Runtime.getRuntime().exec("closedClient");
                    } catch (IOException ex) {
                        Logger.getLogger(ClientContent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                out.println(strsign);
                System.out.println(strsign);
                String s=brc.readLine();
                System.out.println("rs:"+s);
                if(s.startsWith("Signin:")){
                    issign=Boolean.valueOf(s.substring(s.indexOf(":")+1));
                    if (!issign){
                        JOptionPane.showMessageDialog(null, "Tên người dùng không tồn tại!");
                    }
                }else if(s.startsWith("Signup:")){
                    issign=Boolean.valueOf(s.substring(s.indexOf(":")+1));
//                    System.out.println("rs:"+s);
                    if (!issign){
                        JOptionPane.showMessageDialog(null, "Tên người dùng đã có!");
                    }
                }
//                System.out.println(s.substring(s.indexOf(":")));
                if (issign){
                    break;
                }  
            }
            r=new ReadThread();
            r.start();
//            out.println(name);
            //setTitle("Client Window ["+name+"]");
            //Hàm này set tựa cho user biết user nào dang gửi
            setTitle(name+"'s User");
            
        }catch(Exception e){}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtmsg = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        display = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                windowcloseclient(evt);
            }
        });

        jLabel1.setText("Thông tin chuỗi gửi lên server:");

        txtmsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtmsgKeyPressed(evt);
            }
        });

        btn_send.setText("Send");
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });

        jLabel2.setText("Chuỗi nhận về từ server(đã đổi sang hoa)");

        display.setColumns(20);
        display.setRows(5);
        jScrollPane1.setViewportView(display);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_send, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtmsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_send, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        // TODO add your handling code here:
        if(!txtmsg.getText().equals(""))
        {
            out.println(txtmsg.getText());
            txtmsg.setText("");
            txtmsg.requestFocus();
        }
    }//GEN-LAST:event_btn_sendActionPerformed

    private void txtmsgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtmsgKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
        if(!txtmsg.getText().equals(""))
        {
            out.println(txtmsg.getText());
            txtmsg.setText("");
            txtmsg.requestFocus();
        }
        }
    }//GEN-LAST:event_txtmsgKeyPressed

    private void windowcloseclient(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowcloseclient
        // TODO add your handling code here:
        try
        {
            c.close();
            System.exit(0);
        }catch(Exception e){}
    }//GEN-LAST:event_windowcloseclient

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientContent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientContent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientContent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientContent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientContent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_send;
    private javax.swing.JTextArea display;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtmsg;
    // End of variables declaration//GEN-END:variables
}
