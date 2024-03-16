import java.util.*;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();
        String[] suits = {"D", "C", "H", "S"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String suit : suits) {
            for (String value : values) {
                Card card = new Card(suit, value);
                deck.add(card);
            }
        }
    }

    public void displayDeck() {
        for (Card card : deck) {
            System.out.println(card);
        }
    }

    public void shuffleDeck() {
        Random rand = new Random();
        // fisher-yates shuffle
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    public int sizeOfDeck() {
        return deck.size();
    }

    public void returnCardToDeck(Card card) {
        deck.add(card);
    }

    public Card drawTopCard() {
        return deck.get(0);
    }

    public Card drawRandomCard() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(deck.size());
        // removes the drawn card from the deck as well
        Card drawnCard = deck.remove(randomIndex);
        return drawnCard;
    }
}