import java.util.Arrays;
import java.lang.Math;

//specific instance of Pokemon during battle
//stat changes are fully reset between battles
public class BattlePokemon
{
    //for use by Atk, Def, SpAtk, SpDef, Spd {-6 to +6}
    protected static final float[] statChanges1 = {2f/8, 2f/7, 2f/6, 2f/5, 2f/4, 2f/3,
                                                2f/2,
                                                3f/2, 4f/2, 5f/2, 6f/2, 7f/2, 8f/2};
    //for use by Acc, Eva {-6 to +6}
    protected static final float[] statChanges2 = {3f/9, 3f/8, 3f/7, 3f/6, 3f/5, 3f/4,
                                                3f/3,
                                                4f/3, 5f/3, 6f/3, 7f/3, 8f/3, 9f/3};
    //for use by crit {0 to +4}
    protected static final float[] statChanges3 = {1f/16, 1f/8, 1f/4, 1f/2, 1f/1};

    public int[] statStages = new int[8];
    public int[] tempStats = new int[8];
    public Pokemon p;

    //construct battle specific instance of Pokemon
    public BattlePokemon(Pokemon parent)
    {
        //all statStages begin at 0
        Arrays.fill(statStages, 6);
        statStages[7] = 0;
        p = parent;
        recalculate();
    }

    //change stat stage then recalculate stats
    public void changeStat(int s, int i)
    {
        if(s != 7)
        {
            if(statStages[s]+i < 0){statStages[s] = 0;}
            else if (statStages[s]+i > 12){statStages[s] = 12;}
            else{statStages[s] += i;}
        }
        else
        {
            if(statStages[s]+i < 0){statStages[s] = 0;}
            else if (statStages[s]+i > 4){statStages[s] = 4;}
            else{statStages[s] += i;}
        }
        recalculate();
    }

    //outputs a string to be printed detailing the stat changes
    public String listChanges()
    {
        return String.format("%s (%s) stat changes:\n", p.nickname, p.name) +
            String.format("Acc:    %+2d Eva:    %+2d Crit:    %+2d\nAtk:    %+2d Def:    %+2d\nSpAtk:  %+2d SpDef:  %+2d\nSpd:    %+2d",
            statStages[5]-6, statStages[6]-6, statStages[6],
            statStages[0]-6, statStages[1]-6,
            statStages[2]-6, statStages[3]-6,
            statStages[4]-6);
    }

    //outputs a string to be printed detailing the effective stats
    public String listStats()
    {
        return String.format("%s (%s) current stats:\n", p.nickname, p.name) +
            String.format("Acc:   %3d Eva:   %3d Crit:   %d\nAtk:   %3d Def:   %3d\nSpAtk: %3d SpDef: %3d\nSpd:   %3d",
            tempStats[5], tempStats[6], tempStats[7],
            tempStats[0], tempStats[1],
            tempStats[2], tempStats[3],
            tempStats[4]);
    }

    //allows for printing via print(BattlePokemon)
    public String toString()
    {
        return String.format("%s (%s) lv. %d\nType(s): %s %s\n%s %s\n%s %s\n%s %s\n%s\n",
            p.nickname, p.name, p.level,
            p.types[p.type1], p.types[p.type2].replace("NONE", ""),
            String.format("HP: %d/%d", p.currHP, p.stats[0]), p.statuses[p.status].replace("NONE", ""),
            String.format("Atk:   %3d", tempStats[0]), String.format("Def:   %3d", tempStats[1]),
            String.format("SpAtk: %3d", tempStats[2]), String.format("SpDef: %3d", tempStats[3]),
            String.format("Spd:   %3d", tempStats[4]));
    }

    //handle taking damage
    public void damage(int dmg)
    {
        p.currHP = Math.max(0, p.currHP - dmg);
        HomebrewEngine.setBattleText(p.nickname + " took " + dmg + " damage. (" + p.currHP + "/" + p.stats[0] + "HP)");
        if(p.currHP == 0)
        {
            p.status = 1;
            HomebrewEngine.setBattleText(p.nickname + " fainted!");
        }
    }

    //calculate effective stats given stat changes
    private void recalculate()
    {
        for(int i = 0; i < 5; i++)
        {
            tempStats[i] = (int) (p.stats[i+1] * statChanges1[statStages[i]]);
        }
        tempStats[5] = (int) (100f * statChanges2[statStages[5]]);
        tempStats[6] = (int) (100f * statChanges2[statStages[6]]);
        tempStats[7] = (int) (10000f * statChanges3[statStages[7]]);
    }
}
