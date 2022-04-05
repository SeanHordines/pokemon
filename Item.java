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
        File itemDex = new File("itemdex.txt");
        String data = "";
        try
        {
            Scanner dexReader = new Scanner(itemDex);
            for(int i = 0; i < n+1; i++)
            {
                //format of data:
                //name, cat, type, power, acc, priority, PP
                data = dexReader.nextLine();
            }
            dexReader.close();

            int s1 = data.indexOf(" ");
            int s2 = data.indexOf(" ", s1+1);
            name = data.substring(0, s1);
            cat = Integer.parseInt(data.substring(s1+1, s2));
            effect = data.substring(s2+1);
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
