import java.util.Random;

public class World {

    public static final int SIM_STEPS = 3;
    private static final int MAX_ROOM_ITERATIONS = 100;

    private final long seed;
    private final int roomSize, worldSize;
    private final Node[] rooms;
    private boolean[][] tiles;

    public World(long seed, int rooms, int roomSize, int worldSize) {
        this.seed = seed;
        this.roomSize = roomSize;
        this.worldSize = worldSize;
        this.rooms = new Node[rooms];
        this.tiles = new boolean[0][0];
    }

    public void print() {
        for (int y = this.tiles.length - 1; y >= 0; y--) {
            for (int x = 0; x < this.tiles[y].length; x++) {
//                System.out.print(this.tiles[x][y] ? "██" : "..");
                System.out.print(this.tiles[x][y] ? "   " : "███");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void generate() {
        final Random r = new Random(this.seed);

        this.tiles = new boolean[this.worldSize][this.worldSize];
        this.rooms[0] = new Node(this.worldSize / 2, this.worldSize / 3);

        int iterations = 0, roomIndex = 1;
        roomLoop: while (roomIndex < this.rooms.length && iterations++ < MAX_ROOM_ITERATIONS) {
            Node node = this.randomNode(r);

            // Check if the new node is valid
            for (Node other : this.rooms) {
                if (other != null && node.distanceSq(other) < this.roomSize * this.roomSize + 3) {
                    continue roomLoop;
                }
            }

            this.rooms[roomIndex++] = node;
        }

        RoomGenerator roomGen = new RoomGenerator(this.roomSize, r);
        for (Node room : this.rooms) if (room != null)
            this.addRoom(room, roomGen.generateRoom());
    }

    private Node randomNode(final Random r) {
        return new Node(r.nextInt(this.roomSize, this.worldSize - this.roomSize), r.nextInt(this.roomSize, this.worldSize - this.roomSize));
    }

    private void addRoom(final Node node, final boolean[][] room) {
//        System.out.println("Adding room at: [" + node.x() + "," + node.y() + "]"); // TODO: Remove
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

//    private void generateRoom(final Random r, final Node node) {
//        this.tiles[node.x][node.y] = true;
//
//        for (int x = Math.max(0, node.x - roomSize); x < Math.min(worldSize, node.x + roomSize); x++) {
//            for (int y = Math.max(0, node.y - roomSize); y < Math.min(worldSize, node.y + roomSize); y++) {
//                float chance = 1f - node.distanceSteps(x, y) / (float) this.roomSize;
//                if (chance > 0) {
//                    this.tiles[x][y] = r.nextDouble() < chance;
//                }
//            }
//        }
//    }
}
