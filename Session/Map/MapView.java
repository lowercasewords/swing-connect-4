package Session.Map;

import javax.swing.*;
import java.awt.*;

public class MapView extends JPanel{
    // private Color color = new Color(Color.BLACK);
    private MapModel _currentSession;
    private JButton[] _fieldJButtons;

    private static MapView _instance = null;
    private MapView()
    {
        // super(new GridLayout(row, col));
        // fieldJButtons = new JButton[row + col];
    }

    public static MapView getInstance()
    {
        if(_instance == null)
        {
            _instance = new MapView();
        }
        return _instance;
    }
}
