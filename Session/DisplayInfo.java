package Session;

import java.util.function.Consumer;

import javax.swing.JPanel;

import Exceptions.UnimplementedException;
import Session.Map.MapController;

/** Does NOT follow MVC pattern 
 * Displays information about current session (next player move, what player won)
 * <b> Extends JPanel </b> 
 */
public class DisplayInfo extends JPanel implements Consumer<GameOverInfoArgs>
{
    /** Displayed message to the user */
    private String displayString;
    private MapController _mapController;

    public DisplayInfo(MapController mapController)
    {
        _mapController = mapController;
    }

    /** Constructs and Assigns the message about who will make move next */
    public void buildNextTurnString() throws UnimplementedException
    {
        int nextPlayerTurn = _mapController.getNextPlayerTurn();
        throw new UnimplementedException();
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
        switch (gameOverInfoArgs._reason) {
            case GameOverInfoArgs.NO_FREE_SPACE:
                displayString = "TIE We've ran out of space!";
                break;
            case GameOverInfoArgs.PLAYER_WON:
                displayString = "Player #" + gameOverInfoArgs.getWinnerPlayerTurn() + " has won!";
                break;
            case GameOverInfoArgs.QUIT:
                displayString = "You Quit. Game is over";
                break;
            case GameOverInfoArgs.REASON_UKNOWN:
                displayString = "Game session was ended for uknown reason. Acess the console for further details";
                break;
        }

    }
}
