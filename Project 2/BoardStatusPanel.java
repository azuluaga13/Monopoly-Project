import javax.swing.*;
import java.awt.*;

public class BoardStatusPanel extends JPanel
{
    private JTextArea statusArea;       // Text area to display board status
    private JScrollPane scrollPanel;    // Scroll bar for statusArea
    private String status;              // String board status

    public BoardStatusPanel()
    // POST: Creates a BoardStatusPanel object initialized to default values
    {
        super();

        setLayout(new GridLayout(1, 1));
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        status = "";

        statusArea = new JTextArea();
        statusArea.setBackground(Color.white);
        statusArea.setFont(new Font("Courier", Font.PLAIN, 14));

        scrollPanel = new JScrollPane(statusArea);

        add(scrollPanel);
    }

    public void updatePanel(Board currBoard)
    // PRE:  currBoard != null, and must be initialized
    // POST: Displays the board status on statusArea
    {
        statusArea.setText("");
        statusArea.append("                     B O A R D   S T A T U S\n");
        for (int i = 0; i < currBoard.locations.length; i++){
            statusArea.append(currBoard.locations[i].toString()+"\n");
        }
    }
}
