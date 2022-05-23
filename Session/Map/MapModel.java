package Session.Map;

import javax.swing.JToggleButton;
import javax.swing.text.DefaultCaret;

import Exceptions.TooManyPlayersException;
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
  private static final int WIN_CONNECTIONS = 4;
  private static final int MAX_PLAYERS = 4;
  private static final int MAX_ROWS = 14;
  private static final int MAX_COLS = 14;
  // Ensuring SingleTon Pattern
  
  public int getPlayerCount() { return _playerCount; }
  public int getRows() { return _rows; }
  public int getCols() { return _cols; }
  public Chip[][] getGameBoard() { return _gameBoard; }
  public Chip getChip(int row, int col) { return _gameBoard[row][col]; }
  
  public void startSession(int playerCount, int rows, int cols) throws TooManyPlayersException
  {
    _gameBoard = new Chip[rows][cols];
    if(playerCount > MAX_PLAYERS) 
    {
      _playerCount = playerCount;
    }  
    else
    {
      throw new TooManyPlayersException(playerCount, this);
    }
    System.out.println("The session has been started");
  }
  public void endSession()
  {
    System.out.println("The session has ended");
  }

  public boolean placeChip(int col)
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
    // check for connect-4 4 times
    for(int i = 0; i < 4; i++)
    {
      // number of connections (max = 4)
      int con = 0;
      try {
        switch(i)
        {
            //horizontal
          case 0:
            int tempCol = col;
            while(con < WIN_CONNECTIONS)
            {
              if (_gameBoard[row][tempCol].getTurn() == Chip.getTurnCounter())
              {
                con++;
              }
              else
              {
                break;
              }
              tempCol++;  
            }
            tempCol = col-1;
            while(con < WIN_CONNECTIONS)
            {
              if (_gameBoard[row][tempCol].getTurn() == Chip.getTurnCounter())
              {
                con++;
              }
              else
              {
                break;
              }
              tempCol--;
            }
            if (con >= 4)
            {
              return true;
            }
            break;
            // vertical
          case 1: 
            con = 0;
            tempCol = col;
            int tempRow = row;
            while(true)
            {
              while(con < WIN_CONNECTIONS)
              {
                if (_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
                {
                  con++;
                }
                else
                {
                  break;
                }
                tempRow++;
              }

              tempRow = row - 1;
              while(con < WIN_CONNECTIONS)
              {
                if (_gameBoard[tempRow][tempCol].getTurn() == Chip.getTurnCounter())
                {
                  con++;
                }
                else
                {
                  break;
                }
                tempRow--;
              }
              if (con >= 4)
              {
                return true;
              }
              break;
            } 
            // diagonal back slash
          case 2:
          break;
            // diagonal forward slash
          case 3:
            
          break;
          default:
            throw new IndexOutOfBoundsException();
        }
      } catch (IndexOutOfBoundsException ex)
      {
       
      }
    }
    return false;  
  }

  public class Chip extends JToggleButton
  {
    private static int _turnCounter = 0;
    private int _turn = _turnCounter;
    private char _color;
  
    public Chip()
    {
      if(_turnCounter > _playerCount) 
      {
        _turnCounter = 1;
      } 
      else 
      {
        _turnCounter++;
      }
      _turn = _turnCounter;
    }

    public int getTurn()
    {
      return _turn;
    }  

    public static int getTurnCounter()
    {
      return _turnCounter;
    }
  }
}