import java.lang.Math;
import java.util.Random;

public class Pokemon extends BasePokemon
{
    protected static final String[][] natMatrix =
        {{"Hardy", "Lonely", "Adamant", "Naughty", "Brave"},
        {"Bold", "Docile", "Impish", "Lax", "Relaxed"},
        {"Modest", "Mild", "Bashful", "Rash", "Quiet"},
        {"Calm", "Gentle", "Careful", "Quirky", "Sassy"},
        {"Timid", "Hasty", "Jolly", "Naive", "Serious"}};
    protected static final String[] statuses = {"NONE", "FAINTED", "BURNED", "FROZEN", "PARALYZED", "POISONED", "ASLEEP"};

    public String nickname = "";
    public int level, Exp, levelUpExp;
    public int[] stats = new int[6];
    public int currHP, status = 0;
    public String nature;
    public float[] natMods = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    public int[] IVs = new int[6], EVs = new int[6];

    public Move[] moves = new Move[]{new Move(0), new Move(0), new Move(0), new Move(0)};

    public Pokemon(int n)
    {
        super(n);
        level = 5;
        nickname = name;

        Random rand = new Random();
        nature = natMatrix[rand.nextInt(5)][rand.nextInt(5)];
        calcNat();

        for(int i = 0; i < 6; i++)
        {
            IVs[i] = rand.nextInt(33);
        }

        buildPokemon();
    }

    public Pokemon(int n, int lev, String nat, int[] inputIVs)
    {
        super(n);

        level = lev;
        nickname = name;
        nature = nat;
        calcNat();

        for(int i = 0; i < 6; i++)
        {
            IVs[i] = inputIVs[i];
        }

        buildPokemon();
    }

    public void setNick(String nick)
    {
        nickname = nick;
    }

    public String toString()
    {
        return String.format("%s (%s) lv. %d\nType(s): %s %s\n%s %s\n%s %s\n%s %s\n%s",
            nickname, name, level,
            types[type1], types[type2].replace("NONE", ""),
            String.format("HP: %d/%d", currHP, stats[0]), statuses[status].replace("NONE", ""),
            String.format("Atk:   %3d", stats[1]), String.format("Def:   %3d", stats[2]),
            String.format("SpAtk: %3d", stats[3]), String.format("SpDef: %3d", stats[4]),
            String.format("Spd:   %3d", stats[5]));
    }

    public void award(int nExp, int[] inputEVs)
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

    public String listMoves()
    {
        String out = "";
        for(int i = 0; i < 4; i++)
        {
            out += (moves[i].index != 0) ? String.format("%d. %s\n", i+1, moves[i].name) : String.format("%d. %s\n", i+1, "(Empty)");
        }
        return out.substring(0, out.length()-1);
    }

    private void buildPokemon()
    {
        float temp = (2f * baseStats[0]) + IVs[0] + (EVs[0]/4);
        temp = 0.01f * level * temp;
        temp = temp + 10f + level;
        stats[0] = (int) temp;
        for(int i = 1; i < 6; i++)
        {
            stats[i] = calcStat(baseStats[i], IVs[i], EVs[i], natMods[i-1]);
        }
        levelUpExp = BST * level * level / 18;
        currHP = stats[0];
    }

    private void levelUp()
    {
        while(Exp >= levelUpExp && level < 100)
        {
            level += 1;
            Exp -= levelUpExp;
            buildPokemon();
        }
    }

    private int calcStat(int base, int IV, int EV, float mod)
    {
        float temp = (2f * base) + IV + (EV/4);
        temp = 0.01f * level * temp;
        temp = (temp + 5f) * mod;
        return (int) temp;
    }

    private void calcNat()
    {
        int inc = 0, dec = 0;
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
        natMods[inc] += 0.1f;
        natMods[dec] -= 0.1f;
    }
}
