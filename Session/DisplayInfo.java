package Session;

import java.util.Random;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;

import Session.Map.MapController;
import Session.Map.Chip;
import StartUp.ImagePanel;
import Exceptions.UnimplementedException;

/** Does NOT follow MVC pattern 
 * Displays information about current session (next player move, what player won)
 * <b> Extends JPanel </b> 
 */
public class DisplayInfo extends JPanel implements Consumer<GameOverInfoArgs>
{
    private static final Random randomizer = new Random();

    /** A Map Controller it is binded to */
    private MapController _mapController;

    /** 
     * Convenient retreval of current layout without downcasting
     * @return Grid Layout of the current instance
     */
    public GridLayout getLayout() { return (GridLayout)this.getLayout(); }

    public DisplayInfo(MapController mapController)
    {
        super(new GridLayout());
        _mapController = mapController;;
        nonRunningDisplay();
    }

    /** Creates randomized text when the game is not running */
    private void nonRunningDisplay()
    {
        this.removeAll();
        
        String text;
        switch (randomizer.nextInt(3)) {
            default: text = "NO TEXT"; break;
            case 0: text = "Hi from devs! <3"; break;
            case 1: text = "Nothing interesting here"; break;
            case 2: text = "So...you want to play or not?"; break;
        }
        this.add(new JTextArea(text));
    }

    /**
     *  Displays the turns of the current and next player 
     * <b> IMPORTANT </b> in the future should use actual images, not colored panels!
     * */
    private void playerTurns() throws UnimplementedException
    {
        this.removeAll();
        getLayout().setColumns(2);
        
        JPanel currentTurnBox = new JPanel(new BorderLayout());
        JTextArea currentTurnText = new JTextArea("Moves:");
        ImagePanel currentTurnImage = new ImagePanel(Chip.getCorrectImage(_mapController.getCurrentPlayerTurn()));
        currentTurnBox.add(currentTurnText);
        currentTurnBox.add(currentTurnImage);
        this.add(currentTurnBox);

        JPanel nextTurnBox = new JPanel(new BorderLayout());
        JTextArea nextTurnText = new JTextArea("Next:");
        ImagePanel nextTurnImage = new ImagePanel(Chip.getCorrectImage(_mapController.getNextPlayerTurn()));
        nextTurnBox.add(nextTurnText);
        nextTurnBox.add(nextTurnImage);
        this.add(nextTurnBox);
    }
    
    /** Constructs and Assigns the message about who makes the move right now */
    public void buildCurrentTurnString() throws UnimplementedException
    {
        int nextPlayerTurn = _mapController.getNextPlayerTurn();
        throw new UnimplementedException();
    }

    /** <b> Runs when the Game Session is over </b> */
    @Override
    public void accept(GameOverInfoArgs gameOverInfoArgs)
    {
        this.removeAll();
        JTextArea jTextArea = new JTextArea();
        switch (gameOverInfoArgs._reason) {
            case GameOverInfoArgs.NO_FREE_SPACE:
                jTextArea.setText("TIE We've ran out of space!");
                break;
            case GameOverInfoArgs.PLAYER_WON:
                jTextArea.setText("Player #" + gameOverInfoArgs.getWinnerPlayerTurn() + " has won!");
                break;
            case GameOverInfoArgs.QUIT:
                jTextArea.setText("You Quit. Game is over");
                break;
            case GameOverInfoArgs.REASON_UKNOWN:
                jTextArea.setText("Game session was ended for uknown reason. Acess the console for further details");
                break;
        }

    }
}
