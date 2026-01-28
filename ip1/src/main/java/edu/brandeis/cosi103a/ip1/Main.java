package edu.brandeis.cosi103a.ip1;

/**
 * Main entry point for Dominion.
 * 
 * This class initializes and starts the game with a default of 2 players.
 * Dominion is a card-based strategy game where players build their deck
 * to generate resources and compete for the highest score.
 * 
 * @author COSI 103a Students
 * @version 1.0
 */
public class Main {
    
    /**
     * Main method to start the game.
     * 
     * @param args command line arguments (not used in this version)
     */
    public static void main(String[] args) {
        Game game = new Game(2);
        game.start();
    }
}
