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
    public static final Pokemon pokeNull = new Pokemon(0);

    public String nickname = "";
    public int level, Exp, levelUpExp;
    public int[] stats = new int[6];
    public int currHP, status = 0;
    public String nature;
    public float[] natMods = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    public int[] IVs = new int[6], EVs = new int[6];

    public Move[] moves = new Move[]{Move.moveNull, Move.moveNull, Move.moveNull, Move.moveNull};

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
            types[primaryType], types[secondaryType].replace("NONE", ""),
            String.format("HP: %d/%d", currHP, stats[0]), statuses[status].replace("NONE", ""),
            String.format("Atk:   %3d",stats[1]), String.format("Def:   %3d",stats[2]),
            String.format("SpAtk: %3d",stats[3]), String.format("SpDef: %3d",stats[4]),
            String.format("Spd:   %3d",stats[5]));
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
            EVs[i] = Math.max(256, EVs[i] + inputEVs[i]);
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
        return String.format("%s (%s)", nickname, name) +
            "\nMove 1:\n" + moves[0].name +
            "\nMove 2:\n" + moves[1].name +
            "\nMove 3:\n" + moves[2].name +
            "\nMove 4:\n" + moves[3].name;
    }

    private void buildPokemon()
    {
        stats[0] = (2 * baseStats[0]) + IVs[0] + (int) Math.floor(EVs[0]/4);
        stats[0] = (int) Math.floor((float) stats[0] * level / 100f);
        stats[0] = stats[0] + level + 10;
        currHP = stats[0];

        for(int i = 1; i < 6; i++)
        {
            stats[i] = calcStat(baseStats[i], IVs[i], EVs[i], natMods[i-1]);
        }

        levelUpExp = (int) Math.floor(BST * Math.pow(level, 2) / 18);
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
        int temp = (2 * base) + IV + (int) Math.floor(EV/4);
        temp = (int) Math.floor((float) temp * level / 100f);
        temp = (int) Math.floor((float) (temp + 5) * mod);
        return temp;
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
