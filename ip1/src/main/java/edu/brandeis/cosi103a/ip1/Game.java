package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main game controller for Dominion.
 * 
 * The Game class manages the overall game flow including turns, rounds,
 * player actions, purchasing, and production. By default, it creates
 * computer-controlled players that automatically play the game.
 * 
 * @author COSI 103a Students
 * @version 1.0
 */
public class Game {
    private List<Player> players;
    private Market market;
    private int roundCount;
    private static final int HAND_SIZE = 5;
    private static final int GAME_ROUNDS = 10;
    
    /**
     * Constructs a Game with the specified number of computer players.
     * 
     * @param playerCount the number of computer players (recommended 2-4)
     */
    public Game(int playerCount) {
        this.players = new ArrayList<>();
        this.market = new Market();
        this.roundCount = 1;
        
        // Initialize computer players
        for (int i = 0; i < playerCount; i++) {
            players.add(new ComputerPlayer("Computer " + (i + 1)));
        }
    }
    
    /**
     * Starts the game and runs the main game loop.
     * Displays welcome message, plays all rounds, and displays final results.
     */
    public void start() {
        System.out.println("=== Welcome to Dominion ===");
        System.out.println("Players: " + players.size());
        System.out.println("Rounds: " + GAME_ROUNDS);
        System.out.println();
        
        // Main game loop
        for (int round = 1; round <= GAME_ROUNDS; round++) {
            playRound();
        }
        
        endGame();
    }
    
    /**
     * Plays a single round of the game.
     * Each player takes a turn, then production is resolved.
     */
    private void playRound() {
        System.out.println("\n========== ROUND " + roundCount + " ==========");
        roundCount++;
        
        // Each player takes a turn
        for (int i = 0; i < players.size(); i++) {
            playTurn(players.get(i));
        }
        
        // Resolve production
        resolveProduction();
    }
    
    /**
     * Plays a single player's turn.
     * For computer players, automatically plays cards and buys from market.
     * 
     * @param player the player whose turn it is
     */
    private void playTurn(Player player) {
        System.out.println("\n--- " + player.getName() + "'s Turn ---");
        
        // Draw cards
        player.drawCards(HAND_SIZE);
        System.out.println(player.getName() + " drew " + HAND_SIZE + " cards.");
        
        // If computer player, automate actions
        if (player instanceof ComputerPlayer) {
            ComputerPlayer computerPlayer = (ComputerPlayer) player;
            
            // Auto play cards
            int cardsPlayed = computerPlayer.autoPlayCards();
            System.out.println(computerPlayer.getName() + " played " + cardsPlayed + " cards.");
            System.out.println("Resources after playing: " + computerPlayer.getResources());
            
            // Auto buy cards
            int cardsBought = computerPlayer.autoBuyCards(market);
            System.out.println(computerPlayer.getName() + " bought " + cardsBought + " cards.");
        } else {
            // For human players (if any)
            playerActions(player);
            buyPhase(player);
        }
        
        // End turn
        player.endTurn();
        System.out.println("Turn ended. " + player);
    }
    
    /**
     * Handles the action phase where a player can play cards from their hand.
     * 
     * @param player the player taking actions
     */
    private void playerActions(Player player) {
        Scanner scanner = new Scanner(System.in);
        try {
            boolean actioningComplete = false;
            
            while (!actioningComplete) {
                List<Card> hand = player.getHand();
                
                System.out.println("\nYour hand:");
                for (int i = 0; i < hand.size(); i++) {
                    System.out.println(i + ": " + hand.get(i));
                }
                System.out.println("Current Resources: " + player.getResources());
                System.out.println("\nOptions: [0-" + (hand.size() - 1) + "] to play card, [b] to buy, [e] to end actions");
                System.out.print("Choice: ");
                
                String input = scanner.nextLine().trim();
                
                if ("e".equalsIgnoreCase(input)) {
                    actioningComplete = true;
                } else if ("b".equalsIgnoreCase(input)) {
                    actioningComplete = true;
                } else {
                    try {
                        int cardIndex = Integer.parseInt(input);
                        if (cardIndex >= 0 && cardIndex < hand.size()) {
                            Card card = hand.get(cardIndex);
                            player.playCard(card);
                            System.out.println("Played: " + card.getName());
                        } else {
                            System.out.println("Invalid card index.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                }
            }
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Handles the buying phase where a player can purchase cards from the market.
     * 
     * @param player the player buying cards
     */
    private void buyPhase(Player player) {
        Scanner scanner = new Scanner(System.in);
        try {
            boolean buyingComplete = false;
            
            while (!buyingComplete) {
                market.displayMarket();
                System.out.println("\nYour Resources: " + player.getResources());
                System.out.println("\nOptions: [0-5] to buy card, [d] to done buying");
                System.out.print("Choice: ");
                
                String input = scanner.nextLine().trim();
                
                if ("d".equalsIgnoreCase(input)) {
                    buyingComplete = true;
                } else {
                    try {
                        int cardIndex = Integer.parseInt(input);
                        Card card = market.buyCard(cardIndex);
                        
                        if (card != null && player.getResources() >= card.getCost()) {
                            player.buyCard(card);
                            System.out.println("Bought: " + card.getName());
                            System.out.println("Remaining Resources: " + player.getResources());
                        } else if (card != null) {
                            System.out.println("Not enough resources! Cost: " + card.getCost() + 
                                             ", You have: " + player.getResources());
                        } else {
                            System.out.println("Invalid card index.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input.");
                    }
                }
            }
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Resolves the production phase for all players.
     * Each player gains resources from their production and active automation cards.
     */
    private void resolveProduction() {
        System.out.println("\n--- Production Phase ---");
        for (Player player : players) {
            int producedResources = player.getProduction();
            int automationCount = player.getActiveAutomation().size();
            producedResources += automationCount; // Each automation produces 1 resource
            player.addResources(producedResources);
            System.out.println(player.getName() + " produced " + producedResources + " resources.");
        }
    }
    
    /**
     * Handles the end of game logic.
     * Calculates final scores, displays rankings, and congratulates the winner.
     */
    private void endGame() {
        System.out.println("\n========== GAME END ==========");
        
        // Calculate final scores
        for (Player player : players) {
            int score = player.getResources() + (player.getActiveAutomation().size() * 5);
            player.setScore(score);
        }
        
        // Display final rankings
        System.out.println("\nFinal Rankings:");
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        
        int rank = 1;
        for (Player player : players) {
            System.out.println(rank + ". " + player);
            rank++;
        }
        
        System.out.println("\nThanks for playing Dominion!");
    }
    
    /**
     * Main method to launch the game with two computer players.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create and start game with 2 computer players
        Game game = new Game(2);
        game.start();
    }
}
