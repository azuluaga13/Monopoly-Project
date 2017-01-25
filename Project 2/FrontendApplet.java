// Programmer:  Anthony Zuluaga, Guillermo Martinez, Joshua Kilmer, Rushit Takker
// Assignment:  Project 2
// Date:        October 22, 2015
// Description: Monopoly board game with back end and front end interface


import java.awt.GridLayout;
import java.awt.Container;
import javax.swing.JApplet;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FrontendApplet extends JApplet implements ActionListener
{
    private static final String[] NEW_OPTIONS =                 // Game mode options
                            { "Demo Mode", "Faster Game" };
    private static final String[] NUM_PLAYERS =                 // Number of players
                            { "1", "2", "3", "4" };

    private boolean gameInProgress;                             // Boolean game progress value
    private int numPlayers;                                     // Number of current players
    private int gameMode;                                       // Type of game mode
    private int[] dice;                                         // Dice values

    private String[] playerActions;                             // Player possible actions
    private Board gameBoard;                                    // Game board object

    private JButton newGameButton;                              // New game button
    private JButton submitAction;                               // Submit action button
    private JButton nextPlayer;                                 // Next player button
    private JButton rollDiceButton;                             // Roll dice button
    private JComboBox<String> actionComboBox;                   // Drop down player's action menu
    private JLabel actionLabel;                                 // Drop down action label

    private PlayerPanel playerPanels[];                         // GUI player panels
    private CurrentStatusPanel currentStatusPanel;              // GUI game status panel
    private BoardStatusPanel boardStatusPanel;                  // GUI board status panel


    public GameState gameState;                                 // Current game state
    private enum GameState                                      // Game states
    {
        TURN_START,
        TURN_BUYING_HOUSE,
        TURN_SELLING_HOUSE,
        TURN_LOCAL_ACTION
    }



    @Override
    public void init()
    {
        Container contentPane;          // GUI Content panel
        SpringLayout layout;            // GUI Layout
        JSplitPane bottomPane;          // GUI Bottom panel
        JPanel allPlayersPanel;         // GUI Players panel
        JPanel rightPanel;              // GUI Right panel

        contentPane = getContentPane();
        layout = new SpringLayout();

        setLayout(layout);

        // New Game button
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onNewGame(); }
        });

        add(newGameButton);
        layout.putConstraint(SpringLayout.WEST, newGameButton,
                5,
                SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, newGameButton,
                5,
                SpringLayout.NORTH, contentPane);

        // Roll dice button
        rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { onRollDice(); }
        });
        add(rollDiceButton);
        layout.putConstraint(SpringLayout.WEST, rollDiceButton,
                5,
                SpringLayout.EAST, newGameButton);
        layout.putConstraint(SpringLayout.NORTH, rollDiceButton,
                5,
                SpringLayout.NORTH, contentPane);


        // Action Label
        actionLabel = new JLabel("Player Actions");
        add(actionLabel);
        layout.putConstraint(SpringLayout.WEST, actionLabel,
                10,
                SpringLayout.EAST, rollDiceButton);
        layout.putConstraint(SpringLayout.NORTH, actionLabel,
                5,
                SpringLayout.NORTH, contentPane);


        // Action combo box
        actionComboBox = new JComboBox<String>();
        add(actionComboBox);
        layout.putConstraint(SpringLayout.WEST, actionComboBox,
                5,
                SpringLayout.EAST, actionLabel);
        layout.putConstraint(SpringLayout.NORTH, actionComboBox,
                5,
                SpringLayout.NORTH, contentPane);


        // Submit action button
        submitAction = new JButton("Submit Action!");
        submitAction.addActionListener(this);
        submitAction.setEnabled(false);
        add(submitAction);
        layout.putConstraint(SpringLayout.WEST, submitAction,
                5,
                SpringLayout.EAST, actionComboBox);
        layout.putConstraint(SpringLayout.NORTH, submitAction,
                5,
                SpringLayout.NORTH, contentPane);


        // GUI Panel
        bottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bottomPane.setResizeWeight(0.25);
        bottomPane.setEnabled(false);
        bottomPane.setDividerSize(5);
        add(bottomPane);
        layout.putConstraint(SpringLayout.NORTH, bottomPane,
                5,
                SpringLayout.SOUTH, newGameButton);
        layout.putConstraint(SpringLayout.WEST, bottomPane,
                5,
                SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.EAST, bottomPane,
                -5,
                SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, bottomPane,
                -5,
                SpringLayout.SOUTH, contentPane);
        {
            allPlayersPanel = new JPanel();
            allPlayersPanel.setLayout(new GridLayout(0, 1, 5, 5));
            bottomPane.add(allPlayersPanel);
            {
                playerPanels = new PlayerPanel[4];
                for (int i = 0; i < 4; i++)
                {
                    playerPanels[i] = new PlayerPanel(Color.YELLOW);
                    allPlayersPanel.add(playerPanels[i]);
                }
            }

            rightPanel = new JPanel();
            rightPanel.setLayout(new GridLayout(2, 1, 5, 5));
            bottomPane.add(rightPanel);
            {
                currentStatusPanel = new CurrentStatusPanel();
                rightPanel.add(currentStatusPanel);

                boardStatusPanel = new BoardStatusPanel();
                rightPanel.add(boardStatusPanel);
            }
        }
    }


    public void actionPerformed(ActionEvent e)
    {
        String message;     // Player action executed
        int index;          // Index of action selected

        if (e.getSource() == newGameButton)
        {
            onNewGame();
        }

        if (e.getSource() == rollDiceButton)
        {
            onRollDice();
        }

        if (e.getSource() == submitAction)
        {
            index = actionComboBox.getSelectedIndex();

            if (gameState == GameState.TURN_START)
            {
                if (index == 0)
                {
                    currentStatusPanel.playerPass(gameBoard);
                    return;
                }

                if (index == 1)
                {
                    prepareBuyHouses();

                }
                if (index == 2)
                {
                    prepareSellHouses();
                }
            }
            else if (gameState == GameState.TURN_BUYING_HOUSE)
            {
                if (index == 0) {
                    currentStatusPanel.playerPass(gameBoard);
                    return;
                }
                if (index <= 1)
                {
                    startTurn();
                }
                else
                {
                    buyHouse(index-2);
                }
            }
            else if (gameState == GameState.TURN_SELLING_HOUSE)
            {
                if (index == 0) {
                    currentStatusPanel.playerPass(gameBoard);
                    return;
                }
                if (index <= 1)
                {
                    startTurn();
                }
                else
                {
                    sellHouse(index-2);
                }
            }
            else if (gameState == GameState.TURN_LOCAL_ACTION)
            {
                message = gameBoard.doAction(index);
                currentStatusPanel.playerBuy(message);
                gameBoard.updatePlayer();
                startTurn();
            }
        }

    }


    public void updatePanels()
    // POST: Updates the GUI panels (players, game status, and board status)
    {
        if (!gameBoard.checkActivePlayers())
        {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null,
                    "Game Over!", "",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            rollDiceButton.setEnabled(false);
        }
        else
        {
            boardStatusPanel.updatePanel(gameBoard);
            for (int i = 0; i < numPlayers; i++)
            {
                playerPanels[i].updatePanel(gameBoard.players[i]);
            }
        }
    }


    public void onRollDice()
    // POST: Rolls dice, move player on board, and populates the drop down menu
    //       of possible player actions
    {
        dice = gameBoard.rollDice();

        gameBoard.movePlayer();
        playerActions = gameBoard.getActions();

        actionComboBox.removeAllItems();
        for (int i = 0; i < playerActions.length; i++)
        {
            actionComboBox.addItem(playerActions[i]);
        }
        currentStatusPanel.updatePanel(gameBoard);

        rollDiceButton.setEnabled(false);
        submitAction.setEnabled(true);
        gameState = GameState.TURN_LOCAL_ACTION;
    }


    public void onNewGame()
    // POST: Creates a new game and resets the GUI panels to default
    {
        String numPlayersS;     // Stores pop-up prompt for number of players
        int select;             // Option to restart game
        int gameMode;           // Game mode chosen


        if(gameInProgress)
        {

            select = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to restart the game?",
                    "Restart Game",
                    JOptionPane.YES_NO_OPTION);

            if (select != JOptionPane.YES_OPTION) return;
        }


        gameMode = JOptionPane.showOptionDialog(null, "Please select a new game mode",
                "New Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                NEW_OPTIONS,
                NEW_OPTIONS[1]);

        if(gameMode != JOptionPane.YES_OPTION)
        {
            numPlayers = 3;
            gameBoard = new Board(3, true);
        }
        else
        {

            numPlayersS = (String) JOptionPane.showInputDialog(null, "Number of players",
                    "Select Number of Players",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    NUM_PLAYERS,
                    "---");
            if (numPlayersS == null) return;

            numPlayers = Integer.parseInt(numPlayersS); // number used to select player

            gameBoard = new Board(numPlayers, false);
        }


        startTurn();
        gameInProgress = true;
        updatePanels();
    }


    private void startTurn()
    // POST: Starts a turn for the current player
    {
        gameState = GameState.TURN_START;
        actionComboBox.removeAllItems();

        actionComboBox.addItem( "Roll dice to move or choose an action" );
        actionComboBox.addItem( "Buy Houses" );
        actionComboBox.addItem( "Sell Houses" );

        rollDiceButton.setEnabled(true);

        submitAction.setEnabled(true);
        updatePanels();
    }


    private void prepareBuyHouses()
    // POST: Sets the dropdown menu to buy houses if possible
    {
        Player player;              // Current player
        BoardLocation location;     // Locations
        Lot lot;                    // Board locations lot

        gameState = GameState.TURN_BUYING_HOUSE;
        player = gameBoard.getCurrentPlayer();

        actionComboBox.removeAllItems();
        actionComboBox.addItem( "Select lot to buy a house for" );
        actionComboBox.addItem( "Cancel" );

        for (int i = 0; i < gameBoard.locations.length; i++)
        {
            location = gameBoard.locations[i];
            if (location instanceof Lot)
            {
                lot = (Lot) location;
                if (lot.canBuyHouse(player))
                {
                    actionComboBox.addItem( lot.getName() + " for $" + lot.getHousePrice() );
                }
            }
        }
    }


    private void buyHouse(int index)
    // PRE:  index >= 0, and must be an integer
    // POST: A player buys one of the houses from the current location on the board
    {
        Player player;              // Current player
        BoardLocation location;     // Locations
        Lot lot;                    // Board locations lot

        player = gameBoard.getCurrentPlayer();

        for (int i = 0; i < gameBoard.locations.length; i++)
        {
            location = gameBoard.locations[i];
            if (location instanceof Lot)
            {
                lot = (Lot) location;
                if (lot.canBuyHouse(player))
                {
                    if (index == 0)
                    {
                        lot.buyHouse(player);
                        break;
                    }
                    index -= 1;
                }
            }
        }

        startTurn();
    }


    private void prepareSellHouses()
    // POST: Sets the dropdown menu to sale houses if possible
    {
        Player player;              // Current player
        BoardLocation location;     // Locations
        Lot lot;                    // Board locations lot

        gameState = GameState.TURN_SELLING_HOUSE;
        player = gameBoard.getCurrentPlayer();

        actionComboBox.removeAllItems();
        actionComboBox.addItem( "Select lot to sell a house from" );
        actionComboBox.addItem( "Cancel" );

        for (int i = 0; i < gameBoard.locations.length; i++)
        {
            location = gameBoard.locations[i];
            if (location instanceof Lot)
            {
                lot = (Lot) location;
                if (lot.canSellHouse(player))
                {
                    actionComboBox.addItem( lot.getName() + " for $" + lot.getHousePrice() );
                }
            }
        }
    }


    private void sellHouse(int index)
    // PRE:  index >= 0, and must be an integer
    // POST: A player sales one of the houses owned from the current location on the board
    {
        Player player;              // Current player
        BoardLocation location;     // Locations
        Lot lot;                    // Board locations lot


        player = gameBoard.getCurrentPlayer();

        for (int i = 0; i < gameBoard.locations.length; i++)
        {
            location = gameBoard.locations[i];
            if (location instanceof Lot)
            {
                lot = (Lot) location;
                if (lot.canSellHouse(player))
                {
                    if (index == 0)
                    {
                        lot.sellHouse(player);
                        break;
                    }
                    index -= 1;
                }
            }
        }

        startTurn();
    }
}