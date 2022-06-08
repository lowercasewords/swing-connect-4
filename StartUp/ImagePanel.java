package StartUp;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static StartUp.HelperLib.log;
public class ImagePanel extends JPanel 
{
    private BufferedImage _image;
    private static String _missingTexture = "Images/missing.png";
    
    /** Builds an image from
     * @param filePath a path to build the image from 
     */
    public void setImage(String filePath) 
    { 
        try { _image = ImageIO.read(new File(filePath)); }
        catch (IOException e) { log("Image couldn't be loaded"); }
    }
    /** Stores an image from specified file path */
    public ImagePanel(String filePath) 
    {
       setImage(filePath);
    }
    /** Default missing Image */
    public ImagePanel()
    {
        this(_missingTexture);
    }
    public ImagePanel(LayoutManager layout)
    {
        super(layout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(_image, 0, 0, this); // see javadoc for more info on the parameters            
    }
}