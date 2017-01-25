public abstract class Property extends BoardLocation
{
    private int cost;               // Price a player would need to pay to buy this property
    protected Player owningPlayer;  // Current owner of this property. If null, there is no owner.


    public Property(String name, int index, int cost)
    // PRE:  name != null, and must be a string
    //       index <= 0, and must be an integer
    //       cost < 0, must be an integer, and dollar amount
    // POST: Creates a Property object initialized with cost set to cost, and owningPlayer set to null.
    {
        super(name, index);
        this.cost = cost;
        owningPlayer = null;
    }


    public abstract int getRent();
    // POST: FCTVAL == Returns the rent amount in dollars


    public void claim(Player player)
    // PRE:  player != null, and must be initialized
    // POST: Updates owner to player, and owned to true
    {
        player.subBalance(cost);
        this.owningPlayer = player;
    }


    public void payRent(Player player)
    // PRE:  player != null, and must be initialized
    // POST: player pays rent to the current owner of this property
    {
        int rent = getRent();
        player.subBalance(rent);
        this.owningPlayer.addBalance(rent);
    }


    public int getCost()
    // POST: FCTVAL == This function does not change any state;
    {
        return cost;
    }


    @Override
    public String[] getPossibleActions(Player player)
    // PRE:  player != null, and must be initialized
    // POST: FCTVAL == returns a list of potential actions that can be taken by the player
    {
        String[] result;

        if (owningPlayer == null) //If no one owns owns this property....
        {
            int cost = getCost();

            if (player.getBalance() >= cost) // Maybe the player can buy it
            {
                result = new String[2];
                result[1] = "Buy " + getName() + " ($" + getCost() + ")";
            }
            else // Or not, player is too poor.
            {
                result = new String[1];
            }
            result[0] = "No action";
        }
        else if (player != owningPlayer)
        {
            // This property is part of the mighty castle of owningPlayer, pay the rent peasant.
            result = new String[1];
            result[0] = "Pay Rent ($" + getRent() + ")";
        }
        else
        {
            result = new String[1];
            result[0] = "No Action";
        }

        return result;
    }


    public String executeAction(Player player, int action)
    // PRE:  player != null, and must be initialized
    //       action >= 0, and must be an integer
    // POST: FCTVAL == Returns the string form of the player action performed
    {
        if (owningPlayer == null) //If no one owns owns this property....
        {
            int cost = getCost();

            if (player.getBalance() >= cost && action == 1) // Maybe the player can buy it
            {
                claim(player);
                return player.getPlayerToken() + " just bought " + getName() + "\n";
            }
        }
        else
        {
            // This property is part of the mighty castle of owningPlayer, pay the rent peasant.
            if (action == 0)
            {
                payRent(player);
            }
        }

        return "";
    }


    @Override
    public String toString()
    // POST: FCTVAL == String representation of the object
    {
        String object = super.toString() + " [Cost: $"+cost+"]";
        if (owningPlayer == null)
            object = object + " [No Owner]";
        else
            object = object + " [Owner: " + owningPlayer.getPlayerToken()+"]";

        return object;
    }
}
