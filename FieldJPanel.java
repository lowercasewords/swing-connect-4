
import javax.swing.*;
import java.awt.*;

public class FieldJPanel extends JPanel{
    // private Color color = new Color(Color.BLACK);
    JButton[] fieldJButtons;
    public FieldJPanel(int row, int col)
    {
        super(new GridLayout(row, col));
        fieldJButtons = new JButton[row + col];
    }
}
