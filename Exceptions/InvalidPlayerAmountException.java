package Exceptions;
import Session.Map.*;
public class InvalidPlayerAmountException extends Exception{
    /**
     * Creates InvalidPlayerAmountException object
     * @param mapModel 
     */
    public InvalidPlayerAmountException(MapModel mapModel)
    {
        super(mapModel.getPlayerCount() + 
        " is invalid amount of players for the session, should be more than " + 
        mapModel.MIN_PLAYERS + 
        " and less than " + 
        mapModel.MAX_PLAYERS);
    }
}
