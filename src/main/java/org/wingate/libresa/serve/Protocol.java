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
package org.wingate.libresa.serve;

/**
 *
 * @author util2
 */
public enum Protocol {
    Nothing("nothing"),
    ChatMessage("chat-message");
    
    String type;

    private Protocol(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public static Protocol search(String type){
        Protocol protocol = Nothing;
        
        for(Protocol p : values()){
            if(p.getType().equals(type)){
                protocol = p;
                break;
            }
        }
        
        return protocol;
    }
}
