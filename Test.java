import java.util.Arrays;

public class Test
{
    public static void main(String[] args)
    {
        Pokemon test1 = new Pokemon(3, 50, "Sassy", new int[]{31, 31, 31, 31, 31, 31});
        test1.setNick("Bruteroot");
        test1.setEVs(new int[]{64, 64, 64, 64, 64, 64});
        // System.out.println(test1);

        Pokemon test2 = new Pokemon(6, 50, "Rash", new int[]{31, 31, 31, 31, 31, 31});
        test2.setNick("Cinderbreath");
        test2.setEVs(new int[]{64, 64, 64, 64, 64, 64});
        // System.out.println(test2);

        Pokemon test3 = new Pokemon(9, 50, "Impish", new int[]{31, 31, 31, 31, 31, 31});
        test3.setNick("Shellshocker");
        test3.setEVs(new int[]{0, 0, 0, 0, 0, 0});
        // System.out.println(test3);

        test1.setMove(0, new Move(1));
        test2.setMove(0, new Move(2));

        Pokemon[] heroParty = new Pokemon[6];
        Arrays.fill(heroParty, new Pokemon(0));
        heroParty[0] = test1;
        heroParty[1] = test3;
        Pokemon[] villainParty = new Pokemon[6];
        Arrays.fill(villainParty, new Pokemon(0));
        villainParty[0] = test2;

        Battler b = new Battler(heroParty, villainParty);
        b.start();
    }
}
