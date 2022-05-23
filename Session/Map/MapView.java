package Session.Map;

import javax.management.modelmbean.ModelMBean;
import javax.swing.*;
import java.awt.*;

import Session.Map.MapModel.Chip;
import Exceptions.TooManyPlayersException;

/**
 * MVC pattern in use!
 * Displays the Map for the User by retrieving data from its Model.
 *  Class is handled by its Controller 
 */
public class MapView extends JPanel
{
    // Provides Singleton
    private static MapView _instance = null;
    // Gets the object of its model
    private MapModel _model;
    private MapModel.Chip[][] chips;

    public static final Color CHIP_NONE = Color.WHITE;
    public static final Color CHIP_RED = Color.RED;
    public static final Color CHIP_YELLOW = Color.YELLOW;
    public static final Color CHIP_GREEN = Color.GREEN;
    public static final Color CHIP_CYAN = Color.CYAN;

    private MapView()
    {
        // 7x7 for a default grid
        super(new GridLayout(7, 7));
        setBackground(Color.black);
    }
    public void linkModel(MapModel mapModel)
    {
        _model = mapModel;
    }
    public static MapView getInstance()
    {
        if(_instance == null)
        {
            _instance = new MapView();
        }
        return _instance;
    }
    
    @Deprecated
    private void clearBoard()
    {
        
    }
    public void drawBoard() throws TooManyPlayersException
    {
        chips = _model.getGameBoard();
        for (int r = 0; r < _model.getRows(); r++)
        {
            for (int c = 0; c < _model.getCols(); c++)
            {
                Chip chip = chips[r][c];
                switch(chip)
                {
                    case null:
                        ((JToggleButton)chip).setForeground(CHIP_NONE);
                        break;
                    // more cases to be implemented
                    default:
                        throw new TooManyPlayersException(_model.getPlayerCount(), _model);
                }
            }
        }
    }
}
