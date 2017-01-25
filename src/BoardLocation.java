public abstract class BoardLocation
{
    public int position;       // Index location on the board
    public String name;        // Name of location
    public static int dice;    // Dice value


    public BoardLocation()
    // POST: Creates a BoardLocation object with default values for position and name
    {
        position = 0;
        name = "Default";
    }


    public BoardLocation(String place, int index)
    // PRE:  place != null, index <= 0 and must be an integer
    // POST: Creates a BoardLocation object with name set to place, and position set to index
    {
        this.name = place;
        position = index;

    }


    public abstract String[] getPossibleActions(Player player);
    // PRE:  player must be initialized
    // POST: FCTVAL == array of options player has upon landing on this space, to be used in a
    //       menu in a user interface


    public abstract String executeAction(Player player, int action);
    // PRE:  player != null, and must be initialized
    //       action >= 0 and must be an integer
    // POST: FCTVAL == string of action executed


    public int getLocation()
    // POST: FCTVAL == Returns the position of the object
    {
        return position;
    }


    public String getName()
    // POST: FCTVAL == Returns the name of the object
    {
        return name;
    }


    public static int getDice()
    // POST: FCTVAL ==  returns the dice integer value
    {
        return dice;
    }


    public static void setDice(int d)
    // PRE:  d > 0 and must be an integer
    // POST: sets the dice value to d
    {
        dice = d;
    }


    @Override
    public String toString()
    // POST: FCTVAL == Returns a string of the current status of the object
    {
        return "*["+name+"]" + "[Index:" + position + "]";
    }
}
