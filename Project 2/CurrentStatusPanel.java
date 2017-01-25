import javax.swing.*;
import java.awt.*;

/**
 * Created by Memo on 10/21/15.
 */
public class CurrentStatusPanel extends JPanel
{
    private JTextArea infoTextArea;     // Text are to display board info
    private JScrollPane scrollPanel;    // Scroll bar for infoTextArea


    public CurrentStatusPanel()
    // POST: Creates a CurrentStatusPanel object initialized to default values
    {
        super();

        setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        this.setBackground(Color.YELLOW);

        infoTextArea = new JTextArea();
        scrollPanel = new JScrollPane(infoTextArea);
        add(scrollPanel, BorderLayout.CENTER);

        gameInstructions();
    }


    private void gameInstructions()
    // POST: Displays the game instructions
    {
        infoTextArea.append("Instructions:\n " +
                "- Click New Game to start a new game\n " +
                "- Click Roll Dice to advance player on the board\n " +
                "- Select a Player Action and click submit to execute action, the next player will be " +
                "up next automatically after executing a player action");
    }


    public void updatePanel(Board currBoard)
    // PRE:  currBoard != null, and must be initialized
    // POST: Updates the panel with the current player, previous location,
    //       location landed and dice rolled
    {
        Player player;
        int currPlayer;
        int currLocation;
        int prevLocation;

        infoTextArea.setText("");

        player = currBoard.getCurrentPlayer();
        currPlayer = currBoard.getCurrPlayerIndex();
        currLocation = currBoard.players[currPlayer].currentLocation();
        prevLocation = currBoard.players[currPlayer].getPrevLocation();

        infoTextArea.append("Current Player:      "+player.getPlayerToken()+"\n");
        infoTextArea.append("Previous Location:   "+currBoard.locations[prevLocation].getName()+"\n");
        infoTextArea.append("Location Landed:     "+currBoard.locations[currLocation].getName()+"\n");
        infoTextArea.append("Dice Rolled:        ["+currBoard.diceRoll[0]+"] ["+currBoard.diceRoll[1]+"]\n");

        if (currBoard.diceRoll[0] == currBoard.diceRoll[1])
            infoTextArea.append("Player gets an extra turn!\n");
    }

    public void playerPass(Board currBoard)
    // PRE:  currBoard != null, and must be initialized
    // POST: Displays the action taken by the player
    {
        int currPlayer;
        int currLocation;
        currPlayer = currBoard.getCurrPlayerIndex();
        currLocation = currBoard.players[currPlayer].currentLocation();

        infoTextArea.append(currBoard.players[currPlayer].getPlayerToken() + " does nothing...\n");
    }

    public void playerBuy(String msg)
    // PRE:  currBoard != null, and must be initialized
    // POST: Displays the action taken by the player
    {
        infoTextArea.append(msg + "\n");            // Prints out the name of the current player buying the property
    }

}
