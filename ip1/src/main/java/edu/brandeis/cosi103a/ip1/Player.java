package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Automation game.
 * 
 * A Player maintains their own deck, hand, resources, and automation cards.
 * Players draw cards, play them to generate resources, buy new cards from
 * the market, and accumulate automation cards for passive income.
 * 
 * @author COSI 103a Students
 * @version 1.0
 */
public class Player {
    private String name;
    private int resources; // Money/currency
    private int production; // Production per turn
    private List<Card> hand;
    private List<Card> playedCards;
    private List<Card> activeAutomation; // Cards that provide ongoing effects
    private Deck deck;
    private int score;
    
    /**
     * Constructs a Player with the given name.
     * Initializes with a deck containing 10 basic starter cards.
     * 
     * @param name the player's name
     */
    public Player(String name) {
        this.name = name;
        this.resources = 0;
        this.production = 0;
        this.hand = new ArrayList<>();
        this.playedCards = new ArrayList<>();
        this.activeAutomation = new ArrayList<>();
        this.deck = new Deck();
        this.score = 0;
        initializeDeck();
    }
    
    private void initializeDeck() {
        // Start with basic cards
        for (int i = 0; i < 5; i++) {
            deck.addCard(new Card("Worker", "PRODUCTION", 0, "Produces 1 resource", 1));
        }
        for (int i = 0; i < 5; i++) {
            deck.addCard(new Card("Resource", "RESOURCE", 0, "Worth 1 resource", 1));
        }
        deck.shuffle();
    }
    
    /**
     * Draws cards from the player's deck into their hand.
     * 
     * @param count the number of cards to draw
     */
    public void drawCards(int count) {
        List<Card> drawn = deck.drawCards(count);
        hand.addAll(drawn);
    }
    
    /**
     * Plays a card from the player's hand.
     * Removes the card from hand and applies its effects:
     * - RESOURCE cards add their value to resources
     * - PRODUCTION cards increase production
     * - AUTOMATION cards are added to active automation
     * 
     * @param card the card to play
     */
    public void playCard(Card card) {
        if (hand.contains(card)) {
            hand.remove(card);
            playedCards.add(card);
            
            // Apply card effects
            if ("RESOURCE".equals(card.getType())) {
                resources += card.getValue();
            } else if ("PRODUCTION".equals(card.getType())) {
                production += card.getValue();
            } else if ("AUTOMATION".equals(card.getType())) {
                activeAutomation.add(card);
            }
        }
    }
    
    /**
     * Buys a card from the market if the player has enough resources.
     * The card is added to the player's deck.
     * 
     * @param card the card to buy
     */
    public void buyCard(Card card) {
        if (resources >= card.getCost()) {
            resources -= card.getCost();
            deck.addCard(card);
        }
    }
    
    /**
     * Ends the player's turn.
     * - Applies production from active automation cards
     * - Discards all played cards
     * - Discards remaining hand cards
     */
    public void endTurn() {
        // Apply production from active automation
        for (Card card : activeAutomation) {
            if ("AUTOMATION".equals(card.getType())) {
                resources += 1; // Each automation produces 1 resource per turn
            }
        }
        
        // Discard played cards
        deck.discardCards(playedCards);
        playedCards.clear();
        
        // Discard hand
        deck.discardCards(hand);
        hand.clear();
    }
    
    /**
     * Gets the player's name.
     * 
     * @return the player's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the player's current resource count.
     * 
     * @return the number of resources
     */
    public int getResources() {
        return resources;
    }
    
    /**
     * Sets the player's resource count.
     * 
     * @param resources the new resource count
     */
    public void setResources(int resources) {
        this.resources = resources;
    }
    
    /**
     * Adds resources to the player's total.
     * 
     * @param amount the number of resources to add
     */
    public void addResources(int amount) {
        this.resources += amount;
    }
    
    /**
     * Gets the player's production value.
     * 
     * @return the production value
     */
    public int getProduction() {
        return production;
    }
    
    /**
     * Gets a copy of the player's hand.
     * 
     * @return a list of cards in the player's hand
     */
    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }
    
    /**
     * Gets a copy of the player's active automation cards.
     * 
     * @return a list of active automation cards
     */
    public List<Card> getActiveAutomation() {
        return new ArrayList<>(activeAutomation);
    }
    
    /**
     * Gets the player's current score.
     * 
     * @return the player's score
     */
    public int getScore() {
        return score;
    }
    
    /**
     * Adds points to the player's score.
     * 
     * @param points the number of points to add
     */
    public void addScore(int points) {
        this.score += points;
    }
    
    /**
     * Sets the player's score.
     * 
     * @param score the new score
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * Returns a string representation of the player.
     * 
     * @return a formatted string with player details
     */
    @Override
    public String toString() {
        return String.format("%s - Resources: %d, Production: %d, Score: %d, Hand: %d cards", 
            name, resources, production, score, hand.size());
    }
}
