import java.util.Random;

public class Main {

    private static final long SEED = new Random().nextLong();

    public static void main(String[] args) {
        World world = new World(SEED, 100, 12, 120);
        world.generate();
        world.print();
    }
}
