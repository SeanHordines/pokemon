import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//non-specific instance of a Pokemon
//stats common to all instances of BasePokemon
public class BasePokemon{
    public static final String[] types = {"NONE",
        "NORMAL", "FIGHTING", "FLYING", "POISON", "GROUND",
        "ROCK", "BUG", "GHOST", "STEEL", "FIRE",
        "WATER", "GRASS", "ELECTRIC", "PSYCHIC", "ICE",
        "DRAGON", "DARK", "FAIRY"};

    public int dexNum; //national pokedex
    public String name;
    public int type1, type2; //type 2 may be null upon creation
    public int[] baseStats = new int[6]; //HP, Atk, Def, SpAtk, SpDef, Spd
    public int BST; // base stat total aka sum of above

    //construct based on nation pokedex number
    public BasePokemon(int n){
        dexNum = n;
        buildBasePokemon(n);
    }

    //build pokemon by importing stats from file
    protected void buildBasePokemon(int n)
    {
        File statDex = new File("statdex.txt");
        String[] data = {};
        try
        {
            Scanner dexReader = new Scanner(statDex);
            for(int i = 0; i < n+1; i++)
            {
                //format of data:
                //name, type1, type2, HP, Atk, Def, SpAtk, SpDef, Spd
                data = dexReader.nextLine().split(" ");
            }
            dexReader.close();

            //assign stats as data from file
            name = data[0];
            type1 = Integer.parseInt(data[1]);
            type2 = Integer.parseInt(data[2]);
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

    //allows for printing via print(BasePokemon)
    public String toString()
    {
        return String.format("%s\nType(s): %s %s\n%s %s\n%s %s\n%s %s\n",
            name,
            types[type1], types[type2].replace("NONE", ""),
            String.format("HP:    %3d",baseStats[0]), String.format("Spd:   %3d",baseStats[5]),
            String.format("Atk:   %3d",baseStats[1]), String.format("Def:   %3d",baseStats[2]),
            String.format("SpAtk: %3d",baseStats[3]), String.format("SpDef: %3d",baseStats[4]));
    }
}
