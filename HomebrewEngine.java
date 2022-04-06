import java.awt.*;
import javax.swing.*;

public class HomebrewEngine extends JPanel
{
    public static void main(String[] args)
    {
        JFrame frame1 = new JFrame("Pokemon(tm): Java Edition");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.getContentPane().add("Center", new HomebrewEngine());
        frame1.pack();
        frame1.setSize(new Dimension(800, 600));
        frame1.setVisible(true);
    }

    public HomebrewEngine()
    {
        setBackground(new Color(40, 40, 40));
        System.out.println("Is it working?");
    }

    public void paint(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(getToolkit().getImage("test.jpg"), null, this);
    }
}
