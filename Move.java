import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//move known by pokemon
//stats common to all moves of same index (except currPP)
public class Move
{
    protected static final String[] category = {"PHYSICAL", "SPECIAL", "STATUS"};

    public String name;
    public int index, cat, type, power, acc, ppMax, ppCurr, priority;
    public String effect;

    //construct move from file index
    public Move(int n)
    {
        index = n;
        File moveDex = new File("data/movedex.txt");
        String[] data = {};
        try
        {
            Scanner dexReader = new Scanner(moveDex);
            do
            {
                //format of data:
                //num, name, effect, type, category, power, acc, pp
                data = dexReader.nextLine().split(",");
            }
            while(n != Integer.parseInt(data[0]));
            dexReader.close();

            name = data[1];
            effect = data[2];
            type = Integer.parseInt(data[3]);
            cat = Integer.parseInt(data[4]);
            power = Integer.parseInt(data[5]);
            acc = Integer.parseInt(data[6]);
            ppMax = Integer.parseInt(data[7]);
            ppCurr = ppMax;
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("file: " + moveDex.getName() + " not found!");
        }
    }

    //construct custom move
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

    //allows for printing via print(Move)
    public String toString()
    {
        return String.format("%s\n%s %s\nPOW:  %3d\nACC:  %3d\nPP: %d/%d",
            name,
            BasePokemon.types[type], category[cat],
            power, acc, ppCurr, ppMax);
    }
}
