package Exceptions;
import Session.Map.MapModel;
public class InvalidPlayerAmountException extends Exception{
    /** Creates InvalidPlayerAmountException object*/
    public InvalidPlayerAmountException(int playerCount)
    {
        super(playerCount + 
        " is invalid amount of players for the session, should be more than " + 
        MapModel.MIN_PLAYERS + 
        " and less than " + 
        MapModel.MAX_PLAYERS);
    }
}
