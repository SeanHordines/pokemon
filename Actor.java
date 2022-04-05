import java.util.Arrays;

public class Actor
{
    public String name;
    public Pokemon[] team = new Pokemon[6];

    public Actor(String n)
    {
        name = n;
        Arrays.fill(team, Pokemon.nullPokemon);
    }

    public Actor(String n, Pokemon[] t)
    {
        name = n;
        team = t;
    }

    public void addPokemon(Pokemon p)
    {
        for(int i = 0; i < 6; i++)
        {
            if(team[i].dexNum == 0)
            {
                team[i] = p;
                return;
            }
        }
        System.out.println("Failed to add pokemon: team full");
    }

    public void removePokemon(int i)
    {
        team[i] = Pokemon.nullPokemon;
    }

    public String listTeam()
    {
        String out = "";
        for(int i = 0; i < 6; i++)
        {
            if(team[i].dexNum != 0)
            {
                out += String.format("%s (%s) lv.%d (%d/%d) %s\n",
                    team[i].nickname, team[i].name, team[i].level, team[i].currHP, team[i].stats[0],
                    team[i].statuses[team[i].status].replace("NONE", ""));
            }
        }

        return out;
    }

    public void refreshAll()
    {
        for(int i = 0; i < 6; i++)
        {
            team[i].refresh();
        }
    }
}
