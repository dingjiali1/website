import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import javax.swing.JOptionPane;

public class UDPChat extends javax.swing.JFrame {

    public UDPChat() {
        initComponents();
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setText("10.26.40.45");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(203, 203, 203)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 243, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       try{
 File fileToSend = new File("C:\\Users\\27364\\Document\\csci121\\data.txt"); 
        if (!fileToSend.exists() || fileToSend.isDirectory()) {
            JOptionPane.showMessageDialog(this, "The specified file does not exist or is a directory. Please check!");
            return;
        }
        DatagramSocket s = new DatagramSocket();
        InetAddress addr = InetAddress.getByName(jTextField1.getText());

       
        String fileName = fileToSend.getName();
        byte[] fileNameBytes = fileName.getBytes("UTF-8");
        byte[] fileNameLengthBytes = ByteBuffer.allocate(4).putInt(fileNameBytes.length).array();
        DatagramPacket fileNameLengthPacket = new DatagramPacket(fileNameLengthBytes, 4, addr, 30000);
        s.send(fileNameLengthPacket);

        
        DatagramPacket fileNamePacket = new DatagramPacket(fileNameBytes, fileNameBytes.length, addr, 30000);
        s.send(fileNamePacket);

        
        long fileLength = fileToSend.length();
        byte[] fileLengthBytes = ByteBuffer.allocate(4).putInt((int) fileLength).array();
        DatagramPacket fileLengthPacket = new DatagramPacket(fileLengthBytes, 4, addr, 30000);
        s.send(fileLengthPacket);

       
        FileInputStream fis = new FileInputStream(fileToSend);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer))!= -1) {
            DatagramPacket filePacket = new DatagramPacket(buffer, bytesRead, addr, 30000);
            s.send(filePacket);
        }
        fis.close();
        s.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
 void recev() {
    try {
        DatagramSocket s = new DatagramSocket(30000);
        while (true) {
            
            byte[] fileNameLengthBuf = new byte[4];
            DatagramPacket fileNameLengthPacket = new DatagramPacket(fileNameLengthBuf, 4);
            s.receive(fileNameLengthPacket);
            int fileNameLength = ByteBuffer.wrap(fileNameLengthBuf).getInt();

            
            byte[] fileNameBuf = new byte[fileNameLength];
            DatagramPacket fileNamePacket = new DatagramPacket(fileNameBuf, fileNameLength);
            s.receive(fileNamePacket);
            String fileName = new String(fileNameBuf, "UTF-8");

            
            FileOutputStream fos = new FileOutputStream(fileName);

            
            byte[] fileLengthBuf = new byte[4];
            DatagramPacket fileLengthPacket = new DatagramPacket(fileLengthBuf, 4);
            s.receive(fileLengthPacket);
            int fileLength = ByteBuffer.wrap(fileLengthBuf).getInt();

            
            int totalBytesReceived = 0;
            byte[] buffer = new byte[1024];
            while (totalBytesReceived < fileLength) {
                DatagramPacket filePacket = new DatagramPacket(buffer, buffer.length);
                s.receive(filePacket);
                fos.write(buffer, 0, filePacket.getLength());
                totalBytesReceived += filePacket.getLength();
            }
            fos.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
            java.util.logging.Logger.getLogger(UDPChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UDPChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UDPChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UDPChat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UDPChatFile u = new UDPChatFile();
                new Thread(){public void run(){
                    u.recev();
                }}.start();
                new UDPChatFile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
