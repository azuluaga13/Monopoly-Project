
public class Player
{
    private static final int AMOUNT = 1500; // Default money amount for a player
    private static boolean active = true;   // Active status

    private int balance;                    // Player's balance
    private int prevLocation;               // Player's previous location
    private int currLocation;               // Player's current location
    private int railroads;                  // Number of Railroads owned
    private int utilities;                  // Number of Utilities owned
    private int lot;                        // Number of Lots owned
    private int houses;                     // Number of houses owned
    private int hotel;                      // Number of hotels owned

    private String playerToken;             // Player token string


    public Player(String token)
    // PRE:  token != null, and must be a string
    // POST: Creates a Player object initialized with balance set to AMOUNT, currLocation set to 0, railroads
    //       set to 0, utilities set to 0, active to true, and playerToken to token
    {
        this.balance = AMOUNT;
        this.currLocation = 0;
        this.prevLocation = 0;
        this.railroads = 0;
        this.utilities = 0;
        this.lot = 0;
        this.houses = 0;
        this.hotel = 0;
        this.playerToken = token;
    }


    public void addUtilities()
    // POST: Adds one to the number of utilities
    {
        utilities ++;

        if (utilities > 2)
            utilities = 2;
    }


    public void addRailroads()
    // POST: Adds one to the number of railroads
    {
        railroads++;

        if (railroads > 4)
            railroads = 4;
    }


    public void setHouses(int numHouses)
    // PRE:  numHouses >= 0 and must be an integer
    // POST: updates houses to numHouses
    {
        houses = numHouses;
    }


    public void setHotel()
    // POST: Updates the number of hotels, and sets houses to 0
    {
        hotel++;
        houses = 0;
    }

    public void removeHotel()
    // POST: Removes hotes owned
    {
        hotel = 0;
    }

    public void movePlayer(int index)
    // PRE:  index <= 0, and must be an integer
    // POST: Sets currLocation to index
    {
        prevLocation = currLocation;

        for(int i = 0; i < index; i++)
        {
            currLocation++;

            if (currLocation == 40)
            {
                currLocation = 0;
                this.addBalance(200);
            }
        }
    }

    public void setLocation(int index)
    // PRE:  index <= 0, and must be an integer
    // POST: Sets currLocation to index
    {
        currLocation = index;
    }

    public void printBalance()
    // POST: Prints the players current balance
    {
        System.out.println("Your balance is $" + balance);

    }

    public void addBalance(int value)
    // PRE:  value <= 0, and must be an integer, denoting dollars
    // POST:
    {
        balance+=value;
        printBalance();

    }

    public int getBalance()
    // POST: FCTVAL == Returns the balance of the player
    {
        return balance;
    }


    public boolean subBalance(int value)
    // PRE:  value <= 0, and must be an integer, denoting dollars
    // POST: FCTVAL == Returns false if value goes below zero, true if value is above zero
    {
        if(balance-value<0)
        {
            active=false;
            return false;
        }

        balance=balance-value;

        printBalance();
        return true;
    }

    public int getRails()
    // POST: FCTVAL == Returns the number of railroads
    {

        return railroads;
    }


    public int getUtil()
    // POST: FCTVAL == Returns the number of utilities
    {

        return utilities;
    }

    public String getPlayerToken()
    // POST: FCTVAL == Returns the player's playerToken
    {
        return playerToken;
    }


    public int getPrevLocation()
    // POST: FCTVAL ==  Reeturns player's previous location index
    {
        return prevLocation;
    }


    public int currentLocation()
    // POST: FCTVAL == Returns player's current location index
    {
        return currLocation;
    }


    public static boolean getStatus()
    // POST: FCTVAL == Returns the active status of the player
    {
        return active;
    }

    @Override
    public String toString()
    // POST: FCTVAL == Returns a string of the current status of the object
    {
        return "[Token: "+playerToken+"]\n\n [Balance: $"+balance+"]\n [Railroads: "+railroads+"]\n [Utilities: "+
                utilities+"]\n [Houses: "+houses+"]\n [Hotel: "+hotel+ "]";

    }
}