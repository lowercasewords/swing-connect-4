package Session;

import java.util.Random;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.imageio.ImageIO;

import Session.Map.MapController;
import Session.Arguments.Args;
import Session.Arguments.GameOverArgs;
import Session.Arguments.PlacedChipArgs;
import Session.Map.Chip;

import StartUp.ImagePanel;
import Exceptions.UnimplementedException;

/** Does NOT follow MVC pattern 
 * Displays information about current session (next player move, what player won)
 * <b> Extends JPanel </b> 
 */
public class DisplayInfo extends ImagePanel implements Consumer<Args>
{
    private static final Random randomizer = new Random();

    private JPanel _currentTurnBox = new JPanel(new BorderLayout()); 
    private JTextArea _currentTurnText = new JTextArea("Moves Now:");
    private ImagePanel _currentTurnImage = new ImagePanel();    
    private JPanel _nextTurnBox = new JPanel(new BorderLayout());
    private JTextArea _nextTurnText = new JTextArea("Moves Next:");
    private ImagePanel _nextTurnImage = new ImagePanel();
    private JPanel _messagePanel;
    private JTextArea _messageText;
    /** A Map Controller it is binded to */
    private MapController _mapController;

    /** 
     * Convenient retreval of current layout without downcasting
     * @return Grid Layout of the current instance
     */
    // public GridLayout getLayout() { return (GridLayout)this.getLayout(); }

    public DisplayInfo(MapController mapController)
    {
        // super(new GridLayout());
        super(new BorderLayout());
        _mapController = mapController;
        _mapController.addModelListener(this);

        _messageText = new JTextArea();
        _messageText.setEnabled(false);

        _messagePanel = new JPanel(new BorderLayout());
        _messagePanel.add(_messageText, BorderLayout.CENTER);
        _messagePanel.revalidate();
        _messageText.repaint();

        this.add(_messagePanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        
        showDefault();
    }
    
    /** Creates randomized text when the game is not running */
    private void showDefault()
    {
        _messageText.setVisible(true);
        this.setBackground(Color.white);
        String text;
        switch (randomizer.nextInt(3)) {
            case 0: text = "Hi from devs! <3"; break;
            case 1: text = "Nothing interesting here"; break;
            case 2: text = "So...you want to play or not?"; break;
            default: text = "NO TEXT"; break;
        }
    }

    /**
     *  Displays the turns of the current and next player 
     */
    @Deprecated
    private void showPlayerTurns()
    {
        // this.removeAll();
        // this.revalidate();
        // this.repaint();
        
        // ((GridLayout)getLayout()).setColumns(2);
        // updateTurns();

        // this.add(_currentTurnBox);
        // this.revalidate();
        // this.repaint();
        // _currentTurnBox.add(_currentTurnText);
        // _currentTurnBox.add(_currentTurnImage);
        // _currentTurnBox.revalidate();
        // _currentTurnBox.repaint();

        // this.add(_nextTurnBox);
        // this.revalidate();
        // this.repaint();
        // _nextTurnBox.add(_nextTurnText);
        // _nextTurnBox.add(_nextTurnImage);
        // _nextTurnBox.revalidate();
        // _nextTurnBox.repaint();
    }
    @Deprecated
    private void updateTurns()
    {
        _currentTurnImage = new ImagePanel(Chip.getCorrectPath(_mapController.getCurrentPlayerTurn()));
        _nextTurnImage = new ImagePanel(Chip.getCorrectPath(_mapController.getNextPlayerTurn()));
    }

    @Override
    public String toString() { return "Display Component"; }
    
    /** <b> Runs when the Game Session is over </b> */
    @Override
    public void accept(Args args)
    {
        _messageText.setVisible(true);
        // Handle Game Over
        if(args instanceof GameOverArgs)
        {
            GameOverArgs gameOverArgs = (GameOverArgs)args;
            _messageText = new JTextArea();
            switch (gameOverArgs._reason) 
            {
                case GameOverArgs.NO_FREE_SPACE:    
                    _messageText.setText("TIE We've ran out of space!");
                    break;
                case GameOverArgs.PLAYER_WON:
                    _messageText.setText("Player #" + gameOverArgs.getWinnerPlayerTurn() + " has won!");
                    break;
                case GameOverArgs.QUIT:
                    _messageText.setText("You Quit. Game is over");
                    break;
                case GameOverArgs.REASON_UKNOWN:
                    _messageText.setText("Game session was ended for uknown reason. Acess the console for further details");
                    break;
                    
                }
            _messageText.revalidate();
            _messageText.repaint();
        }
        // To update the chip information accordingly 
        else if (args instanceof PlacedChipArgs) {
            PlacedChipArgs placedChipArgs =  (PlacedChipArgs)args;
            // _messageText.setVisible(false);
            _messageText.revalidate();
            _messageText.repaint();
            this.setImage(placedChipArgs.getChip().getImagePath());
            this.revalidate();
            this.repaint();
            // _message
        }
    }
}
