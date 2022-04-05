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
        // System.out.println(test1);

        //setup charizard
        Pokemon test2 = new Pokemon(6, 50, "Rash", new int[]{31, 31, 31, 31, 31, 31});
        test2.setNick("Cinderbreath");
        test2.setEVs(new int[]{64, 64, 64, 64, 64, 64});
        test2.setMove(0, new Move(3));
        test2.setMove(1, new Move(1));
        // System.out.println(test2);

        //setup blastoise
        Pokemon test3 = new Pokemon(9, 50, "Impish", new int[]{31, 31, 31, 31, 31, 31});
        test3.setNick("Shellshocker");
        test3.setEVs(new int[]{0, 0, 0, 0, 0, 0});
        test3.setMove(0, new Move(4));
        test3.setMove(1, new Move(1));
        test3.status = 5;
        // System.out.println(test3);

        //create player party
        Player you = new Player("Your Name");
        you.addPokemon(test1);
        you.addPokemon(test3);

        //setup and start battle
        Battler b = new Battler(you, test2);
        // b.start();
        // System.out.println(test2);
        // System.out.println(you.listTeam());

        you.refreshAll();
        test2.refresh();
        // System.out.println(test2);
        // System.out.println(you.listTeam());

        you.awardBadge(3);
        System.out.println(you.listBadges());

        Item[] testItems = {new Item(1), new Item(2), new Item(3), new Item(4), new Item(5)};
        you.addItem(testItems[0]);
        you.addItem(testItems[1], 5);
        you.addItem(testItems[2], 20);
        you.addItem(testItems[3], 99);
        you.removeItem(testItems[1]);
        you.removeItem(testItems[2], 5);
        you.removeAllItem(testItems[3]);
        System.out.println(you.listBag());

        System.out.println(new Item(0));
        System.out.println(testItems[4]);

    }
}
