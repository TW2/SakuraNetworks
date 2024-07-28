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
package org.wingate.libresa.ui.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.graalvm.polyglot.*;

/**
 *
 * @author util2
 */
public class DrawPanel extends JPanel {
    
    private Color backgroundColor = null;
    private final List<GeoFormInterface> geoForms = new ArrayList<>();
        
    public DrawPanel(int width, int height) {
        setPreferredSize(new java.awt.Dimension(width, height));
        setMaximumSize(new java.awt.Dimension(width, height));
    }
    
    public DrawPanel(){
        this(500, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(backgroundColor != null){
            g2d.setColor(backgroundColor);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        
        for(GeoFormInterface gfi : geoForms){
            gfi.draw(g2d);
        }
    }
    
    public void exec(String script){
        try(StringReader sr = new StringReader(script);){
            Source source = Source.newBuilder("js", sr, "test.js").build();
            
            Context context = Context.newBuilder().allowAllAccess(true).build();
            context.getBindings("js").putMember("draw", this);
            context.eval("js", source.getCharacters());
            
        } catch (IOException ex) {
            Logger.getLogger(DrawPanel.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    @HostAccess.Export
    public void showOK(){
        System.out.println("OK OK Okay");
    }
    
    @HostAccess.Export
    public void setBackgroundColor(int r, int g, int b){
        backgroundColor = new Color(r, g, b);
        repaint();
    }
    
    @HostAccess.Export
    public void addLine(double x1, double y1, double x2, double y2, int r, int g, int b, float thickness){
        geoForms.add(new Line(x1, y1, x2, y2, r, g, b, thickness));
    }
}
