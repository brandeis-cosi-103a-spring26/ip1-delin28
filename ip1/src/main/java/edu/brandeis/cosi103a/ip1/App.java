package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;
import java.util.Random;

/**
 * Dice Game: 2 players take turns rolling a die and accumulating scores
 */
public class App 
{
    private static final int NUM_TURNS = 10;
    private static final int DIE_SIDES = 6;
    private static final int MAX_REROLLS = 2;
    
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        displayWelcomeMessage();
        String player1Name = getPlayerName("Player 1", scanner);
        String player2Name = getPlayerName("Player 2", scanner);
        
        int player1Score = 0;
        int player2Score = 0;
        
        // Game loop - 10 turns per player
        for (int turn = 0; turn < NUM_TURNS; turn++) {
            displayTurnHeader(turn + 1);
            
            // Player 1's turn
            displayPlayerTurnStart(player1Name, player1Score);
            int player1RollValue = playerTurn(player1Name, scanner, random);
            player1Score += player1RollValue;
            displayPointsAdded(player1Name, player1RollValue, player1Score);
            
            // Player 2's turn
            displayPlayerTurnStart(player2Name, player2Score);
            int player2RollValue = playerTurn(player2Name, scanner, random);
            player2Score += player2RollValue;
            displayPointsAdded(player2Name, player2RollValue, player2Score);
        }
        
        // Determine and display winner
        displayGameEnd();
        displayFinalScores(player1Name, player1Score, player2Name, player2Score);
        displayWinner(player1Name, player1Score, player2Name, player2Score);
        
        scanner.close();
    }
    
    /**
     * Displays the welcome message
     */
    private static void displayWelcomeMessage() {
        System.out.println("Welcome to the Dice Game!");
    }
    
    /**
     * Gets a player's name from input
     */
    private static String getPlayerName(String playerLabel, Scanner scanner) {
        System.out.print("Enter " + playerLabel + " name: ");
        return scanner.nextLine().trim();
    }
    
    /**
     * Displays the turn header
     */
    private static void displayTurnHeader(int turnNumber) {
        System.out.println("\n=== Turn " + turnNumber + " ===");
    }
    
    /**
     * Displays the start of a player's turn with their current score
     */
    private static void displayPlayerTurnStart(String playerName, int currentScore) {
        System.out.println("\n" + playerName + "'s turn (Score: " + currentScore + ")");
    }
    
    /**
     * Displays points added to a player's score
     */
    private static void displayPointsAdded(String playerName, int pointsAdded, int newScore) {
        System.out.println(playerName + " added " + pointsAdded + " points. New score: " + newScore);
    }
    
    /**
     * Displays "GAME OVER" message
     */
    private static void displayGameEnd() {
        System.out.println("\n=== GAME OVER ===");
    }
    
    /**
     * Displays final scores for both players
     */
    private static void displayFinalScores(String player1Name, int player1Score, 
                                          String player2Name, int player2Score) {
        System.out.println(player1Name + " final score: " + player1Score);
        System.out.println(player2Name + " final score: " + player2Score);
    }
    
    /**
     * Determines and displays the winner
     */
    private static void displayWinner(String player1Name, int player1Score, 
                                      String player2Name, int player2Score) {
        String winner = determineWinner(player1Name, player1Score, player2Name, player2Score);
        System.out.println(winner);
    }
    
    /**
     * Determines the winner based on scores
     * @return String message indicating the winner or a tie
     */
    public static String determineWinner(String player1Name, int player1Score, 
                                          String player2Name, int player2Score) {
        if (player1Score > player2Score) {
            return player1Name + " wins!";
        } else if (player2Score > player1Score) {
            return player2Name + " wins!";
        } else {
            return "It's a tie!";
        }
    }
    
    /**
     * Handles a player's turn: rolls die, allows up to 2 re-rolls, returns final value
     */
    private static int playerTurn(String playerName, Scanner scanner, Random random) {
        int currentRoll = rollDie(random);
        displayRollResult(currentRoll);
        
        int rerollCount = 0;
        while (rerollCount < MAX_REROLLS) {
            if (shouldReroll(scanner)) {
                currentRoll = rollDie(random);
                displayRollResult(currentRoll);
                rerollCount++;
            } else {
                break;
            }
        } 
        
        if (rerollCount == MAX_REROLLS) {
            displayNoMoreRerolls();
        }
        
        return currentRoll;
    }
    
    /**
     * Asks the player if they want to re-roll and returns their choice
     */
    private static boolean shouldReroll(Scanner scanner) {
        System.out.print("Do you want to re-roll? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return isAffirmativeResponse(response);
    }
    
    /**
     * Checks if the user's response is affirmative (yes/y)
     * Returns false for negative responses or invalid input
     */
    public static boolean isAffirmativeResponse(String response) {
        String lowerResponse = response.toLowerCase().trim();
        if (lowerResponse.equals("yes") || lowerResponse.equals("y")) {
            return true;
        } else if (lowerResponse.equals("no") || lowerResponse.equals("n")) {
            return false;
        } else {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            return false;
        }
    }
    
    /**
     * Displays the result of a die roll
     */
    private static void displayRollResult(int rollValue) {
        System.out.println("Rolled: " + rollValue);
    }
    
    /**
     * Displays message when no more re-rolls are available
     */
    private static void displayNoMoreRerolls() {
        System.out.println("No more re-rolls available.");
    }
    
    /**
     * Rolls a 6-sided die and returns the value (1-6)
     */
    public static int rollDie(Random random) {
        return random.nextInt(DIE_SIDES) + 1;
    }
}
