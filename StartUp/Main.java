package StartUp;

import org.w3c.dom.Text;
import javax.swing.*;
import Session.Settings;
import java.util.Random;
import java.awt.*;
import java.lang.reflect.GenericDeclaration;
import java.lang.*;
import javax.swing.*;

public class Main {
    private static Random _random = new Random();
    private static final int MIN_WINDOW_LENGTH = 700;
    private static final int MIN_WINDOW_WIDTH = 700;

    private static JFrame _window = new JFrame("Connect - 4");
    
    public static void main (String[] args) throws Exception
    {
      System.out.println("something");
      throw new Exception();
        // _window.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_LENGTH));
        // _window.setPreferredSize(new Dimension(2048, 2048));
        // _window.setVisible(true);
        // _window.setLayout(new BorderLayout());

      // Session.Map.MapModel connectFour = new Session.Map.MapModel();
    }
}
