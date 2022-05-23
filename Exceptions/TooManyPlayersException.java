package Exceptions;
import Session.Map.*;
public class TooManyPlayersException extends Exception{
    public TooManyPlayersException(int players, Session.Map.MapModel mapModel)
    {
        super(players + " is too much for the current game, should exceed " + mapModel.getPlayerCount());
    }
}
