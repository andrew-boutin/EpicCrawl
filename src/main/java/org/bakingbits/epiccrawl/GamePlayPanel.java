package org.bakingbits.epiccrawl;

import javax.swing.*;
import java.awt.*;

/**
 * Created by aboutin on 7/10/17.
 */
public class GamePlayPanel extends JPanel {

    public GamePlayPanel(JButton mainScreenButton) {
        this.setLayout(new BorderLayout());
        Dimension dimension = new Dimension(EpicCrawl.screenWidth,EpicCrawl.screenHeight - 60);
        this.setPreferredSize(dimension);
        this.setBackground(Color.GRAY);

        this.add(mainScreenButton);
    }
}
