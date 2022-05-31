package Session.Map;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * Does <b>NOT</b> follow MVC pattern!
 * Represents a singlechip that is binded to the certain player
 * <b>Extends JPanel</b>
 */
public class Chip extends JPanel
{
  /** Determines to what player the Chip instance belongs to */
  private int _playerTurn;
  /** Color of the current Chip instance */
  private Color _chipColor;

  // Get / Set methods
  public Color getChipColor() { return _chipColor; }
  public int getPlayerTurn() { return _playerTurn; } 

  /** Instantiates a Chip, assings the current STATIC NextPlayer Turn and increments the STATIC NextPlayerTurn */
  public Chip(int playerTurn) 
  {
    _playerTurn = playerTurn;
    // tries to color a chip according to the turn, fails if the current turn wasn't implemented by color method
    try { setVisual(); } 
    catch (Exception ex) { System.out.println(ex); }
  }

  /***  Sets the color of a chip based on the player turn! */
  private void setVisual() throws Exception
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