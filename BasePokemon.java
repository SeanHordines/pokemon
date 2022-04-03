import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BasePokemon{
    public int dexNum;
    public String name;
    public String primaryType, secondaryType;
    public int[] baseStats = new int[6];
    public int BST;

    public BasePokemon(int n){
        dexNum = n;
        buildBasePokemon(n);
    }

    protected void buildBasePokemon(int n)
    {
        File pokeDex = new File("pokedex.txt");
        String[] data = {};
        try
        {
            Scanner dexReader = new Scanner(pokeDex);
            for(int i = 0; i < n; i++)
            {
                data = dexReader.nextLine().split(" ");
            }
            dexReader.close();

            name = data[0];
            primaryType = data[1];
            secondaryType = data[2];
            BST = 0;
            for(int i = 0; i < 6; i++)
            {
                baseStats[i] = Integer.parseInt(data[i+3]);
                BST += baseStats[i];
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("file: " + pokeDex.getName() + " not found!");
        }
    }

    public String toString()
    {
        return String.format("%s\nType(s): %s %s\n%s %s\n%s %s\n%s %s",
            name,
            primaryType, secondaryType,
            String.format("HP:    %3d",baseStats[0]), String.format("Spd:   %3d",baseStats[5]),
            String.format("Atk:   %3d",baseStats[1]), String.format("Def:   %3d",baseStats[2]),
            String.format("SpAtk: %3d",baseStats[3]), String.format("SpDef: %3d",baseStats[4]));
    }
}
