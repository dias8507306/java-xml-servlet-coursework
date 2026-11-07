public abstract class Game {

    protected String name;
    protected int weight;
    protected int age;
    protected int duration;

    public Game(String name, int weight, int age, int duration) {
        this.name = name;
        this.weight = weight;
        this.age = age;
        this.duration = duration;
    }

    public int getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (weight=" + weight
                + ", age=" + age
                + ", duration=" + duration + ")";
    }

    // ---------------------------
    // Вложенные классы-наследники
    // ---------------------------
    public static class Puzzle extends Game {
        public Puzzle(String name, int weight, int age, int duration) {
            super(name, weight, age, duration);
        }
    }

    public static class Board extends Game {
        public Board(String name, int weight, int age, int duration) {
            super(name, weight, age, duration);
        }
    }

    public static class Action extends Game {
        public Action(String name, int weight, int age, int duration) {
            super(name, weight, age, duration);
        }
    }

    // ---------------------------
    // Фабрика
    // ---------------------------
    public static Game create(String type, String name, int weight, int age, int duration) {
        return switch (type) {
            case "Puzzle" -> new Puzzle(name, weight, age, duration);
            case "Board" -> new Board(name, weight, age, duration);
            case "Action" -> new Action(name, weight, age, duration);
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
