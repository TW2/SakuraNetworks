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
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author util2
 */
public class Client {
    
    public Client() {
    }
    
    public Socket connect(Node node) throws IOException{
        if(node == null) return null;
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(node.getIp(), node.getPort()), 1500);
        return socket;
    }
    
    public void close(Socket socket) throws IOException{
        if(socket != null) socket.close();
    }
}
