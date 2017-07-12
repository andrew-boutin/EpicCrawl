package org.bakingbits.epiccrawl;

/**
 * Created by aboutin on 7/10/17.
 *
 * Provide Ids for the various top level panels in the game.
 *
 * The layout management setup needs unique Strings to identify the different panels in order to switch between them.
 */
public enum PanelId {
    MAIN_SCREEN("MAIN_SCREEN"),
    DESIGNER("DESIGNER"),
    GAME_PLAY("GAME_PLAY"),
    INFO("INFO");

    private final String name;

    PanelId(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
