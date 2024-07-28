package org.wingate.libresa.net;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

/**
 * Définit un noeud du réseau
 * @author util2
 */
public class Node {
    private NodeType nodeType;
    private String ip;
    private int port;
    
    public Node(String ip) {
        this(NodeType.Public, ip, 17777);
    }
    
    public Node(NodeType nodeType, String ip, int port) {
        this.nodeType = nodeType;
        this.ip = ip.isEmpty() ? "localhost" : ip;
        this.port = port;
    }
    
    public static Node create(String path){
        try(FileReader fr = new FileReader(path);
                BufferedReader br = new BufferedReader(fr);){
            String line;
            NodeType nodeType = NodeType.Public;
            String ip = "localhost";
            int port = 17777;
            while((line = br.readLine()) != null){
                if(line.startsWith("nodetype ")){
                    String type = line.substring("nodetype ".length()).toLowerCase();
                    switch(type){
                        case "public" -> { nodeType = NodeType.Public; }
                        case "private" -> { nodeType = NodeType.Private; }
                        case "drm" -> {  nodeType = NodeType.DRM; }
                        default -> { nodeType = NodeType.Public; }
                    }
                }else if(line.startsWith("ip ")){
                    ip = line.substring("ip ".length());
                }else if(line.startsWith("port ")){
                    port = Integer.parseInt(line.substring("port ".length()));
                }
            }
            return new Node(nodeType, ip, port);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
}
