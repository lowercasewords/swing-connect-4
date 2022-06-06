package ScoreBoard;

import ScoreBoard.*;
import javax.swing.*;

/**
 * MVC pattern in use!
 * Displays the Score Board for the User by retrieving data from its Model.
 *  Class is handled by the Controller 
 */
@Deprecated
public class ScoreBoardView extends JPanel
{
    private ScoreBoardModel _model;

    public ScoreBoardView(ScoreBoardModel model)
    {
        _model = model;
    }

    public ScoreBoardModel getModel()
    {
        return _model;
    }
}