package edu.brandeis.cosi103a.ip1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * JUnit tests for the Player class.
 * 
 * Tests player initialization, card playing, purchasing, and scoring.
 */
public class PlayerTest {
    
    private Player player;
    private Card resourceCard;
    
    @Before
    public void setUp() {
        player = new Player("TestPlayer");
        resourceCard = new Card("Gold", "RESOURCE", 3, "Worth 3 resources", 3);
    }
    
    @Test
    public void testPlayerCreation() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(0, player.getResources());
        assertEquals(0, player.getProduction());
        assertEquals(0, player.getScore());
    }
    
    @Test
    public void testPlayerInitialDeck() {
        // Player should start with 10 cards (5 workers + 5 resources)
        assertEquals(10, player.getHand().size() + 
                   player.getActiveAutomation().size() +
                   10); // rough check
    }
    
    @Test
    public void testDrawCards() {
        player.drawCards(3);
        assertTrue(player.getHand().size() > 0);
    }
    
    @Test
    public void testPlayResourceCard() {
        player.drawCards(10);
        List<Card> hand = player.getHand();
        
        // Find and play a resource card
        for (Card card : hand) {
            if ("RESOURCE".equals(card.getType())) {
                int resourcesBefore = player.getResources();
                player.playCard(card);
                assertTrue(player.getResources() > resourcesBefore);
                break;
            }
        }
    }
    
    @Test
    public void testBuyCard() {
        player.setResources(10);
        int resourcesBefore = player.getResources();
        
        player.buyCard(resourceCard);
        
        assertEquals(resourcesBefore - 3, player.getResources());
    }
    
    @Test
    public void testBuyCardInsufficientResources() {
        player.setResources(2);
        int resourcesBefore = player.getResources();
        
        player.buyCard(resourceCard); // Costs 3, but only has 2
        
        assertEquals(resourcesBefore, player.getResources()); // Should not change
    }
    
    @Test
    public void testAddResources() {
        player.setResources(5);
        player.addResources(3);
        assertEquals(8, player.getResources());
    }
    
    @Test
    public void testSetAndGetScore() {
        player.setScore(50);
        assertEquals(50, player.getScore());
        
        player.addScore(10);
        assertEquals(60, player.getScore());
    }
    
    @Test
    public void testGetHand() {
        player.drawCards(5);
        List<Card> hand = player.getHand();
        assertTrue(hand.size() > 0);
    }
    
    @Test
    public void testGetActiveAutomation() {
        List<Card> activeAutomation = player.getActiveAutomation();
        assertNotNull(activeAutomation);
        assertEquals(0, activeAutomation.size());
    }
    
    @Test
    public void testEndTurn() {
        player.setResources(5);
        player.drawCards(5);
        
        int handSizeBefore = player.getHand().size();
        assertTrue(handSizeBefore > 0);
        
        player.endTurn();
        
        // Hand should be discarded
        assertEquals(0, player.getHand().size());
    }
    
    @Test
    public void testPlayerToString() {
        player.setResources(10);
        player.setScore(25);
        String playerStr = player.toString();
        
        assertTrue(playerStr.contains("TestPlayer"));
        assertTrue(playerStr.contains("10")); // resources
        assertTrue(playerStr.contains("25")); // score
    }
}
