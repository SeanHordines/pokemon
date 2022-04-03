import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BasePokemon{
    static final protected String[] types = new String[]{"NONE",
        "NORMAL", "FIGHTING", "FLYING", "POISON", "GROUND",
        "ROCK", "BUG", "GHOST", "STEEL", "FIRE",
        "WATER", "GRASS", "ELECTRIC", "PSYCHIC", "ICE",
        "DRAGON", "DARK", "FAIRY"};

    public int dexNum;
    public String name;
    public int primaryType, secondaryType;
    public int[] baseStats = new int[6];
    public int BST;

    public BasePokemon(int n){
        dexNum = n;
        buildBasePokemon(n);
    }

    protected void buildBasePokemon(int n)
    {
        File statDex = new File("statdex.txt");
        String[] data = {};
        try
        {
            Scanner dexReader = new Scanner(statDex);
            for(int i = 0; i < n; i++)
            {
                data = dexReader.nextLine().split(" ");
            }
            dexReader.close();

            name = data[0];
            primaryType = Integer.parseInt(data[1]);
            secondaryType = Integer.parseInt(data[2]);
            BST = 0;
            for(int i = 0; i < 6; i++)
            {
                baseStats[i] = Integer.parseInt(data[i+3]);
                BST += baseStats[i];
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("file: " + statDex.getName() + " not found!");
        }
    }

    public String toString()
    {
        return String.format("%s\nType(s): %s %s\n%s %s\n%s %s\n%s %s",
            name,
            types[primaryType], types[secondaryType].replace("NONE", ""),
            String.format("HP:    %3d",baseStats[0]), String.format("Spd:   %3d",baseStats[5]),
            String.format("Atk:   %3d",baseStats[1]), String.format("Def:   %3d",baseStats[2]),
            String.format("SpAtk: %3d",baseStats[3]), String.format("SpDef: %3d",baseStats[4]));
    }
}
