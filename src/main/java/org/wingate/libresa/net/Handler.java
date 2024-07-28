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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import org.wingate.libresa.serve.Protocol;
import org.wingate.libresa.ui.ChatPanel;

/**
 *
 * @author util2
 */
public class Handler implements Runnable {
    private final ServerSocket serverSocket;
    private final Set<ChatPanel> chatPanels = new HashSet<>();

    public Handler(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    @Override
    public void run() {
        try{
            while(true){
                Socket socket = serverSocket.accept();
                
                /*
                Arrive ici tout ce qui entre (read area)
                */
                InputStream in = socket.getInputStream();
                try(DataInputStream dis = new DataInputStream(in);){
                    Protocol p = Protocol.search(dis.readUTF());
                    switch(p){
                        case ChatMessage -> {
                            // Arrivé (Départ dans ChatPanel)
                            String uniqueName = dis.readUTF();
                            for(ChatPanel chat : chatPanels){
                                if(uniqueName.equalsIgnoreCase(chat.getUniqueName())){
                                    ChatMessage cm = new ChatMessage();
                                    cm.get(dis);
                                    chat.writeMessage(cm);
                                    break;
                                }
                            }
                        }
                    }
                }
            }            
        }catch(IOException exc){
            System.err.println("Error in Handler class:\n" + exc.getMessage());
        }
    }

    public Set<ChatPanel> getChatPanels() {
        return chatPanels;
    }
    
}
