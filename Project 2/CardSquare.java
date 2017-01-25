public class CardSquare extends BoardLocation
{
    private int money;          // money that the player receives or owes


    public CardSquare(String name, int index)
    // PRE:  name, index, and money are initialized, money >= -200 and money <= 200
    // POST: an instance of CornerSquare has been created. It
    //       is located at index [index] and named name.
    {
        super(name, index);
    }


    public int getEarnings()
    // POST: FCTVAL == money the player receives if above 0 or owes if below 0
    {
        return ((int) Math.floor(Math.random()*401) - 200);
    }


    public String[] getPossibleActions(Player player)
    // PRE:  player must be initialized
    // POST: FCTVAL == array of options player has upon landing on this space, to be used in a
    //       menu in a user interface
    {
        String[] actions;

        this.money = getEarnings();
        actions = new String[1];

        if(this.money > 0)
        {
            actions[0] = "Player receives $" + this.money;
        }

        else if(this.money < 0)
        {
            actions[0] = "Player owes $" + this.money;
        }

        else if (this.money == 0)
        {
            actions[0] = "Player neither receives nor owes anything.";
        }

        return actions;
    }


    public String executeAction(Player player, int action)
    // PRE:  player != null, and must be initialized
    //       action >= 0 and must be an integer
    // POST: FCTVAL == string of action executed
    {
        if(this.money > 0 && action == 0)
        {
            player.addBalance(money);
        }

        else if(this.money < 0 && action == 0)
        {
            player.subBalance(money);
        }

        return "";
    }

    @Override
    public String toString()
    // POST: FCTVAL == String representation of the CardSquare object
    {
        return super.toString();
    }
}    