
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class HundredForNow {
    private int totalSum;
    private ArrayList<Player> allPlayers;
    private int currentPlayerIndex; // the index to keep track of the current player
    private ArrayList<Card> secondaryPool; // stores all the cards that the player puts down
    private boolean hasPreviousRoundDeath; // flag to check if a death occurs in the previous round (size of allPlayer // reduces)

    public HundredForNow(int numberOfPlayers) {
        Scanner scan = new Scanner(System.in);

        hasPreviousRoundDeath = false;
        totalSum = 0;
        currentPlayerIndex = 0;
        secondaryPool = new ArrayList<Card>();
        allPlayers = new ArrayList<Player>();

        // get the player's inputs for their names, adds to allPlayers
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.printf("Enter player %d's name: ", i + 1);
            String name = scan.nextLine();
            Player player = new Player(name, i);
            allPlayers.add(player);
        }
        System.out.println("");
    }

    // invoke this method to move on to the next player
    public void nextPlayer() {
        if (hasPreviousRoundDeath) {
            if (currentPlayerIndex == allPlayers.size()) {
                currentPlayerIndex = 0;
            }
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % allPlayers.size();
        }
    }

    // when the player loses a life
    public void loseLife(ArrayList<Player> allPlayers, int playerNumber, Deck deck) {
        Player player = allPlayers.get(currentPlayerIndex);
        player.loseLife(deck); // returns the player's cards back to the main deck of cards
    }

    // display the players in the game
    public void displayPlayer(ArrayList<Player> allPlayers) {
        for (Player player : allPlayers) {
            System.out.println("Player " + player.getPlayerNumber() + ": " + player.getPlayerName(player.getPlayerNumber()));
        }
    }

    // when the player loses all his lives
    public void losePlayer(ArrayList<Player> allPlayers, int playerNumber) {
        hasPreviousRoundDeath = true;
        Iterator<Player> iterator = allPlayers.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getPlayerNumber() == playerNumber) {
                iterator.remove();
            }
        }
    }

    public boolean lowNumberOfCards(Deck deckOfCards) {
        if (deckOfCards.sizeOfDeck() <= 5) {
            return true;
        }
        return false;
    }

    // add the cards from the secondary pool to the main pool
    public void cardsFromSecondaryPoolToMainDeck(Deck deckOfCards) {
        for (Card card : secondaryPool) {
            deckOfCards.returnCardToDeck(card);
        }
        deckOfCards.shuffleDeck();
        secondaryPool.clear(); // clear the cards in secondaryPool, since they are returned to the main deck
    }

    public boolean isPowerCard(String rank) {
        if (rank.equals("K") || rank.equals("Q") || rank.equals("J") || rank.equals("4")) {
            return true;
        } return false;
    }
    
    public int playCard(String rank, int value, int totalSum, Player currentPlayer) {
        // if chosen card is a King
        Scanner scan = new Scanner(System.in);
        
        if (!isPowerCard(rank) && totalSum + value <= 100) {
            totalSum += value;
        }else if (value == 100) {
            totalSum = 100;
            System.out.printf("%s goes STRAIGHT TO HUNDRED! \n", currentPlayer.getPlayerName(currentPlayer.getPlayerNumber()));
            System.out.println("");

            // if chosen card is a Jack - power play: can either minus or add 10
        } else if (rank.equals("J") && totalSum + value <= 100) {
            System.out.println("If you wish to add 10 to the sum, press '1'");
            System.out.println("If you wish to subtract 10 from the sum, press '2'");
            System.out.print("Enter choice: ");
            int addOrSubtractChoice = scan.nextInt();
            scan.nextLine();
            if (addOrSubtractChoice == 1) {
                totalSum += 10;
            } else {
                totalSum -= 10;
            }

            // if chosen card is a Queen - power play: can either minus or add 20
        } else if (rank.equals("Q") && totalSum + value <= 100) {
            System.out.println("If you wish to add 20 to the sum, press '1'");
            System.out.println("If you wish to subtract 20 from the sum, press '2'");
            System.out.print("Enter choice: ");
            int addOrSubtractChoice = scan.nextInt();
            scan.nextLine();
            if (addOrSubtractChoice == 1) {
                totalSum += 20;
            } else {
                totalSum -= 20;
            }

            // if chosen card is a '4' - power play: can either skip turn or add 4
        } else if (value == 4 && totalSum + value <= 100) {
            System.out.println("If you wish to add 4 to the sum, press '1'");
            System.out.println("If you wish to skip your turn, press '2'");
            System.out.print("Enter choice: ");
            int addOrSubtractChoice = scan.nextInt();
            scan.nextLine();
            if (addOrSubtractChoice == 1) {
                totalSum += 4;
            } else {
                totalSum += 0;
                System.out.printf("%s has skipped their turn! \n", currentPlayer.getPlayerName(currentPlayer.getPlayerNumber()));

            }

            // if the card is a Queen or Jack and the sum will be more than 100 when added, we automatically subtract
        } else if (totalSum + value > 100 && (rank.equals("Q") || rank.equals("J"))) {
            totalSum -= value;
                
            // if the card is a Queen or Jack or '4', and the subtraction from the total sum will be less than 0, automatically add
        } else if (totalSum - value < 0 && (rank.equals("Q") || rank.equals("J") || value == 4)) {
            totalSum += value;
        
            // if the card is a '4' and the sum will be more than 100 when added, we automatically skip turn
        } else if (value == 4 && totalSum + value > 100) {
            totalSum += 0;
            System.out.printf("%s has skipped their turn! \n", currentPlayer.getPlayerName(currentPlayer.getPlayerNumber()));
    
                // if the card chosen is not a valid card
        } else if (totalSum + value > 100) {
            ArrayList<Card> validCards = currentPlayer.getValidCards(totalSum);
            if (validCards.size() != 0) {
                String validCardsString = "";
                for (Card card : validCards) {
                    validCardsString += card.toString() + " ";
                }
                 throw new InvalidCardException("Can't play this! The sum will exceed 100! These are your valid cards: " + validCardsString);
            } else {
                throw new InvalidCardException("Can't play this! The sum will exceed 100! You have no valid cards! KYS!");
            }
                    
        }

        return totalSum;
    }

    public void playRound(ArrayList<Player> allPlayers, int numberOfPlayers, Deck deckOfCards, ArrayList<Card> secondaryPool) {
        Scanner scan = new Scanner(System.in);

        Player currentPlayer = allPlayers.get(currentPlayerIndex);
        int currentPlayerNumber = currentPlayer.getPlayerNumber();
        String currentPlayerName = currentPlayer.getPlayerName(currentPlayerNumber);
        int currentPlayerLife = currentPlayer.getPlayerLife();
        //boolean //validChoice = false;  // flag to check if the player's choice is valid

        System.out.printf("%s's turn: \n", currentPlayerName.toUpperCase());
        System.out.println("");

        // check if the main deck of cards are running low (add cards from secondary pool to main deck)
        if (lowNumberOfCards(deckOfCards)) {
            cardsFromSecondaryPoolToMainDeck(deckOfCards);
        }

        // [MESSAGE] display the current sum
        if (totalSum == 100) {
            System.out.println("TEST STATEMENT: CODE GOES HERE WHEN SUM = 100");
            System.out.println("We are at the limit! \n");
        } else {
            System.out.println("TEST STATEMENT: CODE GOES HERE WHEN SUM < 100");
            System.out.printf("Current sum is %d \n", totalSum);
            System.out.println("");
        }

        // DEBUGGING
        int cardsInPlayersHands = 0;
        for (Player player : allPlayers) {
            cardsInPlayersHands += player.getPlayerLife();
        }
        int totalCards = deckOfCards.sizeOfDeck() + secondaryPool.size() + cardsInPlayersHands;
        System.out.println("FOR DEBUGGING COUNTING CARDS:");
        System.out.println("       Cards in deck: " + deckOfCards.sizeOfDeck());
        System.out.println("Cards in played pile: " + cardsInPlayersHands);
        System.out.println("         Total Cards: " + totalCards);
        System.out.println("      Correct number: " + (totalCards == 52));
        System.out.println("");
        
        // [MESSAGE] display player's hand and input option
        currentPlayer.displayHand();
        System.out.print("Enter your option (If you have no option, type '0'): ");
        int cardChoice = scan.nextInt();
        scan.nextLine();

        if (cardChoice > currentPlayerLife) {
            throw new IndexOutOfBoundsException();
        } else if (cardChoice <= 0 && cardChoice >= 9) {
            throw new InputMismatchException();
        }
        
        // [MESSAGE] if player wants to kill himself (chooses option '0')
        if (cardChoice == 0) {
            currentPlayerLife--;

            // player is at his last life
            if (currentPlayer.getPlayerLife() == 1) {
                System.out.printf("%s is finished! \n", currentPlayerName);
                System.out.println("");
                loseLife(allPlayers, currentPlayerNumber, deckOfCards);
                losePlayer(allPlayers, currentPlayerNumber);

                // add all the cards from the secondary pool to the main pool, reshuffle the cards (restart game)
                cardsFromSecondaryPoolToMainDeck(deckOfCards);
                totalSum = 0;

            // player is not at his last life
            } else {
                hasPreviousRoundDeath = false;
                System.out.printf("%s just lost his life! \n", currentPlayerName);
                System.out.println("");
                loseLife(allPlayers, currentPlayerNumber, deckOfCards);
                
                // move on to the next player
                nextPlayer();

                // add all the cards from the secondary pool to the main pool, reshuffle the cards (restart game)
                cardsFromSecondaryPoolToMainDeck(deckOfCards);
                totalSum = 0;
            }

        // if player chooses another option 
        } else {
            hasPreviousRoundDeath = false;

            // total sum == 100
           
            Card drawnCardFromHand = currentPlayer.getCardFromHand(cardChoice); // gets the card based on player's choice
            String rank = drawnCardFromHand.getRank();
            int value = drawnCardFromHand.getValue(); 
            
            totalSum = playCard(rank, value, totalSum, currentPlayer);

            currentPlayer.removeCardFromHand(cardChoice);
            secondaryPool.add(drawnCardFromHand);
                
            Card topCard = deckOfCards.drawTopCard();
            currentPlayer.addCardToHand(topCard);
            deckOfCards.removeCardFromDeck(topCard); // remove the drawn card from the deck
            System.out.println("You have taken a card from the deck. \n");
            System.out.println("");
                
            nextPlayer();
            
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // get the number of players
        System.out.print("Enter the number of players: ");
        int numberOfPlayers = scan.nextInt();
        scan.nextLine();

        HundredForNow game = new HundredForNow(numberOfPlayers);
        Deck deckOfCards = new Deck();


        // assign each players their hands
        for (Player player : game.allPlayers) {
            for (int i = 0; i < 4; i++) {
                Card randomCard = deckOfCards.drawRandomCard();
                player.addCardToHand(randomCard);
            }
        }
        
        // start game
        while(game.allPlayers.size() > 1) {
            int round = 0;
            if (round % 3 == 0) {
                deckOfCards.shuffleDeck();
            }

            try {
                game.playRound(game.allPlayers, numberOfPlayers, deckOfCards, game.secondaryPool);
            } catch (InvalidCardException e) {
                System.out.println(e.getMessage());
            } catch (IndexOutOfBoundsException f) {
                System.out.println("Please select within your card limit!");
            } catch (InputMismatchException g) {
                System.out.println("Please select a numerical value!");
            }

            if (game.allPlayers.size() == 1) {
                System.out.printf("Game Over. Winner: %s \n", game.allPlayers.get(0).getPlayerName(game.allPlayers.get(0).getPlayerNumber()));
                break;
            }

            round++;
        }
    }
}
