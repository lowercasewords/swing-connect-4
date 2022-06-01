package Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Session.Map.*;
/**
 * Doesn't follow MVC pattern
 * Configures overall settings and the upcoming session (e.g. player count, map size, sound and music)
 */
public class Settings extends JPanel
{
    // Constants
    private static final String RESTART_SIGN = "Restart";
    private static final String START_SIGN = "Start";
    
    // Buttons 
    private JButton _startSessionButton = new JButton();
    private JToggleButton _musicButton = new JToggleButton();
    private JToggleButton _soundButton = new JToggleButton();
    private JTextField _askPlayerCount;
    private JTextField _askRowCount;
    private JTextField _askColCount;

    private MapController _mapController;

    /** 
     * Creates an Settings object for a Session, and binds itself with MapController in order to affect the session
     * @param A Map Controller to bind with
    */
    public Settings(MapController mapController)
    {
        super(new FlowLayout(FlowLayout.TRAILING));
        _mapController = new MapController(new MapView(new MapModel()));
        // adding buttons to Settings Panel
        this.add(_startSessionButton);
        this.add(_musicButton);
        this.add(_soundButton);
        this.add(_askPlayerCount);
        this.add(_askRowCount);
        this.add(_askColCount);

        // providing logic to each button
        _musicButton.addItemListener(itemEvent  -> {
            int state = itemEvent.getStateChange();
            if(itemEvent.SELECTED == state) {
                _musicButton.setText("Music: on");
            }
            else
            {
                _musicButton.setText("Music: off");
            }
        });
        _soundButton.addItemListener(itemEvent  -> {
            int state = itemEvent.getStateChange();
            if(itemEvent.SELECTED == state) {
                _musicButton.setText("Sound: on");
            }
            else
            {
                _musicButton.setText("Sound: off");
            }
        });
      
        _askPlayerCount.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                String value = _askPlayerCount.getText();
                int l = value.length();
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
         });
        //  _askRowCount.addKeyListener(new KeyListener() {
        //      @Override
        //      public void keyPressed(KeyEvent e) {
        //         String value = _askPlayerCount.getText();
        //         int l = value.length();
        //         if (e.getKeyChar() > '0' && e.getKeyChar() <= '4') {
        //             _askPlayerCount.setEditable(true);
        //         } else {
        //             _askPlayerCount.setEditable(false);
        //         }
        //      }
        //      @Override
        //      public void keyTyped(KeyEvent e) {}
 
        //     @Override
        //     public void keyReleased(KeyEvent e) {}
             
        //  });
        _startSessionButton.addActionListener(x -> {
            _startSessionButton.setText(RESTART_SIGN);
            try {
                int playerCount = Integer.parseInt(_askPlayerCount.getText());
                int rows = Integer.parseInt(_askRowCount.getText());
                int cols = Integer.parseInt(_askColCount.getText());
                _mapController.startSession(playerCount, rows, cols);
            } catch (Exception e) {
                //TODO: handle exception
            }
            
        }); 
    }
}
