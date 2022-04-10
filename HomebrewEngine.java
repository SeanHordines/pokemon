import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomebrewEngine
{
    private static final int width = 800, height = 600;
    private static JFrame window = null;
    private static BattleScene bs;
    private static BattleText bt;
    private static MenuScene ms;
    private static HealthBar heroHP = new HealthBar(Pokemon.nullPokemon);
    private static HealthBar villainHP = new HealthBar(Pokemon.nullPokemon);

    public static void init()
    {
        makeWindow(width, height);

        bs = new BattleScene(width, (int) (0.8f * height));
        window.add(bs, BorderLayout.NORTH);

        bt = new BattleText(width, (int) (0.2f * height));
        window.add(bt, BorderLayout.CENTER);

        ms = new MenuScene(width, (int) (0.5f * height));
        window.add(ms, BorderLayout.SOUTH);

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
        if(hero){bs.setHero(path);}
        else{bs.setVillain(path);}
        window.repaint();
    }

    public static void createHealthBar(Pokemon p, Boolean hero)
    {
        if(hero)
        {
            bs.remove(heroHP);
            heroHP = new HealthBar(p);
            heroHP.setBounds(340, 400, 450, 70);
            bs.add(heroHP);
        }
        else
        {
            bs.remove(villainHP);
            villainHP = new HealthBar(p);
            villainHP.setBounds(10, 10, 450, 70);
            bs.add(villainHP);
        }
        window.repaint();
    }

    public static void updateHealthBar(int newHP, Boolean hero)
    {
        if(hero){heroHP.setHP(newHP);}
        else{villainHP.setHP(newHP);}
        window.repaint();
    }

    public static void setBattleText(String text)
    {
        bt.setText(text);
        window.repaint();
    }

    public static void setMenuText(String text)
    {
        ms.setText(text);
        window.repaint();
    }

    public static void addMenuButton(String text, int a, int x, int y, int w, int h, Boolean left)
    {
        JButton b = new JButton(text);

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ms.action = a;}});

        b.setBounds(x, y, w, h);
        b.setFont(new Font("Courier New", Font.PLAIN, 30));
        if(left){b.setHorizontalAlignment(SwingConstants.LEFT);}

        ms.add(b);
        window.repaint();
    }

    public static void showPokemon(BattlePokemon p)
    {
        ms.showPokemon(p);
        addMenuButton("Back", 0, 680, 10, 110, 30, false);
        window.repaint();
    }

    public static void showMove(Move m)
    {
        ms.showMove(m);
        addMenuButton("Back", 0, 680, 10, 110, 30, false);
        window.repaint();
    }

    public static int getMenuAction()
    {
        int out = ms.action;
        ms.action = -9;
        return out;
    }

    public static void clearMenu()
    {
        ms.removeAll();
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
