import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class GamePanel extends JPanel {
    private int cardWidth = 110;
    private int cardHeight = 154;
    private int numOfPlayers;
    private ArrayList<Card> hand;
    private Player player;

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setLayout(new BorderLayout());
        setBackground(new Color(53, 101, 77));
        try {
            graphics.setFont(new Font("Arial", Font.BOLD, 28));
            graphics.drawString(player.getPlayerName() +"'s Turn", 50, 50);
            // Main Players hand
            for (int i = 0; i < 4; i++) {
                Card card = hand.get(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImgPath())).getImage();
                graphics.drawImage(cardImg, 370 + (cardWidth + 5) * i, 600, cardWidth, cardHeight, null);
            }

            // Other Players hand
            if (numOfPlayers == 2) {
                drawPosition3(graphics, cardWidth, cardHeight);
            } else if (numOfPlayers == 3) {
                drawPosition2(graphics, cardWidth, cardHeight);
                drawPosition4(graphics, cardWidth, cardHeight);
            } else if (numOfPlayers == 4) {
                drawPosition2(graphics, cardWidth, cardHeight);
                drawPosition3(graphics, cardWidth, cardHeight);
                drawPosition4(graphics, cardWidth, cardHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GamePanel(int numOfPlayers, Player player) {
        this.numOfPlayers = numOfPlayers;
        this.player = player;
        this.hand = player.getHand();
    }

    public void drawPosition2(Graphics graphics, int cardWidth, int cardHeight) {
        for (int i = 0; i < 4; i++) {
            Image cardImg = new ImageIcon(getClass().getResource("./cards/card-imgs/BACK-rotate.png")).getImage();
            graphics.drawImage(cardImg, 100, 175 + (cardWidth + 5) * i, cardHeight, cardWidth, null);
        }
    }

    public void drawPosition3(Graphics graphics, int cardWidth, int cardHeight) {
        for (int j = 0; j < 4; j++) {
            Image cardImg = new ImageIcon(getClass().getResource("./cards/card-imgs/BACK.png")).getImage();
            graphics.drawImage(cardImg, 370 + (cardWidth + 5) * j, 50, cardWidth, cardHeight, null);
        }
    }

    public void drawPosition4(Graphics graphics, int cardWidth, int cardHeight) {
        for (int i = 0; i < 4; i++) {
            Image cardImg = new ImageIcon(getClass().getResource("./cards/card-imgs/BACK-rotate.png")).getImage();
            graphics.drawImage(cardImg, 945, 175 + (cardWidth + 5) * i, cardHeight, cardWidth, null);
        }
    }
}
