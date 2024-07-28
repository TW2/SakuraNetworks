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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author util2
 */
public class ChatImage {
    private final String path;
    private BufferedImage image = null;

    public ChatImage(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
    
    public BufferedImage convert(){
        if(image != null) return image;
        if(path == null) return null;
        ImageIcon ii = new ImageIcon(path);
        BufferedImage img = new BufferedImage(ii.getIconWidth(), ii.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        
        g.drawImage(img, 0, 0, null);
        
        g.dispose();
        return img;
    }
    
    public static byte[] toBytes(BufferedImage img){
        byte[] bytes = null;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            ImageIO.write(img, "png", baos);
            bytes = baos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(ChatImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bytes;
    }
    
    public static BufferedImage toImage(byte[] bytes){
        BufferedImage img = null;
        try(ByteArrayInputStream bais = new ByteArrayInputStream(bytes);){
            img = ImageIO.read(bais);
        } catch (IOException ex) {
            Logger.getLogger(ChatImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
