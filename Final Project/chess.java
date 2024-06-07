import javax.swing.*;
import java.awt.*;

public class chess {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private chessGameGUI gameGUI;
    private chessMenuGUI menu;

    public chess() {
        frame = new JFrame("Chess");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        menu = new chessMenuGUI();
        chessGameLogic logic = new chessGameLogic(menu);
        gameGUI = new chessGameGUI(logic);

        mainPanel.add(menu, "Menu");
        mainPanel.add(gameGUI, "Game");

        frame.setSize(755, 847);
        frame.setLocation(20, 20);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel); // Set mainPanel as the content pane
        frame.setVisible(true);
    }

    public void togglePanels() {
        cardLayout.next(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new chess());
    }
}




