package Session.Map;
import java.awt.event.*;

import javax.swing.*;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.Set;
import java.util.HashMap;

import Exceptions.InvalidPlayerAmountException;
import Exceptions.InvalidPlayerWonReasonException;
import Session.Arguments.Args;
import Session.Arguments.GameOverArgs;
import Session.Arguments.PlacedChipArgs;
import static StartUp.HelperLib.log;
/**
 * MVC Pattern in use!
 * Responsible for the map logic 
*/
public class MapModel
{
  // Static Constants
  public static final int WIN_CONNECTIONS = 4;

  /**
   * Set Rules for size of the board depending on the player count 
   * <b>KEY</b>: player count
   * <b>VALUE</b/: board size
   * */
  private static final HashMap<Integer, Integer> BOARD_SIZES = new HashMap<Integer, Integer>() {{
    put(2, 7); put(3, 9); put(4, 14); }};
    
    
  /** Container of chips on the game board */
  private Chip[][] _mapChips;
  private int _playerCount;
    // Provides ability to traverse through players //
  //---------------------------\\
  private int _maxPlayerTurn;
  private int _minPlayerTurn = 1;
  //---------------------------//
  /** Rows and Columns of the board (they are equal) */
  public int _boardSize;
  /** Buttons to click to try summon a Chip */
  private JButton[][] _mapButtons;
  /** The turn of player whose move is awaited*/
  private int _currentPlayerTurn = _minPlayerTurn;
  /** The turn of the next player who will make a move  */
  private int _nextPlayerTurn = _currentPlayerTurn + 1;

  // Get / Set methods
  public int getPlayerCount() { return _playerCount; }
  public int getBoardSize() { return _boardSize; }
  public Chip getChip(int row, int col) { return _mapChips[row][col]; }
  public Chip[][] getMapChips() { return _mapChips; }
  public JButton[][] getMapButtons() { return _mapButtons; }
  public int getCurrentPlayerTurn() { return _currentPlayerTurn; }
  public int getNextPlayerTurn() { return _nextPlayerTurn; }
  public int getMaxPlayers() { return _maxPlayerTurn; }
  public int getMinPlayers() { return _minPlayerTurn; }
  public static int[] getPlayerChoices()
  {
    Integer[] integerArr = BOARD_SIZES.keySet().toArray(Integer[]::new);
    int[] toReturn = new int[integerArr.length];
    for (int i = 0; i < toReturn.length; i++) {
      toReturn[i] = integerArr[i].intValue();
    }
      return toReturn;
  }

    // Implementation of Producer/Consumer pattern
    //    Producer: (this) class that notifies Consumer classes when something interesting happened inside
    // of Producer, in other words, events. For example, an event could be raised when the game is over
    //    Consumer: any class that listens to Producer in order to handle the event. In this case, Consumer
    // must implement Consumer<GameOverInfoArgs> interface in order to receive and handle events 
  // --------------------------------------------------------------------------------------------\\
  /** Collection of Event Listeners that implement a Consumer interface.
   *  If the game comes to an end, all listeners would be notified and called upon */
  private Set<Consumer<Args>> listeners = new HashSet<Consumer<Args>>();

  /** Call to add an Event Listener*/
  public void addListener(Consumer<Args> listener) { listeners.add(listener); }

  /** Invokes the <b>accept method</b> in listeners*/
  public void notifyConsumers(Args args) { listeners.forEach(x -> x.accept(args)); }
  // --------------------------------------------------------------------------------------------//
  
  /** Provides the instructions for the map buttons */ 
  private class ButtonListener implements ActionListener 
  {
    private final int _row;
    private final int _col;
    public ButtonListener(int row, int col)
    {
      this._row = row;
      this._col = col;
    }
        
    /** Executes automatically on button click:
     *  tries to place a chip in correct place, disables the button if the chip is at its spot 
     * */
    @Override
    public void actionPerformed(ActionEvent e) {
      placeChip(_col);
      if(_mapChips[_row][_col] != null){
          _mapButtons[_row][_col].setEnabled(false);
        // log("Not enough place to put another chip");
        }
    }
  }
  
  /** Progresses the player turn in the loop manner */
  private void progressPlayerTurn()
  {
    _currentPlayerTurn = _nextPlayerTurn;
    _nextPlayerTurn++;
    
    if(_nextPlayerTurn > _maxPlayerTurn) { _nextPlayerTurn = _minPlayerTurn; }
  }
  /**
   * Prepares the board logic and sets players up from scratch
   * @param playerCount player count for current session
   * @param rows amount of rows for a current session
   * @param cols amount of cols for a current session
   * @throws InvalidPlayerAmountException
   */
  public void restartBoard(int playerCount) throws InvalidPlayerAmountException
  { 
    _playerCount = playerCount;
    _maxPlayerTurn = _playerCount;
    _currentPlayerTurn = _minPlayerTurn;
    _nextPlayerTurn = _currentPlayerTurn + 1;

    Integer boardSideSize = BOARD_SIZES.get(playerCount);

    if(boardSideSize == null)
    { throw new InvalidPlayerAmountException(this, playerCount); }
    
    _mapChips = new Chip[boardSideSize][boardSideSize];

    // creates map buttons with functionallity
    _mapButtons = new JButton[boardSideSize][boardSideSize];
    for (int row = 0; row < boardSideSize; row++){
        for (int col = 0; col < boardSideSize; col++){
            _mapButtons[row][col] = new JButton();
            _mapButtons[row][col].addActionListener(new ButtonListener(row, col));
        }
    }
  }
  
  /** Destroyes the Chip Board board */
  @Deprecated
  public void destroyBoard() {
    for (int row = 0; row < _mapChips.length; row++) {
      for (int col = 0; col < _mapChips.length; col++) {
        _mapChips[row][col] = null; 
      }
    }
    // log("The session has ended");
  }
  
  /** Notifies Event Listeners if the board is full with 'No Free Space' reason. <b>Doesn't decide the winner!</b>*/
  private void isBoardFull() throws InvalidPlayerWonReasonException
  {
    for (int row = 0; row < _mapChips.length; row++) {
      for (int col = 0; col < _mapChips[row].length; col++) {
        if(_mapChips[row][col] != null){
          return;
        }
      }
    }
    // Notifies all listeners about Game Over Event
    notifyConsumers(new GameOverArgs(GameOverArgs.NO_FREE_SPACE));
  }
  
  /**
   * Places a chip in the board and progreses the player turn,
   * notifying consumers if the chip was placed or the game is either over
   * @param col in what column should chip be placed with the lowest possible row
   * @throws NullPointerException when try accessing a non-existing column
   * @return The row the Chip was placed in
  */
  public int placeChip(int col) throws NullPointerException
  {
    int row = _mapChips.length - 1;
    for (; row > -1; row--)
    {
      // place the chip if possible and exit for loop
      if (_mapChips[row][col] == null)
      {
        Chip putChip = new Chip(_currentPlayerTurn);
        _mapChips[row][col] = putChip;
        // log("Chip was placed: ("+row+","+col+")");
        // notify about the placed chip
        notifyConsumers(new PlacedChipArgs(putChip, row, col));
        
        // check if the placed chip caused the game to end 
        if(checkWinner(row, col)) { 
          notifyConsumers(new GameOverArgs(putChip.getPlayerTurn()));  
        }
        // checkWinner(row, col);
        
        progressPlayerTurn();
        break;
      }
    }
    try
    { isBoardFull(); }
    catch(InvalidPlayerWonReasonException ex)
    { log(ex); } 
    return row;
  }
  /**
   * Checks if the chip at specific location has triggered the winner event
   * @param chipRow in what row the base chip is located
   * @param chipCol in wht column the base chip is located
   * @return whether the winner was decided
   */
  private boolean checkWinner(int baseRow, int baseCol)
  {
    Chip baseChip = _mapChips[baseRow][baseCol];
    // log("\t  " + String.format("Base chip: (%d,%d)", baseRow, baseCol));
    
    //   Check for winner Vertically
    //----------------------------------------------------------------------------------------------------\\
    // Default value of rows to be checked
    try {
      int rowCheck = -1;
      int vertConnections = 1;
      for (rowCheck = baseRow + 1; rowCheck < baseRow + WIN_CONNECTIONS; rowCheck++)
      {
        // log(String.format("Checking the: (%d,%d)", rowCheck, baseCol));
        if(baseChip.equals(_mapChips[rowCheck][baseCol]))
        {
          // log(vertConnections + " connections found");
          vertConnections++; 
        }
        if(vertConnections >= WIN_CONNECTIONS)
        {
          // log("winner found!");
          return true; 
        }
      }
    } catch (NullPointerException e) {
      // log(String.format("Chip (%d,%d) doesn't exist: cell is emty", rowCheck, baseCol));
    } catch(IndexOutOfBoundsException e) {
      // log(String.format("Chip (%d,%d) doesn't exist: off the board", rowCheck, baseCol));
    }
    //----------------------------------------------------------------------------------------------------//


    //  Check for winner Horizontally
    //---------------------------------------------------------------------\\
    int horizConnections = 1;
    // On first repeat, checks to the right; on the second, checks to the left
    for (int repeat = 0; repeat < 2; repeat++) 
    {
      // Default value of columns to be checked
      int colCheck = -1;
      // log(String.format("Repeating #%d", repeat + 1));
      try {
        // for loop checks right or left depending on the 'repeat' value above 
        for (
          colCheck = (repeat == 0 ? baseCol + 1 : baseCol - 1);
          (repeat == 0 ? colCheck < baseCol + WIN_CONNECTIONS : colCheck >= baseCol - WIN_CONNECTIONS);
          colCheck = (repeat == 0 ? colCheck+1 : colCheck-1))
        {
          // log(String.format("Checking the: (%d,%d)", baseRow, colCheck));
          if(baseChip.equals(_mapChips[baseRow][colCheck]))
          {
            horizConnections++; 
          }
          if(horizConnections >= WIN_CONNECTIONS)
          {
            // log("winner found!");
            return true; 
          }
        }
        // log("for-loop has ended");
      } catch (NullPointerException e) {
        // log(String.format("Chip (%d,%d) doesn't exist: cell is emty", baseRow, colCheck));
      } catch(IndexOutOfBoundsException e) {
        // log(String.format("Chip (%d,%d) doesn't exist: off the board", baseRow, colCheck));
      }
    }
    //---------------------------------------------------------------------//
    

    //  Check for winner diagonally: both 'backslash' (\) and 'forwardslash' (/)
    //----------------------------------------------------------------------------------------------------\\
    // diagonal back slash (\)
    int diagConnections = 0;
    int checkCol = baseCol;
    int checkRow = baseRow;
    // ascending (left + up)
    while(diagConnections < WIN_CONNECTIONS)
    {
      try 
      { 
        if(_mapChips[checkRow][checkCol].getPlayerTurn() == _currentPlayerTurn)
        { diagConnections++; }
        else { break; }
        checkCol--;
        checkRow++;
      } catch (NullPointerException ex) { break; }
        catch (IndexOutOfBoundsException ex) { break; }
    }

    checkCol = baseCol + 1;
    checkRow = baseRow - 1;
    // descending (right + down)
    while(diagConnections < WIN_CONNECTIONS)
    {
      try 
      { 
        if(_mapChips[checkRow][checkCol].getPlayerTurn() == _currentPlayerTurn)
        { diagConnections++; }
        else { break; }
        checkCol--;
        checkRow++;
      } catch (NullPointerException ex) { break; }
        catch (IndexOutOfBoundsException ex) { break; }
    }
    if (diagConnections >= WIN_CONNECTIONS)
    { return true; }

    // diagonal forward slash (/)
    diagConnections = 0;
    checkCol = baseCol;
    checkRow = baseRow;
    // ascending (right + up)
    while(diagConnections < WIN_CONNECTIONS)
    {
      try 
      { 
        if(_mapChips[checkRow][checkCol].getPlayerTurn() == _currentPlayerTurn)
        { diagConnections++; }
        else { break; }
        checkCol++;
        checkRow++;
      } catch (NullPointerException ex) { break; }
        catch (IndexOutOfBoundsException ex) { break; }
    }
    
    checkCol = baseCol - 1;
    checkRow = baseRow - 1;
    // descending (left + down)
    while(diagConnections < WIN_CONNECTIONS)
    {
      try 
      { 
        if(_mapChips[checkRow][checkCol].getPlayerTurn() == _currentPlayerTurn)
        { diagConnections++; }
        else { break; }
        checkCol--;
        checkRow--;
      } catch (NullPointerException ex) { break; }
        catch (IndexOutOfBoundsException ex) { break; }
      if (diagConnections >= WIN_CONNECTIONS)
      { return true; }
    }
    //----------------------------------------------------------------------------------------------------//
    return false;
  }
  
}