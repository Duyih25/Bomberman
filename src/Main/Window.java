package Main;

import Main.Game;

import javax.swing.*;
import java.awt.*;

public class Window {
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        frame.setSize(new Dimension(width, height));

        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close when press X
        frame.setLocationRelativeTo(null); // set the window to the center
        frame.setVisible(true);
    }
}
