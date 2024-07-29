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
import javax.swing.JOptionPane;
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
    private String localSurname = "Newbie";
    private String localImage = null;

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

    public String getLocalSurname() {
        return localSurname;
    }

    public void setLocalSurname(String localSurname) {
        this.localSurname = localSurname;
    }

    public String getLocalImage() {
        return localImage;
    }

    public void setLocalImage(String localImage) {
        this.localImage = localImage;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popSet = new javax.swing.JPopupMenu();
        popmSurname = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        tfSendText = new javax.swing.JTextField();
        btnOK = new javax.swing.JButton();

        popmSurname.setText("Set a surname");
        popmSurname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popmSurnameActionPerformed(evt);
            }
        });
        popSet.add(popmSurname);

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setViewportView(textPane);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        tfSendText.setComponentPopupMenu(popSet);
        jPanel1.add(tfSendText, java.awt.BorderLayout.CENTER);

        btnOK.setText("Send");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });
        jPanel1.add(btnOK, java.awt.BorderLayout.EAST);

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        // Send text
        ChatMessage cm = new ChatMessage(localSurname, tfSendText.getText());
        // 1. Send to network
        readMessage(cm);
        // 2. Display
        writeMessage(cm);
        // 3. Clear text
        tfSendText.setText("");
    }//GEN-LAST:event_btnOKActionPerformed

    private void popmSurnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popmSurnameActionPerformed
        // Define surname
        String s = JOptionPane.showInputDialog("Please type a surname:");
        if(s == null || s.isEmpty()){
            localSurname = "Anonymous";
        }else{
            localSurname = s;
        }
    }//GEN-LAST:event_popmSurnameActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu popSet;
    private javax.swing.JMenuItem popmSurname;
    private javax.swing.JTextPane textPane;
    private javax.swing.JTextField tfSendText;
    // End of variables declaration//GEN-END:variables

}
