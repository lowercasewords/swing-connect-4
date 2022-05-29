package Session.Map;

import Exceptions.InvalidPlayerAmountException;

/**
 * MVC Pattern in use!
 * Controlls its view and model by talking to the model which will affect the view
 */
public class MapController
{
    private MapView _view;
    private MapModel _model;

    public int getMaxPlayers() {return _model.MAX_PLAYERS; }
    public int getMaxRows() {return _model.MAX_ROWS; }
    public int getMaxCols() {return _model.MAX_COLS; }
    
    /**
     * Creates a Map Controller (the brain of the Map that creates communication between View and Model)
     * @param view A view to bind the controller with
     */
    public MapController(MapView view)
    {
        this._view = view;
        this._model = view.getModel();
    }
    
    /**
     * Starts the game by invoking view and model corresponding start methods
     * @param playerCount Amount of Active Players
     * @param row Amount of rows in the map
     * @param cols Amount of cols in the map
     * @throws TooManyPlayersException 
     */
    public void startSession(int playerCount, int rows, int cols) throws InvalidPlayerAmountException
    {
        _model.startSession(playerCount, rows, cols);
        _view.visualizeBoard();
    }

    /**
     * Asks a player to make a move and updates the board accordingly
     * @param col
     * @throws InvalidPlayerAmountException 
     */
    public void makeMove(int col) throws InvalidPlayerAmountException
    {
        int playerTurn = _model.placeChip(col);
    }
}