public class TaxSquare extends BoardLocation
{
    private int tax;          // amount of tax for the square in dollars


    public TaxSquare(String name, int index, int tax)
    // PRE:  name, index, and tax are initialized, tax >= 0 & tax <= 200
    // POST: an instance of TaxSquare has been created. It
    //       is located at index [index] and named name with a tax of tax in dollars.
    {
        super(name, index);
        this.tax = tax;

    }


    public int getTax()
    // POST: FCTVAL == tax of this TaxSquare in dollars
    {
        return tax;
    }


    public String[] getPossibleActions(Player player)
    // PRE:  player must be initialized
    // POST: FCTVAL == array of options player has upon landing on this space, to be used in a
    //       menu in a user interface
    {
        String[] actions;

        actions = new String[1];

        actions[0] = "Player owes $" + this.tax;

        return actions;
    }


    public String executeAction(Player player, int action)
    // PRE:  player != null, and must be initialized
    //       action >= 0, and must be an integer
    // POST: FCTVAL == Returns the string form of the player action performed
    {
        if (action == 0)
            player.subBalance(tax);

        return "";
    }


    @Override
    public String toString()
    // POST: FCTVAL == String representation of the TaxSquare object
    {
        return super.toString() + " [Tax: $" + tax+"]";
    }
}