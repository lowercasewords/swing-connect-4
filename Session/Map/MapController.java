package Session.Map;

import java.util.function.Consumer;

import Exceptions.InvalidPlayerAmountException;
import Exceptions.UnimplementedException;
import Session.GameOverInfoArgs;
/**
 * MVC Pattern in use!
 * Controlls its view and model by talking to the model which will affect the view
 */
public class MapController implements Consumer<GameOverInfoArgs>
{
    private MapView _view;
    private MapModel _model;

    // Get / Set methods
    public int getMaxPlayers() { return MapModel.MAX_PLAYERS; }
    public int getMaxRows() { return MapModel.MAX_ROWS; }
    public int getMaxCols() { return MapModel.MAX_COLS; }
    public int getCurrentPlayerTurn() { return _model.getCurrentPlayerTurn(); }
    public int getNextPlayerTurn() { return _model.getNextPlayerTurn(); }
    public MapView getView() { return _view; }
    
    /**
     * Creates a Map Controller (the brain of the Map that creates communication between View and Model)
     * @param view A Map View to bind with. Gets Map Model from the View
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
        _model.restartBoard(playerCount, rows, cols);
        _view.visualizeBoard();
    }

    /**
     * Asks a player to make a move and updates the board accordingly
     * @param col
     * @throws InvalidPlayerAmountException 
     */
    public void makeMove(int col) throws InvalidPlayerAmountException
    {
        int row = _model.placeChip(col);
        _view.addVisualChip(row, col);
    }

    /** Handles the end of the Game Session using Game Over information */
    @Override
    public void accept(GameOverInfoArgs gameOverInfoArgs) {
        try {
            _view.endSessionVisualizer(gameOverInfoArgs);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}