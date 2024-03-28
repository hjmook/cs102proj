import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private int cardWidth = 110;
    private int cardHeight = 154;
    private int numOfPlayers;
    private Deck deck;
    private Player player;
    private DiscardPile discardPile;

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        try {
            graphics.setFont(new Font("Arial", Font.BOLD, 28));
            graphics.drawString(player.getPlayerName() + "'s Turn", 50, 50);

            // Main Player
            JButton[] cardArr = new JButton[4];
            for (int i = 0; i < 4; i++) {
                Card card = player.getCardFromHand(i);
                Image cardImg = new ImageIcon(getClass().getResource(card.getImgPath())).getImage()
                        .getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(cardImg);
                cardArr[i] = new JButton(imageIcon);
                cardArr[i].setName(Integer.toString(i));
                cardArr[i].setBounds(372 + (cardWidth + 5) * i, 600, cardWidth, cardHeight);
                this.add(cardArr[i]);

                cardArr[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();

                        Card discardedCard = player.getCardFromHand(Integer.parseInt(button.getName()));
                        player.removeCardFromHand(Integer.parseInt(button.getName()));
                        System.out.print(discardedCard.toString());
                        discardPile.addToDiscardPile(discardedCard);

                        Card newCard = deck.drawRandomCard();
                        player.addCardToHand(newCard);
                        Image newCardImg = new ImageIcon(getClass().getResource(newCard.getImgPath())).getImage()
                                .getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
                        ImageIcon newImageIcon = new ImageIcon(newCardImg);
                        button.setIcon(newImageIcon);

                        repaint();
                    }
                });
            }

            // Other players
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

            // Discard Pile
            if (discardPile.sizeOfDiscardPile() > 0) {
                Image cardImg = new ImageIcon(getClass().getResource(discardPile.getLastdiscardedCard().getImgPath())).getImage();
                graphics.drawImage(cardImg, 540, 300, cardWidth, cardHeight, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GamePanel(int numOfPlayers, Player player, Deck deck, DiscardPile discardPile) {
        this.numOfPlayers = numOfPlayers;
        this.player = player;
        this.deck = deck;
        this.discardPile = discardPile;
        this.setLayout(null);
        this.setBackground(new Color(53, 101, 77));
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
            graphics.drawImage(cardImg, 372 + (cardWidth + 5) * j, 50, cardWidth, cardHeight, null);
        }
    }

    public void drawPosition4(Graphics graphics, int cardWidth, int cardHeight) {
        for (int i = 0; i < 4; i++) {
            Image cardImg = new ImageIcon(getClass().getResource("./cards/card-imgs/BACK-rotate.png")).getImage();
            graphics.drawImage(cardImg, 945, 175 + (cardWidth + 5) * i, cardHeight, cardWidth, null);
        }
    }
}
