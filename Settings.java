
import javax.swing.*;
import javax.swing.plaf.multi.MultiScrollBarUI;

import java.awt.*;
import java.awt.event.*;

public class Settings extends JPanel
{
    private JToggleButton _startSessionButton = new JToggleButton();
    private JToggleButton _musicButton = new JToggleButton();
    private JToggleButton _soundButton = new JToggleButton();
    private static Settings _classInstance = null;
    public static Settings getInstance() 
    {
        if(_classInstance == null)
        {
            _classInstance = new Settings();
        }
        return _classInstance;
    }
    private Settings()
    {
        super(new FlowLayout(FlowLayout.TRAILING));

        this.add(_startSessionButton);
        this.add(_musicButton);
        this.add(_soundButton);

        _musicButton.addItemListener(itemEvent  -> {
            int state = itemEvent.getStateChange();
            if(ItemEvent.SELECTED == state) {
                _musicButton.setText("Selected!");
            }
            else
            {
                _musicButton.setText("Deselected!");
            }
        });
        _startSessionButton.addItemListener(
        new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent)
            {
                // event is generated in button
                int state = itemEvent.getStateChange();
                
                // if selected print selected in console
                if (state == ItemEvent.SELECTED) {
                    System.out.println("Selected");
                }
                else {
                    
                    // else print deselected in console
                    System.out.println("Deselected");
                }
            }
        });
    }
}
