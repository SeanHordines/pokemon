import java.util.Random;

public class Battler
{
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

    public void ply()
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
            if(b)
            {
                Move m = hero[currHero].p.moves[action[1]];
                float acc = (float) hero[currHero].tempStats[5];
                float eva = (float) villain[currVillain].tempStats[6];
                float chance = (acc / eva) * ((float) m.acc /100f);
                Random rand = new Random();
                if(rand.nextFloat() < chance)
                {
                    float ratio = 0f;
                    if(m.cat == 0)
                    {
                        ratio = ((float) hero[currHero].tempStats[0]) / ((float) villain[currVillain].tempStats[1]);
                    }
                    else if(m.cat == 1)
                    {
                        ratio = ((float) hero[currHero].tempStats[2]) / ((float) villain[currVillain].tempStats[3]);
                    }

                    float mod = 1f;
                    float dmg = (2f * hero[currHero].p.level / 5);
                    dmg = ((dmg + 2) * m.power * ratio) / 50 + 2;
                    dmg *= mod * (rand.nextFloat(0.15f) + 0.85f);
                    villain[currVillain].damage((int) dmg);
                    System.out.println(hero[currHero].p.name + " attacked " +
                        villain[currVillain].p.name + " for "+
                        (int) dmg + " damage");
                }
            }
            else
            {
                Move m = villain[currVillain].p.moves[action[1]];
                float acc = (float) villain[currVillain].tempStats[5];
                float eva = (float) hero[currHero].tempStats[6];
                float chance = (acc / eva) * ((float) m.acc /100f);
                Random rand = new Random();
                if(rand.nextFloat() < chance)
                {
                    float ratio = 0f;
                    if(m.cat == 0)
                    {
                        ratio = ((float) villain[currVillain].tempStats[0]) / ((float) hero[currHero].tempStats[1]);
                    }
                    else if(m.cat == 1)
                    {
                        ratio = ((float) villain[currVillain].tempStats[2]) / ((float) hero[currHero].tempStats[3]);
                    }

                    float mod = 1f;
                    float dmg = (2f * villain[currVillain].p.level / 5);
                    dmg = ((dmg + 2) * m.power * ratio) / 50 + 2;
                    dmg *= mod * (rand.nextFloat(0.15f) + 0.85f);
                    hero[currHero].damage((int) dmg);
                    System.out.println(villain[currVillain].p.name + " attacked " +
                        hero[currHero].p.name + " for "+
                        (int) dmg + " damage");
                }
            }
        }
    }
}
