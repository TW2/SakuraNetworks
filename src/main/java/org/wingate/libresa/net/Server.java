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

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author util2
 */
public class Server {
    private Thread thread;
    private Handler handler;

    public Server() {
    }
    
    public static Server create(Node node){
        ServerSocket serverSocket;
        try{
            Server server = new Server();
            serverSocket = new ServerSocket(node.getPort());
            
            server.handler = new Handler(serverSocket);
            server.thread = new Thread(server.handler);
            server.thread.start();
            return server;
        }catch(IOException ex){
            System.err.println("Hep!");
        }
        return null;
    }
    
    public void stop(Thread th){
        th.interrupt();
    }

    public Thread getThread() {
        return thread;
    }

    public Handler getHandler() {
        return handler;
    }
}
