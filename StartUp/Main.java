package StartUp;
import javax.swing.*;

import Exceptions.UnimplementedException;

import java.awt.*;
import java.util.Random;

import Session.DisplayInfo;
import Session.Settings;

import Session.Map.MapController;
import Session.Map.MapModel;
import Session.Map.MapView;
import ScoreBoard.*;
import static  StartUp.HelperLib.log;

/* Represents the whole application window */
public class Main extends JFrame
{
    private static final Random _random = new Random();
    private static final int WINDOW_LENGTH = 700;
    private static final int WINDOW_WIDTH = 700;
    
    public static void main (String[] args) throws UnimplementedException
    {
        Main main = new Main();
    }

    public Main()
    {
        super("Connect - 4");

        visualizeComponents();
    }
    private void visualizeComponents()
    {
        this.setSize(new Dimension(WINDOW_LENGTH, WINDOW_WIDTH));
        this.setLayout(new BorderLayout());
        this.setVisible(true);

       MapController gameBoard = new MapController(new MapView(new MapModel()));
       this.add(gameBoard.getView(), BorderLayout.CENTER);
    //    gameBoard.getView().setBackground(Color.green);
       gameBoard.getView().setPreferredSize(new Dimension(10,10));
       
       Settings settings = new Settings(gameBoard);
       settings.setBackground(Color.red);
       this.add(settings, BorderLayout.NORTH);   

    //    ScoreBoardController scoreBoard = new ScoreBoardController(new ScoreBoardView(new ScoreBoardModel()));
    //    scoreBoard.getView().setBackground(Color.black);
    //    this.add(scoreBoard.getView(), BorderLayout.WEST);
       
       DisplayInfo displayInfo = new DisplayInfo(gameBoard);
       displayInfo.setBackground(Color.CYAN);
       this.add(displayInfo, BorderLayout.EAST);
       
       this.revalidate();
       this.repaint();

       log(gameBoard.getView().getSize());
    }
}
