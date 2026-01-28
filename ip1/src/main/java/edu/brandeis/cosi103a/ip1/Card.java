package edu.brandeis.cosi103a.ip1;

/**
 * Represents a card in the Automation game.
 * 
 * Each card has a name, type, cost, description, and optional value.
 * Card types include:
 * - PRODUCTION: Cards that produce resources when played
 * - AUTOMATION: Cards that provide passive income
 * - RESOURCE: Cards that give immediate resources
 * - UPGRADE: Cards that improve player capabilities
 * 
 * @author COSI 103a Students
 * @version 1.0
 */
public class Card {
    private String name;
    private String type; // PRODUCTION, AUTOMATION, RESOURCE, UPGRADE
    private int cost;
    private String description;
    private int value; // For resource/value cards
    
    /**
     * Constructs a Card with no value.
     * 
     * @param name the name of the card
     * @param type the type of card (PRODUCTION, AUTOMATION, RESOURCE, UPGRADE)
     * @param cost the cost to buy this card
     * @param description the description of what the card does
     */
    public Card(String name, String type, int cost, String description) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.description = description;
        this.value = 0;
    }
    
    /**
     * Constructs a Card with a value.
     * 
     * @param name the name of the card
     * @param type the type of card (PRODUCTION, AUTOMATION, RESOURCE, UPGRADE)
     * @param cost the cost to buy this card
     * @param description the description of what the card does
     * @param value the value or power level of the card
     */
    public Card(String name, String type, int cost, String description, int value) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.description = description;
        this.value = value;
    }
    
    /**
     * Gets the name of the card.
     * 
     * @return the card's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the type of the card.
     * 
     * @return the card's type
     */
    public String getType() {
        return type;
    }
    
    /**
     * Gets the cost to purchase this card.
     * 
     * @return the cost in resources
     */
    public int getCost() {
        return cost;
    }
    
    /**
     * Gets the description of what the card does.
     * 
     * @return the card's description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the value or power level of the card.
     * 
     * @return the card's value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Returns a string representation of the card.
     * 
     * @return a formatted string with card details
     */
    @Override
    public String toString() {
        return String.format("%s [%s] - Cost: %d - %s", name, type, cost, description);
    }
}
