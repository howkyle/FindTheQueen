package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Game {

//    private Socket _player1, _player2;
    private PrintWriter output;
    private BufferedReader input;
    private String serverOutput, clientInputLine;
    private int spotterGuess,  queenPosition; //hardcoded position for queen
    private int roundCounter = 0, rounds = 3;
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
            roundCounter++;
//            System.out.println(clientInputLine);
            _player1.setSuggestedQueenPosition(_player1.readLine());
            _player2.setSuggestedQueenPosition(_player2.readLine());

            System.out.println("Player 1 says: "+_player1.getSuggestedQueenPosition());
            System.out.println("Player 2 says: "+_player2.getSuggestedQueenPosition());


            queenPosition = GetDealerPosition(_player1,_player2);
            spotterGuess = GetSpotterGuess(_player1, _player2);

            if (CheckIfCorrectGuess(spotterGuess)){
                SpotterSuccess(GetSpotter(_player1, _player2));
                DealerFailure(GetDealer(_player1, _player2));
            }else{
                DealerSuccess(GetDealer(_player1, _player2));
                SpotterFailure(GetSpotter(_player1, _player2));
            }



            System.out.println("End of round "+roundCounter);
            if(clientInputLine == "lol" || roundCounter == rounds){
                DetermineWinner(_player1, _player2);

                _player1.get_playerSocket().close();
                _player2.get_playerSocket().close();

                break;
            }
            _player1.switchRole();
            _player2.switchRole();

        }



    }

    private boolean CheckIfCorrectGuess(int spotterGuess){
        return queenPosition == spotterGuess;
    }

    private void AssignRole(Player player1, Player player2){

        //should be random later
        Random rand = new Random();
        int randInt = rand.nextInt(20);

        if(randInt%2== 0){
            player1.setRole("SPOTTER");
            player1.writeLine("You are spotting, find the queen (1,2,3)");

            player2.setRole("DEALER");
            player2.writeLine("You are dealing, hide your queen (1,2,3)");
        }else{
            player1.setRole("DEALER");
            player1.writeLine("You are dealing, hide your queen (1,2,3)");

            player2.setRole("SPOTTER");
            player2.writeLine("You are spotting, find the queen (1,2,3)");

        }



    }

    private Player GetDealer(Player player1, Player player2){
        if(player1.getRole().equals("DEALER")){
            return player1;
        }else{
            return player2;
        }
    }

    private Player GetSpotter(Player player1, Player player2){
        if(player1.getRole().equals("SPOTTER")){
            return player1;
        }else{
            return player2;
        }
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
            guess = Integer.parseInt(player1.getSuggestedQueenPosition());
        }
        else{
            guess = Integer.parseInt(player2.getSuggestedQueenPosition());
        }

        return guess;
    }


    private int GetDealerPosition(Player player1, Player player2) throws IOException {
        int position;

        if(player1.getRole().equals("DEALER")){
            position = Integer.parseInt(player1.getSuggestedQueenPosition());
        }
        else{
            position = Integer.parseInt(player2.getSuggestedQueenPosition());
        }

        return position;
    }
    private void DetermineWinner(Player player1, Player player2){
        if(player1.getPoints()>player2.getPoints()){
            player1.writeLine("Victory...thanks for playing");
            player2.writeLine("Defeat...thanks for playing");

        }
        else{
            player1.writeLine("Defeat...thanks for playing");
            player2.writeLine("Victory...thanks for playing");
        }

    }




}
