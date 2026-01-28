package edu.brandeis.cosi103a.ip1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * JUnit tests for the Market class.
 * 
 * Tests market initialization, card availability, and purchasing.
 */
public class MarketTest {
    
    private Market market;
    
    @Before
    public void setUp() {
        market = new Market();
    }
    
    @Test
    public void testMarketCreation() {
        assertNotNull(market);
        List<Card> available = market.getAvailableCards();
        assertNotNull(available);
        assertEquals(6, available.size()); // Market size is 6
    }
    
    @Test
    public void testMarketHasCards() {
        List<Card> available = market.getAvailableCards();
        assertTrue(available.size() > 0);
        
        for (Card card : available) {
            assertNotNull(card.getName());
            assertNotNull(card.getType());
            assertTrue(card.getCost() >= 0);
        }
    }
    
    @Test
    public void testMarketCardTypes() {
        List<Card> available = market.getAvailableCards();
        
        boolean hasProduction = false;
        boolean hasAutomation = false;
        boolean hasUpgrade = false;
        
        for (Card card : available) {
            String type = card.getType();
            if ("PRODUCTION".equals(type)) hasProduction = true;
            if ("AUTOMATION".equals(type)) hasAutomation = true;
            if ("UPGRADE".equals(type)) hasUpgrade = true;
        }
        
        assertTrue(hasProduction || hasAutomation || hasUpgrade);
    }
    
    @Test
    public void testBuyCard() {
        List<Card> available = market.getAvailableCards();
        int sizeBefore = available.size();
        
        Card card = market.buyCard(0);
        
        assertNotNull(card);
        List<Card> availableAfter = market.getAvailableCards();
        assertEquals(sizeBefore, availableAfter.size()); // Market should refill
    }
    
    @Test
    public void testBuyCardInvalidIndex() {
        Card card = market.buyCard(100); // Invalid index
        assertNull(card);
    }
    
    @Test
    public void testBuyCardNegativeIndex() {
        Card card = market.buyCard(-1);
        assertNull(card);
    }
    
    @Test
    public void testMarketRefill() {
        List<Card> available = market.getAvailableCards();
        assertEquals(6, available.size());
        
        // Buy a card
        market.buyCard(0);
        
        // Market should still have 6 cards
        available = market.getAvailableCards();
        assertEquals(6, available.size());
    }
    
    @Test
    public void testMarketMultiplePurchases() {
        for (int i = 0; i < 5; i++) {
            Card card = market.buyCard(0);
            assertNotNull(card);
            
            List<Card> available = market.getAvailableCards();
            assertEquals(6, available.size());
        }
    }
    
    @Test
    public void testMarketGetAvailableCards() {
        List<Card> available = market.getAvailableCards();
        assertNotNull(available);
        assertEquals(6, available.size());
        
        // Modifying returned list should not affect market
        available.clear();
        
        List<Card> availableAfter = market.getAvailableCards();
        assertEquals(6, availableAfter.size());
    }
}
