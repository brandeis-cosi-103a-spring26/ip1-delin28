package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;
  
/**
 * Unit tests for the Dice Game App
 */
public class AppTest 
{
    /**
     * Test rollDie returns a value between 1 and 6.
     */
    @Test
    public void testRollDie_ReturnsValidDieValue()
    {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int roll = App.rollDie(random);
            assertTrue("Roll should be >= 1", roll >= 1);
            assertTrue("Roll should be <= 6", roll <= 6);
        }
    }
    
    /**
     * Test rollDie produces a range of values over multiple rolls
     */
    @Test
    public void testRollDie_ProducesVariousValues()
    {
        Random random = new Random(42); // Use seed for reproducibility
        boolean hasDifferentValues = false;
        int firstRoll = App.rollDie(random);
        
        for (int i = 0; i < 50; i++) {
            int roll = App.rollDie(random);
            if (roll != firstRoll) {
                hasDifferentValues = true;
                break;
            }
        }
        
        assertTrue("Random rolls should produce different values", hasDifferentValues);
    }
    
    /**
     * Test isAffirmativeResponse returns true for "yes"
     */
    @Test
    public void testIsAffirmativeResponse_YesReturnsTrue()
    {
        assertTrue(App.isAffirmativeResponse("yes"));
    }
    
    /**
     * Test isAffirmativeResponse returns true for "y"
     */
    @Test
    public void testIsAffirmativeResponse_YLowerReturnsTrue()
    {
        assertTrue(App.isAffirmativeResponse("y"));
    }
    
    /**
     * Test isAffirmativeResponse returns false for "no"
     */
    @Test
    public void testIsAffirmativeResponse_NoReturnsFalse()
    {
        assertFalse(App.isAffirmativeResponse("no"));
    }
    
    /**
     * Test isAffirmativeResponse returns false for "n"
     */
    @Test
    public void testIsAffirmativeResponse_NReturnsFalse()
    {
        assertFalse(App.isAffirmativeResponse("n"));
    }
    
    /**
     * Test isAffirmativeResponse returns false for invalid input
     */
    @Test
    public void testIsAffirmativeResponse_InvalidInputReturnsFalse()
    {
        assertFalse(App.isAffirmativeResponse("maybe"));
        assertFalse(App.isAffirmativeResponse(""));
        assertFalse(App.isAffirmativeResponse("YES123"));
    }
    
    /**
     * Test isAffirmativeResponse is case-insensitive for "YES"
     */
    @Test
    public void testIsAffirmativeResponse_YESUppercaseReturnsTrue()
    {
        assertTrue(App.isAffirmativeResponse("YES"));
    }
    
    /**
     * Test isAffirmativeResponse is case-insensitive for "NO"
     */
    @Test
    public void testIsAffirmativeResponse_NOUppercaseReturnsFalse()
    {
        assertFalse(App.isAffirmativeResponse("NO"));
    }
    
    /**
     * Test determineWinner when player 1 has higher score
     */
    @Test
    public void testDetermineWinner_Player1Higher()
    {
        String result = App.determineWinner("Alice", 50, "Bob", 40);
        assertEquals("Alice wins!", result);
    }
    
    /**
     * Test determineWinner when player 2 has higher score
     */
    @Test
    public void testDetermineWinner_Player2Higher()
    {
        String result = App.determineWinner("Alice", 30, "Bob", 60);
        assertEquals("Bob wins!", result);
    }
    
    /**
     * Test determineWinner when scores are tied
     */
    @Test
    public void testDetermineWinner_Tie()
    {
        String result = App.determineWinner("Alice", 50, "Bob", 50);
        assertEquals("It's a tie!", result);
    }
    
    /**
     * Test determineWinner with edge case of zero scores
     */
    @Test
    public void testDetermineWinner_ZeroScores()
    {
        String result = App.determineWinner("Alice", 0, "Bob", 0);
        assertEquals("It's a tie!", result);
    }
    
    /**
     * Test determineWinner with large scores
     */
    @Test
    public void testDetermineWinner_LargeScores()
    {
        String result = App.determineWinner("Alice", 1000, "Bob", 999);
        assertEquals("Alice wins!", result);
    }
}
