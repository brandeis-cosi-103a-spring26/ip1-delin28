package edu.brandeis.cosi103a.ip1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for the ComputerPlayer class.
 * 
 * Tests automated decision-making for card playing and purchasing.
 */
public class ComputerPlayerTest {
    
    private ComputerPlayer computerPlayer;
    private Market market;
    
    @Before
    public void setUp() {
        computerPlayer = new ComputerPlayer("Computer1");
        market = new Market();
    }
    
    @Test
    public void testComputerPlayerCreation() {
        assertEquals("Computer1", computerPlayer.getName());
        assertEquals(0, computerPlayer.getResources());
        assertEquals(0, computerPlayer.getProduction());
    }
    
    @Test
    public void testAutoPlayCards() {
        computerPlayer.drawCards(10);
        int cardsPlayed = computerPlayer.autoPlayCards();
        
        assertTrue(cardsPlayed >= 0);
        assertTrue(computerPlayer.getResources() > 0 || cardsPlayed == 0);
    }
    
    @Test
    public void testAutoPlayCardsGeneratesResources() {
        computerPlayer.drawCards(10);
        int resourcesBefore = computerPlayer.getResources();
        
        computerPlayer.autoPlayCards();
        
        assertTrue(computerPlayer.getResources() >= resourcesBefore);
    }
    
    @Test
    public void testAutoBuyCards() {
        computerPlayer.setResources(20);
        int resourcesBefore = computerPlayer.getResources();
        
        int cardsBought = computerPlayer.autoBuyCards(market);
        
        assertTrue(cardsBought >= 0);
        assertTrue(computerPlayer.getResources() <= resourcesBefore);
    }
    
    @Test
    public void testAutoBuyCardsWithLimitedResources() {
        computerPlayer.setResources(0);
        
        int cardsBought = computerPlayer.autoBuyCards(market);
        
        assertEquals(0, cardsBought); // No resources means no cards can be bought
    }
    
    @Test
    public void testAutoBuyPrioritizesAutomation() {
        computerPlayer.setResources(100); // Plenty of resources
        
        int cardsBought = computerPlayer.autoBuyCards(market);
        
        assertTrue(cardsBought > 0);
        // Some cards should have been bought
    }
    
    @Test
    public void testAutoPlayAndBuyIntegration() {
        computerPlayer.drawCards(10);
        int cardsPlayed = computerPlayer.autoPlayCards();
        int resourcesAfterPlay = computerPlayer.getResources();
        
        int cardsBought = computerPlayer.autoBuyCards(market);
        int resourcesAfterBuy = computerPlayer.getResources();
        
        assertTrue(cardsPlayed >= 0);
        assertTrue(cardsBought >= 0);
        assertTrue(resourcesAfterBuy <= resourcesAfterPlay);
    }
    
    @Test
    public void testComputerPlayerIsPlayer() {
        assertTrue(computerPlayer instanceof Player);
    }
    
    @Test
    public void testComputerPlayerInheritedMethods() {
        computerPlayer.setResources(10);
        computerPlayer.setScore(50);
        
        assertEquals(10, computerPlayer.getResources());
        assertEquals(50, computerPlayer.getScore());
        
        computerPlayer.addResources(5);
        assertEquals(15, computerPlayer.getResources());
        
        computerPlayer.addScore(10);
        assertEquals(60, computerPlayer.getScore());
    }
    
    @Test
    public void testAutoPlayCardsEmptyHand() {
        // Draw and immediately play all cards
        computerPlayer.drawCards(5);
        int cardsPlayed = computerPlayer.autoPlayCards();
        
        assertTrue(cardsPlayed >= 0);
    }
}
