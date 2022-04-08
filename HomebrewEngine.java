import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomebrewEngine
{
    private static JFrame window = null;
    private static final int WIDTH = 800, HEIGHT = 600;
    private static final int SIZE = 80;

    public static void main(String[] args)
    {
        makeWindow(WIDTH, HEIGHT);
        makeBattleScene("sprites/back/3.png", "sprites/front/9.png");
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

    public static void makeBattleScene(String hero, String villain)
    {
        JPanel battleScene = new JPanel();
        battleScene.setBackground(new Color(128, 64, 64));
        int panelWidth = WIDTH;
        int panelHeight = (int) ((float) HEIGHT * 0.8f);
        battleScene.setPreferredSize(new Dimension(panelWidth, panelHeight));
        battleScene.setLayout(null);

        JLabel imageHero = new JLabel(scaleImage(hero, 4));
        imageHero.setBounds(0, panelHeight-(4*SIZE), 4*SIZE, 4*SIZE);
        battleScene.add(imageHero);


        JLabel imageVillain = new JLabel(scaleImage(villain, 4));
        imageVillain.setBounds(panelWidth-(4*SIZE), 0, 4*SIZE, 4*SIZE);
        battleScene.add(imageVillain);

        window.add(battleScene, BorderLayout.NORTH);

        JPanel battleText = new JPanel();
        battleText.setBackground(new Color(64, 128, 64));
        battleText.setPreferredSize(new Dimension(WIDTH, (int) ((float) HEIGHT * 0.2f)));
        window.add(battleText, BorderLayout.SOUTH);
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
