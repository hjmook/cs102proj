import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class StartMenu {
    private int numOfPlayers;
    private ArrayList<Player> players = new ArrayList<>();

    public ArrayList<Player> getPlayers() {
        Object[] nums = { 2, 3, 4 };
        numOfPlayers = (Integer) JOptionPane.showInputDialog(
                null,
                "Select number of players:",
                "HUNDRED FOR NOW",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nums,
                4);

        for (int i = 0; i < numOfPlayers; i++) {
            String playerNum = Integer.toString(i+1);
            String playerName = (String) JOptionPane.showInputDialog(null,
                    "Enter player " + playerNum + " name:", 
                    "HUNDRED FOR NOW", 
                    JOptionPane.PLAIN_MESSAGE);
            Player player = new Player(playerName, i);
            players.add(player);
        }

        return players;
    }
}
