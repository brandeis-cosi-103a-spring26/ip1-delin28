## Dominion Game - Issues Found and Fixed

### Issues Fixed:

#### 1. **Game.java**
   - **Unused Field**: `currentPlayerIndex` was declared but never used
     - Fixed: Removed the field and its initialization
   - **Resource Leaks**: Two Scanner objects were never closed
     - Fixed: Wrapped both `playerActions()` and `buyPhase()` methods with try-finally blocks to ensure Scanner is closed
   - **Unused Variable**: `card` was iterated but not used in `resolveProduction()`
     - Fixed: Changed to calculate automation count directly without iterating

#### 2. **ComputerPlayer.java**
   - **Logic Error**: In `autoBuyCards()`, the method called both `buyCard(bestCard)` and `market.buyCard(bestIndex)` separately
     - Issue: This created a mismatch where the card was bought from the player's perspective but the market wasn't properly updated
     - Fixed: Now properly checks the return value of `market.buyCard()` to ensure the card was actually removed from the market

#### 3. **PlayerTest.java**
   - **Unused Fields**: `productionCard` and `automationCard` were declared but never used
     - Fixed: Removed unused field declarations
   - **Unused Variables**: `initialResources` and `handSizeBefore` were declared but never used in test
     - Fixed: Removed unused variables and used existing assertions

#### 4. **ComputerPlayerTest.java**
   - **Unused Variable**: `moreCardsPlayed` was assigned but never used
     - Fixed: Removed the redundant second call to `autoPlayCards()` in the test

### Logic Issues Reviewed:

1. **Game Flow**: The game correctly:
   - Initializes 2 computer players
   - Draws 5 cards per turn
   - Plays cards automatically
   - Buys cards from the market
   - Resolves production
   - Calculates final scores

2. **Market Mechanics**: The market:
   - Maintains 6 available cards
   - Refills when a card is bought
   - Properly removes bought cards

3. **Player Mechanics**: Players:
   - Start with 10 basic cards (5 Workers, 5 Resources)
   - Play cards to gain resources
   - Purchase new cards to strengthen their deck
   - Accumulate automation cards for passive income

4. **Scoring**: Final score is calculated as:
   - Resources remaining + (number of automation cards × 5)

### All Tests Now Pass ✓

The project is now clean with no compilation errors, resource leaks, or unused variables.
