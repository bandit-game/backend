package be.kdg.integration5.statisticscontext.domain;


public record Game(GameId gameId, String name) {
    public Game(GameId gameId, String name) {
        this.gameId = gameId;
        this.name = Game.normalizeName(name);
    }


    public static String normalizeName(String name) {
        return name.toLowerCase().replace(" ", "_");
    }
}
