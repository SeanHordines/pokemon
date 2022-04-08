import java.awt.*;
import javax.swing.*;

public class HomebrewEngine
{
    private static JFrame window = null;
    private static final int width = 800, height = 600;

    public static void main(String[] args)
    {
        makeWindow(width, height);

        BattleScene bs = new BattleScene(width, (int) (0.8f * height));
        window.add(bs, BorderLayout.NORTH);
        bs.setHero("sprites/back/3.png");
        bs.setVillain("sprites/front/6.png");

        BattleText bt = new BattleText(width, (int) (0.2f * height));
        window.add(bt, BorderLayout.SOUTH);
        bt.setText("Let's Fight!");

        window.pack();
        window.setVisible(true);

        bs.setHero("sprites/back/9.png");
    }

    public static void makeWindow(int w, int h)
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
