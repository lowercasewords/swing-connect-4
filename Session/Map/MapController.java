package Session.Map;

import java.util.function.Consumer;

import Exceptions.InvalidPlayerAmountException;
import Exceptions.UnimplementedException;
import Session.Arguments.Args;
import Session.Arguments.GameOverArgs;
import Session.Arguments.PlacedChipArgs;
import static StartUp.HelperLib.log;
/**
 * MVC Pattern in use!
 * Controlls its view and model by talking to the model which will affect the view
 */
public class MapController implements Consumer<Args>
{
    private MapView _view;
    private MapModel _model;

    // Get / Set methods
    public int getMaxPlayersTurn() { return _model.getMaxPlayers(); }
    public int getBoardSize() { return _model._boardSize; }
    public int getCurrentPlayerTurn() { return _model.getCurrentPlayerTurn(); }
    public int getNextPlayerTurn() { return _model.getNextPlayerTurn(); }
    public MapView getView() { return _view; }
    /* Adds a listener from the model */
    public void addModelListener(Consumer<Args> listener) { _model.addListener(listener); }
    /**
     * Creates a Map Controller (the brain of the Map that creates communication between View and Model)
     * @param view A Map View to bind with. Gets Map Model from the View
     */
    public MapController(MapView view)
    {
        this._view = view;
        this._model = view.getModel();
        _model.addListener(this);
    }
    
    /**
     * Starts the game by invoking view and model corresponding start methods
     * @param playerCount Amount of Active Players
     * @param row Amount of rows in the map
     * @param cols Amount of cols in the map
     * @throws TooManyPlayersException 
     */
    public void startSession(int playerCount)
    {
        try {
            _model.restartBoard(playerCount);
            _view.visualizeBoard();
            log("Player amount is right");
            
        } catch (InvalidPlayerAmountException e) {
            System.out.print("Player Amount is invaild, try again");
        }

    }

    /** Handles the Producer notifications by downcasting args  */
    @Override
    public void accept(Args args) {
        log("view accept was invoked");
        if(args instanceof GameOverArgs)
        {
            GameOverArgs gameOverArgs = (GameOverArgs)args;
            try {
                _view.endSessionVisualizer(gameOverArgs);
            } catch (Exception e) {
                log(e);
            }
        }
        else if(args instanceof PlacedChipArgs)
        {
            log("args in view accept was downcasted to PlacedChipsArgs type");
            PlacedChipArgs placedChipArgs = (PlacedChipArgs)args;
            _view.addChipVisually(
                placedChipArgs.getChip(),
                placedChipArgs.getRow(),
                placedChipArgs.getCol());
        }
    }
    
}