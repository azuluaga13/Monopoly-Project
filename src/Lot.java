
public class Lot extends Property
{
    private boolean hasHotel;   // If a hotel was built on this lot, overrides numHouses if true
    private int[] housesRent;   // Rent of the lot depending on the number of built houses
    private int hotelRent;      // Rent of the lot if it has a hotel
    private int housePrice;     // Current house price
    private int numHouses;      // Num of houses built on this lot


    public Lot(String name, int start, int cost, int housePrice,
               int rent0Houses, int rent1Houses, int rent2Houses,
               int rent3Houses, int rent4Houses, int rentHotel)
    // PRE:  name != null, and must be a string
    //       start <= 0, and must be an integer
    //       rent0Houses, rent1Houses, rent2Houses, rent3Houses, rent4Houses <= 0, and must an integer
    //       rentHotel <=0, and must be an integer
    // POST: Creates a Lot object with values initialized to the paramater values
    {
        super(name, start, cost);

        housesRent = new int[5];
        housesRent[0] = rent0Houses;
        housesRent[1] = rent1Houses;
        housesRent[2] = rent2Houses;
        housesRent[3] = rent3Houses;
        housesRent[4] = rent4Houses;
        hotelRent = rentHotel;

        hasHotel = false;
        numHouses = 0;

        this.housePrice = housePrice;
    }


    public void setHotel(boolean hasHotel)
    // PRE:  hasHotel must be a boolean value
    // POST: Adds or removes a hotel from this lot as needed
    {
        this.hasHotel = hasHotel;
    }


    public void setNumHouses(int numHouses)
    // PRE:  numHouses is in the range [0, 4]
    // POST: Lot will have the specified number of houses on it, when there is no hotel
    {
        this.numHouses = numHouses;
    }


    public void buyHouse(Player p)
    // PRE:  p != null, and must be initialized
    // POST: p buys a house and updates the house number it owns
    {
        if (!canBuyHouse(p)) return;

        p.subBalance(housePrice);

        if (numHouses >= 4)
        {
            hasHotel = true;
        }
        else
        {
            numHouses += 1;
        }
    }


    public void sellHouse(Player p)
    // PRE:  p != null, and must be initialized
    // POST: p sales one of the house it owns
    {
        if (!canSellHouse(p)) return;

        p.addBalance(housePrice);

        if (hasHotel)
        {
            hasHotel = false;
        }
        else
        {
            numHouses -= 1;
        }
    }


    @Override
    public int getRent()
    // POST: FCTVAL == This function does not change any state;
    {
        if (hasHotel)
            return hotelRent;
        else
            return housesRent[numHouses];
    }


    public boolean canBuyHouse(Player p)
    // POST: FCTVAL == This function does not change any state;
    {
        if (p != owningPlayer) return false;
        if (hasHotel) return false;
        if (p.getBalance() < housePrice) return false;
        return true;
    }


    public int getHousePrice()
    // POST: FCTVAL == Returns the house price
    {
        return housePrice;
    }


    public boolean canSellHouse(Player p)
    // POST: FCTVAL == This function does not change any state;
    {
        if (p != owningPlayer)
            return false;
        if (!hasHotel && numHouses == 0)
            return false;

        return true;
    }


    public int getNumHouses()
    // POST: FCTVAL == This function does not change any state;
    {
        if (hasHotel)
            return 0;

        return numHouses;
    }


    public boolean hasHotel()
    // POST: FCTVAL == This function does not change any state;
    {
        return hasHotel;
    }

    @Override
    public String toString()
    // POST: FCTVAL == Returns a string of the current status of the object
    {
        if (hasHotel)
            return super.toString() + " [Hotel]";
        else
            return super.toString() + " [Houses: "+numHouses+"]";
    }
}
