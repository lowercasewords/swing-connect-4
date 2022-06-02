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
public class Main 
{
    private static Random _random = new Random();
    private static final int MIN_WINDOW_LENGTH = 700;
    private static final int MIN_WINDOW_WIDTH = 700;

    private static JFrame _window = new JFrame("Connect - 4");
    
    public static void main (String[] args) throws UnimplementedException
    {
        _window.setPreferredSize(new Dimension(2048, 2048));

        _window.setMinimumSize(new Dimension(MIN_WINDOW_LENGTH, MIN_WINDOW_WIDTH));
        _window.setVisible(true);
        _window.setLayout(new BorderLayout());
        System.out.println(_window.getSize());

       MapController game = new MapController(new MapView(new MapModel()));
       _window.add(game.getView(), BorderLayout.CENTER);
       game.getView().setBackground(Color.green);
       
        Settings settings = new Settings(game);
        _window.add(settings, BorderLayout.EAST);
        settings.setBackground(Color.red);

       ScoreBoardController scoreBoard = new ScoreBoardController(new ScoreBoardView(new ScoreBoardModel()));
       _window.add(scoreBoard.getView(), BorderLayout.WEST);
       scoreBoard.getView().setBackground(Color.black);
       
       
       DisplayInfo displayInfo = new DisplayInfo(game);
       _window.add(displayInfo, BorderLayout.NORTH);
       displayInfo.setBackground(Color.yellow);
    }
}
