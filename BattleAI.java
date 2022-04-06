import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BattleAI
{
    public static final BattleAI aiWild = new BattleAI(1);
    public static final BattleAI aiBasic = new BattleAI(2);

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

    public int[] getAction(int currHero, BattlePokemon[] hero, BattlePokemon villain)
    {
        //check if fleeing is appropriate
        float levelRatio = (float) villain.p.level / hero[currHero].p.level;
        float hpRatio = (float) hero[currHero].p.currHP / hero[currHero].p.stats[0];
        if(levelRatio > variables[0] || hpRatio < variables[1])
        {
            return new int[]{4, 1};
        }

        //check if switching is appropriate
        int swap = currHero;
        float[] eff = new float[6];
        //calc type matchup for party
        for(int i = 0; i < 6; i++)
        {
            eff[i] = checkMatchup(hero[i].p, villain.p);
        }
        if(eff[currHero] < variables[2]) //if current matchup is bad
        {
            for(int i = 0; i < 6; i++) //find the best matchup
            {
                //potential swap must not be current
                //potential swap matchup must be better than current swap matchup
                //potential swap must not be fainted
                //potential swap must not be null
                if(i != currHero && eff[i] > eff[swap] && hero[i].p.status != 1 && hero[i].p.dexNum != 0){swap = i;}
            }

            if(swap != currHero){return new int[]{2, swap};} //switch out for better matchup if availible
        }

        //check if using an item is appropriate

        //check which move would be best
        float[] effPower = new float[4];
        int choice = 0;
        for(int i = 0; i < 4; i++)
        {
            Move m = hero[currHero].p.moves[i];
            effPower[i] = (float) m.power * Battler.checkEff(m.type, villain.p.type1) * Battler.checkEff(m.type, villain.p.type2);
            if(effPower[i] > effPower[choice]){choice = i;}
        }
        return new int[]{1, choice+1};
    }

    private float checkMatchup(Pokemon hero, Pokemon villain)
    {
        float offensiveEff = 1.0f;
        offensiveEff *= Battler.checkEff(hero.type1, villain.type1);
        offensiveEff *= Battler.checkEff(hero.type1, villain.type2);
        offensiveEff *= Battler.checkEff(hero.type2, villain.type1);
        offensiveEff *= Battler.checkEff(hero.type2, villain.type2);

        float defensiveEff = 1.0f;
        defensiveEff *= Battler.checkEff(villain.type1, hero.type1);
        defensiveEff *= Battler.checkEff(villain.type1, hero.type2);
        defensiveEff *= Battler.checkEff(villain.type2, hero.type1);
        defensiveEff *= Battler.checkEff(villain.type2, hero.type2);

        return offensiveEff / defensiveEff;
    }
}
