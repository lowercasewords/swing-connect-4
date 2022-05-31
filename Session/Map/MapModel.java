package Session.Map;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.Set;
import Exceptions.InvalidPlayerAmountException;
import Exceptions.InvalidPlayerWonReasonException;
import Exceptions.UnimplementedException;
import Session.GameOverInfoArgs;
/**
 * MVC Pattern in use!
 * Talks to the database to retrieve the overal scores
*/
public class MapModel 
{
  public static final int WIN_CONNECTIONS = 4;

  public static final int MAX_PLAYERS = 4;
  public static final int MIN_PLAYERS = 2;

  public static final int MAX_ROWS = 14;
  public static final int MIN_ROWS = 7;
  public static final int MAX_COLS = 14;
  public static final int MIN_COLS = 7;

  private static final int MAX_PLAYER_TURN = MAX_PLAYERS;
  private static final int MIN_PLAYER_TURN = 1;

  /** Container of chips on the game board */
  private Chip[][] _gameBoard;
  private int _playerCount;
  /** Number of rows in the game board */
  private int _rows;
  /** Number of columns in the game board */
  private int _cols;
  /** Buttons to click to try summon a Chip */
  private JButton[][] _mapButtons;
  /** The turn of player whose move is awaited*/
  private int _currentPlayerTurn = MIN_PLAYER_TURN;
  /** The turn of the next player who will make a move  */
  private int _nextPlayerTurn = _currentPlayerTurn + 1;

  // Get / Set methodsurn _nextPlayerTurn; }
  public int getPlayerCount() { return _playerCount; }
  public int getRows() { return _rows; }
  public int getCols() { return _cols; }
  public Chip getChip(int row, int col) { return _gameBoard[row][col]; }
  public Chip[][] getGameBoard() { return _gameBoard; }
  public int getCurrentPlayerTurn() { return _currentPlayerTurn; }
  public int getNextPlayerTurn() { return _nextPlayerTurn; }
  
  /** Collection of Event Listeners that implement a Consumer interface.
   *  If the game comes to an end, all listeners would be notified and called upon */
  private Set<Consumer<GameOverInfoArgs>> listeners = new HashSet<Consumer<GameOverInfoArgs>>();

  /** Call to add an Event Listener*/
  public void addListener(Consumer<GameOverInfoArgs> listener)
  { listeners.add(listener); }

  /** Invokes the <b>accept method</b> in listeners*/
  public void gameOverNotify(GameOverInfoArgs args) 
  { listeners.forEach(x -> x.accept(args)); }

  /** Provides the instructions for the map buttons */ 
  private class ButtonListener implements ItemListener 
  {
    private final int _row;
    private final int _col;
    public ButtonListener(int row, int col)
    {
      this._row = row;
      this._col = col;
    }
    /** Executes automatically on button click:
     *  tries to place a chip in correct place, disables the button if the chip is at its spot */
    @Override
    public void itemStateChanged(ItemEvent e){
      // placeChip(_col);
      if(_gameBoard[_row][_col] == null){
        _gameBoard[_row][_col].setEnabled(false);
      }
    }
  }
  
  /** Progresses the player turn in the loop manner */
  private void progressPlayerTurn() throws UnimplementedException
  {
    _currentPlayerTurn = _nextPlayerTurn;
    if(_currentPlayerTurn >= MAX_PLAYER_TURN)
    {
      _currentPlayerTurn = MIN_PLAYER_TURN;
    }
    else{
      _currentPlayerTurn++;
    }
  }
  /**
   * Prepares the board logic and sets players up
   * @param playerCount player count for current session
   * @param rows amount of rows for a current session
   * @param cols amount of cols for a current session
   * @throws InvalidPlayerAmountException
   */
  public void prepareBoard(int playerCount, int rows, int cols) throws InvalidPlayerAmountException
  {
    // creates an empty Chip board array
    _gameBoard = new Chip[rows][cols];

    // ensures correct number of players 
    if
    (
      playerCount > MAX_PLAYERS)  { _playerCount = playerCount; 
    }  
    else
    {
      // raises the exception, passing current model instance as an argument
      throw new InvalidPlayerAmountException(playerCount);
    }
    // creates map buttons with functionallity
    _mapButtons = new JButton[rows][cols];
    for (int row = 0; row < rows; row++){
        for (int col = 0; col < cols; col++){
            _mapButtons[row][col] = new JButton();
            _mapButtons[row][col].addItemListener(new ButtonListener(row, col));
        }
    }
    // DEGUG LOG:
    System.out.println("The session has been started");
  }
  
  /** Destroyes the Chip Board board */
  public void destroyBoard() {
    for (int row = 0; row < _gameBoard.length; row++) {
      for (int col = 0; col < _gameBoard.length; col++) {
        _gameBoard[row][col] = null; 
      }
    }
    System.out.println("The session has ended");
  }
  
  /** Notifies Event Listeners if the board is full with 'No Free Space' reason. <b>Doesn't decide the winner!</b>*/
  private void isBoardFull() throws InvalidPlayerWonReasonException
  {
    for (int row = 0; row < _gameBoard.length; row++) {
      for (int col = 0; col < _gameBoard[row].length; col++) {
        if(_gameBoard[row][col] != null){
          return;
        }
      }
    }
    gameOverNotify(new GameOverInfoArgs(GameOverInfoArgs.NO_FREE_SPACE));
  }
  /**
   * Places a chip in the board, then checks for winner afterwards, then checks if the board is full. 
   * Notifies the Event Listeners if the game is over by the reasons listed before.
   * @param col in what column should chip be placed with the lowest possible row
   * @throws NullPointerException when try accessing a non-existing column
   * @return The row the Chip was placed in
  */
  public int placeChip(int col) throws NullPointerException, UnimplementedException
  {
    int row = 0;
    for (; row < _gameBoard.length; row++){
      if (_gameBoard[row][col] != null){
        _gameBoard[row][col] = new Chip(_currentPlayerTurn);
        progressPlayerTurn();
        break;
      }
    }
    checkWinner(col, row);
    try
    { isBoardFull(); }
    catch(InvalidPlayerWonReasonException ex)
    { System.out.println(ex); }
    return row;
  }

  /**
   * Checks for a winner. Alerts Event Listeners if the winner was decided
   * @param row row of the chip
   * @param col column of the chip
   */
  private void checkWinner(int row, int col)
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
        //horizontal 
        case 0:
          con = 0;
          tempCol = col;
          // right
          while(con < WIN_CONNECTIONS)
          {
            try 
            {
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == _currentPlayerTurn)
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
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == _currentPlayerTurn)
              { con++; }
              else { break; }
              tempCol--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            gameOverNotify(new GameOverInfoArgs(_currentPlayerTurn));
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
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == _currentPlayerTurn)
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
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == _currentPlayerTurn)
              { con++; }
              else { break; }
              tempRow--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            gameOverNotify(new GameOverInfoArgs(_currentPlayerTurn));
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
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == _currentPlayerTurn)
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
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == _currentPlayerTurn)
              { con++; }
              else { break; }
              tempCol--;
              tempRow++;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            gameOverNotify(new GameOverInfoArgs(_currentPlayerTurn));
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
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == _currentPlayerTurn)
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
              if(_gameBoard[tempRow][tempCol].getPlayerTurn() == _currentPlayerTurn)
              { con++; }
              else { break; }
              tempCol--;
              tempRow--;
            } catch (NullPointerException ex) { break; }
              catch (IndexOutOfBoundsException ex) { break; }
          }
          if (con >= WIN_CONNECTIONS)
          {
            gameOverNotify(new GameOverInfoArgs(_currentPlayerTurn));
          }
          break;
        default:
          throw new NullPointerException();
      }
    }
  }
}