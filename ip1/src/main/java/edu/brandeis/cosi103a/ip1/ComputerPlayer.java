package edu.brandeis.cosi103a.ip1;

import java.util.List;

/**
 * Represents a computer-controlled player in the Automation game.
 * 
 * The ComputerPlayer extends Player and implements automated decision-making
 * for playing cards and purchasing cards from the market. The computer uses
 * a strategy-based approach to maximize resources and build automation.
 * 
 * @author COSI 103a Students
 * @version 1.0
 */
public class ComputerPlayer extends Player {
    
    /**
     * Constructs a ComputerPlayer with the given name.
     * 
     * @param name the name of the computer player
     */
    public ComputerPlayer(String name) {
        super(name);
    }
    
    /**
     * Automatically plays cards from the player's hand.
     * Strategy: Play all resource and production cards first, then automation.
     * 
     * @return the number of cards played
     */
    public int autoPlayCards() {
        List<Card> hand = getHand();
        int cardsPlayed = 0;
        
        // First, play all RESOURCE cards
        for (Card card : hand) {
            if ("RESOURCE".equals(card.getType())) {
                playCard(card);
                cardsPlayed++;
            }
        }
        
        // Then play PRODUCTION cards
        hand = getHand(); // Refresh hand after playing cards
        for (Card card : hand) {
            if ("PRODUCTION".equals(card.getType())) {
                playCard(card);
                cardsPlayed++;
            }
        }
        
        return cardsPlayed;
    }
    
    /**
     * Automatically buys cards from the market based on strategy.
     * Strategy: Buy AUTOMATION cards if affordable, otherwise buy cheapest PRODUCTION cards.
     * 
     * @param market the market to buy from
     * @return the number of cards purchased
     */
    public int autoBuyCards(Market market) {
        int cardsBought = 0;
        boolean shouldBuy = true;
        
        while (shouldBuy && getResources() > 0) {
            List<Card> available = market.getAvailableCards();
            Card bestCard = null;
            int bestIndex = -1;
            
            // Strategy 1: Try to find and buy AUTOMATION cards
            for (int i = 0; i < available.size(); i++) {
                Card card = available.get(i);
                if ("AUTOMATION".equals(card.getType()) && 
                    getResources() >= card.getCost()) {
                    
                    if (bestCard == null || card.getValue() > bestCard.getValue()) {
                        bestCard = card;
                        bestIndex = i;
                    }
                }
            }
            
            // Strategy 2: If no automation affordable, buy cheapest affordable card
            if (bestCard == null) {
                for (int i = 0; i < available.size(); i++) {
                    Card card = available.get(i);
                    if (getResources() >= card.getCost()) {
                        if (bestCard == null || card.getCost() < bestCard.getCost()) {
                            bestCard = card;
                            bestIndex = i;
                        }
                    }
                }
            }
            
            // If we found a card to buy, buy it
            if (bestCard != null && bestIndex >= 0) {
                buyCard(bestCard);
                Card boughtCard = market.buyCard(bestIndex);
                if (boughtCard != null) {
                    cardsBought++;
                    System.out.println(getName() + " bought: " + bestCard.getName() + 
                                     " (Resources left: " + getResources() + ")");
                }
            } else {
                shouldBuy = false;
            }
        }
        
        return cardsBought;
    }
}
