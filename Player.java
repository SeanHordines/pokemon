import java.util.*;

public class Player extends Actor
{
    private static final String[] badgeNames =
        {"Badge 1", "Badge 2", "Badge 3", "Badge 4",
        "Badge 5", "Badge 6", "Badge 7", "Badge 8"};

    public static Player p;

    private int money = 0;
    private Map<Item, Integer> bag = new HashMap<Item, Integer>();
    private boolean[] badges = new boolean[8];

    public Player(String name)
    {
        super(name);
    }

    public void addMoney(int m)
    {
        money += m;
        if(money < 0)
        {
            money -= m;
            System.out.println("Not Enough Money");
        }
    }

    public void addItem(Item item)
    {
        addItem(item, 1);
    }

    public void addItem(Item item, int quantity)
    {
        if(bag.containsKey(item))
        {
            bag.put(item, bag.get(item) + quantity);
        }
        else
        {
            bag.put(item, quantity);
        }
    }

    public void removeItem(Item item)
    {
        removeItem(item, 1);
    }

    public void removeItem(Item item, int quantity)
    {
        if(bag.containsKey(item))
        {
            if(bag.get(item) <= quantity){bag.remove(item);}
            else{bag.put(item, bag.get(item) - quantity);}
        }
        else
        {
            System.out.println("Item not found");
        }
    }

    public void removeAllItem(Item item)
    {
        if(bag.containsKey(item))
        {
            bag.remove(item);
        }
        else
        {
            System.out.println("Item not found");
        }
    }

    public String listBag()
    {
        String out = "";

        Iterator<Map.Entry<Item, Integer>> bagIterator = bag.entrySet().iterator();
        int index = 0;
        while(bagIterator.hasNext())
        {
            index++;
            Map.Entry<Item, Integer> item = bagIterator.next();
            out += String.format("%d. %s (x%d)\n", index, item.getKey().name, item.getValue());
        }
        return out.substring(0, out.length()-1);
    }

    public Map<Item, Integer> getBag()
    {
        return bag;
    }

    public void awardBadge(int b)
    {
        badges[b] = true;
    }

    public String listBadges()
    {
        String out = "";
        for(int i = 0; i < 8; i++)
        {
            if(badges[i]){out += String.format("%d. %s\n", i+1, badgeNames[i]);}
        }
        return out.substring(0, out.length()-1);
    }
}
