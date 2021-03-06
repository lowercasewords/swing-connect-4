package Session.Map;
import java.awt.Color;
import javax.swing.JPanel;
import StartUp.ImagePanel;
/**
 * Does <b>NOT</b> follow MVC pattern!
 * Represents a singlechip that is binded to the certain player
 */
public class Chip 
{
  // Constants
  public static final String RED = "Images/Red.png";
  public static final String YELLOW = "Images/Yellow.png"; 
  public static final String GREEN = "Images/Green.png"; 
  public static final String GRAY = "Images/Blue.png"; 
  public static final String UNKNOWN = "Images/missing.png"; 

  /** Determines to what player the Chip instance belongs to */
  private int _playerTurn;
  private String _imagePath;
  
  
  // Get / Set methods
  public int getPlayerTurn() { return _playerTurn; } 
  public String getImagePath() { return _imagePath; } 

  /** Creates a chip an image corresponding to the player turn*/
  public Chip(int playerTurn) 
  {
    _playerTurn = playerTurn;
    _imagePath = getCorrectPath(_playerTurn);
  }
  /**
   * Returns an image that matches the player turn
   * @param playerTurn which player's image should be retrieved
   * @return image that matches a player turn
   */
  public static String getCorrectPath(int playerTurn)
  {
    
    switch(playerTurn)
    {
      case 1: return RED;
      case 2: return YELLOW;
      case 3: return GREEN;
      case 4: return GRAY;
     default: return UNKNOWN;
    }
  }

  @Override
  public boolean equals(Object chip)
  {
    if(!(chip instanceof Chip)) { return false; }

    return _playerTurn == ((Chip)chip)._playerTurn;
  }
}