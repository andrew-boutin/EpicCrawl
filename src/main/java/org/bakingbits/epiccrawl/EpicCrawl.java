package org.bakingbits.epiccrawl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by aboutin on 7/10/17.
 *
 * Main class to start the game.
 */
public final class EpicCrawl {
    private final String gameTitle = "Epic Crawl";

    public static final int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    private final int minScreenWidth = 800, minScreenHeight = 500;

    private EpicCrawl(){
        // Set up the frame that the application runs in
        JFrame mainFrame = new JFrame(gameTitle);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close on exit
        mainFrame.setSize(screenWidth - 200, screenHeight - 200); // This makes the restore down button not make the frame very tiny // TODO: Check
        mainFrame.setMinimumSize(new Dimension(minScreenWidth, minScreenHeight));
        mainFrame.setLocationRelativeTo(null); // TODO: Check
        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH); // TODO: Check
        mainFrame.pack(); // TODO: Check

        // This layout controls what panels are visible
        CardLayout cardLayout = new CardLayout();

        // This panel is used to control what panel to actually display
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(cardLayout);

        // Create the different panels for the game
        JPanel mainScreenPanel = new MainScreenPanel(mainPanel, cardLayout);
        JPanel designerPanel = new DesignerPanel(createMainMenuButton(mainPanel, cardLayout));
        JPanel infoPanel = new InfoPanel(createMainMenuButton(mainPanel, cardLayout));
        JPanel gamePlayPanel = new GamePlayPanel(createMainMenuButton(mainPanel, cardLayout));

        // Add the different panels for the game with unique Ids
        mainPanel.add(mainScreenPanel, PanelId.MAIN_SCREEN.toString());
        mainPanel.add(designerPanel, PanelId.DESIGNER.toString());
        mainPanel.add(infoPanel, PanelId.INFO.toString());
        mainPanel.add(gamePlayPanel, PanelId.GAME_PLAY.toString());

        // Show the main screen at the start of the game
        cardLayout.show(mainPanel, PanelId.MAIN_SCREEN.toString());

        // Finish setting up the frame and display everything
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private JButton createMainMenuButton(JPanel mainPanel, CardLayout cardLayout) {
        JButton mainScreenButton  = new JButton("Main Screen");
        mainScreenButton.setBackground(Color.RED);

        mainScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, PanelId.MAIN_SCREEN.toString());
            }
        });

        return mainScreenButton;
    }

    public static void main(String[] args) throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");

        DatabaseConnector.getInstance();

        new EpicCrawl();
    }
}
