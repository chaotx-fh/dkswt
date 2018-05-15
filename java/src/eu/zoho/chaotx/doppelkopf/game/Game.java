package eu.zoho.chaotx.doppelkopf.game;

import java.io.FileInputStream;
import java.io.IOException;

import com.oracle.jrockit.jfr.InvalidValueException;

public class Game implements Runnable {
    private Board board;
    private Player nextplayer;
    private Player[] team1, team2;
    private List<Condition> conditions;

    public Game(Player[] participants, Board board) {
        // something like that
    }

    @Override
    public void run() {
        if(Player.length == 0)
            return;
    }
}