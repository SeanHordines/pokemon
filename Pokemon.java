import java.lang.Math;
import java.util.Random;

//specific instance of BasePokemon
//additional stats NOT common to all instances of Pokemon
public class Pokemon extends BasePokemon
{
    protected static final String[][] natMatrix =
        {{"Hardy", "Lonely", "Adamant", "Naughty", "Brave"},
        {"Bold", "Docile", "Impish", "Lax", "Relaxed"},
        {"Modest", "Mild", "Bashful", "Rash", "Quiet"},
        {"Calm", "Gentle", "Careful", "Quirky", "Sassy"},
        {"Timid", "Hasty", "Jolly", "Naive", "Serious"}};
    protected static final String[] statuses = {"NONE", "FAINTED", "BURNED", "FROZEN", "PARALYZED", "POISONED", "ASLEEP"};

    //basic stats of any specific pokemon
    public String nickname = "";
    public int level, Exp, levelUpExp;
    public int[] stats = new int[6]; //HP, Atk, Def, SpAtk, SpDef, Spd
    public int currHP, status = 0;

    //nature increases one stat by 10% and decreases one stat by 10%
    public String nature;
    public float[] natMods = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f};

    //IVs and EVs govern stat growth
    public int[] IVs = new int[6], EVs = new int[6];

    //list of moves currently known (max 4)
    public Move[] moves = new Move[]{new Move(0), new Move(0), new Move(0), new Move(0)};

    //construct random pokemon from dex number and level
    public Pokemon(int n, int l)
    {
        super(n);
        level = l;
        nickname = name;

        Random rand = new Random();
        nature = natMatrix[rand.nextInt(5)][rand.nextInt(5)];
        calcNat();

        for(int i = 0; i < 6; i++)
        {
            IVs[i] = rand.nextInt(32);
        }

        //necessary to calculate other info
        buildPokemon();
    }

    //constructs custom pokemon
    public Pokemon(int n, int l, String nat, int[] inputIVs)
    {
        super(n);
        level = l;
        nickname = name;
        nature = nat;
        calcNat();

        for(int i = 0; i < 6; i++)
        {
            IVs[i] = inputIVs[i];
        }

        //necessary to calculate other info
        buildPokemon();
    }

    public void setNick(String nick)
    {
        nickname = nick;
    }

    //allows for printing via print(Pokemon)
    public String toString()
    {
        return String.format("%s (%s) lv. %d\nType(s): %s %s\n%s %s\n%s %s\n%s %s\n%s\n",
            nickname, name, level,
            types[type1], types[type2].replace("NONE", ""),
            String.format("HP: %d/%d", currHP, stats[0]), statuses[status].replace("NONE", ""),
            String.format("Atk:   %3d", stats[1]), String.format("Def:   %3d", stats[2]),
            String.format("SpAtk: %3d", stats[3]), String.format("SpDef: %3d", stats[4]),
            String.format("Spd:   %3d", stats[5]));
    }

    //gain the following as reward from battle
    public void reward(int nExp, int[] inputEVs)
    {
        Exp += nExp;

        for(int i = 0; i < 6; i++)
        {
            EVs[i] = Math.max(256, EVs[i] + inputEVs[i]);
        }
        levelUp();
    }

    public void setEVs(int[] inputEVs)
    {
        for(int i = 0; i < 6; i++)
        {
            EVs[i] = Math.min(256, EVs[i] + inputEVs[i]);
        }
        buildPokemon();
    }

    public void setMove(int i, Move m)
    {
        moves[i] = m;
    }

    public void setStatus(int s)
    {
        status = s;
    }

    //outputs a string to be printed detailing the known moves
    public String listMoves()
    {
        String out = "";
        for(int i = 0; i < 4; i++)
        {
            out += (moves[i].index != 0) ? String.format("%d. %s\n", i+1, moves[i].name) : String.format("%d. %s\n", i+1, "(Empty)");
        }
        return out.substring(0, out.length()-1);
    }

    //build pokemon stats and health given inputs
    private void buildPokemon()
    {
        //calculate HP using formula 2
        float temp = (2f * baseStats[0]) + IVs[0] + (EVs[0]/4);
        temp = 0.01f * level * temp;
        temp = temp + 10f + level;
        stats[0] = (int) temp;

        //calculate Atk, Def, SpAtk, SpDef, Spd
        for(int i = 1; i < 6; i++)
        {
            stats[i] = calcStat(baseStats[i], IVs[i], EVs[i], natMods[i-1]);
        }

        //growth rate determined by BST
        levelUpExp = BST * level * level / 18;

        //set current health to full
        currHP = stats[0];
    }

    private void levelUp()
    {
        //loop until not enough to keep leveling up
        while(Exp >= levelUpExp && level < 100)
        {
            level += 1;
            Exp -= levelUpExp;
            buildPokemon();
        }
    }

    //calculate stat using formula 1
    private int calcStat(int base, int IV, int EV, float mod)
    {
        float temp = (2f * base) + IV + (EV/4);
        temp = 0.01f * level * temp;
        temp = (temp + 5f) * mod;
        return (int) temp;
    }

    //nature increases one stat by 10% and decreases one stat by 10%
    //these stats may be the same
    private void calcNat()
    {
        //which 2 stats to change
        int inc = 0, dec = 0;

        //find index in 2d array from nature
        boolean found = false;
        for(int i = 0; i < 5; i++)
        {
            if(found){break;}
            for(int j = 0; j < 5; j++)
            {
                if(nature == natMatrix[i][j])
                {
                    inc = i;
                    dec = j;
                    found = true;
                    break;
                }
            }
        }

        //record changes
        natMods[inc] += 0.1f;
        natMods[dec] -= 0.1f;
    }
}
