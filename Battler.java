import java.io.*;
import java.util.*;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Thread;

public class Battler
{
    private float[][] effMatrix;
    private TransientPokemon[] hero = new TransientPokemon[6], villain = new TransientPokemon[6];
    private int currHero = 0, currVillain = 0;
    private static boolean ended = false;

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
        System.out.println(String.format("%s lv.%d VS %s lv.%d", hero[currHero].p.name, hero[currHero].p.level, villain[currVillain].p.name, villain[currVillain].p.level));
        do
        {
            if(hero[currHero].p.status == 1)
            {
                currHero = promptSwitch();
            }
            if(villain[currVillain].p.status == 1)
            {
                System.out.println(String.format("%s switched out for %s.", villain[currVillain].p.name, villain[currVillain+1].p.name));
                currVillain += 1;
            }

            System.out.println();
            int[] heroAction = promptHeroAction();
            int[] villainAction = promptVillainAction();
            if(heroAction[0] > 0)
            {
                execute(true, heroAction);
                execute(false, villainAction);
            }
            else if(villainAction[0] > 0)
            {
                execute(false, villainAction);
                execute(true, heroAction);
            }
            else
            {
                if(hero[currHero].p.moves[heroAction[1]].priority > villain[currVillain].p.moves[heroAction[1]].priority)
                {
                    execute(true, heroAction);
                    execute(false, villainAction);
                }
                else if(hero[currHero].p.moves[heroAction[1]].priority < villain[currVillain].p.moves[heroAction[1]].priority)
                {
                    execute(false, villainAction);
                    execute(true, heroAction);
                }
                else
                {
                    if(hero[currHero].tempStats[4] >= villain[currVillain].tempStats[4])
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

            System.out.println();
            ended = checkEnded();
        }
        while(!ended);
        // System.out.println(hero[currHero]);
        // System.out.println(villain[currVillain]);
    }

    private boolean checkEnded()
    {
        boolean checkHero = true;
        boolean checkVillain = true;
        for(int i = 0; i < 6; i++)
        {
            if(hero[i].p.dexNum != 0 && hero[i].p.status != 1){checkHero = false;}
            if(villain[i].p.dexNum != 0 && villain[i].p.status != 1){checkVillain = false;}
        }

        if(checkHero){System.out.println("You lost the battle!");}
        if(checkVillain){System.out.println("You won the battle!");}

        return (checkHero || checkVillain);
    }

    private int[] promptHeroAction()
    {
        Scanner sc = new Scanner(System.in);
        int[] choices = new int[2];
        System.out.println("Choose action:");
        System.out.println("1. Attack    3. Item");
        System.out.println("2. Switch    4. Flee");
        choices[0] = sc.nextInt() - 1;
        if(choices[0] == 0)
        {
            choices[1] = promptMove();
        }
        else if(choices[0] == 1)
        {
            choices[1] = promptSwitch();
        }
        else if(choices[0] == 2)
        {
            choices[1] = 0;
        }
        else if(choices[0] == 3){choices[1] = 0;}

        if(choices[0] >= 4 || choices[0] < 0)
        {
            System.out.println("Invalid Selection...");
            return promptHeroAction();
        }

        return choices;
    }

    private int[] promptVillainAction()
    {
        return new int[]{0, 0};
    }

    private int promptMove()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose move:");
        System.out.println(hero[currHero].p.listMoves());
        int choice = sc.nextInt() - 1;
        if(hero[currHero].p.moves[choice].index == 0 || choice >= 4 || choice < 0)
        {
            System.out.println("Invalid Selection...");
            return promptMove();
        }
        return choice;
    }

    private int promptSwitch()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose who to switch:");
        for(int i = 0; i < 6; i++)
        {
            if(currHero != i)
            {
                System.out.println((hero[i].p.dexNum != 0) ? String.format("%d. %s lv.%d (%d/%d) %s",
                i+1, hero[i].p.name, hero[i].p.level, hero[i].p.currHP, hero[i].p.stats[0], hero[i].p.statuses[hero[i].p.status].replace("NONE", "")) :
                String.format("%d. %s", i+1, "(empty)"));
            }
        }

        int choice = sc.nextInt() - 1;
        if(currHero == choice || choice >= 6 || choice < 0)
        {
            System.out.println("Invalid Selection...");
            return promptSwitch();
        }
        else if(hero[choice].p.dexNum == 0)
        {
            System.out.println("Invalid Selection...");
            return promptSwitch();
        }

        System.out.println(String.format("%s retreated. Go %s!", hero[currHero].p.name,  hero[choice].p.name));
        return choice;
    }

    private void execute(boolean b, int[] action)
    {
        if(ended){return;}

        if(action[0] == 0)
        {
            TransientPokemon attacker = b ? hero[currHero] : villain[currVillain];
            if(attacker.p.status == 1){return;}
            System.out.println();
            useMove(b, action[1]);
        }
        else if(action[0] == 1)
        {
            System.out.println();
            if(b)
            {
                currHero = action[1];
            }
            else
            {
                System.out.println(String.format("%s switched out for %s.", villain[currVillain].p.name, villain[action[1]].p.name));
                currVillain = action[1];
            }
        }
        else if(action[0] == 2)
        {
            //use item
        }
        else if(action[0] == 3)
        {
            System.out.println();
            System.out.println("Ran away from the fight...");
            ended = true;
        }

        try{Thread.sleep(1500);}catch(Exception e){}
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
        System.out.println(attacker.p.name + " attacked " +
            defender.p.name + " with " +
            m.name);
        try{Thread.sleep(750);}catch(Exception e){}
        if(rand.nextFloat() < chance)
        {
            float ratio = (m.cat == 0)?
                ((float) attacker.tempStats[0]) / ((float) defender.tempStats[1]) :
                ((float) attacker.tempStats[2]) / ((float) defender.tempStats[3]);

            float adv = calcMod(attacker, defender, m, false);
            float dmg = (2f * attacker.p.level / 5);
            dmg = ((dmg + 2) * m.power * ratio) / 50 + 2;
            dmg *= adv * (rand.nextFloat(0.15f) + 0.85f);
            try{Thread.sleep(750);}catch(Exception e){}
            defender.damage((int) dmg);
        }
        else{System.out.println("The move missed!");}
    }

    private float calcMod(TransientPokemon attacker, TransientPokemon defender, Move m, boolean crit)
    {
        float mod = 1.0f;
        mod *= checkEff(m.type, defender.p.type1);
        mod *= checkEff(m.type, defender.p.type2);
        if(mod >= 2.0){System.out.println("It's super effective!");}
        if(mod <= 0.5){System.out.println("It's not very effective...");}
        if(mod == 0.0){System.out.println("It's had no effect!");}
        if(m.type == attacker.p.type1 || m.type == attacker.p.type2){mod *= 1.5f;}
        if(crit){mod *= 2f;}
        return mod;
    }

    private float checkEff(int A, int D)
    {
        if(effMatrix == null){buildeffMatrix();}
        return effMatrix[A][D];
    }

    private void buildeffMatrix()
    {
        File em = new File("effMatrix.txt");
        String[] data = {};
        try
        {
            Scanner emReader = new Scanner(em);
            effMatrix = new float[18][18];
            for(int i = 0; i < 18; i++)
            {
                data = emReader.nextLine().split(" ");
                for(int j = 0; j < 18; j++)
                {
                    effMatrix[i][j] = Float.parseFloat(data[j]);
                }
            }
            emReader.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("file: " + em.getName() + " not found!");
        }
    }
}
