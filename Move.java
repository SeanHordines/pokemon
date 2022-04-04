import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Move
{
    protected static final String[] types = {"NONE",
        "NORMAL", "FIGHTING", "FLYING", "POISON", "GROUND",
        "ROCK", "BUG", "GHOST", "STEEL", "FIRE",
        "WATER", "GRASS", "ELECTRIC", "PSYCHIC", "ICE",
        "DRAGON", "DARK", "FAIRY"};
    protected static final String[] category = {"PHYSICAL", "SPECIAL", "STATUS"};

    public String name;
    public int index, cat, type, power, acc, ppMax, ppCurr, priority;

    public Move(int n)
    {
        index = n;
        File moveDex = new File("movedex.txt");
        String[] data = {};
        try
        {
            Scanner dexReader = new Scanner(moveDex);
            for(int i = 0; i < n+1; i++)
            {
                data = dexReader.nextLine().split(" ");
            }
            dexReader.close();

            name = data[0];
            cat = Integer.parseInt(data[1]);
            type = Integer.parseInt(data[2]);
            power = Integer.parseInt(data[3]);
            acc = Integer.parseInt(data[4]);
            priority = Integer.parseInt(data[5]);
            ppMax = Integer.parseInt(data[6]);
            ppCurr = ppMax;
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("file: " + moveDex.getName() + " not found!");
        }
    }

    public Move(String n, int[] data)
    {
        index = 0;
        name = n;
        cat = data[0];
        type = data[1];
        power = data[2];
        acc = data[3];
        priority = data[4];
        ppMax = data[5];
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
