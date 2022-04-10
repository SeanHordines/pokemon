import java.awt.*;
import javax.swing.*;
import java.awt.font.*;
import java.awt.geom.*;

public class HealthBar extends JPanel
{
    private Pokemon poke;
    private float percent;
    private int width = 450, height = 70;

    public HealthBar(Pokemon p)
    {
        poke = p;
        setBackground(new Color(128, 128, 128));
        setLayout(null);
    }

    public void setHP(int newHP)
    {
        poke.currHP = newHP;
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.WHITE);
        g2.fillRect(5, 5, width-10, height-10);

        g2.setColor(Color.BLACK);
        Font font = new Font("Courier New", Font.PLAIN, 30);
        g2.setFont(font);
        String text = String.format("%s lv.%d", poke.name, poke.level);
        g2.drawString(text, 10, 30);

        percent = ((float) poke.currHP)/((float) poke.stats[0]);
        if(percent > 0.5){g2.setColor(Color.GREEN);}
        else if(percent > 0.2){g2.setColor(Color.YELLOW);}
        else{g2.setColor(Color.RED);}
        g2.fillRect(5, 35, (int) (percent*(width-10)), 30);

        g2.setColor(Color.BLACK);
        text = String.format("(%d/%d) %s", poke.currHP, poke.stats[0], poke.statuses[poke.status].replace("NONE", ""));
        Rectangle2D bounds = font.getStringBounds(text, new FontRenderContext(null, true, true));
        int posX = (int) (width-bounds.getWidth())/2;
        g2.drawString(text, posX, 58);
    }
}
