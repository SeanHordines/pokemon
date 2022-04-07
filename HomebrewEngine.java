import java.awt.*;
import javax.swing.*;

public class HomebrewEngine
{
    private static JFrame window = null;
    private static final int WIDTH = 800, HEIGHT = 600;

    public static void main(String[] args)
    {
        makeWindow(WIDTH, HEIGHT);
        makeBattleScene();
        window.pack();
        window.setVisible(true);
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

    public static void makeBattleScene()
    {
        JPanel battleScene = new JPanel();
        battleScene.setBackground(new Color(128, 0, 0));
        battleScene.setPreferredSize(new Dimension(WIDTH, (int) ((float) HEIGHT * 0.8f)));
        JLabel image = new JLabel(new ImageIcon("test.jpg"));
        battleScene.add(image);
        window.add(battleScene, BorderLayout.CENTER);

        JPanel battleText = new JPanel();
        battleText.setBackground(new Color(0, 128, 0));
        battleText.setPreferredSize(new Dimension(WIDTH, (int) ((float) HEIGHT * 0.2f)));
        window.add(battleText, BorderLayout.SOUTH);
    }
}
