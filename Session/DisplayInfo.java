 package Session;

import java.util.Random;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.BorderLayout;

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
public class DisplayInfo extends JPanel implements Consumer<Args>
{
    private static final Random randomizer = new Random();

    private JPanel _currentTurnBox = new JPanel(new BorderLayout());
    private JTextArea _currentTurnText = new JTextArea("Moves Now:");
    private ImagePanel _currentTurnImage = new ImagePanel();    
    private JPanel _nextTurnBox = new JPanel(new BorderLayout());
    private JTextArea _nextTurnText = new JTextArea("Moves Next:");
    private ImagePanel _nextTurnImage = new ImagePanel();
    
    /** A Map Controller it is binded to */
    private MapController _mapController;

    /** 
     * Convenient retreval of current layout without downcasting
     * @return Grid Layout of the current instance
     */
    // public GridLayout getLayout() { return (GridLayout)this.getLayout(); }

    public DisplayInfo(MapController mapController)
    {
        super(new GridLayout());
        _mapController = mapController;
        _mapController.addModelListener(this);
        showDefault();
    }
    
    /** Creates randomized text when the game is not running */
    private void showDefault()
    {
        this.removeAll();

        String text;
        switch (randomizer.nextInt(3)) {
            case 0: text = "Hi from devs! <3"; break;
            case 1: text = "Nothing interesting here"; break;
            case 2: text = "So...you want to play or not?"; break;
            default: text = "NO TEXT"; break;
        }
        this.add(new JTextArea(text));
    }

    /**
     *  Displays the turns of the current and next player 
     */
    private void showPlayerTurns()
    {
        this.removeAll();
        ((GridLayout)getLayout()).setColumns(2);
        updateTurns();

        this.add(_currentTurnBox);
        _currentTurnBox.add(_currentTurnText);
        _currentTurnBox.add(_currentTurnImage);

        this.add(_nextTurnBox);
        _nextTurnBox.add(_nextTurnText);
        _nextTurnBox.add(_nextTurnImage);
    }
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
        // Handle Game Over
        if(args instanceof GameOverArgs)
        {
            GameOverArgs gameOverArgs = (GameOverArgs)args;
            this.removeAll();
            JTextArea jTextArea = new JTextArea();
            switch (gameOverArgs._reason) 
            {
                case GameOverArgs.NO_FREE_SPACE:
                    jTextArea.setText("TIE We've ran out of space!");
                    break;
                case GameOverArgs.PLAYER_WON:
                    jTextArea.setText("Player #" + gameOverArgs.getWinnerPlayerTurn() + " has won!");
                    break;
                case GameOverArgs.QUIT:
                    jTextArea.setText("You Quit. Game is over");
                    break;
                case GameOverArgs.REASON_UKNOWN:
                    jTextArea.setText("Game session was ended for uknown reason. Acess the console for further details");
                    break;
            }
        }
        // To update the chip information accordingly 
        else if (args instanceof PlacedChipArgs) {
            this.removeAll();
            showPlayerTurns();
        }
    }
}
