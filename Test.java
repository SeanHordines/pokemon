public class Test
{
    public static void main(String[] args)
    {
        HomebrewEngine.init();

        //setup Venusaur
        Pokemon test1 = new Pokemon(3, 50, "Sassy", new int[]{31, 31, 31, 31, 31, 31});
        test1.setNick("Bruteroot");
        test1.setEVs(new int[]{64, 64, 64, 64, 64, 64});
        test1.setMove(0, new Move(348));
        test1.setMove(1, new Move(15));
        // System.out.println(test1);

        //setup charizard
        Pokemon test2 = new Pokemon(6, 50, "Rash", new int[]{31, 31, 31, 31, 31, 31});
        test2.setNick("Cinderbreath");
        test2.setEVs(new int[]{64, 64, 64, 64, 64, 64});
        test2.setMove(0, new Move(53));
        test2.setMove(1, new Move(15));
        // System.out.println(test2);

        //setup blastoise
        Pokemon test3 = new Pokemon(9, 50, "Impish", new int[]{31, 31, 31, 31, 31, 31});
        test3.setNick("Shellshocker");
        test3.setEVs(new int[]{0, 0, 0, 0, 0, 0});
        test3.setMove(0, new Move(57));
        test3.setMove(1, new Move(15));
        test3.status = 5;
        // System.out.println(test3);

        Actor.player.addPokemon(test1);
        Actor.player.addPokemon(test3);

        Actor other = new Actor("Rival Asswipe");
        other.addPokemon(test2);

        Battler b = new Battler(Actor.player, other);
        b.start();

        HomebrewEngine.close();
    }
}
