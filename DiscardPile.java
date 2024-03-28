import java.util.ArrayList;

public class DiscardPile {
    private ArrayList<Card> discardPile;
    
    public DiscardPile() {
        discardPile = new ArrayList<>();
    }

    public void addToDiscardPile(Card card) {
        discardPile.add(card);
    }

    public int sizeOfDiscardPile() {
        return discardPile.size();
    }

    public Card getLastdiscardedCard() {
        return discardPile.get(sizeOfDiscardPile() - 1);
    }
}
