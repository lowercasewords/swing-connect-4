package Session.Map;

import Exceptions.TooManyPlayersException;

/**
 * MVC Pattern in use!
 * Controlls its view and model by talking to the model which will affect the view
 */
public class MapController
{
    private MapView _view;
    private MapModel _model;

    public MapController(MapView view, MapModel model)
    {
        this._view = view;
        this._model = model;

        view.linkModel(model);
    }

    public void startSession(int playerCount, int rows, int cols) throws TooManyPlayersException
    {
        _model.startSession(playerCount, rows, cols);
        _view.drawBoard();
    }

}