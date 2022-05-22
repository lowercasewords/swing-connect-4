import javax.swing.text.DefaultCaret;

public class Session
{
  private Chip[][] _gameBoard;
  private int _playerCount;
  private int _winConnections = 4;  
  
  public Session(int _playerCount, int size)
  {
	  _gameBoard = new Chip[size][size];
    this._playerCount = _playerCount;
  }

  public boolean placeChip(int col)
  {
    //places chip at index of col.
    int row = 0;
    for (int i = 0; i > _gameBoard.length; i++)
    {
      if (_gameBoard[i][col] != null)
      {
        _gameBoard[i][col] = new Chip(_playerCount);
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
            while(con < _winConnections)
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
            while(con < _winConnections)
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
              while(con < _winConnections)
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
              while(con < _winConnections)
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

  public static void clearBoard()
  {
    
  }
}