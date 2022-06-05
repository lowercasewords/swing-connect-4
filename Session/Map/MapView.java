package Session.Map;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import Exceptions.InvalidPlayerAmountException;
import Exceptions.UnimplementedException;
import Session.Arguments.*;
/**
 * MVC pattern in use!
 * Displays the Map for the User by retrieving data from its Model.
 * Class is handled by its Controller. <b>Extends JPanel</b>
 */
public class MapView extends JPanel implements Consumer<Args>
{
    /** Stores the object of its model */
    private MapModel _model;
    /** Retrieves chips from the Model */
    private Chip[][] _mapChips;
    /** Retrieves buttons from the Model */
    private JButton[][] _mapButtons;
    /** Is used to configure grd layout settings (amount of rows, columns, size, e.g.) */
    private GridLayout _gridLayout;
    /** Background color of View JPanel */
    public Color _backGroundColor = Color.BLACK;

    // Get / Set methods
    public MapModel getModel() { return _model; }

    /** Creates a Map View that extends from JPanel with grid Layout */
    public MapView(MapModel mapModel)
    {
        super(new GridLayout());
        _gridLayout = (GridLayout)getLayout();
        
        _gridLayout.setVgap(1);
        _gridLayout.setHgap(1);
        
        _model = mapModel;

        this.revalidate();
        this.repaint();
    }

    /** Handles what should be shown when the Game Session ends */
    public void endSessionVisualizer(GameOverArgs gameOverArgs) throws UnimplementedException
    {
        throw new UnimplementedException();
    } 
    /**
     * Creates the visuals of the board
     * @throws InvalidPlayerAmountException
     */
    public void visualizeBoard()
    {
        _mapChips = _model.getMapChips();
        _mapButtons = _model.getMapButtons();

        _gridLayout.setRows(_mapChips.length);
        _gridLayout.setColumns(_mapChips[0].length);

        for (int row = 0; row < _gridLayout.getRows(); row++) {
            for (int col = 0; col < _gridLayout.getColumns(); col++) {
                this.add(_mapButtons[row][col]);
                _mapButtons[row][col].setForeground(Color.black);
                _mapButtons[row][col].setBackground(Color.yellow);
                _mapButtons[row][col].revalidate();
                _mapButtons[row][col].repaint();
            }
        }
        System.out.println("Amount of buttons: " + (_mapButtons.length + _mapButtons[0].length));
        System.out.println("Buttons added to the visual board");
    }
    /** Adds a chip to visual board */
    public void addVisualChip(int row, int col)
    {
        this.add(_mapChips[row][col]);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void accept(Args args) {
        
       if(args instanceof GameOverArgs)
       {
           GameOverArgs GameOverArgs = ((GameOverArgs)args);
       }
       else if(args instanceof PlacedChipArgs)
       {
           PlacedChipArgs placedChipArgs = ((PlacedChipArgs)args);
       }   
    }
}