import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Item
{
    public static final String[] itemCategories = {"MISC", "MEDICINE", "BATTLE", "POKEBALLS", "KEY",};

    public String name, effect;
    public int index, cat;

    public Item(int n)
    {
        index = n;
        File itemDex = new File("data/itemdex.txt");
        String[] data = {};
        try
        {
            Scanner dexReader = new Scanner(itemDex);
            for(int i = 0; i < n+1; i++)
            {
                //format of data:
                data = dexReader.nextLine().split(",");
            }
            dexReader.close();
            name = data[0];
            cat = Integer.parseInt(data[2]);
            effect = data[3];
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("file: " + itemDex.getName() + " not found!");
        }
    }

    public String toString()
    {
        return String.format("%d. %s (%s) - %s", index, name, itemCategories[cat], effect);
    }
}
