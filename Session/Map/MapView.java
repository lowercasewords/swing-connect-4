package Session.Map;

import javax.management.modelmbean.ModelMBean;
import javax.swing.*;
import java.awt.*;

import Session.Map.MapModel.Chip;
import Exceptions.InvalidPlayerAmountException;
/**
 * MVC pattern in use!
 * Displays the Map for the User by retrieving data from its Model.
 *  Class is handled by its Controller 
 */
public class MapView extends JPanel
{
    /** Stores the object of its model */
    private MapModel _model;
    /** Visualizes chips retrieved from the Model */
    private Chip[][] _chips;
    /** Visualization of buttons */
    private JButton[][] _mapButtons;

    public static final Color CHIP_NONE = Color.WHITE;
    public static final Color CHIP_RED = Color.RED;
    public static final Color CHIP_YELLOW = Color.YELLOW;
    public static final Color CHIP_GREEN = Color.GREEN;
    public static final Color CHIP_CYAN = Color.CYAN;

    public Color backGroundColor = Color.BLACK;

    public MapView(MapModel mapModel)
    {
        super(new GridLayout());
        _chips = _model.getGameBoard();
        _model = mapModel;
    }
    
    public MapModel getModel()
    {
        return _model;
    }
    /**
     * Creates the visual EMPTY board
     * @throws InvalidPlayerAmountException
     */
    public void visualizeBoard()
    {
        _mapButtons = new JButton[_chips.length][_chips[0].length];
        GridLayout gridLayout = (GridLayout)this.getLayout();
        gridLayout.setRows(_chips.length);
        gridLayout.setColumns(_chips[0].length);
    }
    /**
     * 
     */
    public void updateSingleCol(int col)
    {
        for (int row = 0; row < _chips.length; row++) 
        {
            // switch(_chip[row][col])
            // {
            //     case value:
                    
            //         break;
            
            //     default:
            //         break;
            // }
        }
    }
    /**
     * Updates the visuals of the board
     */
    public void updateWholeBoard()
    {
        for (int row = 0; row < _chips.length; row++) {
            for (int col = 0; col < _chips[row].length; col++) {
                // switch (_chips[row][col].getPlayerTurn()) {
                //     case value:
                        
                //         break;
                
                //     default:
                //         break;
                // }
            }
        }
    }
}