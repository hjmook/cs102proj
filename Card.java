
public class Card {
    // attributes of Cards: (1) suit (2) value
    private String suit;
    private String rank;
    private int value;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getValue() {
        switch (rank) {
            case "Ace":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
                return 10;
            case "J":
                return 10;
            case "Q":
                return 20;
            // King is straight to 100, don't need to add any value
           default: 
                return 100;
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    // New equals method to compare cards by suit and rank to determine equality
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Card)){
            return false;
        }
        Card otherCard = (Card)o;
        if (this.rank.equals(otherCard.rank) && this.suit.equals(otherCard.suit)){
            return true;
        }
        return false;
    }
}
