package StartUp;

import org.w3c.dom.Text;
import javax.swing.*;
import Session.Settings;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.awt.*;
import java.lang.reflect.GenericDeclaration;
import java.lang.*;
import javax.swing.*;

public class Main implements Consumer{
    private static Random _random = new Random();
    private static final int MIN_WINDOW_LENGTH = 700;
    private static final int MIN_WINDOW_WIDTH = 700;

    private static JFrame _window = new JFrame("Connect - 4");
    
    public static void main (String[] args)
    {
      Main main = new Main();
      Publisher publisher = main.new Publisher();
      publisher.addListener(main);
      publisher.Invoke();
        // _window.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_LENGTH));
        // _window.setPreferredSize(new Dimension(2048, 2048));
        // _window.setVisible(true);
        // _window.setLayout(new BorderLayout());

      // Session.Map.MapModel connectFour = new Session.Map.MapModel();
    }
    public class Publisher
    {
      private Set<Consumer<EventArgs>> listeners = new HashSet();

      public void addListener(Consumer<EventArgs> listener) {
          listeners.add(listener);
      }
  
      public void broadcast(EventArgs args) {
          listeners.forEach(x -> x.accept(args));
      }
      public void Invoke()
      {
        broadcast(null);
      }
    }
    public class EventArgs
    {
      
    }
    @Override
    public void accept(Object t) {
      System.out.println("something happened!");
      
    }
}
