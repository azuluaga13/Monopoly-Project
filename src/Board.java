import java.util.Scanner;

public class Board
{
    private static final String[] TOKEN =
            {"Battleship", "Automobile", "Shoe", "Cat"};    // Player tokens
    private static final int BOARD_SIZE = 40;               // Size of the board

    private int currPlayer;                                 // Current player
    private int currLoc;                                    // Current player's location
    private String[] options;                               // Player options
    private boolean extraTurn;                              // Extra turn

    public Player[] players;                                // Players array
    public BoardLocation[] locations;                       // Locations array
    public int[] diceRoll;                                  // Game dice

    public Board(int numPlayers, boolean fastGame)
    // PRE:  numPlayers > 1, and must be an integer
    //       fastGame != null, and must be a boolean
    // POST: Creates a Board object with initilazing the dice, players, locations, and initializes
    //       each one of the board locations
    {
        diceRoll = new int[2];
        diceRoll[0] = -1;
        diceRoll[1] = -2;

        players = new Player[numPlayers];
        for(int i = 0; i < numPlayers; i++)
        {
            players[i] = new Player(TOKEN[i]);
        }


        extraTurn = false;
        currPlayer = randNumGen(0, numPlayers);
        locations = new BoardLocation[BOARD_SIZE];
        options = null;

        initBoardlocations();

        if (fastGame)
            fasterGame();
    }

    private void initBoardlocations()
    // POST: Initializes board locations for locations array
    {
        initRailroads();
        initUtilities();
        initCardSquares();
        initTaxSquares();
        initCornerSquares();
        initLots();
    }


    private void fasterGame()
    // POST: Sets the Board object to 3 players with owned properties and money given
    {
        // Player 1
        players[0].addBalance(5000);
        players[0].setLocation(10);
        // Properties 11-16 should be free, so we can rig player 1 to buy a property.
        ((Railroad) locations[5]).claim(players[0]); // Railroad
        ((Utility) locations[12]).claim(players[0]);
        //((Property) locations[25].claim(players[0]); // Railroad
        ((Property) locations[1]).claim(players[0]);  // Brown Lot
        ((Property) locations[3]).claim(players[0]);  // Bownw Lot
        ((Lot)      locations[1]).setHotel(true);        // Set Hotel Brown 1 lot
        ((Lot)      locations[3]).setHotel(true);        // Set Hotel Brown 3 lot
        System.out.println("Done w Player 1");

        // Player 2
        players[1].addBalance(12300);

        //Rig player's 2 first turn so he pays rent
        players[1].setLocation(22);
        for (int i = 23; i <= 28; i++) ((Property) locations[i]).claim(players[0]);

        System.out.println("Done w Player 2");

        // Player 3
        players[2].addBalance(5000);
        players[2].setLocation(32);
        ((Property) locations[18]).claim(players[2]);
        ((Property) locations[3]).claim(players[2]);
        ((Property) locations[37]).claim(players[2]);
        ((Property) locations[39]).claim(players[2]);
        ((Lot)      locations[37]).setNumHouses(3);
        System.out.println("Done w Player 3");

        currPlayer = 1;
    }

    private void initRailroads()
    // POST: Initializes Railroad objects for locations array
    {
        locations[5] = new Railroad("Reading Railroad", 5);
        locations[15] = new Railroad("Pennsylvania", 15);
        locations[25] = new Railroad("B. & O. Railroad", 25);
        locations[35] = new Railroad("Short Line", 35);
    }


    private void initUtilities()
    // POST: Initializes Utility objects for locations array
    {
        locations[12] = new Utility("Electric Company", 12);
        locations[28] = new Utility("Water Works", 28);
    }

    private void initCardSquares()
    // POST: Initializes 5 CardSquares objects
    {
        // Community Chest
        locations[2] = new CardSquare("Community Chest", 2);
        locations[17] = new CardSquare("Community Chest", 17);
        locations[33] = new CardSquare("Community Chest", 33);

        // Chance
        locations[7] = new CardSquare("Chance", 7);
        locations[22] = new CardSquare("Chance", 22);
        locations[36] = new CardSquare("Chance", 36);

    }

    private void initTaxSquares()
    // POST: Initializes 2 TaxSquares objects
    {
        // IncomeTax
        locations[4] = new TaxSquare("Income Tax", 4, 200);

        // LuxuryTax
        locations[38] = new TaxSquare("Luxury Tax", 38, 75);

    }

    private void initCornerSquares()
    // POST: Initializes 4 CornerSquares objects
    {
        // CornerSquares 
        locations[0] = new CornerSquare("Go", 0);
        locations[10] = new CornerSquare("Jail", 10);
        locations[20] = new CornerSquare("Free Parking", 20);
        locations[30] = new CornerSquare("Go To Jail", 30);

    }


    private void initLots()
    // POST: Initializes Lot objects for the locations array
    {
        //                         NAME                 IDX  BUY HOUSE   RENT (Num of Houses)    HOTEL
        //                                                       PRICE  0   1    2     3     4
        // Brown Lot
        locations[ 1] = new Lot("Mediterranean Avenue",   1,  60,  50,  2,  10,  30,   90,  160,  250);
        locations[ 3] = new Lot("Baltic Avenue",          3,  60,  50,  4,  20,  60,  180,  320,  450);

        // Light Blue Lot
        locations[ 6] = new Lot("Oriental Avenue",        6, 100,  50,  6,  30,  90,  270,  400,  550);
        locations[ 8] = new Lot("Vermont Avenue",         8, 100,  50,  6,  30,  90,  270,  400,  550);
        locations[ 9] = new Lot("Connecticut Avenue",     9, 120,  50,  8,  40, 100,  300,  450,  600);

        // Pink Lot
        locations[11] = new Lot("St. Charles Place",     11, 140, 100, 10,  50, 150,  450,  625,  750);
        locations[13] = new Lot("States Avenue",         13, 140, 100, 10,  50, 150,  450,  625,  750);
        locations[14] = new Lot("Virginia Avenue",       14, 160, 100, 12,  60, 180,  270,  700,  900);

        // Orange Lot
        locations[16] = new Lot("St. James Place",       16, 180, 100, 14,  70, 200,  550,  750,  950);
        locations[18] = new Lot("Tennessee Avenue",      18, 180, 100, 14,  70, 200,  550,  750,  950);
        locations[19] = new Lot("New York Avenue",       19, 200, 100, 16,  80, 220,  600,  800, 1000);

        // Red Lot
        locations[21] = new Lot("Kentucky Avenue",       21, 220, 150, 18,  90, 250,  700,  875, 1050);
        locations[23] = new Lot("Indiana Avenue",        23, 220, 150, 18,  90, 250,  700,  875, 1050);
        locations[24] = new Lot("Illinois Avenue",       24, 240, 150, 20, 100, 300,  750,  925, 1100);

        // Yellow Lot
        locations[26] = new Lot("Atlantic Avenue",       26, 260, 150, 22, 110, 330,  800,  975, 1150);
        locations[27] = new Lot("Ventnor Avenue",        27, 260, 150, 22, 110, 330,  800,  975, 1150);
        locations[29] = new Lot("Marvin Gardens",        29, 280, 150, 24, 120, 360,  850, 1025, 1200);

        // Green Lot
        locations[31] = new Lot("Pacific Avenue",        31, 300, 200, 26, 130, 390,  900, 1100, 1275);
        locations[32] = new Lot("North Carolina Avenue", 32, 300, 200, 26, 130, 390,  900, 1100, 1275);
        locations[34] = new Lot("Pennsylvania Avenue",   34, 320, 200, 28, 150, 450, 1000, 1200, 1400);

        // Blue Lot
        locations[37] = new Lot("Park Place",            37, 350, 200, 35, 175, 500, 1100, 1300, 1500);
        locations[39] = new Lot("Boardwalk",             39, 400, 200, 50, 200, 600, 1400, 1700, 2000);

    }


    public void printBoardInfo()
    // POST: Print out the board locations information
    {
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            System.out.println(locations[i].toString());
            System.out.println();
        }
    }


    public void movePlayer()
    // POST: Updates the players locations
    {
        players[currPlayer].movePlayer(diceRoll[0] + diceRoll[1]);
        currLoc = players[currPlayer].currentLocation();
    }


    public void updatePlayer()
    // POST: Switches to the next player turn
    {
        if (extraTurn)
        {
            extraTurn = false;
        }
        else
        {
            currPlayer++;

            if (currPlayer == players.length)
                currPlayer = 0;
        }
    }


    public int[] rollDice()
    // POST: FCTVAL == returns two random numbers for the dice values
    {
        int firstDie;               // First die value
        int secDie;                 // Second die value

        firstDie = randNumGen(1,6);
        secDie = randNumGen(1,6);

        diceRoll[0] = firstDie;
        diceRoll[1] = secDie;

        if (diceRoll[0] == diceRoll[1])
            extraTurn = true;

        BoardLocation.setDice(diceRoll[0]+diceRoll[1]);

        return diceRoll;
    }


    public String[] getActions()
    // POST: FCTVAL == returns a string array with the possible player options
    {
        currLoc = players[currPlayer].currentLocation();

        return locations[currLoc].getPossibleActions(players[currPlayer]);
    }


    public String doAction(int action)
    // PRE:  action >=0 and must be an integer
    // POST: FCTVAL == string of action executed
    {
        int location;

        location = players[currPlayer].currentLocation();
        return locations[location].executeAction(players[currPlayer], action);
    }


    private int randNumGen(int min, int max)
    // PRE:  min must be an integer
    //       max must be an integer
    // POST: FCTVAL == returns a random integer between min or max
    {
        return ((int)Math.floor(Math.random()*(max)) + min);
    }


    public boolean checkActivePlayers()
    // POST: FCTVAL == the players status as a boolean
    {
        return Player.getStatus();
    }


    public Player getCurrentPlayer()
    // POST: FCTVAL == current player playing on board
    {
        return players[currPlayer];
    }

    public int getCurrPlayerIndex()
    // POST: FCTVAL == returns the current plater location index
    {
        return currPlayer;
    }

    public String toString()
    // POST: FCTVAL == String representation of the object
    {
        return "The current player is" + currPlayer +"\n Current dice role is:" + diceRoll+ "\n";
    }
}