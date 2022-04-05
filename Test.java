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

        //create a test player
        Player.p = new Player("Your Name");

        //test some items
        Player.p.awardBadge(3);
        // System.out.println(you.listBadges());
        Item[] testItems = {new Item(1), new Item(2), new Item(3), new Item(4), new Item(5)};
        Player.p.addItem(testItems[0]);
        Player.p.addItem(testItems[1], 5);
        Player.p.addItem(testItems[2], 20);
        Player.p.addItem(testItems[3], 99);
        Player.p.removeItem(testItems[1]);
        Player.p.removeItem(testItems[2], 5);
        Player.p.removeAllItem(testItems[3]);
        // System.out.println(you.listBag());

        // System.out.println(new Item(0));
        // System.out.println(testItems[4]);

        //create player party
        Player.p.addPokemon(test1);
        Player.p.addPokemon(test3);

        //setup and start battle
        Battler b = new Battler(Player.p, test2);
        b.start();
        // System.out.println(test2);
        // System.out.println(you.listTeam());

        Player.p.refreshAll();
        test2.refresh();
        // System.out.println(test2);
        // System.out.println(you.listTeam());

        Actor other = new Actor("The other guy");
        other.addPokemon(test2);

        b = new Battler(Player.p, other);
        b.start();
    }
}
