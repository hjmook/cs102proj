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

        for (Player player : players) {
            System.out.println(player.getPlayerName());
        }

        Deck deck = new Deck();
        deck.shuffleDeck();
        // Deal Hand
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                Card randomCard = deck.drawRandomCard();
                player.addCardToHand(randomCard);
            }
        }

        buildBoard(0);
    }

    public void buildBoard(int playerNumber) {
        int boardWidth = 1200;
        int boardHeight = 1000;
        int cardWidth = 110;
        int cardHeight = 154;

        Player player = players.get(playerNumber);
        JFrame frame = new JFrame("Hundred For Now");
        GamePanel gamePanel = new GamePanel(numOfPlayers, player);

        frame.add(gamePanel);
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void drawPosition2(Graphics2D graphics2d, int cardWidth, int cardHeight) {
        for (int i = 0; i < 4; i++) {
            Image cardImg = new ImageIcon(getClass().getResource("./cards/card-imgs/BACK-rotate.png")).getImage();
            graphics2d.drawImage(cardImg, 100, 175 + (cardWidth + 5) * i, cardHeight, cardWidth, null);
        }
    }

    public void drawPosition3(Graphics2D graphics2d, int cardWidth, int cardHeight) {
        for (int j = 0; j < 4; j++) {
            Image cardImg = new ImageIcon(getClass().getResource("./cards/card-imgs/BACK.png")).getImage();
            graphics2d.drawImage(cardImg, 375 + (cardWidth + 5) * j, 50, cardWidth, cardHeight, null);
        }
    }

    public void drawPosition4(Graphics2D graphics2d, int cardWidth, int cardHeight) {
        for (int i = 0; i < 4; i++) {
            Image cardImg = new ImageIcon(getClass().getResource("./cards/card-imgs/BACK-rotate.png")).getImage();
            graphics2d.drawImage(cardImg, 945, 175 + (cardWidth + 5) * i, cardHeight, cardWidth, null);
        }
    }


}
