import javax.swing.*;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.awt.*;
import java.lang.reflect.GenericDeclaration;

import javax.swing.*;

public class Main {
    private static Random _random = new Random();
    private static final int MIN_WINDOW_LENGTH = 700;
    private static final int MIN_WINDOW_WIDTH = 700;

    private JFrame _connectFourWindow = new JFrame("Connect - 4");
    private int gridRow = 7;
    private int gridCol = 7;
    public static void main (String[] args) 
    {
        Main main = new Main();
        main._connectFourWindow.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_LENGTH));
        main._connectFourWindow.setPreferredSize(new Dimension(2048, 2048));
        // main._connectFourWindow.setLayout();
        main._connectFourWindow.setVisible(true);
        
        JPanel gameField = new JPanel(new GridLayout(main.gridRow, main.gridCol));
        for(int i = 0; i < main.gridRow + main.gridCol; i++)
        {
            JButton button = new JButton();
            button.addActionListener(x -> {
            });
            // button.Color
        }
        main._connectFourWindow.add(gameField, BorderLayout.CENTER);
    }
}
