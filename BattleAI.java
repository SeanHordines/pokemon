import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BattleAI
{
    public static final BattleAI aiBasic = new BattleAI(1);

    public int index;
    public String name;
    private float[] variables = new float[10];

    public BattleAI(int n)
    {
        index = n;
        File aiFile = new File("aiFile.txt");
        String[] data = {};
        try
        {
            Scanner fileReader = new Scanner(aiFile);
            for(int i = 0; i < n+1; i++)
            {
                //format of data:
                //name, vars[1-10]
                data = fileReader.nextLine().split(",");
            }
            fileReader.close();

            name = data[0];
            for(int i = 0; i < 10; i++)
            {
                variables[i] = Float.parseFloat(data[i+1]);
            }
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("file: " + aiFile.getName() + " not found!");
        }
    }

    public int[] getAction(int currHero, BattlePokemon[] hero, BattlePokemon currVillain)
    {
        return new int[]{1, 1};
    }

}
