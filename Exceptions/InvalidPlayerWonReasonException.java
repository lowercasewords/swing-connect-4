package Exceptions;

import Session.Arguments.GameOverArgs;

/** Thrown when trying to put PlayerWon as the reason for the session to end <b>manually</b>.
 *  To do that, pass the turn of the winning player*/
public class InvalidPlayerWonReasonException extends Exception
{
    /** Cannot put Player Won reason manually. 
     * Said reason is being called automatically when specifying a turn of the winning player */
    public InvalidPlayerWonReasonException()
    {
        super(GameOverArgs.PLAYER_WON + " reason cannot be chosen without manually, specify ");
    }
}
