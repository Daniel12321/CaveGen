import java.util.Random;

public class World {

    public static final int SIM_STEPS_ROOM = 1;
    public static final int SIM_STEPS_POST_CORRIDOR = 2;
    private static final int MAX_ROOM_ITERATIONS = 100;

    private final long seed;
    private final int rooms, roomSize, worldSize;
    private final Graph graph;

    private boolean[][] tiles;

    public World(long seed, int rooms, int roomSize, int worldSize) {
        this.seed = seed;
        this.rooms = rooms;
        this.roomSize = roomSize;
        this.worldSize = worldSize;
        this.graph = new Graph();
        this.tiles = new boolean[0][0];
    }

    public void print() {
        for (int y = this.tiles.length - 1; y >= 0; y--) {
            for (int x = 0; x < this.tiles[y].length; x++) {
//                System.out.print(this.tiles[x][y] ? "   " : "███");
                System.out.print(this.tiles[x][y] ? "   " : "\033[100m   \033[49m");
//                System.out.print(this.tiles[x][y] ? "   " : "\033[101m   \033[49m");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void generate() {
        final Random r = new Random(this.seed);

        this.tiles = new boolean[this.worldSize][this.worldSize];
        this.graph.addRoom(new Node(this.worldSize / 2, this.worldSize / 3));

        int iterations = 0;
        roomLoop: while (this.graph.getRooms().size() < this.rooms && iterations++ < MAX_ROOM_ITERATIONS) {
            Node node = this.randomNode(r);

            // Check if the new node is valid
            for (Node other : this.graph.getRooms())
                if (node.distanceSq(other) < this.roomSize * this.roomSize + 3)
                    continue roomLoop;

            this.graph.addRoom(node);
        }

        // Generates the rooms and add them to the map
        RoomGenerator roomGen = new RoomGenerator(this.roomSize, r);
        for (Node room : this.graph.getRooms())
            this.addRoom(room, roomGen.generateRoom());

        var map = this.graph.generateCorridors();
        map.forEach((v1, l) -> l.forEach(v2 -> Bresenham.walkLineA(v1.x(), v1.y(), v2.x(), v2.y(), this::drawPixel)));

        for (int i = 0; i < World.SIM_STEPS_POST_CORRIDOR; i++)
            this.tiles = CellularAutomata.doSimulationStep(this.tiles);
    }

    private void drawPixel(int x, int y) {
        this.tiles[x][y] = true;
//        this.tiles[x+1][y] = true;
//        this.tiles[x][y+1] = true;
    }

    private Node randomNode(final Random r) {
        return new Node(r.nextInt(this.roomSize, this.worldSize - this.roomSize), r.nextInt(this.roomSize, this.worldSize - this.roomSize));
    }

    private void addRoom(final Node node, final boolean[][] room) {
        for (int x = 0; x < room.length; x++) {
            for (int y = 0; y < room[x].length; y++) {
                int worldX = node.x() + x - this.roomSize, worldY = node.y() + y - this.roomSize;
                if (worldX < 0 || worldX >= this.worldSize || worldY < 0 || worldY >= this.worldSize)
                    continue;

                if (room[x][y])
                    this.tiles[worldX][worldY] = true;
            }
        }
    }
}
