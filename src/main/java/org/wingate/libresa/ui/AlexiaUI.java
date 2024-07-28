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

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import org.wingate.libresa.MainFrame;

/**
 *
 * @author util2
 */
public class AlexiaUI extends JPanel {
    private final MainFrame mainFrame;
    
    private final JTextPane textPane;
    private final StyledDocument styledDocument;
    private final Style baseStyle;

    public AlexiaUI(MainFrame mainFrame, JTextPane textPane) {
        this.mainFrame = mainFrame;
        this.textPane = textPane;
        this.styledDocument = textPane.getStyledDocument();
        
        baseStyle = styledDocument.addStyle("baseStyle", null);
        
        init();
    }
    
    private void init(){
        textPane.setStyledDocument(styledDocument);
    }
    
    public void render(String alexiaScript){
        if(alexiaScript == null) return;
        try{
            textPane.setText("");
            Alexia alexia = new Alexia(mainFrame, textPane, styledDocument, baseStyle);
            alexia.parseXML(alexiaScript);
        }catch(Exception exc){
            System.err.println("Error in alexia script!");
        }
    }
}
