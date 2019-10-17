package com.server;

import java.net.*;
import  java.io.*;
import java.util.concurrent.Executors;


public class Server {

    private Socket  socket1, socket2;

    private Game game;
    private Player player1, player2;

    public Server(int portNumber){

        try(ServerSocket serverSocket = new ServerSocket(portNumber);){

            System.out.println("Waiting on player 1");
            while(true){
                try{
                    Socket testSocket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader( testSocket.getInputStream()));
                    PrintWriter output = new PrintWriter(testSocket.getOutputStream(),true);

                    boolean isValid = ValidateUser(input,output);
                    if (isValid){
//                        socket1 = testSocket;
                        player1 = new Player(testSocket, input, output);
                        break;

                    }else{
                        testSocket.close();
                    }
                }catch(Exception e){

                }
            }


//            socket1 = serverSocket.accept();

            System.out.println("Waiting on player 2");
            while(true){
                try{

                    Socket testSocket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader( testSocket.getInputStream()));
                    PrintWriter output = new PrintWriter(testSocket.getOutputStream(),true);

                    boolean isValid = ValidateUser(input, output);
                    if (isValid){
//                        socket2 = testSocket;
                        player2 = new Player(testSocket, input, output);
                        break;
                    }else{
                        testSocket.close();
                    }
                }catch (Exception e){

                }
            }
//            socket2 = serverSocket.accept();

            System.out.println("Both players connected");




//            game = new Game(socket1, socket2);

            game = new Game(player1, player2);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    public void CloseConnection(){

    }

    private boolean ValidateUser(BufferedReader input, PrintWriter output) throws IOException {
        String username;
        String password;


        output.println("Prove yourself, enter your username: ");
        username = input.readLine();
        output.println("Prove yourself, enter your password: ");
        password = input.readLine();

        if((username.equals("dannyboi") && password.equals("re@margh_shelled"))|| (username.equals("matty7") && password.equals("win&win99")
                )){
            output.println("Access");
            return true;
        }else{
            output.println("No Access");
            return false;
        }
    }
}
