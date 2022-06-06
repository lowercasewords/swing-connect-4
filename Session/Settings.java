package Session;

import javax.swing.*;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Flow;

import Session.Map.*;
import Exceptions.InvalidPlayerAmountException;
import Exceptions.UnimplementedException;
import static StartUp.HelperLib.log;
/**
 * Doesn't follow MVC pattern
 * Configures overall and the upcoming session settings (e.g. player count, map size, sound and music)
 * <a href="https://www.tutorialsfield.com/jbutton-click-event/">Event Cheat Sheet</a>
 */
public class Settings extends JPanel
{   
    private static final String START_SIGN = "Start New Session";
    private static final String RESTART_SIGN = "Restart";
    private static final String MUSIC_ON = "Music: ON";
    private static final String MUSIC_OFF = "Music: OFF";
    private static final String SOUND_ON = "Sound: ON";
    private static final String SOUND_OFF = "Sound: OFF";
    
    
    /* Game Session being configured */
    private MapController _mapController;


    private JPanel _startSessionPanel = new JPanel(new GridLayout());
    private JButton _startSessionButton = new JButton(START_SIGN);
    

    private JPanel _overallSettingsPanel = new JPanel();
    private JToggleButton _musicSwitchButton = new JToggleButton(MUSIC_OFF);
    private JToggleButton _soundSwitchButton = new JToggleButton(SOUND_OFF);


    private JPanel _playerSettings = new JPanel(new BorderLayout());
    private JPanel _playerCountPanel = new JPanel(new GridLayout());
    private ButtonGroup _playerCountButtonGroup = new ButtonGroup();
    /* Radio buttons for choosing player amount */
    private JRadioPlayerCount[] _playerCountRadButtons;
    private JPanel _playerMessagePanel = new JPanel(new FlowLayout());
    private JTextPane _playerMessage = new JTextPane();
    
    
    /* Array of possible amounts of players */
    private static final int[] PLAYER_CHOICES = MapModel.getPlayerChoices();
    
    /** 
     * Creates an Settings object for a Session, and binds itself with MapController in order to affect the session.
     * @param A Map Controller to bind with
    */
    public Settings(MapController mapController)
    {
        super();
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        _mapController = mapController;

        //   Configuring Sound and Music parts
        //--------------------------------------------\\
        this.add(_overallSettingsPanel);
        _overallSettingsPanel.add(_musicSwitchButton);
        _overallSettingsPanel.add(_soundSwitchButton);
        // Implementing Sound & Music buttons
        _musicSwitchButton.addItemListener(click  -> {
            int state = click.getStateChange();
            if(ItemEvent.SELECTED == state) {
                _musicSwitchButton.setText(MUSIC_ON);
            }
            else
            {
                _musicSwitchButton.setText(MUSIC_OFF);
            }
        });
        _soundSwitchButton.addItemListener(click  -> {
            int state = click.getStateChange();
            if(ItemEvent.SELECTED == state) {
                _soundSwitchButton.setText(SOUND_ON);
            }
            else
            {
                _soundSwitchButton.setText(SOUND_OFF);
            }
        });
        //--------------------------------------------//
        
        //  Configuring StartSession parts
        //----------------------------------------------------------------------------------------\\
        // Adding Start Session components
        this.add(_startSessionPanel);
        _startSessionPanel.add(_startSessionButton);
        // Implementing the start session button 
        _startSessionButton.addActionListener(click -> {
            for(int i = 0; i < _playerCountRadButtons.length; i++)
            {
                if(_playerCountRadButtons[i].isSelected())
                {
                    _mapController.startSession(_playerCountRadButtons[i].getPlayerCount());
                    break;
                }
            }
        }); 
        //----------------------------------------------------------------------------------------//

        //  Configuring Player Settings part 
        //--------------------------------------------\\
        // Adding Player Settings components
        _playerMessage.setEditable(false);
        _playerMessage.setText("Select Players");
        _playerMessagePanel.add(_playerMessage, BorderLayout.CENTER);
        // Implementing Radio Buttons from MapModel to button group
        _playerCountRadButtons = new JRadioPlayerCount[PLAYER_CHOICES.length];
        for (int i = 0; i < PLAYER_CHOICES.length; i++)
        {
            JRadioPlayerCount radButton = new JRadioPlayerCount(PLAYER_CHOICES[i] +
                                                    " players", PLAYER_CHOICES[i]);
            _playerCountRadButtons[i] = radButton;
            radButton.addItemListener(click -> {
                if(click.getStateChange() == ItemEvent.SELECTED)
                {
                    
                    // radButton.setText("Selected!");
                }
                else
                {
                    // radButton.setText("Deselected");
                }
            });
            if(i == 0) { radButton.setSelected(true); }
            _playerCountButtonGroup.add(radButton);
            _playerCountPanel.add(radButton);
        }
        this.add(_playerSettings);
        _playerSettings.add(_playerMessagePanel);
        _playerMessagePanel.setBackground(Color.black);
        _playerMessagePanel.add(_playerMessage);
        this.add(_playerCountPanel);
        _playerSettings.add(_playerCountPanel);
        //--------------------------------------------\\
    }
    /* Used to store information about Player Count in each Radio button */
    private class JRadioPlayerCount extends JRadioButton{
        private int _playerCount;
        public int getPlayerCount() { return _playerCount; }
        public JRadioPlayerCount(String text, int playerCount) {
            super(text);
            _playerCount = playerCount;
        }
    }
}
