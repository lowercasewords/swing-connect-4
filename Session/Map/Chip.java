package Session.Map;
import Session.Map.MapModel;
public class Chip
{
  public static final int MAX_TURN_COUNTER = MapModel.MAX_PLAYERS;
  public static final int MIN_TURN_COUNTER = 1;
  
  /** *Kkeeps tracks of turns among all chips, a turn represents */
  private static int _playerTurnCounter = MIN_TURN_COUNTER;
  /** * Assigned player turn to the Chip Instance */
  private int _playerTurn = _playerTurnCounter;

  public Chip()
  {
    _playerTurn = _playerTurnCounter;
    if(_playerTurnCounter > MAX_TURN_COUNTER) 
    {
       _playerTurnCounter = MIN_TURN_COUNTER; 
    } 
    else 
    {
       _playerTurnCounter++; 
    }
  }

  /**
   * @return a player turn associated with Chip instance (whose player the chip is)
   */
  public int getPlayerTurn()
  {
    return _playerTurn;
  }  
  /**
   * @return an overall count player turns
   */
  public static int getPlayerTurnCounter()
  {
    return _playerTurnCounter;
  }
}