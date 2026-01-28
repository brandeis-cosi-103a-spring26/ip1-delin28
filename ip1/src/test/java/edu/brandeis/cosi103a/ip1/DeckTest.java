package edu.brandeis.cosi103a.ip1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * JUnit tests for the Deck class.
 * 
 * Tests deck operations including adding, drawing, discarding, and shuffling.
 */
public class DeckTest {
    
    private Deck deck;
    private Card card1;
    private Card card2;
    private Card card3;
    
    @Before
    public void setUp() {
        deck = new Deck();
        card1 = new Card("Card1", "RESOURCE", 0, "First card", 1);
        card2 = new Card("Card2", "PRODUCTION", 0, "Second card", 1);
        card3 = new Card("Card3", "AUTOMATION", 0, "Third card", 1);
    }
    
    @Test
    public void testDeckCreation() {
        assertEquals(0, deck.getRemainingCards());
        assertEquals(0, deck.getDrawPileSize());
        assertEquals(0, deck.getDiscardPileSize());
    }
    
    @Test
    public void testAddSingleCard() {
        deck.addCard(card1);
        assertEquals(1, deck.getRemainingCards());
        assertEquals(1, deck.getDrawPileSize());
    }
    
    @Test
    public void testAddMultipleCards() {
        List<Card> cards = java.util.Arrays.asList(card1, card2, card3);
        deck.addCards(cards);
        assertEquals(3, deck.getRemainingCards());
        assertEquals(3, deck.getDrawPileSize());
    }
    
    @Test
    public void testDrawCard() {
        deck.addCard(card1);
        deck.addCard(card2);
        
        Card drawn = deck.drawCard();
        assertNotNull(drawn);
        assertEquals(1, deck.getDrawPileSize());
        assertEquals(1, deck.getRemainingCards());
    }
    
    @Test
    public void testDrawMultipleCards() {
        deck.addCards(java.util.Arrays.asList(card1, card2, card3));
        List<Card> drawn = deck.drawCards(2);
        
        assertEquals(2, drawn.size());
        assertEquals(1, deck.getDrawPileSize());
    }
    
    @Test
    public void testDrawMoreCardsThanAvailable() {
        deck.addCards(java.util.Arrays.asList(card1, card2));
        List<Card> drawn = deck.drawCards(5);
        
        assertEquals(2, drawn.size());
        assertEquals(0, deck.getDrawPileSize());
    }
    
    @Test
    public void testDiscardCard() {
        deck.discardCard(card1);
        assertEquals(1, deck.getDiscardPileSize());
        assertEquals(0, deck.getDrawPileSize());
        assertEquals(1, deck.getRemainingCards());
    }
    
    @Test
    public void testDiscardMultipleCards() {
        List<Card> cards = java.util.Arrays.asList(card1, card2, card3);
        deck.discardCards(cards);
        
        assertEquals(3, deck.getDiscardPileSize());
        assertEquals(3, deck.getRemainingCards());
    }
    
    @Test
    public void testShuffleDiscardPileOnEmptyDraw() {
        deck.addCards(java.util.Arrays.asList(card1, card2));
        deck.drawCards(2); // Draw all cards
        deck.discardCard(card1);
        
        Card drawn = deck.drawCard(); // Should trigger shuffle
        assertNotNull(drawn);
        assertEquals(0, deck.getDiscardPileSize());
    }
    
    @Test
    public void testDrawFromEmptyDeck() {
        Card drawn = deck.drawCard();
        assertNull(drawn);
    }
    
    @Test
    public void testDeckShuffle() {
        deck.addCards(java.util.Arrays.asList(card1, card2, card3));
        int initialSize = deck.getDrawPileSize();
        deck.shuffle();
        assertEquals(initialSize, deck.getDrawPileSize());
    }
}
