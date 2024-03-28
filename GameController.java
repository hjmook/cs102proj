import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GameController {
    private ArrayList<Player> players;
    private int numOfPlayers;

    public GameController() {
        startGame();
    }

    public void startGame() {

        StartMenu startMenu = new StartMenu();
        players = startMenu.getPlayers();
        numOfPlayers = players.size();

        Deck deck = new Deck();
        deck.shuffleDeck();

        DiscardPile discardPile = new DiscardPile();

        // Deal Hand
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                Card randomCard = deck.drawRandomCard();
                player.addCardToHand(randomCard);
            }
        }

        buildBoard(0, deck, discardPile);
    }

    public void buildBoard(int playerNumber, Deck deck, DiscardPile discardPile) {
        int boardWidth = 1200;
        int boardHeight = 1000;

        Player player = players.get(playerNumber);
        JFrame frame = new JFrame("Hundred For Now");
        GamePanel gamePanel = new GamePanel(numOfPlayers, player, deck, discardPile);

        frame.add(gamePanel);
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
