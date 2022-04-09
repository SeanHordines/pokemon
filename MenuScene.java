import java.awt.*;
import javax.swing.*;

public class MenuScene extends JPanel
{
    private int width, height;
    private JTextArea text = new JTextArea();
    public int action = -9;

    public MenuScene(int w, int h)
    {
        width = w;
        height = h;
        setBackground(new Color(64, 64, 128));
        setPreferredSize(new Dimension(width, height));
        setLayout(null);
    }

    public void setText(String t)
    {
        remove(text);
        text = new JTextArea(t);
        text.setEditable(false);
        text.setBounds(10, 10, width-140, 30);
        text.setFont(new Font("Courier New", Font.PLAIN, 30));
        text.setLineWrap(true);
        // text.setVerticalAlignment(JTextArea.TOP);
        add(text);
    }

    public void showPokemon(BattlePokemon p)
    {
        remove(text);
        text = new JTextArea(p.toString());
        text.setEditable(false);
        text.setBounds(10, 10, width-140, 280);
        text.setFont(new Font("Courier New", Font.PLAIN, 30));
        text.setLineWrap(true);
        add(text);
    }

    public void showMove(Move m)
    {
        remove(text);
        text = new JTextArea(m.toString());
        text.setEditable(false);
        text.setBounds(10, 10, width-140, 280);
        text.setFont(new Font("Courier New", Font.PLAIN, 30));
        text.setLineWrap(true);
        add(text);
    }
}
