package com.example.ibet.model.Bets;

public class Bet {

    String winner;
    String totalScore;
    String gameId;

    public Bet(String winner, String totalScore,String gameId) {
        this.winner = winner;
        this.totalScore = totalScore;
        this.gameId = gameId;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
