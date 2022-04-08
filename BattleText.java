import java.awt.*;
import javax.swing.*;

public class BattleText extends JPanel
{
    private int width, height;
    private JLabel text = new JLabel();

    public BattleText(int w, int h)
    {
        width = w;
        height = h;
        setBackground(new Color(64, 128, 64));
        setPreferredSize(new Dimension(width, height));
        setLayout(null);
    }

    public void setText(String t)
    {
        remove(text);
        text = new JLabel(t);
        text.setBounds(10, 10, width-20, height-20);
        text.setFont(new Font("Courier New", Font.PLAIN, 30));
        text.setVerticalAlignment(JLabel.TOP);
        add(text);
    }
}
