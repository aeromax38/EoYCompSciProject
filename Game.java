/*
Game class:

In this game, the player will choose the number of sides to a die that they will then try to guess the number of a roll. 

There are 2 aspects to the game, the guessing, and statistics of it. Guessing is relatively simple we would ask for an integer of a die, and randomly find a number comparing it to what the player guessed. If he guesses correctly he wins, if not, he loses. 

All of this information for each player is tracked along the way and at any time, they are able to find their statistics. This means that they will know their predicted chance of guessing compared to what they actually guessed correctly. The higher the actual versus predicted is the higher score for each player. Ultimately, each player becomes a key and the score is a value. 

We will use hashmaps for assigning each person to a key and their score to a value, so once a user asks for their score it will constantly be updated. We will use a try catch in case users input a string not integer. Use a switch statement to understand what the user wants to do: play, statistics, quit. All of this is in a loop so that the player can keep guessing and it would only break once a quit statement is executed. 

We will have String and Integers to hold the guesses, actual values, usernames, and scores. We will use scanners to find the users input and guesses, and randint to compare their guess. 

I don't think we will create our own methods, but once the time comes we will and add it to this list. 

Authors: Max Richardson, Manish Nadendla, and Ryan Choudhury

Changelog:
5/12: Created
5/18: Changed game description
5/19: HashMap and switch command implementation (stat system)
5/20: Implementation of try/catch 
5/23: Validating user input with no error messages
6/2: Increased ease of use with new startup instructions & better-looking leaderboard
*/

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Game {
  HashMap<String, Integer> userStats;
  private String userName; // Current user playing the game
  private int sides; // Sides of dice
  private int guess; // User guesses
  private boolean clear = false;
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
      String command = Utils.inputStr("\nOptions:\nplay: starts the game \nleaderboard: shows score of all current players \nstats: gives your stats \nclear: clears leaderboard \nrules: explains the scoring system and instructions of the game \nquit: ends game \nWhat do you want to do? ");
    
      switch (command) {  
          
        case "play": // Play the game
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
        /*
          if (userName == null || userStats.get(userName) == null)
            System.out.println ("You haven't played yet!"); 
          else */
          for(String name: userStats.keySet()){
            int score = userStats.get(name);
            System.out.println(name + ": " + score);
          }
          break;

        case "stats": // Prints current user's highscore for current session
          if (userName == null || userStats.get(userName) == null)
            System.out.println ("You haven't played yet!");
          else
            System.out.println("Your highscore is: " + userStats.get(userName));
          break;
          
        case "clear": // Reset leaderboard
          if (userName == null || userStats.get(userName) == null)
            System.out.println ("You haven't played yet!");
          else{
            userStats.clear();
            System.out.println("stats have been reset:" + userStats);
          }
           clear = true;
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
          return;
          
        default:
          System.out.println("Please choose an option.");
          break;
      }
    }
  }
}