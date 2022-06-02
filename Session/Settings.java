package Session;

import javax.swing.*;

import Exceptions.UnimplementedException;

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
    private static final String START_SIGN = "Start";
    private static final String RESTART_SIGN = "Restart";
    private static final String MUSIC_ON = "Music: ON";
    private static final String MUSIC_OFF = "Music: OFF";
    private static final String SOUND_ON = "Sound: ON";
    private static final String SOUND_OFF = "Sound: OFF";
    
    // Buttons 
    private JButton _startSessionButton = new JButton(START_SIGN);
    private JToggleButton _musicButton = new JToggleButton(MUSIC_ON);
    private JToggleButton _soundButton = new JToggleButton(SOUND_ON);
    private JPanel _playerCount;
    private JTextField _askRowCount = new JTextField();
    private JTextField _askColCount = new JTextField();

    private MapController _mapController;

    /** 
     * Creates an Settings object for a Session, and binds itself with MapController in order to affect the session
     * @param A Map Controller to bind with
    */
    public Settings(MapController mapController) throws UnimplementedException
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
        // JLabel

        // providing logic to each button
        _musicButton.addItemListener(itemEvent  -> {
            int state = itemEvent.getStateChange();
            itemEvent.
            if(itemEvent.SELECTED == state) {
                _musicButton.setText(MUSIC_ON);
            }
            else
            {
                _musicButton.setText("off");
            }
        });
        _soundButton.addItemListener(itemEvent  -> {
            int state = itemEvent.getStateChange();
            if(itemEvent.SELECTED == state) {
                _soundButton.setText(SOUND_ON);
            }
            else
            {
                _soundButton.setText(SOUND_OFF);
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
