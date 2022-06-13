/*
Game class:

In this game, the player will choose the number of sides to a die that they will then try to guess the number of a roll. The score is calculated using a simple formula based on the difficulty and proximity of the guess. The user can view both their personal stats for the current session and the all-time leaderboard, as well as view the rules/explanation.

Authors: Max Richardson, Manish Nadendla, and Ryan Choudhury

Changelog:
5/12: Created
5/18: Changed game description
5/19: HashMap and switch command implementation (stat system)
5/20: Implementation of try/catch 
5/23: Validating user input with no error messages
6/2: Increased ease of use with new startup instructions & better-looking leaderboard
6/9: Implemented serialization with persistent leaderboard & code simplification
6/12: Fixed "clear" command to persist between sessions
*/

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Game {
  HashMap<String, Integer> userStats;
  private String userName; // Current user playing the game
  private int sides; // Sides of dice
  private int guess; // User guesses
  private boolean clear = false; // for clear command
  private boolean clearaction = false; // for clear command
  
  public Game() {
    LeaderboardState state = LeaderboardState.restore();
    if (state == null)
      userStats = new HashMap<String, Integer>();
    else
      userStats = state.leaderboard;
  }
  
  public void play() {
    System.out.println("\nWelcome to Dice Guesser! If this is your first game, please enter \"rules\"!"); // add intro letter here
    while (true) {
      String command = Utils.inputStr("\nOptions:\nplay: starts the game \nleaderboard: shows score of all current players \nclear: clears leaderboard \nrules: explains the scoring system and instructions of the game \nquit: ends game \nWhat do you want to do? ");
    
      switch (command) {  
          
        case "play": // Play the game
          clear=false;
          clearaction=false;
          userName = Utils.inputStr ("Welcome to Dice Guesser. Please enter your username: ");
          if (userStats.containsKey(userName)){
            System.out.println("Welcome back! Your highscore was " + userStats.get(userName));
          }
          else{
            userStats.put(userName, null);
            System.out.println("Welcome!");
          }
          sides = Utils.inputNum ("How many sides should the dice be? ");
          guess = Utils.inputNum ("What is your guess? ");
          int actualvalue = (int) (Math.random() * sides) + 1;
          System.out.println("The actual number is: " + actualvalue);
          int distance_from_score = (Math.abs(actualvalue-guess)+1);
          if(distance_from_score==0)
            distance_from_score=1;
          int actualScore = sides/distance_from_score;
          if((actualvalue == guess)){
            System.out.println("Congrats, you guessed it! Your score is: " + sides);
          }
          else {
            System.out.println("You are wrong, you were " + distance_from_score+" far away, so your score is: " + actualScore);
          } 
          
          if (userStats.get(userName)== null){
            userStats.put(userName, actualScore);
          }
          int highscore = userStats.get(userName);
          if(actualScore>highscore){
            userStats.put(userName, actualScore);
            System.out.println("New highscore!");
          }
          break;
          
        case "leaderboard": // Prints leaderboard
          if (clear == true)
            System.out.println ("You haven't played yet!"); 
          else
            for(String name: userStats.keySet()){
            int score = userStats.get(name);
            System.out.println(name + ": " + score);
          }
          break;
          
        case "clear": // Reset leaderboard
          userStats.clear();
          System.out.println("Stats have been reset.");
          clear = true;
          clearaction = true;
          if(clearaction==true){
            LeaderboardState state = new LeaderboardState();
            state.delete();
          }
          break;
          
        case "rules": // Rules of the game
          System.out.println("In this game, you will choose the number of sides of a die. You will then try to guess the number of the roll. The larger the die, and the closer to the roll you guess, the higher your score. Have fun!");
          break;
          
        case "quit": // Quit
          System.out.println ("Thanks for playing!");
          if(clear==false){
            LeaderboardState state = new LeaderboardState();
            state.leaderboard = userStats;
            state.save();
          }
          clear=false;
          clearaction=false;
          return;
          
        default:
          System.out.println("Please choose an option.");
          break;
      }
    }
  }
}