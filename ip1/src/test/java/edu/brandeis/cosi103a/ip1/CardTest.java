package edu.brandeis.cosi103a.ip1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests for the Card class.
 * 
 * Tests card creation, getters, and string representation.
 */
public class CardTest {
    
    private Card resourceCard;
    private Card productionCard;
    private Card automationCard;
    private Card upgradeCard;
    
    @Before
    public void setUp() {
        resourceCard = new Card("Gold", "RESOURCE", 3, "Worth 3 resources", 3);
        productionCard = new Card("Worker", "PRODUCTION", 1, "Produces 1 resource", 1);
        automationCard = new Card("Factory", "AUTOMATION", 5, "Produces 2 resources per turn", 2);
        upgradeCard = new Card("Efficiency", "UPGRADE", 2, "Increases production by 1", 0);
    }
    
    @Test
    public void testCardCreationWithValue() {
        assertEquals("Gold", resourceCard.getName());
        assertEquals("RESOURCE", resourceCard.getType());
        assertEquals(3, resourceCard.getCost());
        assertEquals(3, resourceCard.getValue());
        assertEquals("Worth 3 resources", resourceCard.getDescription());
    }
    
    @Test
    public void testCardCreationWithoutValue() {
        Card card = new Card("Basic Card", "UPGRADE", 0, "A basic card");
        assertEquals("Basic Card", card.getName());
        assertEquals("UPGRADE", card.getType());
        assertEquals(0, card.getCost());
        assertEquals(0, card.getValue());
    }
    
    @Test
    public void testCardGetters() {
        assertEquals("Worker", productionCard.getName());
        assertEquals("PRODUCTION", productionCard.getType());
        assertEquals(1, productionCard.getCost());
        assertEquals("Produces 1 resource", productionCard.getDescription());
        assertEquals(1, productionCard.getValue());
    }
    
    @Test
    public void testCardToString() {
        String expected = "Gold [RESOURCE] - Cost: 3 - Worth 3 resources";
        assertEquals(expected, resourceCard.toString());
    }
    
    @Test
    public void testDifferentCardTypes() {
        assertEquals("RESOURCE", resourceCard.getType());
        assertEquals("PRODUCTION", productionCard.getType());
        assertEquals("AUTOMATION", automationCard.getType());
        assertEquals("UPGRADE", upgradeCard.getType());
    }
}
