import java.util.*;

public class Deck {
    private ArrayList<Card> deckOfCards;

    public Deck() {
        deckOfCards = new ArrayList<Card>();
        String[] suits = {"Diamonds", "Hearts", "Clubs", "Spades"};
        String[] values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String suit : suits) {
            for (String value : values) {
                Card card = new Card(suit, value);
                deckOfCards.add(card);
            }
        }
    }

    public void displayDeck() {
        for (Card card : deckOfCards) {
            System.out.println(card);
        }
    }

    public void shuffleDeck() {
        Random rand = new Random();
        // fisher-yates shuffle
        for (int i = deckOfCards.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card temp = deckOfCards.get(i);
            deckOfCards.set(i, deckOfCards.get(j));
            deckOfCards.set(j, temp);
        }
    }

    public int sizeOfDeck() {
        return deckOfCards.size();
    }

    public void returnCardToDeck(Card card) {
        deckOfCards.add(card);
    }

    public Card drawTopCard() {
        return deckOfCards.get(0);
    }

    public Card drawRandomCard() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(deckOfCards.size());
        // removes the drawn card from the deck as well
        Card drawnCard = deckOfCards.remove(randomIndex);
        return drawnCard;
    }
}