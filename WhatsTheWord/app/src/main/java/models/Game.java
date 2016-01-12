package models;

import java.util.ArrayList;
import java.util.Date;

public class Game {
    private int id;
    private Integer categoryId;
    private ArrayList<Player> players;
    private Date playedOn;
    private Player winner;
    private String location;

    public Game(){
        this.players = new ArrayList<Player>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer( Player player) {
        this.players.add(player);
    }

    public Date getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer category) {
        this.categoryId = category;
    }
}
