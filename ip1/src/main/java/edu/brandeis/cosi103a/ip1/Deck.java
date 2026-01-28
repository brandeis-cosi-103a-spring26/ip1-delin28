package edu.brandeis.cosi103a.ip1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of cards with draw and discard functionality.
 * 
 * The Deck manages a draw pile and a discard pile. When the draw pile
 * is empty, the discard pile is shuffled back into the draw pile,
 * allowing for continuous play throughout the game.
 * 
 * @author COSI 103a Students
 * @version 1.0
 */
public class Deck {
    private List<Card> cards;
    private List<Card> discardPile;
    
    /**
     * Constructs an empty Deck with separate draw and discard piles.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }
    
    /**
     * Adds a single card to the draw pile.
     * 
     * @param card the card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }
    
    /**
     * Adds multiple cards to the draw pile.
     * 
     * @param cardList the list of cards to add
     */
    public void addCards(List<Card> cardList) {
        cards.addAll(cardList);
    }
    
    /**
     * Draws a single card from the deck.
     * If the draw pile is empty, the discard pile is shuffled back.
     * 
     * @return the drawn card, or null if no cards are available
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            shuffleDiscardPile();
        }
        
        if (cards.isEmpty()) {
            return null; // No cards available
        }
        
        return cards.remove(cards.size() - 1);
    }
    
    /**
     * Draws multiple cards from the deck.
     * 
     * @param count the number of cards to draw
     * @return a list of drawn cards
     */
    public List<Card> drawCards(int count) {
        List<Card> drawn = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Card card = drawCard();
            if (card != null) {
                drawn.add(card);
            }
        }
        return drawn;
    }
    
    /**
     * Discards a single card to the discard pile.
     * 
     * @param card the card to discard
     */
    public void discardCard(Card card) {
        discardPile.add(card);
    }
    
    /**
     * Discards multiple cards to the discard pile.
     * 
     * @param cardList the list of cards to discard
     */
    public void discardCards(List<Card> cardList) {
        discardPile.addAll(cardList);
    }
    
    /**
     * Shuffles the discard pile back into the draw pile.
     * This allows play to continue when the draw pile is depleted.
     */
    private void shuffleDiscardPile() {
        cards.addAll(discardPile);
        discardPile.clear();
        shuffle();
    }
    
    /**
     * Shuffles the draw pile using random ordering.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    /**
     * Gets the total number of remaining cards in both piles.
     * 
     * @return the total number of cards
     */
    public int getRemainingCards() {
        return cards.size() + discardPile.size();
    }
    
    /**
     * Gets the number of cards in the draw pile.
     * 
     * @return the size of the draw pile
     */
    public int getDrawPileSize() {
        return cards.size();
    }
    
    /**
     * Gets the number of cards in the discard pile.
     * 
     * @return the size of the discard pile
     */
    public int getDiscardPileSize() {
        return discardPile.size();
    }
}
