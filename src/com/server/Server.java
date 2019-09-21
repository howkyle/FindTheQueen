package com.server;

import java.net.*;
import  java.io.*;
import java.util.concurrent.Executors;


public class Server {

    private Socket  socket1, socket2;

    private Game game;

    public Server(int portNumber){

        try(ServerSocket serverSocket = new ServerSocket(portNumber);){

            System.out.println("Waiting on player 1");
            socket1 = serverSocket.accept();
            System.out.println("Waiting on player 2");
            socket2 = serverSocket.accept();

            System.out.println("Both players connected");




            game = new Game(socket1, socket2);


        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public void CloseConnection(){

    }
}
