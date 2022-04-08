import java.awt.*;
import javax.swing.*;

public class BattleScene extends JPanel
{
    private static final int SIZE = 320;
    private int width, height;
    private JLabel imageHero = new JLabel(), imageVillain = new JLabel();

    public BattleScene(int w, int h)
    {
        width = w;
        height = h;
        setBackground(new Color(128, 64, 64));
        setPreferredSize(new Dimension(width, height));
        setLayout(null);
    }

    public void setHero(String heroPath)
    {
        remove(imageHero);
        imageHero = new JLabel(scaleImage(heroPath, 4));
        imageHero.setBounds(0, height-SIZE, SIZE, SIZE);
        add(imageHero);
    }

    public void setVillain(String villainPath)
    {
        remove(imageVillain);
        imageVillain = new JLabel(scaleImage(villainPath, 4));
        imageVillain.setBounds(width-SIZE, 0, SIZE, SIZE);
        add(imageVillain);
    }

    public static ImageIcon scaleImage(String filepath, int k)
    {
        ImageIcon originalImage = new ImageIcon(filepath);
        ImageIcon scaledImage = new ImageIcon(originalImage.getImage().getScaledInstance(
            k * originalImage.getIconWidth(),
            k * originalImage.getIconHeight(),
            Image.SCALE_SMOOTH));
        return scaledImage;
    }
}
