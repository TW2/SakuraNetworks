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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

/**
 *
 * @author util2
 */
public class Line implements GeoFormInterface {
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final int r;
    private final int g;
    private final int b;
    private final float thickness;

    public Line(double x1, double y1, double x2, double y2, int r, int g, int b, float thickness) {        
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        
        this.r = r;
        this.g = g;
        this.b = b;
        
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Line2D line = new Line2D.Double(x1, y1, x2, y2);
        g2d.setColor(new Color(r, g, b));
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        
        g2d.draw(line);
        
        g2d.setStroke(oldStroke);
    }
    
}
