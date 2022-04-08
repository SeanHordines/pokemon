import java.awt.*;
import javax.swing.*;

public class HomebrewEngine
{
    private static final int width = 800, height = 600;
    private static JFrame window = null;
    private static BattleScene bs;
    private static BattleText bt;

    public static void init()
    {
        makeWindow(width, height);

        bs = new BattleScene(width, (int) (0.8f * height));
        window.add(bs, BorderLayout.NORTH);

        bt = new BattleText(width, (int) (0.2f * height));
        window.add(bt, BorderLayout.SOUTH);

        window.pack();
        window.setVisible(true);
    }

    public static void close()
    {
        window.setVisible(false);
        window.dispose();
    }

    public static void setSprite(String path, Boolean hero)
    {
        if(hero)
        {
            bs.setHero(path);
            window.repaint();
        }
        else
        {
            bs.setVillain(path);
            window.repaint();
        }
    }

    public static void setText(String t)
    {
        bt.setText(t);
        window.repaint();
    }

    private static void makeWindow(int w, int h)
    {
        if(window == null)
        {
            window = new JFrame("Pokemon(tm): Java Edition");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(w, h);
            window.setResizable(false);
            window.getContentPane().setBackground(new Color(64, 64, 64));
            window.setLayout(new BorderLayout());
        }
    }
}
