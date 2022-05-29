package Session.Map;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * Does <b>NOT</b> follow MVC pattern!
 * Represents a singlechip that is automatically binded to the certain player via _playerCount upon instantiation.
 * <b>Extends JPanel</ b>
 */
public class Chip extends JPanel
{
  public static final int MAX_TURN_COUNTER = MapModel.MAX_PLAYERS;
  public static final int MIN_TURN_COUNTER = 1;

  /** *Kkeeps tracks of turns among all chips, a turn represents */
  private static int _nextPlayerTurn = MIN_TURN_COUNTER;
  /** Assigned player turn to the Chip instance */
  private int _playerTurn = _nextPlayerTurn;
  /** Color of the current Chip instance */
  private Color _chipColor;


  public Color getChipColor() { return _chipColor; }

  /** @return a player turn associated with Chip instance (whose player the chip is) */
  public int getPlayerTurn() { return _playerTurn; }  
  
  /** @return an overall count player turns */
  public static int getPlayerTurnCounter() { return _nextPlayerTurn; }


  /** Instantiates a Chip, assings the current STATIC NextPlayer Turn and increments the STATIC NextPlayerTurn */
  public Chip() 
  {
    _playerTurn = _nextPlayerTurn;
    // tries to color a chip according to the turn
    try { colorChip(); } 
    catch (Exception ex) { System.out.println(ex); }

    // assings and ascends the player turn
    if(_nextPlayerTurn > MAX_TURN_COUNTER) 
    { _nextPlayerTurn = MIN_TURN_COUNTER; } 
    else { _nextPlayerTurn++; }
  }

  /***  Sets the color of a chip based on the player turn! */
  private void colorChip() throws Exception
  {
      switch (_playerTurn) {
        case 1:
          _chipColor = Color.red;
          break;
        case 2:
          _chipColor = Color.yellow;
          break;
        case 3:
          _chipColor = Color.green;
          break;
        case 4:
          _chipColor = Color.white;
        default:
        _chipColor = Color.black;
          throw new Exception(_playerTurn + " is not a case in the current method. Did you expand maximum amount of players without expanding this method?");
      }
  }
}