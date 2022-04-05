import java.io.*;
import java.util.*;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Thread;
import java.util.Arrays;

//use to simulate battle between two teams
public class Battler
{
    //run the battle sim with delays for easier reading
    private boolean DELAY = true;

    //type effectiveness chart
    private float[][] effMatrix;

    private BattlePokemon[] hero = new BattlePokemon[6], villain = new BattlePokemon[6];
    private int currHero = 0, currVillain = 0;
    private String heroName, villainName = "";
    private static boolean ended = false;
    private Map<Item, Integer> bag;

    //construct battle between two single pokemon
    public Battler(Pokemon a, Pokemon b)
    {
        hero[0] = new BattlePokemon(a);
        villain[0] = new BattlePokemon(b);
        heroName = "You";
    }

    //construct battle between two teams
    public Battler(Pokemon[] a, Pokemon[] b)
    {
        for(int i = 0; i < 6; i++)
        {
            hero[i] = new BattlePokemon(a[i]);
            villain[i] = new BattlePokemon(b[i]);
            heroName = "You";
        }
    }

    //construct battle between teams and single pokemon
    public Battler(Pokemon[] a, Pokemon b)
    {
        for(int i = 0; i < 6; i++)
        {
            hero[i] = new BattlePokemon(a[i]);
            heroName = "You";
        }
        villain[0] = new BattlePokemon(b);
    }

    //construct battle between two Actors
    public Battler(Actor a, Actor b)
    {
        for(int i = 0; i < 6; i++)
        {
            hero[i] = new BattlePokemon(a.team[i]);
            villain[i] = new BattlePokemon(b.team[i]);
        }
        heroName = a.name;
        villainName = b.name;
    }

    //construct battle between Actor and team
    public Battler(Actor a, Pokemon[] b)
    {
        for(int i = 0; i < 6; i++)
        {
            hero[i] = new BattlePokemon(a.team[i]);
            villain[i] = new BattlePokemon(b[i]);
        }
        heroName = a.name;
    }

    //construct battle between Actor and single pokemon
    public Battler(Actor a, Pokemon b)
    {
        for(int i = 0; i < 6; i++)
        {
            hero[i] = new BattlePokemon(a.team[i]);
        }
        heroName = a.name;

        Arrays.fill(villain, new BattlePokemon(Pokemon.nullPokemon));
        villain[0] = new BattlePokemon(b);
    }

    //run the battle simulation
    public void start()
    {
        ended = false;
        if(villainName == "")
        {
            System.out.println(String.format("A wild %s appeared!", villain[currVillain].p.name));
        }
        else
        {
            System.out.println(String.format("%s wants to Battle!", villainName));
            System.out.println(String.format("%s sent out %s", villainName, villain[currVillain].p.nickname));
        }
        System.out.println(String.format("Go %s!", hero[currHero].p.nickname));

        //loop until one side wins
        do
        {
            //check if active pokemon are fainted
            if(hero[currHero].p.status == 1)
            {
                execute(true, new int[]{2, promptSwitch()});
            }
            if(villain[currVillain].p.status == 1)
            {
                execute(false, new int[]{2, currVillain+1});
            }

            //get the actions of hero and villain
            System.out.println();
            int[] heroAction = promptHeroAction();
            int[] villainAction = promptVillainAction();

            if(ended){break;}

            if(heroAction[0] > 1) //not using a move
            {
                execute(true, heroAction);
                execute(false, villainAction);
            }
            else if(villainAction[0] > 1) //not using a move
            {
                execute(false, villainAction);
                execute(true, heroAction);
            }
            else //using a move
            {
                //check for priority on moves
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
                else //no priority difference
                {
                    //check speed of active pokemon
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

            //check if one side has won
            ended = ended || checkEnded();
        }
        while(!ended);
    }

    //checks if all pokemon of either team are fainted
    private boolean checkEnded()
    {
        boolean checkHero = true;
        boolean checkVillain = true;
        for(int i = 0; i < 6; i++)
        {
            if(hero[i].p.dexNum != 0 && hero[i].p.status != 1){checkHero = false;}
            if(villain[i].p.dexNum != 0 && villain[i].p.status != 1){checkVillain = false;}
        }

        if(checkVillain)
        {
            System.out.println(String.format("\n%s won the battle!", heroName));
        }
        else if(checkHero)
        {
            System.out.println(String.format("\n%s lost the battle!", heroName));
        }

        return (checkHero || checkVillain);
    }

    //prompt input via the console
    private int[] promptHeroAction()
    {
        Scanner sc = new Scanner(System.in);
        int[] choices = new int[2];
        System.out.println("Choose action:");
        System.out.println("1. Attack    3. Item");
        System.out.println("2. Switch    4. Flee");
        choices[0] = sc.nextInt();
        if(choices[0] == 1) // use a move
        {
            choices[1] = promptMove();
        }
        else if(choices[0] == 2) //switch pokemon
        {
            choices[1] = promptSwitch();
        }
        else if(choices[0] == 3) //use an item
        {
            choices[1] = promptItem();
        }
        else if(choices[0] == 4) //flee
        {
            if(villainName != "")
            {
                System.out.println("Cannot flee from trainer battle...");
                return promptHeroAction();
            }
            else
            {
                choices[1] = 1;
            }
        }
        else
        {
            System.out.println("Invalid Selection...");
            return promptHeroAction();
        }

        //check if back was selected
        if(choices[1] == 0){return promptHeroAction();}

        return choices;
    }

    //get action from opponent ai
    private int[] promptVillainAction()
    {
        //super sophisticated algorithm that chooses the first move
        return new int[]{1, 1};
    }

    //get move input from console
    private int promptMove()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose move:");
        System.out.println(hero[currHero].p.listMoves());
        System.out.println("Use -X to view move or 0 to go back");
        int choice = sc.nextInt();

        //check if go back is selected
        if(choice == 0){return choice;}

        if(choice < 0 &&  choice >= -4)
        {
            choice *= -1;
            if(hero[currHero].p.moves[choice-1].index == 0)
            {
                System.out.println("Invalid Selection...");
                return promptMove();
            }
            else
            {
                System.out.println(hero[currHero].p.moves[choice-1]);
                return promptMove();
            }
        }

        //check if input is valid
        else if(choice > 4 || choice < 1)
        {
            System.out.println("Invalid Selection...");
            return promptMove();
        }
        else if(hero[currHero].p.moves[choice-1].index == 0)
        {
            System.out.println("Invalid Selection...");
            return promptMove();
        }
        else if(hero[currHero].p.moves[choice-1].ppCurr == 0)
        {
            System.out.println("Selected move is out of PP...");
            return promptMove();
        }
        return choice;
    }

    //get switch input from console
    private int promptSwitch()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose who to switch:");
        for(int i = 0; i < 6; i++)
        {
            System.out.print((hero[i].p.dexNum != 0) ?
                String.format("%d. %s (%s) lv.%d (%d/%d) %s",
                    i+1, hero[i].p.nickname, hero[i].p.name, hero[i].p.level,
                    hero[i].p.currHP, hero[i].p.stats[0], hero[i].p.statuses[hero[i].p.status].replace("NONE", "")) :
                String.format("%d. %s", i+1, "(empty)"));
            if(i == currHero){System.out.println(" *Active*");}
            else{System.out.println();}
        }
        System.out.println("Use -X to view Pokemon or 0 to go back");

        int choice = sc.nextInt();

        //check if go back is selected
        if(choice == 0){return choice;}

        if(choice < 0 && choice >= -6)
        {
            choice *= -1;
            if(hero[choice-1].p.dexNum == 0)
            {
                System.out.println("Invalid Selection...");
                return promptSwitch();
            }
            else
            {
                System.out.println(hero[choice-1]);
                return promptSwitch();
            }
        }

        //check if input is valid
        else if(currHero == choice-1 || choice >= 6 || choice < 0)
        {
            System.out.println("Invalid Selection...");
            return promptSwitch();
        }
        else if(hero[choice-1].p.dexNum == 0)
        {
            System.out.println("Invalid Selection...");
            return promptSwitch();
        }
        else if(hero[choice-1].p.status == 1)
        {
            System.out.println("Invalid Selection...");
            return promptSwitch();
        }

        System.out.println();
        System.out.println(String.format("%s retreated. Go %s!", hero[currHero].p.nickname,  hero[choice-1].p.nickname));
        return choice;
    }

    public int promptItem()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose item:");
        System.out.println(Player.p.listBag());
        System.out.println("Use -X to view Pokemon or 0 to go back");

        int choice = sc.nextInt();
        return choice;
    }

    //execute the specificed action for either the hero or the villain
    private void execute(boolean b, int[] action)
    {
        //check if other team has already lost
        if(ended){return;}

        if(action[0] == 1) //use a move
        {
            BattlePokemon attacker = b ? hero[currHero] : villain[currVillain];
            System.out.println();
            if(attacker.p.status == 1){return;}
            useMove(b, action[1]-1);
        }
        else if(action[0] == 2) //switch pokemon
        {
            if(b)
            {
                currHero = action[1]-1;
            }
            else
            {
                System.out.println(String.format("%s (%s) switched out for %s (%s).",
                    villain[currVillain].p.nickname, villain[currVillain].p.name,
                    villain[action[1]-1].p.nickname, villain[action[1]-1].p.name));
                currVillain = action[1]-1;
            }
        }
        else if(action[0] == 3) //use item
        {
            System.out.println();
            System.out.println("This doesn't work yet");
        }
        else if(action[0] == 4) //flee
        {
            System.out.println();
            System.out.println(String.format("%s ran away from the fight...", b ? "You" : villain[currVillain].p.name));
            ended = true;
        }

        try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
    }

    //execute the use of a move
    private void useMove(boolean b, int moveIndex)
    {
        //determine who is attacking
        BattlePokemon attacker = b ? hero[currHero] : villain[currVillain];
        BattlePokemon defender = b ? villain[currVillain] : hero[currHero];

        //get move from attacker
        Move m = attacker.p.moves[moveIndex];
        m.ppCurr -= 1;

        //get the acc and eva
        float acc = (float) attacker.tempStats[5];
        float eva = (float) defender.tempStats[6];
        float chance = (acc / eva) * ((float) m.acc /100f);

        //setup random
        Random rand = new Random();

        System.out.println(attacker.p.nickname + " attacked " +
            defender.p.nickname + " with " +
            m.name);
        try{if(DELAY){Thread.sleep(750);}}catch(Exception e){}

        if(rand.nextFloat() < chance) //check if hits
        {
            //use Atk and Def or use SpAtk and SpDef
            float ratio = (m.cat == 0)?
                ((float) attacker.tempStats[0]) / ((float) defender.tempStats[1]) :
                ((float) attacker.tempStats[2]) / ((float) defender.tempStats[3]);

            //check for crit
            chance = (float) attacker.tempStats[7] / 10000f;
            boolean crit = (rand.nextFloat() < chance);

            //calculate damage modifier
            float mod = calcMod(attacker, defender, m, crit);

            //calculate and deal damage
            float dmg = (2f * attacker.p.level / 5);
            dmg = ((dmg + 2) * m.power * ratio) / 50 + 2;
            dmg *= mod * (rand.nextFloat(0.15f) + 0.85f);
            defender.damage((int) dmg);
        }
        else{System.out.println("The move missed!");}
    }

    //calculate the damage modifer of an attacking move
    private float calcMod(BattlePokemon attacker, BattlePokemon defender, Move m, boolean crit)
    {
        float mod = 1.0f;
        //check type of move vs type(s) of defender
        mod *= checkEff(m.type, defender.p.type1); //type1 check
        mod *= checkEff(m.type, defender.p.type2); //type2 check
        if(mod >= 2.0)
        {
            System.out.println("It's super effective!");
            try{if(DELAY){Thread.sleep(750);}}catch(Exception e){}
        }
        if(mod <= 0.5)
        {
            System.out.println("It's not very effective...");
            try{if(DELAY){Thread.sleep(750);}}catch(Exception e){}
        }
        if(mod == 0.0)
        {
            System.out.println("It's had no effect!");
            try{if(DELAY){Thread.sleep(750);}}catch(Exception e){}
        }

        //check if type of move matches type of attacker (STAB)
        if(m.type == attacker.p.type1 || m.type == attacker.p.type2){mod *= 1.5f;}

        //check for critical hit
        if(crit)
        {
            try{if(DELAY){Thread.sleep(750);}}catch(Exception e){}
            System.out.println("Critical hit!");
            mod *= 2f;
        }
        return mod;
    }

    //pull data from effMatrix
    private float checkEff(int A, int D)
    {
        //make sure matrix exists, if not then build it
        if(effMatrix == null){buildeffMatrix();}

        return effMatrix[A][D];
    }

    //build effMatrix from file
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
