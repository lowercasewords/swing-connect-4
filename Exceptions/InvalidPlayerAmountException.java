package Exceptions;
import Session.Map.MapModel;
public class InvalidPlayerAmountException extends Exception{
    /** Creates InvalidPlayerAmountException object*/
    public InvalidPlayerAmountException(MapModel mapModel, int playerCount)
    {
        super(playerCount + 
        " is invalid amount of players for the session, should be more than " + 
        mapModel.getMinPlayers() + 
        " and less than " + 
        mapModel.getMaxPlayers());
    }
}
