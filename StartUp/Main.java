package StartUp;
import javax.swing.*;
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
    
    public static void main (String[] args)
    {
        _window.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_LENGTH));
        _window.setPreferredSize(new Dimension(2048, 2048));
        _window.setVisible(true);
        _window.setLayout(new BorderLayout());
      
       MapController game = new MapController(new MapView(new MapModel()));
       Settings gameSettings = new Settings(game);
       ScoreBoardController scoreBoard = new ScoreBoardController(new ScoreBoardView(new ScoreBoardModel()));
    }
}
