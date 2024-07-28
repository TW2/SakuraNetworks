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

import org.wingate.libresa.ui.drawing.DrawPanel;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.wingate.libresa.MainFrame;
import org.wingate.libresa.net.Client;
import org.wingate.libresa.net.Handler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author util2
 */
public class Alexia {
    private final MainFrame mainFrame;
    
    private final JTextPane textPane;
    private final StyledDocument styledDocument;
    private final Style baseStyle;

    public Alexia(MainFrame mainFrame, JTextPane textPane, StyledDocument styledDocument, Style baseStyle) {
        this.mainFrame = mainFrame;
        this.textPane = textPane;
        this.styledDocument = styledDocument;
        this.baseStyle = baseStyle;
    }
    
    public void parseXML(String script){        
        if(script == null) return;
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            
            File file = new File(script);
            AlexiaScriptHandler ash = new AlexiaScriptHandler(textPane, styledDocument, baseStyle);
            parser.parse(file, ash);
        }catch(ParserConfigurationException | SAXException | IOException ex){
            Logger.getLogger(Alexia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private class AlexiaScriptHandler extends DefaultHandler {
        private StringBuffer buffer;
        private final JTextPane textPane;
        private final StyledDocument styledDocument;
        private final Style baseStyle;
        
        private String title = "";
        private boolean inHead = false;
        private boolean inBody = false;

        public AlexiaScriptHandler(JTextPane textPane, StyledDocument styledDocument, Style baseStyle) {
            super();
            this.textPane = textPane;
            this.styledDocument = styledDocument;
            this.baseStyle = baseStyle;
            this.buffer = null;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            
            switch(qName){
                case "head" -> { inHead = true; }
                case "body" -> { inBody = true; }
                case "title" -> { buffer = new StringBuffer(); }
                case "text" -> { buffer = new StringBuffer(); }
                case "br" -> { insertString("\n"); }
                case "chat" -> {
                    Handler handler = mainFrame.getHandler();
                    ChatPanel cp = new ChatPanel(handler, new Client());
                    handler.getChatPanels().add(cp);
                    File currentFolder = new File(".");
                    File file = new File(currentFolder.getAbsolutePath() + File.separator + "nodes");
                    cp.getNodes().addAll(MainFrame.fillNodes(file.getPath()));
                    insertComponent(cp.getChat());
                }
                case "draw" -> { buffer = new StringBuffer(); }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            
            switch(qName){
                case "head" -> { inHead = false; }
                case "body" -> { inBody = false; }
                case "title" -> {
                    if(inHead == true) {
                        title = buffer.toString();
                        buffer = null;
                    }
                }
                case "text" -> {
                    if(inBody == true) {
                        String s = buffer.toString();
                        insertString(s);
                        buffer = null;
                    }
                }
                case "draw" -> {
                    DrawPanel dr = new DrawPanel();
                    insertComponent(dr);
                    String s = buffer.toString();
                    dr.exec(s);
                    buffer = null;
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            if(buffer != null) buffer.append(ch, start, length);
        }
        
        public void insertString(String text, Style style){
            try{
                if(styledDocument.getLength() > 0){
                    styledDocument.insertString(styledDocument.getLength(), text, style);
                }else{
                    styledDocument.insertString(0, text, style);
                }                
            }catch(BadLocationException ex){
                Logger.getLogger(AlexiaScriptHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void insertString(String text){
            try{
                if(styledDocument.getLength() > 0){
                    styledDocument.insertString(styledDocument.getLength(), text, baseStyle);
                }else{
                    styledDocument.insertString(0, text, baseStyle);
                }
            }catch(BadLocationException ex){
                Logger.getLogger(AlexiaScriptHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void insertComponent(Component c){
            textPane.insertComponent(c);
        }
        
        public void insertImage(String path){
            ImageIcon ii = new ImageIcon(path);
            textPane.insertIcon(ii);
        } 

        public String getTitle() {
            return title;
        }
        
        
    }
}
