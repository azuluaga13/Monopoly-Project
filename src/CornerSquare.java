public class CornerSquare extends BoardLocation
{
    public CornerSquare(String name, int index)
    // PRE:  name and index are initialized
    // POST: an instance of CornerSquare has been created. It
    //       is located at index [index] and named name.
    {
        super(name, index);
    }


    public String[] getPossibleActions(Player player)
    // PRE:  player must be initialized
    // POST: FCTVAL == array of options player has upon landing on this space, to be used in a
    //       menu in a user interface
    {
        String[] actions;

        actions = new String[1];

        actions[0] = "On " + this.name + ": no actions necessary.";

        return actions;
    }


    public String executeAction(Player player, int action)
    // PRE:  player != null, and must be initialized
    //       action >= 0 and must be an integer
    // POST: FCTVAL == string of action executed
    {
        return "";
    }


    @Override
    public String toString()
    // POST: FCTVAL == String representation of the TaxSquare object
    {
        return super.toString();
    }
}