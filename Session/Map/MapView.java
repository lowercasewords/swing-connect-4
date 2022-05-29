package Session.Map;
import javax.swing.*;
import java.awt.*;
import Exceptions.InvalidPlayerAmountException;
/**
 * MVC pattern in use!
 * Displays the Map for the User by retrieving data from its Model.
 * Class is handled by its Controller. <b>Extends JPanel</b>
 */
public class MapView extends JPanel
{
    /** Stores the object of its model */
    private MapModel _model;
    /** Retrieves chips from the Model */
    private Chip[][] _chips;
    /** Is used to configure grd layout settings (amount of rows, columns, size, e.g.) */
    private GridLayout _gridLayout;
    /** Background color of View JPanel */
    public Color _backGroundColor = Color.BLACK;


    /** Creates a Map View that extends from JPanel with grid Layout */
    public MapView(MapModel mapModel)
    {
        super(new GridLayout());
        _chips = _model.getGameBoard();
        _model = mapModel;
        _gridLayout = (GridLayout)this.getLayout();
    }
    /** @return the model instance of the view */
    public MapModel getModel() { return _model; }
    
    /**
     * Creates the visuals of the board
     * @throws InvalidPlayerAmountException
     */
    public void visualizeBoard()
    {
        // changes the grid layout settings of the View
        _gridLayout.setRows(_chips.length);
        _gridLayout.setColumns(_chips[0].length);
    }

    /**
     * Updates the column of the board according to model
     * @param col A column to be updated
     */
    public void updateSingleCol(int col) throws Exception
    {
        for (int row = 0; row < _chips.length; row++) 
        {
        //    _chips[row][col].setSize(, 20);
        }
        throw new Exception("Not implemented yet!");
    }
    /*** Updates the visuals of the board */ 
    public void updateWholeBoard() throws Exception
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
        throw new Exception("Not implemented yet!");
    }
}