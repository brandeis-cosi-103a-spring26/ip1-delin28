package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the market/shop where players can buy cards.
 * 
 * The Market maintains a pool of available cards for purchase and
 * a deck of remaining cards. When a card is purchased, it is replaced
 * with a new card from the deck.
 * 
 * @author COSI 103a Students
 * @version 1.0
 */
public class Market {
    private List<Card> availableCards;
    private Deck marketDeck;
    private static final int MARKET_SIZE = 6;
    
    /**
     * Constructs a Market and initializes it with cards.
     */
    public Market() {
        this.availableCards = new ArrayList<>();
        this.marketDeck = new Deck();
        initializeMarket();
        refillMarket();
    }
    
    /**
     * Initializes the market by populating the market deck.
     */
    private void initializeMarket() {
        // Add various cards to the market deck
        addCardsToMarketDeck();
    }
    
    /**
     * Adds various card types to the market deck for purchase.
     */
    private void addCardsToMarketDeck() {
        // Production cards
        for (int i = 0; i < 10; i++) {
            marketDeck.addCard(new Card("Basic Worker", "PRODUCTION", 1, "Produces 1 resource per turn", 1));
        }
        
        // Automation cards
        for (int i = 0; i < 8; i++) {
            marketDeck.addCard(new Card("Simple Automation", "AUTOMATION", 3, "Produces 1 resource per turn automatically", 1));
        }
        
        for (int i = 0; i < 5; i++) {
            marketDeck.addCard(new Card("Advanced Automation", "AUTOMATION", 6, "Produces 2 resources per turn automatically", 2));
        }
        
        // Upgrade cards
        for (int i = 0; i < 6; i++) {
            marketDeck.addCard(new Card("Efficiency Upgrade", "UPGRADE", 4, "Increases production by 1", 0));
        }
        
        for (int i = 0; i < 4; i++) {
            marketDeck.addCard(new Card("Premium Automation", "AUTOMATION", 10, "Produces 3 resources per turn automatically", 3));
        }
        
        marketDeck.shuffle();
    }
    
    /**
     * Refills the market with cards from the market deck until it reaches
     * the maximum market size.
     */
    public void refillMarket() {
        while (availableCards.size() < MARKET_SIZE) {
            Card card = marketDeck.drawCard();
            if (card == null) {
                break;
            }
            availableCards.add(card);
        }
    }
    
    /**
     * Allows a player to buy a card from the market at the given index.
     * The market is refilled after the purchase.
     * 
     * @param index the index of the card to buy
     * @return the purchased card, or null if the index is invalid
     */
    public Card buyCard(int index) {
        if (index >= 0 && index < availableCards.size()) {
            Card card = availableCards.remove(index);
            refillMarket();
            return card;
        }
        return null;
    }
    
    /**
     * Gets a copy of the list of available cards for purchase.
     * 
     * @return a list of available cards
     */
    public List<Card> getAvailableCards() {
        return new ArrayList<>(availableCards);
    }
    
    /**
     * Displays the current market to the console.
     */
    public void displayMarket() {
        System.out.println("\n=== MARKET ===");
        for (int i = 0; i < availableCards.size(); i++) {
            System.out.println(i + ": " + availableCards.get(i));
        }
    }
}
