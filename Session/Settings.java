package Session;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Session.Map.*;

import Exceptions.UnimplementedException;
/**
 * Doesn't follow MVC pattern
 * Configures overall and the upcoming session settings (e.g. player count, map size, sound and music)
 */
public class Settings extends JPanel
{   
    private static final String START_SIGN = "Start";
    private static final String RESTART_SIGN = "Restart";
    private static final String MUSIC_ON = "Music: ON";
    private static final String MUSIC_OFF = "Music: OFF";
    private static final String SOUND_ON = "Sound: ON";
    private static final String SOUND_OFF = "Sound: OFF";
    
    
    private JPanel _startSessionPanel;
    private JButton _startSessionButton = new JButton(START_SIGN);
    
    private JPanel _overallSettingsPanel;
    private JToggleButton _musicSwitchButton = new JToggleButton(MUSIC_OFF);
    private JToggleButton _soundSwitchButton = new JToggleButton(SOUND_OFF);

    private JPanel _playerCountPanel;
    private ButtonGroup _playerCountButtonGroup = new ButtonGroup();
    private JRadioButton[] _playerCountRadButton;
    // Game Session we configuring
    private MapController _mapController;

    /** 
     * Creates an Settings object for a Session, and binds itself with MapController in order to affect the session
     * @param A Map Controller to bind with
    */
    public Settings(MapController mapController) throws UnimplementedException
    {
        super(new FlowLayout(FlowLayout.TRAILING));
        _mapController = new MapController(new MapView(new MapModel()));

        // Adding and Configuring Start Session Part
        _startSessionPanel = new JPanel(new BoxLayout(_startSessionPanel, BoxLayout.Y_AXIS));
        this.add(_startSessionButton, BorderLayout.SOUTH);
        _startSessionPanel.add(_startSessionButton);

        // Adding and Configuring Sound and Music
        _overallSettingsPanel = new JPanel(new BoxLayout(_overallSettingsPanel, BoxLayout.Y_AXIS));
        _overallSettingsPanel.add(_musicSwitchButton);
        _overallSettingsPanel.add(_soundSwitchButton);
        
        // Adding and Configuring Player CountDeclaring 
        _playerCountPanel = new JPanel(new BoxLayout(_playerCountPanel, BoxLayout.Y_AXIS));
        

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
