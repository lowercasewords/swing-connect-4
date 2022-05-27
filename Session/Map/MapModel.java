package Session.Map;

import javax.swing.JToggleButton;
import javax.swing.*;

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

  public static final int WIN_CONNECTIONS = 4;

  public static final int MAX_PLAYERS = 4;
  public static final int MAX_ROWS = 14;
  public static final int MAX_COLS = 14;

  public static final int MIN_PLAYERS = 2;
  public static final int MIN_ROWS = 7;
  public static final int MIN_COLS = 7;

  // Ensuring SingleTon Pattern
  
  public int getPlayerCount() { return _playerCount; }
  public int getRows() { return _rows; }
  public int getCols() { return _cols; }
  public Chip[][] getGameBoard() { return _gameBoard; }
  public Chip getChip(int row, int col) { return _gameBoard[row][col]; }
  
  /**
   * Prepares the board logic 
   * @param playerCount player count for current session
   * @param rows amount of rows for a current session
   * @param cols amount of cols for a current session
   * @throws InvalidPlayerAmountException
   */
  public void startSession(int playerCount, int rows, int cols) throws InvalidPlayerAmountException
  {
    _gameBoard = new Chip[rows][cols];
    if(playerCount > MAX_PLAYERS) 
    {
      _playerCount = playerCount;
    }  
    else
    {
      // raises the exception, passing current model instance as an argument
      throw new InvalidPlayerAmountException(this);
    }
    System.out.println("The session has been started");
  }
  /**
   * Resets the logic
   */
  public void endSession()
  {
    System.out.println("The session has ended");
  }

  /**
   * Places a chip in the boar
   * @param col in what column should chip be placed
   * @return returns getTurnCounter() if a player who placed chip has won
   * @throws NullPointerException when try accessing a non-existing column
   */
  public int placeChip(int col)
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
              if(_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
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
              if(_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
              { con++; }
              else { break; }
              tempCol--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            return Chip.getTurnCounter();
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
              if(_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
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
              if(_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
              { con++; }
              else { break; }
              tempRow--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            return Chip.getTurnCounter();
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
              if(_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
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
              if(_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
              { con++; }
              else { break; }
              tempCol--;
              tempRow++;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            return Chip.getTurnCounter();
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
              if(_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
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
              if(_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
              { con++; }
              else { break; }
              tempCol--;
              tempRow--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            return Chip.getTurnCounter();
          }
        break;
        default:
          throw new NullPointerException();
      }
    }
    return 0;
  }

  public class Chip
  {
    public static final int MAX_TURN_COUNTER = MAX_PLAYERS;
    public static final int MIN_TURN_COUNTER = 1;
    
    /** *Kkeeps tracks of turns among all chips, a turn represents */
    private static int _playerTurnCounter = MIN_TURN_COUNTER;
    /** * Assigned player turn to the Chip Instance */
    private int _playerTurn = _playerTurnCounter;

    /**
     * binds the the chip instance to the turn number, then increments the turn counter
     */
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
}