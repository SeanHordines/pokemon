import java.util.Arrays;
import java.lang.Math;

public class TransientPokemon
{
    static final protected float[] statChanges1 = {2f/8, 2f/7, 2f/6, 2f/5, 2f/4, 2f/3,
                                                2f/2,
                                                3f/2, 4f/2, 5f/2, 6f/2, 7f/2, 8f/2};
    static final protected float[] statChanges2 = {3f/9, 3f/8, 3f/7, 3f/6, 3f/5, 3f/4,
                                                3f/3,
                                                4f/3, 5f/3, 6f/3, 7f/3, 8f/3, 9f/3};

    public int[] statStages = new int[7];
    public int[] tempStats = new int[7];
    public Pokemon p;

    public TransientPokemon(Pokemon parent)
    {
        Arrays.fill(statStages, 6);
        p = parent;
        recalculate();
    }

    public void changeStat(int s, int i)
    {
        if(statStages[s]+i < 0){statStages[s] = 0;}
        else if (statStages[s]+i > 12){statStages[s] = 12;}
        else{statStages[s] += i;}
        recalculate();
    }

    public String listChanges()
    {
        return String.format("%s (%s) stat changes:\n", p.nickname, p.name) +
            String.format("Acc:    %+2d Eva:    %+2d\nAtk:    %+2d Def:    %+2d\nSpAtk:  %+2d SpDef:  %+2d\nSpd:    %+2d",
            statStages[5]-6, statStages[6]-6,
            statStages[0]-6, statStages[1]-6,
            statStages[2]-6, statStages[3]-6,
            statStages[4]-6);
    }

    public String listStats()
    {
        return String.format("%s (%s) current stats:\n", p.nickname, p.name) +
            String.format("Acc:   %3d Eva:   %3d\nAtk:   %3d Def:   %3d\nSpAtk: %3d SpDef: %3d\nSpd:   %3d",
            tempStats[5], tempStats[6],
            tempStats[0], tempStats[1],
            tempStats[2], tempStats[3],
            tempStats[4]);
    }

    private void recalculate()
    {
        for(int i = 0; i < 5; i++)
        {
            tempStats[i] = (int) Math.floor(p.stats[i+1] * statChanges1[statStages[i]]);
        }
        tempStats[5] = (int) Math.floor(100*statChanges2[statStages[5]]);
        tempStats[6] = (int) Math.floor(100*statChanges2[statStages[6]]);
    }
}
