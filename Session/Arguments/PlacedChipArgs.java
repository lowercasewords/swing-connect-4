package Session.Arguments;
import Session.Map.Chip;
/** Contains information about the Chip that was placed. */
public class PlacedChipArgs extends Args{
    private int _row;
    private int _col;
    private Chip _chip;
    /* Specify Chip and position */
    public PlacedChipArgs(Chip chip, int row, int col)
    {
        _row = row;
        _col = col;
        _chip = chip;
    }
    /* Chip without position  */
    public PlacedChipArgs(Chip chip)
    {
        this(chip, -1, -1);
    }
    /* Position specified without chip */
    public PlacedChipArgs(int row, int col)
    {
        this(null, row, col);
    }
    /* Empty args */
    public PlacedChipArgs()
    {
        this(null, -1, -1);
    }
    public int getRow() { return _row; }
    public int getCol() { return _col; }
    public Chip getChip() { return _chip; }
}