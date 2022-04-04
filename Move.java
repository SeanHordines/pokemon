import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Move
{
    static final protected String[] types = new String[]{"NONE",
        "NORMAL", "FIGHTING", "FLYING", "POISON", "GROUND",
        "ROCK", "BUG", "GHOST", "STEEL", "FIRE",
        "WATER", "GRASS", "ELECTRIC", "PSYCHIC", "ICE",
        "DRAGON", "DARK", "FAIRY"};
    static final protected String[] category = new String[]{"PHYSICAL", "SPECIAL", "STATUS"};
    static final public Move moveNull = new Move("NULL", new int[]{0, 0, 0, 0, 0});

    public String name;
    public int cat, type, power, acc, ppMax, ppCurr;

    public Move(int n)
    {
        File moveDex = new File("movedex.txt");
        String[] data = {};
        try
        {
            Scanner dexReader = new Scanner(moveDex);
            for(int i = 0; i < n; i++)
            {
                data = dexReader.nextLine().split(" ");
            }
            dexReader.close();

            name = data[0];
            cat = Integer.parseInt(data[1]);
            type = Integer.parseInt(data[2]);
            power = Integer.parseInt(data[3]);
            acc = Integer.parseInt(data[4]);
            ppMax = Integer.parseInt(data[5]);
            ppCurr = ppMax;
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("file: " + moveDex.getName() + " not found!");
        }
    }

    public Move(String n, int[] data)
    {
        name = n;
        cat = data[0];
        type = data[1];
        power = data[2];
        acc = data[3];
        ppMax = data[4];
        ppCurr = ppMax;
    }

    public String toString()
    {
        return String.format("%s\n%s %s\nPOW:  %3d\nACC:  %3d\nPP: %d/%d",
            name,
            types[type], category[cat],
            power, acc, ppCurr, ppMax);
    }
}
