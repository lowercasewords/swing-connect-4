package Session.Arguments;
import Exceptions.InvalidPlayerWonReasonException;

/** Stores final information about the ending session, called upon the game over*/
public class GameOverArgs extends Args
{
    // Constants
    public static final String REASON_UKNOWN = "Reason Unknown";
    public static final String PLAYER_WON = "Player Won";
    public static final String NO_FREE_SPACE = "No Free Space";
    public static final String QUIT = "Quit";
    
    private int _winningPlayerTurn = 0;
    
    /** Reason for the session to end. Field is public due to it being immutable */
    public final String _reason;
    /** gets the turn of the winner */
    public int getWinnerPlayerTurn() { return _winningPlayerTurn; }

    /** Chooses a winning player. Player Won will be the reason for the session to end  */
    public GameOverArgs(int winningPlayerTurn)
    {
        this._winningPlayerTurn = winningPlayerTurn;
        this._reason = PLAYER_WON;
    }
    /**
     * Sets the reason for the game session to end, <b>cannot be 'Player Won'</b>
     * @param reason Reason for session to end
     * @throws InvalidPlayerWonReasonException Thrown when 'Player Won' was the reason, choose the winning player turn to set it as the reason!
     */
    public GameOverArgs(String reason) throws InvalidPlayerWonReasonException
    {
        if(reason == PLAYER_WON) { throw new InvalidPlayerWonReasonException();}
        this._reason = reason;
    }
}
