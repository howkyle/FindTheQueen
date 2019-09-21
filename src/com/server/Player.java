package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player {


    private Socket _playerSocket;
    private String Role;
    private int points;
    private BufferedReader input;
    private PrintWriter output;
    private String suggestedQueenPosition;


    public Player(Socket socket){
        try{
            _playerSocket = socket;
            points=0;
            input = new BufferedReader(new InputStreamReader( _playerSocket.getInputStream()));
            output = new PrintWriter(_playerSocket.getOutputStream(),true);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public void AddPoint() {
        this.points++;
    }

    public int getPoints() {
        return points;
    }

    public String getSuggestedQueenPosition() {
        return suggestedQueenPosition;
    }

    public void setSuggestedQueenPosition(String suggestedQueenPosition) {
        this.suggestedQueenPosition = suggestedQueenPosition;
    }

    public Socket get_playerSocket() {
        return _playerSocket;
    }

   public String readLine() throws IOException {
        return this.input.readLine();
   }


    public void writeLine(String line){
        this.output.println(line);
    }

    public void switchRole(){
        if(this.Role.equals("SPOTTER")){
            this.Role = "DEALER";
            writeLine("You are now the dealer, where do you want to hide the queen? (1,2,3)");
        }
        else if(this.Role.equals("DEALER")){
            this.Role = "SPOTTER";
            writeLine("You are now the spotter, where do you think the queen is hidden? (1,2,3)");
        }
    }
}
