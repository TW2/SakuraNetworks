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

/**
 *
 * @author util2
 */
public class ChatMessage {
    private String owner = "Anonymous";
    private final ChatImage image;
    private final String message;

    public ChatMessage(String message) {
        this.image = null;
        this.message = message;
    }
    
    public ChatMessage(ChatImage image, String message) {
        this.image = image;
        this.message = message;
    }
    
    public ChatMessage(String imagePath, String message) {
        this.image = new ChatImage(imagePath);
        this.message = message;
    }

    public ChatImage getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
}
