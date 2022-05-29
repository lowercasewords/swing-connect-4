package Session.Map;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.Set;
import Exceptions.InvalidPlayerAmountException;
/**
 * MVC Pattern in use!
 * Talks to the database to retrieve the overal scores
*/
public class MapModel
{
  private Chip[][] _gameBoard;
  private int _playerCount;
  private int _rows;
  private int _cols;
  /** Visualization of buttons (the ones you click to summon a chip)*/
  private JButton[][] _mapButtons;

  public static final int WIN_CONNECTIONS = 4;
  public static final int MAX_PLAYERS = 4;
  public static final int MIN_PLAYERS = 2;
  public static final int MAX_ROWS = 14;
  public static final int MIN_ROWS = 7;
  public static final int MAX_COLS = 14;
  public static final int MIN_COLS = 7;

  private Set<Consumer<EventArgs>> listeners = new HashSet();
  public class EventArgs {}
  public void addListener(Consumer<EventArgs> listener)
  { listeners.add(listener); }
  public void broadcast(EventArgs args)
  {
    listeners.forEach(x -> x.accept(args));
  }

  public int getPlayerCount() { return _playerCount; }
  public int getRows() { return _rows; }
  public int getCols() { return _cols; }
  public Chip[][] getGameBoard() { return _gameBoard; }
  public Chip getChip(int row, int col) { return _gameBoard[row][col]; }
  
  /** Provides the instructions for the map buttons */ 
  private class ButtonListener implements ItemListener 
  {
    private final int _row;
    private final int _col;
    private final JButton _jButton;
    public ButtonListener(JButton jButton, int row, int col)
    {
      this._jButton = jButton;
      this._row = row;
      this._col = col;
      
    }
    /** Executes automatically on button click */
    @Override
    public void itemStateChanged(ItemEvent e){
      
    }
  }

  /**
   * Prepares the board logic 
   * @param playerCount player count for current session
   * @param rows amount of rows for a current session
   * @param cols amount of cols for a current session
   * @throws InvalidPlayerAmountException
   */
  public void startSession(int playerCount, int rows, int cols) throws InvalidPlayerAmountException
  {
    // creates an empty Chip board array
    _gameBoard = new Chip[rows][cols];

    // ensures correct number of players 
    if(playerCount > MAX_PLAYERS) 
    {
      _playerCount = playerCount;
    }  
    else
    {
      // raises the exception, passing current model instance as an argument
      throw new InvalidPlayerAmountException(this);
    }
    // creates map buttons with functionallity
    _mapButtons = new JButton[rows][cols];
    for (int row = 0; row < rows; row++)
    {
        for (int col = 0; col < cols; col++)
        {
            _mapButtons[row][col] = new JButton();
            final int buttonCol = col;
            _mapButtons[row][col].addItemListener(new ButtonListener(_mapButtons[row][col], row, col));
            
        }
    }
    // DEGUG LOG:
    System.out.println("The session has been started");
  }
  /**
   * Destroyes the board
   */
  public void endSession() throws Exception
  {
    for (int row = 0; row < _gameBoard.length; row++) {
      _gameBoard[row] = null;
    }
    System.out.println("The session has ended");
  }

  /**
   * Places a chip in the board and checks for winner afterwards
   * @param col in what column should chip be placed
   * @return returns a Player Turn of the player who won, returns 0 otherwise
   * @throws NullPointerException when try accessing a non-existing column
   */
  public int placeChip(int col) throws NullPointerException
  {
    //places chip at index of col.
    int row = 0;
    for (int i = 0; i > _gameBoard.length; i++)
    {
      if (_gameBoard[i][col] != null)
      {
        _gameBoard[i][col] = new Chip();
        row = i;
      }
    }
    return checkWinner(col, row);
  }

  /**
   * Checks a chip in specified position has winning amount of connections
   * @param row row of the chip
   * @param col column of the chip
   * @return winning turn counter (0 if chip haven't won)
   */
  private int checkWinner(int row, int col)
  {
    // check for connect-WIN_CONNECTIONS WIN_CONNECTIONS times
    for(int i = 0; i < WIN_CONNECTIONS; i++)
    {
      // number of connections (max = WIN_CONNECTIONS)
      int con = 0;
      int tempCol = 0;
      int tempRow = 0;
      
      switch(i)
      {
        //n 
        //horizontal 
        case 0:
          con = 0;
          tempCol = col;
          // right
          while(con < WIN_CONNECTIONS)
          {
            try 
            {
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == Chip.getPlayerTurnCounter())
              { con++; }
              else { break; }
              tempCol++;
            }
            catch (NullPointerException ex) { break; }
            catch (IndexOutOfBoundsException ex) { break; }
          }
          tempCol = col-1;
          // left
          while(con < WIN_CONNECTIONS)
          {
            try 
            {
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == Chip.getPlayerTurnCounter())
              { con++; }
              else { break; }
              tempCol--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            return Chip.getPlayerTurnCounter();
          }
          break;
          // vertical
        case 1: 
          con = 0;
          tempCol = col;
          tempRow = row;
          //up
          while(con < WIN_CONNECTIONS)
          {
            try 
            { 
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == Chip.getPlayerTurnCounter())
              { con++; }
              else { break; }
              tempRow++;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          
          tempRow = row - 1;
          // down
          while(con < WIN_CONNECTIONS)
          {
            try
            {
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == Chip.getPlayerTurnCounter())
              { con++; }
              else { break; }
              tempRow--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            return Chip.getPlayerTurnCounter();
          }
          break; 
          // diagonal back slash (\)
        case 2:
          con = 0;
          tempCol = col;
          tempRow = row;
          // ascending (left + up)
          while(con < WIN_CONNECTIONS)
          {
            try 
            { 
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == Chip.getPlayerTurnCounter())
              { con++; }
              else { break; }
              tempCol--;
              tempRow++;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }

          tempCol = col + 1;
          tempRow = row - 1;
          // descending (right + down)
          while(con < WIN_CONNECTIONS)
          {
            try 
            { 
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == Chip.getPlayerTurnCounter())
              { con++; }
              else { break; }
              tempCol--;
              tempRow++;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            return Chip.getPlayerTurnCounter();
          }
        break;
          // diagonal forward slash (/)
        case 3:
          con = 0;
          tempCol = col;
          tempRow = row;
          // ascending (right + up)
          while(con < WIN_CONNECTIONS)
          {
            try 
            { 
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == Chip.getPlayerTurnCounter())
              { con++; }
              else { break; }
              tempCol++;
              tempRow++;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          
          tempCol = col - 1;
          tempRow = row - 1;
          // descending (left + down)
          while(con < WIN_CONNECTIONS)
          {
            try 
            { 
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == Chip.getPlayerTurnCounter())
              { con++; }
              else { break; }
              tempCol--;
              tempRow--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            return Chip.getPlayerTurnCounter();
          }
        break;
        default:
          throw new NullPointerException();
      }
    }
    return 0;
  }
}