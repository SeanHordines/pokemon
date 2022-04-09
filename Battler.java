import java.io.*;
import java.util.*;
import java.util.Random;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Thread;

//use to simulate battle between two teams
public class Battler
{
    //run the battle sim with delays for easier reading
    private boolean DELAY = true;

    //type effectiveness chart
    private static float[][] effMatrix;

    private BattlePokemon[] hero = new BattlePokemon[6], villain = new BattlePokemon[6];
    private int currHero = 0, currVillain = 0;
    private String heroName, villainName = "";
    private BattleAI heroAI = BattleAI.aiBasic, villainAI = BattleAI.aiWild;
    private static boolean ended = false;

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
        heroAI = a.ai;
        villainAI = b.ai;
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
        heroAI = a.ai;
    }

    //construct battle between Actor and single pokemon
    public Battler(Actor a, Pokemon b)
    {
        for(int i = 0; i < 6; i++)
        {
            hero[i] = new BattlePokemon(a.team[i]);
        }
        heroName = a.name;
        heroAI = a.ai;

        Arrays.fill(villain, new BattlePokemon(Pokemon.nullPokemon));
        villain[0] = new BattlePokemon(b);
    }

    //run the battle simulation
    public void start()
    {
        ended = false;
        if(heroName == ""){heroName = "You";}
        if(villainName == "")
        {
            HomebrewEngine.setBattleText(String.format("A wild %s appeared!", villain[currVillain].p.name));
        }
        else
        {
            HomebrewEngine.setBattleText(String.format("%s wants to Battle!", villainName));
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            HomebrewEngine.setBattleText(String.format("%s sent out %s.", villainName, villain[currVillain].p.nickname));
        }
        String path = String.format("sprites/front/%d.png", villain[currVillain].p.dexNum);
        HomebrewEngine.setSprite(path, false);
        try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}

        HomebrewEngine.setBattleText(String.format("Go %s!", hero[currHero].p.nickname));
        path = String.format("sprites/back/%d.png", hero[currHero].p.dexNum);
        HomebrewEngine.setSprite(path, true);
        try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
        HomebrewEngine.setBattleText("");

        //loop until one side wins
        do
        {
            //check if active pokemon are fainted
            if(hero[currHero].p.status == 1)
            {
                int s = 0;
                do
                {
                 s = promptSwitch();
                }
                while(s == 0);
                execute(true, new int[]{2, s});
            }
            if(villain[currVillain].p.status == 1)
            {
                execute(false, new int[]{2, currVillain+1});
            }

            //get the actions of hero and villain
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
            HomebrewEngine.setBattleText(String.format("%s won the battle!", heroName));
            try{if(DELAY){Thread.sleep(3000);}}catch(Exception e){}
        }
        else if(checkHero)
        {
            HomebrewEngine.setBattleText(String.format("%s lost the battle!", heroName));
            try{if(DELAY){Thread.sleep(3000);}}catch(Exception e){}
        }

        return (checkHero || checkVillain);
    }

    //prompt input via the console or hero ai
    private int[] promptHeroAction()
    {
        //check if not a player
        if(heroAI != Actor.player.ai){return heroAI.getAction(currHero, hero, villain[currVillain]);}

        int[] choices = new int[2];

        HomebrewEngine.setMenuText("Choose an action:");
        HomebrewEngine.addMenuButton("Attack", 1, 10, 50, 385, 115, false);
        HomebrewEngine.addMenuButton("Switch", 2, 405, 50, 385, 115, false);
        HomebrewEngine.addMenuButton("Item", 3, 10, 175, 385, 115, false);
        HomebrewEngine.addMenuButton("Flee", 4, 405, 175, 385, 115, false);

        do
        {
            choices[0] = HomebrewEngine.getMenuAction();
            try{Thread.sleep(100);}catch(Exception e){}
        }
        while(choices[0] == -9);

        HomebrewEngine.clearMenu();


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
                HomebrewEngine.setBattleText("Cannot flee from trainer battle...");
                try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
                HomebrewEngine.setBattleText("");
                return promptHeroAction();
            }
            else
            {
                choices[1] = 1;
            }
        }
        else
        {
            HomebrewEngine.setBattleText("Invalid Selection...");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            HomebrewEngine.setBattleText("");
            return promptHeroAction();
        }

        //check if back was selected
        if(choices[1] == 0){return promptHeroAction();}

        return choices;
    }

    //get action from opponent ai
    private int[] promptVillainAction()
    {
        return villainAI.getAction(currVillain, villain, hero[currHero]);
    }

    //get move input from console
    private int promptMove()
    {
        Move[] moves = hero[currHero].p.moves;
        int choice = 0;

        HomebrewEngine.setMenuText("Choose a move:");
        int[] x = new int[]{10, 405, 10, 405}; int[] y = new int[]{50, 50, 175, 175};
        int w = 385; int h = 110;
        for(int i = 0; i < 4; i++)
        {
            if(moves[i].index != 0)
            {
                HomebrewEngine.addMenuButton(moves[i].name.replace("NULL", "(empty)"), i+1, x[i]+h, y[i], w-h, h, false);
                HomebrewEngine.addMenuButton("View", -(i+1), x[i], y[i], h, h, false);
            }
        }
        HomebrewEngine.addMenuButton("Back", 0, 680, 10, 110, 30, false);

        do
        {
            choice = HomebrewEngine.getMenuAction();
            try{Thread.sleep(100);}catch(Exception e){}
        }
        while(choice == -9);

        HomebrewEngine.clearMenu();

        if(choice < 0 &&  choice >= -4)
        {
            choice *= -1;
            if(moves[choice-1].index == 0)
            {
                HomebrewEngine.setBattleText("Invalid Selection...");
                try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
                HomebrewEngine.setBattleText("");
                return promptMove();
            }
            else
            {
                HomebrewEngine.showMove(moves[choice-1]);
                choice = -9;
                do
                {
                    choice = HomebrewEngine.getMenuAction();
                    try{Thread.sleep(100);}catch(Exception e){}
                }
                while(choice == -9);
                return promptMove();
            }
        }

        if(choice == 0){return choice;}

        //check if input is valid
        else if(choice > 4 || choice < 1)
        {
            HomebrewEngine.setBattleText("Invalid Selection...");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            HomebrewEngine.setBattleText("");
            return promptMove();
        }
        else if(moves[choice-1].index == 0)
        {
            HomebrewEngine.setBattleText("Invalid Selection...");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            HomebrewEngine.setBattleText("");
            return promptMove();
        }
        else if(moves[choice-1].ppCurr == 0)
        {
            HomebrewEngine.setBattleText("Invalid Selection...");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            HomebrewEngine.setBattleText("");
            return promptMove();
        }
        return choice;
    }

    //get switch input from console
    private int promptSwitch()
    {
        int choice = 0;

        HomebrewEngine.setMenuText("Choose a Pokemon:");
        int x = 10; int y = 50; int w = 780; int h = 40;
        for(int i = 0; i < 4; i++)
        {
            if(hero[i].p.dexNum != 0)
            {
                String text = String.format("%s lv.%d (%d/%d) %s",
                    hero[i].p.name, hero[i].p.level, hero[i].p.currHP, hero[i].p.stats[0],
                    hero[i].p.statuses[hero[i].p.status].replace("NONE", ""));
                HomebrewEngine.addMenuButton(text, i+1, x+110, y+i*(h+10), w-110, h, true);
                HomebrewEngine.addMenuButton("View", -(i+1), x, y+i*(h+10), 110, h, false);
            }
        }
        HomebrewEngine.addMenuButton("Back", 0, 680, 10, 110, 30, false);

        do
        {
            choice = HomebrewEngine.getMenuAction();
            try{Thread.sleep(100);}catch(Exception e){}
        }
        while(choice == -9);

        HomebrewEngine.clearMenu();

        //check if go back is selected
        if(choice == 0){return choice;}

        if(choice < 0 && choice >= -6)
        {
            choice *= -1;
            if(hero[choice-1].p.dexNum == 0)
            {
                HomebrewEngine.setBattleText("Invalid Selection...");
                try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
                HomebrewEngine.setBattleText("");
                return promptSwitch();
            }
            else
            {
                HomebrewEngine.showPokemon(hero[choice-1]);
                choice = -9;
                do
                {
                    choice = HomebrewEngine.getMenuAction();
                    try{Thread.sleep(100);}catch(Exception e){}
                }
                while(choice == -9);
                return promptSwitch();
            }
        }

        //check if input is valid
        else if(currHero == choice-1 || choice >= 6 || choice < 0)
        {
            HomebrewEngine.setBattleText("Invalid Selection...");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            HomebrewEngine.setBattleText("");
            return promptSwitch();
        }
        else if(hero[choice-1].p.dexNum == 0)
        {
            HomebrewEngine.setBattleText("Invalid Selection...");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            HomebrewEngine.setBattleText("");
            return promptSwitch();
        }
        else if(hero[choice-1].p.status == 1)
        {
            HomebrewEngine.setBattleText("Invalid Selection...");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            HomebrewEngine.setBattleText("");
            return promptSwitch();
        }

        HomebrewEngine.setBattleText(String.format("%s retreated. Go %s!", hero[currHero].p.nickname,  hero[choice-1].p.nickname));
        return choice;
    }

    public int promptItem()
    {
        return 0;
    }

    //execute the specificed action for either the hero or the villain
    private void execute(boolean b, int[] action)
    {
        //check if other team has already lost
        if(ended){return;}

        if(action[0] == 1) //use a move
        {
            BattlePokemon attacker = b ? hero[currHero] : villain[currVillain];
            if(attacker.p.status == 1){return;}
            useMove(b, action[1]-1);
        }
        else if(action[0] == 2) //switch pokemon
        {
            String path;
            if(b)
            {
                currHero = action[1]-1;
                path = String.format("sprites/back/%d.png", hero[currHero].p.dexNum);
            }
            else
            {
                HomebrewEngine.setBattleText(String.format("%s (%s) switched out for %s (%s).",
                    villain[currVillain].p.nickname, villain[currVillain].p.name,
                    villain[action[1]-1].p.nickname, villain[action[1]-1].p.name));
                currVillain = action[1]-1;
                path = String.format("sprites/front/%d.png", villain[currVillain].p.dexNum);
            }
            HomebrewEngine.setSprite(path, b);
        }
        else if(action[0] == 3) //use item
        {
            System.out.println("This doesn't work yet");
        }
        else if(action[0] == 4) //flee
        {
            HomebrewEngine.setBattleText(String.format("%s ran away from the fight...", b ? "You" : villain[currVillain].p.name));
            ended = true;
        }

        try{if(DELAY){Thread.sleep(3000);}}catch(Exception e){}
        HomebrewEngine.setBattleText("");
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
        HomebrewEngine.setBattleText(String.format("%s attacked %s with %s.",
            attacker.p.nickname, defender.p.nickname, m.name));
        try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}

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
            HomebrewEngine.setBattleText(defender.p.nickname + " took " + (int) dmg +
                " damage. (" + defender.p.currHP + "/" + defender.p.stats[0] + "HP)");
            if(defender.p.currHP == 0)
            {
                defender.p.status = 1;
                try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
                HomebrewEngine.setBattleText(defender.p.nickname + " fainted!");
            }
        }
        else{HomebrewEngine.setBattleText("The move missed!");}
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
            HomebrewEngine.setBattleText("It's super effective!");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
        }
        if(mod <= 0.5)
        {
            HomebrewEngine.setBattleText("It's not very effective...");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
        }
        if(mod == 0.0)
        {
            HomebrewEngine.setBattleText("It's had no effect!");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
        }

        //check if type of move matches type of attacker (STAB)
        if(m.type == attacker.p.type1 || m.type == attacker.p.type2){mod *= 1.5f;}

        //check for critical hit
        if(crit)
        {
            HomebrewEngine.setBattleText("Critical hit!");
            try{if(DELAY){Thread.sleep(1500);}}catch(Exception e){}
            mod *= 2f;
        }
        return mod;
    }

    //pull data from effMatrix
    public static float checkEff(int A, int D)
    {
        //make sure matrix exists, if not then build it
        if(effMatrix == null){buildeffMatrix();}

        return effMatrix[A][D];
    }

    //build effMatrix from file
    private static void buildeffMatrix()
    {
        File em = new File("data/effMatrix.txt");
        String[] data = {};
        try
        {
            Scanner emReader = new Scanner(em);
            effMatrix = new float[18][18];
            for(int i = 0; i < 18; i++)
            {
                data = emReader.nextLine().split(",");
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
