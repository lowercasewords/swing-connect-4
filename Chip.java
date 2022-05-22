
public class Chip
{
  public static final char RED = 'R';
  public static final char YELLOW = 'Y';
    public static final char GREEN = 'G';
    public static final char PURPLE = 'P';

  private static int _turnCounter = 0;
  private int _turn = _turnCounter;
  private char _color;
  
  public Chip(int playerCount)
  {
    if(_turnCounter > playerCount) 
    {
      _turnCounter = 1;
    } 
    else 
    {
      _turnCounter++;
    }
    _turn = _turnCounter;
  }

  public char getColor()
  {
    return _color;
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