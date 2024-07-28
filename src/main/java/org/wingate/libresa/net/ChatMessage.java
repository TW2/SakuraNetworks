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
package org.wingate.libresa.net;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author util2
 */
public class ChatMessage {
    private String owner;
    private String message;
    private Image image;

    public ChatMessage() {
        this.owner = "Anonymous";
        this.image = null;
        this.message = "Just to say nothing!";
    }

    public ChatMessage(String owner, String message) {
        this.owner = owner;
        this.image = null;
        this.message = message;
    }

    public Image getImage() {
        return image;
    }
    
    public void setImageWithPath(String imagePath){
        image = new ImageIcon(imagePath).getImage();
    }
    
    public String getMessage() {
        return message;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    public void get(DataInputStream dis) throws IOException {
        // name
        owner = dis.readUTF();
        // image or not
        if(dis.readBoolean() == true){
            try(ByteArrayInputStream bais = new ByteArrayInputStream(dis.readAllBytes());){
                image = ImageIO.read(bais);
            }
        }
        // message
        message = dis.readUTF();
    }
    
    public void send(DataOutputStream dos) throws IOException {
        // name
        dos.writeUTF(owner);
        // image or not
        boolean hasImage = image != null;
        dos.writeBoolean(hasImage);
        if(hasImage){
            try(ByteArrayOutputStream baos = new ByteArrayOutputStream();){
                BufferedImage img = new BufferedImage(
                        image.getWidth(null),
                        image.getHeight(null),
                        BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2d = img.createGraphics();
                g2d.drawImage(image, 0, 0, null);
                g2d.dispose();
                ImageIO.write(img, "png", baos);
                dos.write(baos.toByteArray());
            }
        }        
        // message
        dos.writeUTF(message);
    }
    
}
