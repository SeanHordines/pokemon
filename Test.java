import java.util.Arrays;

public class Test
{
    public static void main(String[] args)
    {
        //setup Venusaur
        Pokemon test1 = new Pokemon(3, 50, "Sassy", new int[]{31, 31, 31, 31, 31, 31});
        test1.setNick("Bruteroot");
        test1.setEVs(new int[]{64, 64, 64, 64, 64, 64});
        test1.setMove(0, new Move(2));
        test1.setMove(1, new Move(1));
        System.out.println(test1);

        //setup charizard
        Pokemon test2 = new Pokemon(6, 50, "Rash", new int[]{31, 31, 31, 31, 31, 31});
        test2.setNick("Cinderbreath");
        test2.setEVs(new int[]{64, 64, 64, 64, 64, 64});
        test2.setMove(0, new Move(3));
        test2.setMove(1, new Move(1));
        System.out.println(test2);

        //setup blastoise
        Pokemon test3 = new Pokemon(9, 50, "Impish", new int[]{31, 31, 31, 31, 31, 31});
        test3.setNick("Shellshocker");
        test3.setEVs(new int[]{0, 0, 0, 0, 0, 0});
        test3.setMove(0, new Move(4));
        test3.setMove(1, new Move(1));
        test3.status = 5;
        System.out.println(test3);

        //create player party
        Pokemon[] heroParty = new Pokemon[6];
        Arrays.fill(heroParty, new Pokemon(0, 0));
        heroParty[0] = test1;
        heroParty[1] = test3;

        //create opponent party
        Pokemon[] villainParty = new Pokemon[6];
        Arrays.fill(villainParty, new Pokemon(0, 0));
        villainParty[0] = test2;

        //setup and start battle
        Battler b = new Battler(heroParty, villainParty);
        b.start();
    }
}
