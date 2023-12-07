import java.util.Random;

public class Main {

    private static final long SEED = new Random().nextLong();

    public static void main(String[] args) {
        World world = new World(69422, 10, 12, 100);
        world.generate();
        world.print();
    }
}
