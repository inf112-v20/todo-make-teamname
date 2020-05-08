# RoboRally

by team **todo-make-teamname.**

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e769a8e13a844471bf7bd85a1dbff673)](https://www.codacy.com/gh/inf112-v20/todo-make-teamname?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inf112-v20/todo-make-teamname&amp;utm_campaign=Badge_Grade)

## How to run
*   Git clone https://github.com/inf112-v20/todo-make-teamname.git
*   Open in IntelliJ
*   Need internet connection.
*   To start the game - run *Main* in /src/main/java/inf112.skeleton.app/main/Main.java
*   Navigate menu with arrow keys, one player needs to host the game.
*   If hosting, choose Host Game option, then you get your server IP, send this to your friends who are joining.
    When you have sent them the IP press enter to continue.
*   If joining a game, choose Join Game Option, enter the IP and type in your name in the box. Press enter to mark yourself as ready to start the game.
*   You will all now be in a lobby screen and when everybody is ready, the Host can start the game.
    
## Game menu
*   Host Game - Choose which map to play and hosts a game session. Shows your IP.
*   Join Game - Type the host's IP to connect to his session
*   Options - Change screen resolution
*   Quit - Exit the game

## Known bugs
*   Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.
*   An InterruptedExepction is thrown when the game is closed.
