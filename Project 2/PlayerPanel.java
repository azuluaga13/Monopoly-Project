import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel
{
    private JTextArea infoTextArea;         // TextField to display player info
    private JScrollPane scrollPanel;        // Scroll bar for infoTextArea


    public PlayerPanel(Color color)
    // PRE:  color != null, and must be initialized
    // POST: Creates a Color object initialized to default values
    {
        super();

        setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        this.setBackground(color);

        infoTextArea = new JTextArea();
        scrollPanel = new JScrollPane(infoTextArea);


        add(scrollPanel, BorderLayout.CENTER);
    }

    public void updatePanel(Player player)
    // PRE:  player != null, and must be initialized
    // POST: Updates the player panel with the current player object instance values
    {
        infoTextArea.setText("");
        infoTextArea.append(player.toString());
    }
}
