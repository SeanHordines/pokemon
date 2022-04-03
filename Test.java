public class Test
{
    public static void main(String[] args)
    {
        Pokemon test1 = new Pokemon(3, 50, "Sassy", new int[]{24, 8, 32, 8, 32, 8});
        test1.setNick("Bruteroot");
        test1.setEVs(new int[]{128, 64, 248, 64, 248, 64});
        System.out.println(test1);

        Pokemon test2 = new Pokemon(6, 50, "Rash", new int[]{8, 32, 8, 32, 8, 24});
        test2.setNick("Cinderbreath");
        test2.setEVs(new int[]{64, 248, 64, 248, 64, 128});
        System.out.println(test2);

        Pokemon test3 = new Pokemon(9, 50, "Impish", new int[]{8, 8, 32, 8, 32, 24});
        test3.setNick("Shellshocker");
        test3.setEVs(new int[]{64, 64, 248, 64, 248, 128});
        System.out.println(test3);
    }
}
