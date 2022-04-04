import java.util.Random;

public class Battler
{
    protected static final float[][] effMatrix = new float[19][19];

    public TransientPokemon[] hero = new TransientPokemon[6], villain = new TransientPokemon[6];
    public int currHero = 0, currVillain = 0;
    public static boolean ended = false;

    public Battler(Pokemon[] a, Pokemon[] b)
    {
        for(int i = 0; i < 6; i++)
        {
            hero[i] = new TransientPokemon(a[i]);
            villain[i] = new TransientPokemon(b[i]);
        }
    }

    public void start()
    {
        System.out.println("Begin battle:");
        do
        {
            int[] heroAction = getHeroAction();
            int[] villainAction = getVillainAction();
            if(heroAction[0] < 3)
            {
                execute(true, heroAction);
                execute(false, villainAction);
            }
            else if(villainAction[0] < 3)
            {
                execute(false, villainAction);
                execute(true, heroAction);
            }
            else
            {
                if(hero[currHero].p.moves[heroAction[1]].priority > villain[currVillain].p.moves[villainAction[1]].priority)
                {
                    execute(true, heroAction);
                    execute(false, villainAction);
                }
                else if(hero[currHero].p.moves[heroAction[1]].priority < villain[currVillain].p.moves[villainAction[1]].priority)
                {
                    execute(false, villainAction);
                    execute(true, heroAction);
                }
                else if(hero[currHero].tempStats[4] >= villain[currVillain].tempStats[4])
                {
                    execute(true, heroAction);
                    execute(false, villainAction);
                }
                else
                {
                    execute(false, villainAction);
                    execute(true, heroAction);
                }
            }
        }
        while(!ended);
        // System.out.println(hero[currHero]);
        // System.out.println(villain[currVillain]);
    }

    private int[] getHeroAction()
    {
        return new int[]{3, 0};
    }

    private int[] getVillainAction()
    {
        return new int[]{3, 0};
    }

    private void execute(boolean b, int[] action)
    {
        if(ended){return;}
        if(action[0] == 0){ended = true;}
        else if(action[0] == 1)
        {
            //use item
        }
        else if(action[0] == 2)
        {
            //switch
        }
        else if(action[0] == 3)
        {
            useMove(b, action[1]);
        }
    }

    private void useMove(boolean b, int moveIndex)
    {
        TransientPokemon attacker = b ? hero[currHero] : villain[currVillain];
        TransientPokemon defender = b ? villain[currVillain] : hero[currHero];
        Move m = attacker.p.moves[moveIndex];
        float acc = (float) attacker.tempStats[5];
        float eva = (float) defender.tempStats[6];
        float chance = (acc / eva) * ((float) m.acc /100f);
        Random rand = new Random();
        if(rand.nextFloat() < chance)
        {
            float ratio = (m.cat == 0)?
                ((float) attacker.tempStats[0]) / ((float) defender.tempStats[1]) :
                ((float) attacker.tempStats[2]) / ((float) defender.tempStats[3]);

            float mod = calcMod(attacker, defender, m, false);
            float dmg = (2f * attacker.p.level / 5);
            dmg = ((dmg + 2) * m.power * ratio) / 50 + 2;
            dmg *= mod * (rand.nextFloat(0.15f) + 0.85f);
            defender.damage((int) dmg);
            System.out.println(attacker.p.name + " attacked " +
                defender.p.name + " with " +
                m.name + " for " +
                (int) dmg + " damage.");
            if(defender.p.currHP == 0)
            {
                ended = true;
                System.out.println(defender.p.name + " fainted!");
            }
        }
    }

    private float calcMod(TransientPokemon attacker, TransientPokemon defender, Move m, boolean crit)
    {
        float mod = 1.0f;
        if(m.type == attacker.p.type1 || m.type == attacker.p.type2){mod *= 1.5f;}
        //m *= effMatrix[m.type][defender.p.type1];
        //m *= effMatrix[m.type][defender.p.type2];
        if(crit){mod *= 2f;}
        return mod;
    }
}
