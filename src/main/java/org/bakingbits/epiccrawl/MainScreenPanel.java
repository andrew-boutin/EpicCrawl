package org.bakingbits.epiccrawl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by aboutin on 7/10/17.
 *
 * Sets up the main screen that loads when the game starts.
 * Shows images, info, and provides ways to start the game or open the level designer.
 */
public class MainScreenPanel extends JPanel{
    private final JButton startGameButton, openDesignerButton, infoButton;
    private final JPanel optionsPanel;

    public MainScreenPanel(JPanel mainPanel, CardLayout cardLayout) {
        optionsPanel = new JPanel();
        startGameButton = new JButton();
        openDesignerButton = new JButton();
        infoButton = new JButton();

        this.setLayout(new BorderLayout());
        Dimension dimension = new Dimension(EpicCrawl.screenWidth, EpicCrawl.screenHeight - 60);
        this.setPreferredSize(dimension);
        this.setBackground(Color.BLACK);

        JLabel studioLabel = new JLabel("Baking Bits Studios");
        studioLabel.setFont(new Font("Serif", Font.BOLD, 48));
        studioLabel.setHorizontalAlignment(JLabel.CENTER);
        studioLabel.setForeground(Color.RED);
        this.add(studioLabel, BorderLayout.NORTH);

        optionsPanel.setLayout(new FlowLayout());
        optionsPanel.setBackground(Color.BLACK);

        startGameButton.setText("Start Game");
        startGameButton.setBackground(Color.RED);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, PanelId.GAME_PLAY.toString());
            }
        });

        openDesignerButton.setText("Level Designer");
        openDesignerButton.setBackground(Color.RED);
        openDesignerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, PanelId.DESIGNER.toString());
            }
        });

        infoButton.setText("Info");
        infoButton.setBackground(Color.RED);
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, PanelId.INFO.toString());
            }
        });

        optionsPanel.add(startGameButton);
        optionsPanel.add(openDesignerButton);
        optionsPanel.add(infoButton);
        optionsPanel.setVisible(true);

        JPanel mainScreenImageAndOptionsPanel = new JPanel();
        mainScreenImageAndOptionsPanel.setBackground(Color.BLACK);
        mainScreenImageAndOptionsPanel.setLayout(new BorderLayout());
        mainScreenImageAndOptionsPanel.add(optionsPanel, BorderLayout.NORTH);
        //mainScreenImageAndOptionsPanel.add(mainScreenImageLabel, BorderLayout.CENTER);
        this.add(mainScreenImageAndOptionsPanel, BorderLayout.CENTER);
    }
}
