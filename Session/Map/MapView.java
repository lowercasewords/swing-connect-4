package Session.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.function.Consumer;

import Exceptions.InvalidPlayerAmountException;
import Exceptions.UnimplementedException;
import Session.Arguments.*;
import StartUp.ImagePanel;

import static StartUp.HelperLib.log;
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
    private Chip[][] _mapChips;
    /** Retrieves buttons from the Model */
    private JButton[][] _mapButtons;
    /** Panel where the button is in-closed on the board */
    private ImagePanel[][] _mapCells;
    /** Is used to configure grd layout settings (amount of rows, columns, size, e.g.) */
    private GridLayout _gridLayout;
    /** Background color of View JPanel */
    public Color _backGroundColor = Color.BLACK;
    
    
    // Get / Set methods
    public MapModel getModel() { return _model; }
    private ImagePanel getCell(int row, int col) { return _mapCells[row][col]; }

    /** Creates a Map View that extends from JPanel with grid Layout */
    public MapView(MapModel mapModel)
    {
        super(new GridLayout());
        _gridLayout = (GridLayout)getLayout();
        
        _model = mapModel;

        this.revalidate();
        this.repaint();
    }

    /** Handles what should be shown when the Game Session ends */
    @Deprecated
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
        this.removeAll();
        this.revalidate();
        this.repaint();

        _mapChips = _model.getMapChips();
        _mapButtons = _model.getMapButtons();

        _gridLayout.setRows(_mapChips.length);
        _gridLayout.setColumns(_mapChips[0].length);

        _mapCells = new ImagePanel[_gridLayout.getRows()][_gridLayout.getColumns()];

        for (int row = 0; row < _gridLayout.getRows(); row++) 
        {
            for (int col = 0; col < _gridLayout.getColumns(); col++) 
            {
                _mapCells[row][col] = new ImagePanel(/*"Images/cell-frame.png"*/);
                ImagePanel cell = _mapCells[row][col];
                cell.setLayout(new BorderLayout());
                cell.setBorder((Border)new LineBorder(Color.BLACK, 2));
                this.add(cell);
                this.revalidate();
                this.repaint();

                JButton button = _mapButtons[row][col];
                button.setOpaque(false);
                cell.add(button);
                button.revalidate();
                button.repaint();
            }
        }
    }
    /** Adds a chip to visual board */
    public void addChipVisually(Chip chip, int row, int col)
    {
        getCell(row, col).setImage(chip.getImagePath());
        getCell(row, col).removeAll();
        this.revalidate();
        this.repaint();
    }
}