
public class Utility extends Property
{
    private static final int RENT_ONE = 4;   // Rent for owning one utility
    private static final int RENT_TWO = 10;  // Rent for owning two utilities
    private static final int COST = 150;     // Cost for a utility
    private int rent;                        // Current rent based on dice values


    public Utility(String name, int index)
    // PRE:  name != null, and must be a string, index >= 0 and must be an integer
    // POST: Returns an initialized Utility object with a name, index, owner set to null, and
    //       owned set to false
    {
        super(name, index, COST);
        rent = 0;
    }


    @Override
    public void claim(Player player)
    // PRE:  player != null, and must be initialized
    // POST: Updates owner to player, owned set to true, and calls addUtilities() for player
    {
        super.claim(player);
        player.addUtilities();
    }


    public int getRent()
    // PRE:  player != null, and must be initialized
    // POST: FCTVAL == player pays rent to the current owner of this property
    {
        int numOwned;
        int dice;

        dice = BoardLocation.getDice();
        numOwned = owningPlayer.getUtil();


        if (numOwned == 1) {
            rent = RENT_ONE*dice;
            return rent;
        }
        else if (numOwned == 2) {
            rent = RENT_TWO*dice;
            return rent;
        }

        return 0;
    }


    public int getRent(int dice)
    // PRE:  dice > 0, and must be an integer
    // POST: FCTVAL == Returns rent value in dollars
    {
        int numOwned;

        numOwned = owningPlayer.getUtil();


        if (numOwned == 1) {
            rent = RENT_TWO*dice;
            return rent;
        }
        else if (numOwned == 2) {
            rent = RENT_TWO*dice;
            return rent;
        }

        return 0;
    }
}
