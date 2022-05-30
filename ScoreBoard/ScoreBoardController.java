package ScoreBoard;

/**
 * MVC Pattern in use!
 * Controlls its view and model by talking to the model which will affect the view
 */
public class ScoreBoardController 
{
    private ScoreBoardView _view;
    private ScoreBoardModel _model;

    public ScoreBoardController(ScoreBoardView view)
    {
        _view = view;
        _model = view.getModel();
    }
}
