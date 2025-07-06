# SakuraNetworks
Sub layer of classic Internet
-
Just French here cause in English it may be a wrong explanation.

Le Sakura Networks se voudra être un réseau ouvert, opensource et pouvant être privé et sécurisé selon votre législation mais de prime abord, il est sans loi et devra le rester. Le but de ce réseau étant de s'affranchir des lois de l'Internet classique au maximum. Le Sakura Networks utilise des fichiers de type XML afin de générer ses pages, proches de HTML, et pouvant utilisé du JavaScript avec certains composants. Son code est en Java et il est développé comme un programme unique en P2P non centralisé. Libre à ceux voulant faire des plugins de faire des plugins pour d'autres types de Navigateur. Pour les pages Alexia (XML), le logiciel utilise un JTextPane pouvant donc accueillir des composants Java (JButton, JPanel... ).

Le logiciel est encore inutilisable et en test en réseau local. Pour le faire fonctionner, il vous faut le JAR et dans le même dossier, un dossier nommé 'alexia' contenant un ou plusieurs fichiers XML et un dossier 'nodes' contenant un fichier mynode.txt avec votre configuration, et des autres fichiers pour les nodes que vous voudriez atteindre si applications, comme ici chat, développées.

Structure d'un fichier Alexia (fichier XML) :
```xml
<?xml version="1.0" encoding="UTF-8"?>
<alexia>
    <head>
        <title>Welcome to Sakura Networks</title>
    </head>
    <body>
        <text>Chat</text>
        <br />
        <text>Participate to the discussion:</text>
        <br />
        <chat />
        <br />
        <draw>
            console.log('Hello at drawing');
            draw.showOK();
            draw.setBackgroundColor(255, 0, 170);
            draw.addLine(0, 0, 200, 300, 255, 255, 255, 6.5);
        </draw>
    </body>
</alexia>
```

Structure d'un fichier node :
```txt
nodetype public
ip 192.168.1.2
port 17777
```

You can join me on Discord to speak or idle, in English or French (cause I'm a half white black Frenchy).

[![Discord](https://github.com/user-attachments/assets/99ec6536-7624-41c1-afd1-7993fc4a1e25)](https://discord.gg/ef8xvA9wsF)
