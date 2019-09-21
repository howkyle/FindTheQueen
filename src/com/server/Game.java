package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Game {

//    private Socket _player1, _player2;
    private PrintWriter output;
    private BufferedReader input;
    private String serverOutput, clientInputLine;
    private int spotterGuess,  queenPosition = 2; //hardcoded position for queen
    private int roundCounter = 0, rounds = 5;
    private Player _player1, _player2;



    public Game(Socket player1, Socket player2) throws IOException {
        _player1 = new Player(player1);
        _player2 = new Player(player2);

//        output = new PrintWriter(_player1.get_playerSocket().getOutputStream(),true);
//        input = new BufferedReader(new InputStreamReader( _player1.get_playerSocket().getInputStream()));


        _player1.writeLine("Welcome to find the queen, the game begins");
        _player2.writeLine("Welcome to find the queen, the game begins");

        AssignRole(_player1, _player2);
//        while((clientInputLine= _player1.readLine())!= null){
        while(true){
            //while the client is communicating

//            System.out.println(clientInputLine);
            String player1Input = _player1.readLine();
            String player2Input = _player2.readLine();

            System.out.println("Player 1 says: "+player1Input);
            System.out.println("Player 2 says: "+player2Input);


            spotterGuess = GetSpotterGuess(_player1, _player2);

            if (CheckIfCorrectGuess(spotterGuess)){
                SpotterSuccess(_player1);
            }else{
                SpotterFailure(_player1);
            }
            roundCounter++;
            _player1.switchRole();
            _player2.switchRole();

            if(clientInputLine == "lol" || roundCounter == rounds){
                DetermineWinner(_player1, _player2);
//                break;
            }
        }



    }

    private boolean CheckIfCorrectGuess(int spotterGuess){
        return queenPosition == spotterGuess;
    }

    private void AssignRole(Player player1, Player player2){

        //should be random later

        player1.setRole("SPOTTER");
        player1.writeLine("You are spotting, find the queen (1,2,3)");

        player2.setRole("DEALER");
        player2.writeLine("You are dealing, hide your queen (1,2,3)");

    }

    private void DealerMessage(){

    }

    private void SpotterSuccess(Player spotter){
       spotter.writeLine("You've spotted the queen, you win this round");
       spotter.AddPoint();
    }

    private void SpotterFailure(Player spotter){
        spotter.writeLine("You've failed to spot the queen, you lose this round");
    }

    private void DealerSuccess(Player dealer){
        dealer.writeLine("You've successfully hidden the queen, you win this round");
        dealer.AddPoint();
    }

    private void DealerFailure(Player dealer){
        dealer.writeLine("You've failed to hide the queen, you lose this round");
    }

    private int GetSpotterGuess(Player player1, Player player2) throws IOException {
        int guess;
        if(player1.getRole().equals("SPOTTER")){
            guess = Integer.parseInt(player1.readLine());
        }
        else{
            guess = Integer.parseInt(player2.readLine());
        }

        return guess;
    }

    private void DetermineWinner(Player player1, Player player2){
        if(player1.getPoints()>player2.getPoints()){
            player1.writeLine("You win");
            player2.writeLine("You lose");
        }
        else{
            player1.writeLine("You lose");
            player2.writeLine("You win");
        }
    }


}
