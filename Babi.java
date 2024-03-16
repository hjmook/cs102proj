import java.util.*;

public class Babi {
    private int totalSum;
    private ArrayList<Player> allPlayers;
    private int currentPlayerIndex;
    private ArrayList<Card> secondaryPool; // stores all the cards that the players put down
    private boolean hasPreviousRoundDeath; // checks if there's a death in the previous round => changes size of array

    public Babi(int numberOfPlayers) {
        Scanner scan = new Scanner(System.in);
        hasPreviousRoundDeath = false;
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
        if (hasPreviousRoundDeath){
            // currentPlayerIndex = (currentPlayerIndex + 1) % (allPlayers.size() + 1);
            if (currentPlayerIndex == allPlayers.size()){
                currentPlayerIndex = 0;
            }
        }else{
            currentPlayerIndex = (currentPlayerIndex + 1) % allPlayers.size();
        }   
    }

    public void loseLife(ArrayList<Player> allPlayers, int playerNumber, Deck deck) {
        // Player player = allPlayers.get(playerNumber); => not reliable, playerNumber will be out of bounds in some cases
        Player player = allPlayers.get(currentPlayerIndex);
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
        hasPreviousRoundDeath = true;
        Iterator iter = allPlayers.iterator();
        while(iter.hasNext()){
            Player player = (Player) iter.next();
            if (player.getPlayerNumber() == playerNumber){
                iter.remove();
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

        boolean validTurn = false;

        System.out.printf("%s TURN: \n", currentPlayerName);
        System.out.println();

        // Display current sum 
        if (totalSum == 100){
            System.out.printf("Test Case: CODE GOES HERE WHERE SUM == 100 \n");
            System.out.println("We are at the limit!");
            System.out.println();
        }else{
            System.out.printf("Current sum is: %d \n", totalSum);
            System.out.println();
        }

        // Display hand and input option
        currentPlayer.displayHand();
        System.out.println("Enter option of card you wish to put.");
        System.out.println("If you have no option, type '0'");
        int cardChoice = scan.nextInt();
        scan.nextLine();

        // If want to kill self
        if (cardChoice == 0){ 
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
                
                nextPlayer();
            // player is not at his last life
            } else {
                hasPreviousRoundDeath = false;
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
        // If don't want to kill self    
        }else{
            hasPreviousRoundDeath = false;
            if (totalSum == 100) {
                // player has 4 options: either put a number 4, number 7, jack, queen or king
                
                    //System.out.printf("Test Case: CODE GOES HERE WHERE SUM == 100 \n");
                    Card drawnCardFromHand = currentPlayer.getCardFromHand(cardChoice);
        
                    // get the chosen card's value and rank
                    String rank = drawnCardFromHand.getRank();
                    int value = drawnCardFromHand.getValue();
    
                    // their power card is 4, they skip their turn
                    if (value == 4) {
                        validTurn = true;
                        System.out.printf("%s have skipped their turn! \n", currentPlayerName);
                        System.out.println();
                        nextPlayer();
    
                    } 
                    // else if (value == 7) {
                    //     // skip player and reverse the order of play
                    //     System.out.printf("%s has skipped their turn and reversed the play! \n", currentPlayerName);}
                     else if (rank.equals("J")) {
                        validTurn = true;
                        System.out.printf("%s has minused 10! \n", currentPlayerName);
                        System.out.printf("Current sum is: %d \n", totalSum);
                        System.out.println();
                        totalSum -= 10;
                        nextPlayer();
    
                    } else if (rank.equals("Q")) {
                        validTurn = true;
                        System.out.printf("%s has minused 20! \n", currentPlayerName);
                        System.out.printf("Current sum is: %d \n", totalSum);
                        System.out.println();
                        totalSum -= 20;
                        nextPlayer();
    
                    } else if (rank.equals("K")) {
                        validTurn = true;
                        System.out.printf("%s has put straight to 100! \n", currentPlayerName);
                        totalSum = 100;
                        System.out.printf("Current sum is: %d \n", totalSum);
                        System.out.println();
                        nextPlayer();
                    }else{
                        ArrayList<Card> validCards = currentPlayer.getValidCards(totalSum);
                        String validCardsStr = "";
                        if (validCards.size() != 0){
                            for (Card card : validCards) {
                                validCardsStr += card.toString() + " ";
                            }
                            throw new InvalidCardException("Can't play this! Sum will exceed 100! These are your valid cards: " + validCardsStr);

                        }else{
                            throw new InvalidCardException("Can't play this! Sum will exceed 100! You have no valid cards! KYS!");
                        }
                    }
    
                    // after player removes a card from their hand, they take another one from the deck to replace it
                    if (validTurn){
                        currentPlayer.removeCardFromHand(cardChoice);
                        secondaryPool.add(drawnCardFromHand);
                        Card topCard = deckOfCards.drawTopCard();
                        currentPlayer.addCardToHand(topCard);
                        System.out.println("You have taken a card from the deck.");
                    }
                    
            // sum is still below 100
            } else {
    
                // get chosen card from player
                Card drawnCardFromHand = currentPlayer.getCardFromHand(cardChoice);
    
                // get the chosen card's value and rank
                String rank = drawnCardFromHand.getRank();
                int value = drawnCardFromHand.getValue();
    
                if (value == 100) {
                    validTurn = true;
                    totalSum = 100;
                    currentPlayer.removeCardFromHand(cardChoice);
                    secondaryPool.add(drawnCardFromHand);
        
                }
                else if(totalSum + value > 100 && (rank == "Q" || rank == "J")) {
                    validTurn = true;
                    totalSum -= value;
                    currentPlayer.removeCardFromHand(cardChoice);
                    secondaryPool.add(drawnCardFromHand);
        
                }
                else if(rank == "K"){
                    validTurn = true;
                    currentPlayer.removeCardFromHand(cardChoice);
                    secondaryPool.add(drawnCardFromHand);
                }
                else if (totalSum + value > 100) {
                    ArrayList<Card> validCards = currentPlayer.getValidCards(totalSum);
                    if (validCards.size() != 0){
                        String validCardsString = "";
                        for (Card card : validCards) {
                            validCardsString += card.toString() + " ";
                        }
                        throw new InvalidCardException("Can't play this! Sum will exceed hundred! These are your valid cards: " + validCardsString);
                    }else{
                        throw new InvalidCardException("Can't play this! Sum will exceed hundred! You have no valid cards! KYS!");
                    }
                } else {
                    validTurn = true;
                    totalSum += value;
                    currentPlayer.removeCardFromHand(cardChoice);
                    secondaryPool.add(drawnCardFromHand);
        
                }
    
                // after player removes a card from their hand, they take another one from the deck to replace it
                Card topCard = deckOfCards.drawTopCard();
                currentPlayer.addCardToHand(topCard);
                System.out.printf("%s has taken a card from the deck. \n", currentPlayerName);
                System.out.println();
    
                if (validTurn) {
                    nextPlayer();
                }
            }
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


        // start game
        while (babi.allPlayers.size() > 1) {
            int round = 0;
            if (round % 3 == 0) {
                deckOfCards.shuffleDeck();
            }
            try {
                babi.playRound(babi.allPlayers, numberOfPlayers, deckOfCards, babi.secondaryPool);
            } catch (InvalidCardException e) {
                System.out.println(e.getMessage());
            }
            if (babi.allPlayers.size() == 1) {
                System.out.printf("Game Over. Winner: %s \n", babi.allPlayers.get(0).getPlayerName(babi.allPlayers.get(0).getPlayerNumber()));
                break;
            }
            round++;
        }

    }
}
