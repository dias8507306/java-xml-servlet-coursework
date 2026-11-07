import java.util.*;

public class Gift {

    private final List<Game> games = new ArrayList<>();

    public void addGame(Game g) {
        games.add(g);
    }

    public int getTotalWeight() {
        return games.stream().mapToInt(Game::getWeight).sum();
    }

    public void sortByAge() {
        games.sort(Comparator.comparingInt(Game::getAge));
    }

    public List<Game> findByAgeRange(int min, int max) {
        List<Game> result = new ArrayList<>();
        for (Game g : games) {
            if (g.getAge() >= min && g.getAge() <= max) {
                result.add(g);
            }
        }
        return result;
    }

    public void print() {
        games.forEach(System.out::println);
    }
}
