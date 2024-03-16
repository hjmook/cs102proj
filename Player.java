import java.util.*;

public class Player {
    private String playerName;
    private int playerNumber;
    private ArrayList<Card> playerHand;
    private int playerLife;
    
    public Player(String playerName, int playerNumber) {
        this.playerName = playerName;
        this.playerNumber = playerNumber;
        this.playerLife = 4;
        this.playerHand = new ArrayList<Card>();
    }

    public String getPlayerName(int player) {
        return playerName;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerLife() {
        return playerLife;
    }

    @Override
    public String toString() {
        return "Player " + playerNumber + " = " + playerName;
    }

    public void addCardToHand(Card card) {
        playerHand.add(card);
    }

    public Card getCardFromHand(int choice) {
        return playerHand.get(choice - 1);
    }

    public void removeCardFromHand(int choice) {
        playerHand.remove(choice - 1);
    }

    public void displayHand() {
        System.out.println(playerName + " has:");
        int i = 1;
        for (Card card : playerHand) {
            System.out.println("" + i + ". " + card);
            i++;
        }
        System.out.print("\n");
    }

    public void loseLife(Deck deckOfCards) {
        playerLife--;
        Card randomCard = null;
        // return random card from playerHand to the deck
        if (playerLife != 0) {
            int randomIndex = new Random().nextInt(playerLife);
            randomCard = playerHand.remove(randomIndex);
        } else {
            randomCard = playerHand.remove(playerLife);
        }
        deckOfCards.returnCardToDeck(randomCard);
    }

}
