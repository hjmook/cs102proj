
import java.util.*;

public class Babi {
    private int totalSum;
    private ArrayList<Player> allPlayers;
    private int currentPlayerIndex;
    private ArrayList<Card> secondaryPool; // stores all the cards that the players put down

    public Babi(int numberOfPlayers) {
        Scanner scan = new Scanner(System.in);
        totalSum = 0;
        currentPlayerIndex = 0;
        secondaryPool = new ArrayList<Card>();
        allPlayers = new ArrayList<Player>();
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.printf("Enter player %d's name: ", i + 1);
            String name = scan.nextLine();
            Player player = new Player(name, i);
            allPlayers.add(player);
        }
        System.out.println();
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % allPlayers.size();
    }

    public void loseLife(ArrayList<Player> allPlayers, int playerNumber, Deck deck) {
        Player player = allPlayers.get(playerNumber);
        // player.loselife() will return the player's card back into the main deck of cards
        player.loseLife(deck);
    }

    public void displayPlayers(ArrayList<Player> allPlayers) {
        for (Player player : allPlayers) {
            String name = player.getPlayerName(player.getPlayerNumber());
            System.out.println("Player " + player.getPlayerNumber() + ": " + name);
        }
    }

    public void losePlayer(ArrayList<Player> allPlayers, int playerNumber) {
        for (Player player : allPlayers) {
            if (player.getPlayerNumber() == playerNumber) {
                allPlayers.remove(player);
            }
        }
    }

    public String getPlayerName(Player currentPlayer, int playerNumber) {
        return currentPlayer.getPlayerName(playerNumber);
    }


    public void playRound(ArrayList<Player> allPlayers, int numberOfPlayers, Deck deckOfCards, ArrayList<Card> secondaryPool) {
        Scanner scan = new Scanner(System.in);
        Player currentPlayer = allPlayers.get(currentPlayerIndex);
        int currentPlayerNumber = currentPlayer.getPlayerNumber();
        String currentPlayerName = currentPlayer.getPlayerName(currentPlayerNumber);
        int currentPlayerLife = currentPlayer.getPlayerLife();

        System.out.printf("%s TURN: \n", currentPlayerName);
        System.out.println();
        //System.out.printf("Test Case: SUM = %d \n", totalSum);

        if (totalSum >= 100) {
            totalSum = 100;
            System.out.printf("Test Case: CODE GOES HERE WHERE SUM >= 100 \n");
            
            System.out.println("We are at the limit!");

            currentPlayer.displayHand();
            System.out.println("Enter option of card you wish to put.");
            System.out.println("If you have no option, type '0'");
            int cardChoice = scan.nextInt();
            scan.nextLine();
        
            // if player has no other option, they lose a life
            if (cardChoice == 0) {

                // check if player is at his last life 
                if (currentPlayerLife == 1) {
                    currentPlayerLife--;
                    System.out.printf("%s is finished! \n", currentPlayerName);
                    loseLife(allPlayers, currentPlayerNumber, deckOfCards);
                    losePlayer(allPlayers, currentPlayerNumber);

                    // add all the cards from secondary pool to the main pool, and reshuffle the cards
                    for (Card card : secondaryPool) {
                        deckOfCards.returnCardToDeck(card);
                    }
                    deckOfCards.shuffleDeck();
                    totalSum = 0;
                
                // player is not at his last life
                } else {
                    System.out.printf("%s just lost a life! \n", currentPlayerName);
                    currentPlayerLife--;
                    loseLife(allPlayers, currentPlayerNumber, deckOfCards);

                    // move on to the next player for 'restart' of the game
                    nextPlayer();

                    // add all the cards from secondary pool to the main pool, and reshuffle the cards
                    for (Card card : secondaryPool) {
                        deckOfCards.returnCardToDeck(card);
                    }
                    deckOfCards.shuffleDeck();
                    totalSum = 0;
                }
            
            // player has 4 options: either put a number 4, number 7, jack, queen or king
            } else {
                System.out.printf("Test Case: CODE GOES HERE WHERE SUM <= 100 \n");
                Card drawnCardFromHand = currentPlayer.getCardFromHand(cardChoice);
                currentPlayer.removeCardFromHand(cardChoice);
                secondaryPool.add(drawnCardFromHand);
    
                // get the chosen card's value and rank
                String rank = drawnCardFromHand.getRank();
                int value = drawnCardFromHand.getValue(rank);

                // their power card is 4, they skip their turn
                if (value == 4) {
                    System.out.printf("%s have skipped their turn! \n", currentPlayerName);
                    System.out.println();
                    nextPlayer();

                } else if (value == 7) {
                    // skip player and reverse the order of play
                    System.out.printf("%s has skipped their turn and reversed the play! \n", currentPlayerName);

                } else if (rank.equals("J")) {
                    System.out.printf("%s has minused 10! \n", currentPlayerName);
                    System.out.printf("Current sum is: %d \n", totalSum);
                    System.out.println();
                    totalSum -= 10;
                    nextPlayer();

                } else if (rank.equals("Q")) {
                    System.out.printf("%s has minused 20! \n", currentPlayerName);
                    System.out.printf("Current sum is: %d \n", totalSum);
                    System.out.println();
                    totalSum -= 20;
                    nextPlayer();

                } else if (rank.equals("K")) {
                    System.out.printf("%s has put straight to 100! \n", currentPlayerName);
                    totalSum = 100;
                    System.out.printf("Current sum is: %d \n", totalSum);
                    System.out.println();
                    nextPlayer();
                }

                // after player removes a card from their hand, they take another one from the deck to replace it
                Card topCard = deckOfCards.drawTopCard();
                currentPlayer.addCardToHand(topCard);
                System.out.println("You have taken a card from the deck.");
            }

        // sum is still below 100
        } else {
            //System.out.printf("Test Case: CODE GOES HERE WHERE SUM <= 100 \n");

            System.out.printf("Current sum is: %d \n", totalSum);
            System.out.println();
            currentPlayer.displayHand();
            System.out.println("Enter option of card you wish to put.");
            System.out.println("Your option: ");
            int cardChoice = scan.nextInt();
            scan.nextLine();

            // get chosen card from player
            Card drawnCardFromHand = currentPlayer.getCardFromHand(cardChoice);
            currentPlayer.removeCardFromHand(cardChoice);
            secondaryPool.add(drawnCardFromHand);

            // get the chosen card's value and rank
            String rank = drawnCardFromHand.getRank();
            int value = drawnCardFromHand.getValue(rank);

            totalSum += value;

            // after player removes a card from their hand, they take another one from the deck to replace it
            Card topCard = deckOfCards.drawTopCard();
            currentPlayer.addCardToHand(topCard);
            System.out.printf("%s has taken a card from the deck. \n", currentPlayerName);
            System.out.println();

            nextPlayer();
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // get the number of players;
        System.out.print("Enter the number of players: ");
        int numberOfPlayers = scan.nextInt();
        scan.nextLine();

        Babi babi = new Babi(numberOfPlayers);

        Deck deckOfCards = new Deck();

        // assign each player their hands
        for (Player player : babi.allPlayers) {
            for (int i = 0; i < 4; i++) { // each player has 4 lives initially
                // pick a random card from the deck
                Card randomCard = deckOfCards.drawRandomCard();
                player.addCardToHand(randomCard);
            }
        }

        //for (Player player : babi.allPlayers) {
        //    player.displayHand();
        //}

        // start game
        while (true) {
            int round = 0;
            if (round % 3 == 0) {
                deckOfCards.shuffleDeck();
            }
            babi.playRound(babi.allPlayers, numberOfPlayers, deckOfCards, babi.secondaryPool);
            if (babi.allPlayers.size() == 1) {
                System.out.printf("Game Over. Winner: %s \n", babi.allPlayers.get(0).getPlayerName(babi.allPlayers.get(0).getPlayerNumber()));
                break;
            }
            round++;
        }
    }
}
