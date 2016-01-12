package models;

public class Player implements Comparable<Player> {
    private int id;
    private String name;
    private int score;
    private int gameId;

    public Player(){
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public int compareTo(Player another) {
        if (this.getScore() < another.getScore()){
            return -1;
        }else{
            return 1;
        }
    }
}
