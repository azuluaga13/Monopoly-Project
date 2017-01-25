
public class Railroad extends Property
{
    private static final int COST = 200;           // Cost of the property
    private static final int RENT_ONE = 25;        // Rent amount for one property owned
    private static final int RENT_TWO = 50;        // Rent amount for one property owned
    private static final int RENT_THREE = 100;     // Rent amount for one property owned
    private static final int RENT_FOUR = 200;      // Rent amount for one property owned


    public Railroad(String name, int index)
    // PRE:  name != null, and must be a string, index >= 0 and must be an integer
    // POST: Returns an initialized Railroad object with a name, index, owner set to null, and
    //       owned set to false
    {
        super(name, index, COST);
    }


    public void claim(Player player)
    // PRE:  player != null, and must be initialized
    // POST: Updates owner to player, owned set to true, and calls addRailroads() for player
    {
        super.claim(player);
        player.addRailroads();
    }


    public int getRent()
    // PRE:  player != null, and must be initialized
    // POST: FCTVAL == player pays rent to the current owner of this property
    {
        int numOwned;

        numOwned = owningPlayer.getRails();

        if (numOwned == 1)
            return RENT_ONE;
        else if (numOwned == 2)
            return RENT_TWO;
        else if (numOwned == 3)
            return RENT_THREE;
        else if (numOwned == 4)
            return RENT_FOUR;

        return 0;
    }
}

