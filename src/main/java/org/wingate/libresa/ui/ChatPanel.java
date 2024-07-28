/*
 * Copyright (C) 2024 util2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wingate.libresa.ui;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import org.wingate.libresa.net.ChatMessage;
import org.wingate.libresa.net.Client;
import org.wingate.libresa.net.Handler;
import org.wingate.libresa.net.Node;
import org.wingate.libresa.serve.Protocol;

/**
 *
 * @author util2
 */
public class ChatPanel extends javax.swing.JPanel {
    
    private final List<Node> nodes = new ArrayList<>();
    private final StyledDocument styledDocument;
    private final Style baseStyle;
    
    private final Handler handler;
    private final Client client;
    
    private String uniqueName = "www.whatsup.people";

    /**
     * Creates new form ChatPanel
     * @param handler
     * @param client
     */
    public ChatPanel(Handler handler, Client client) {
        initComponents();
        
        this.handler = handler;
        this.client = client;
        
        styledDocument = textPane.getStyledDocument();
        textPane.setStyledDocument(styledDocument);
        
        baseStyle = styledDocument.addStyle("baseStyle", null);
    }
    
    public ChatPanel getChat(){
        return getChat(300, 400);
    }
    
    public ChatPanel getChat(int w, int h){
        if(w > 0 && h > 0){
            setSize(w, h);
            setPreferredSize(new java.awt.Dimension(w, h));
            setMaximumSize(new java.awt.Dimension(w, h));
            setMinimumSize(new java.awt.Dimension(w, h));
        }
        return this;
    }
    
    public void insertString(String text){
        try{
            if(styledDocument.getLength() > 0){
                styledDocument.insertString(styledDocument.getLength(), text + "\n", baseStyle);
            }else{
                styledDocument.insertString(0, text + "\n", baseStyle);
            }                
        }catch(BadLocationException ex){
            Logger.getLogger(ChatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertString(String text, Style style){
        try{
            if(styledDocument.getLength() > 0){
                styledDocument.insertString(styledDocument.getLength(), text + "\n", style);
            }else{
                styledDocument.insertString(0, text + "\n", style);
            }                
        }catch(BadLocationException ex){
            Logger.getLogger(ChatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeMessage(ChatMessage cm){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String d = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(date);
        String h = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).format(time);
        String n = cm.getOwner();
        String t = cm.getMessage();

        insertString(String.format("%s|%s %s -> %s", d, h, n, t));
    }
    
    // Départ (Arrivé dans Handler)
    public void readMessage(ChatMessage cm){
        for(Node n : nodes){
            System.out.println(n);        
            try(Socket socket = client.connect(n);
                    OutputStream out = socket.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(out);)
            {
                // Protocol
                dos.writeUTF(Protocol.ChatMessage.getType());
                // Unique name
                dos.writeUTF(uniqueName);
                // ChatMesssage all-in-one
                cm.send(dos);
                client.close(socket);
            } catch (IOException ex) {
                Logger.getLogger(ChatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public List<Node> getNodes() {
        return nodes;
    }
    
    public Handler getHandler() {
        return handler;
    }

    public Client getClient() {
        return client;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();
        jToolBar1 = new javax.swing.JToolBar();
        btnCool = new javax.swing.JButton();
        btnSend = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        tfName = new javax.swing.JTextField();
        tfSendText = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setViewportView(textPane);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        btnCool.setText("Emoji");
        btnCool.setFocusable(false);
        btnCool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCoolActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCool);

        btnSend.setText("Send");
        btnSend.setFocusable(false);
        btnSend.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSend.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSend);

        jPanel1.setLayout(new java.awt.BorderLayout());

        tfName.setText("Newbie");
        jPanel1.add(tfName, java.awt.BorderLayout.WEST);
        jPanel1.add(tfSendText, java.awt.BorderLayout.CENTER);

        jToolBar1.add(jPanel1);

        add(jToolBar1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCoolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCoolActionPerformed
        // Emoji here TODO
    }//GEN-LAST:event_btnCoolActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // Send text
        ChatMessage cm = new ChatMessage(tfName.getText(), tfSendText.getText());
        // 1. Send to network
        readMessage(cm);
        // 2. Display
        writeMessage(cm);
    }//GEN-LAST:event_btnSendActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCool;
    private javax.swing.JButton btnSend;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextPane textPane;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfSendText;
    // End of variables declaration//GEN-END:variables
}
