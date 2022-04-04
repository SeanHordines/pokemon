import java.util.Arrays;

public class Test
{
    public static void main(String[] args)
    {
        Pokemon test1 = new Pokemon(3, 50, "Sassy", new int[]{24, 8, 32, 8, 32, 8});
        test1.setNick("Bruteroot");
        test1.setEVs(new int[]{128, 64, 248, 64, 248, 64});
        // System.out.println(test1);

        Pokemon test2 = new Pokemon(6, 50, "Rash", new int[]{8, 32, 8, 32, 8, 24});
        test2.setNick("Cinderbreath");
        test2.setEVs(new int[]{64, 248, 64, 248, 64, 128});
        // System.out.println(test2);

        Pokemon test3 = new Pokemon(9, 50, "Impish", new int[]{8, 8, 32, 8, 32, 24});
        test3.setNick("Shellshocker");
        test3.setEVs(new int[]{64, 64, 248, 64, 248, 128});
        // System.out.println(test3);

        test1.setMove(0, new Move(1));
        test2.setMove(0, new Move(1));
        TransientPokemon chosen = new TransientPokemon(test1);
        chosen.changeStat(0, -3);
        chosen.changeStat(3, +5);
        // System.out.println(chosen.listChanges());
        // System.out.println(chosen.listStats());

        Pokemon[] heroParty = new Pokemon[6];
        Arrays.fill(heroParty, Pokemon.pokeNull);
        Pokemon[] villainParty = new Pokemon[6];
        Arrays.fill(villainParty, Pokemon.pokeNull);
        heroParty[0] = test1;
        villainParty[0] = test2;
        Battler b = new Battler(heroParty, villainParty);
        b.ply();
        System.out.println(test1);
        System.out.println(test2);
    }
}
