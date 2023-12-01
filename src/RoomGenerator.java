import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RoomGenerator {

    private final int roomSize;
    private final Random r;

    public RoomGenerator(final int roomSize, final Random r) {
        this.roomSize = roomSize;
        this.r = r;
    }

    public void print(boolean[][] room) {
        for (int y = room.length - 1; y >= 0; y--) {
            for (int x = 0; x < room[y].length; x++) {
                System.out.print(room[x][y] ? "  " : "██");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean[][] generateRoom() {
        boolean[][] room = new boolean[roomSize*2+1][roomSize*2+1];
        Node middle = new Node(roomSize, roomSize);

        room[middle.x()][middle.y()] = true;

        Node n1 = middle.up();
        Node n2 = middle.down();
        Node n3 = middle.left();
        Node n4 = middle.right();

        for (int i = 0; i < roomSize * 4; i++) {
            n1 = generateTile(room, middle, n1);
            n2 = generateTile(room, middle, n2);
            n3 = generateTile(room, middle, n3);
            n4 = generateTile(room, middle, n4);
        }

//        print(room);
        for (int i = 0; i < World.SIM_STEPS; i++) {
            room = CellularAutomata.doSimulationStep(room);
//            print(room);
        }

        return room;
    }

    private Node generateTile(final boolean[][] room, final Node middle, final Node node) {
        if (node == null)
            return null;

        room[node.x()][node.y()] = true;

        List<Node.Direction> dirs = this.getRandomDirections();

//        System.out.printf("Generated dirs: [%d, %d, %d, %d]", dirs.get(0), dirs.get(1), dirs.get(2), dirs.get(3)); // TODO: Remove
//        System.out.println();

        Node next;
        for (Node.Direction dir : dirs) {
            next = dir.apply(node);
            if (next.distanceSteps(middle) > roomSize || room[next.x()][next.y()]) // Bounds check no longer needed as the distance prevents going out of bounds
                continue;

            room[next.x()][next.y()] = true;
            return next;
        }

        /*next.x() < 0 || next.y() < 0 || next.x() >= room.length || next.y() >= room[next.x()].length ||*/

        return null;
    }

    private List<Node.Direction> _getRandomDirection() {
        List<Node.Direction> dirs = new ArrayList<>(4);
        int root = r.nextInt(24); // 0 - 23

        dirs.add(Node.Direction.DIRECTIONS.get(root / 6));
        dirs.add(Node.Direction.DIRECTIONS.get(root / 3));




        int root2 = root % 6; // 0 - 5
        dirs.add(Node.Direction.DIRECTIONS.get(root2 / 3));
        int root3 = root2 % 3; // 0 - 2
        dirs.add(Node.Direction.DIRECTIONS.get(root3 / 2));






        return dirs;

        //   3 2 1 0   |   3 2 0 1   |   3 1 2 0   |   3 1 0 2   |   3 0 2 1   |   3 0 1 2
        //   2 3 1 0   |   2 3 1 0   |   2 1 3 0   |   2 1 0 3   |   2 0 3 1   |   2 0 1 3
        //   1 3 2 0   |   1 3 0 2   |   1 2 3 0   |   1 2 0 3   |   1 0 3 2   |   1 0 2 3
        //   0 3 2 1   |   0 3 1 2   |   0 2 3 1   |   0 2 1 3   |   0 1 3 2   |   0 1 2 3

        // 24 options

        // 000000 - 111111
        // 24 = 11000
        // 24 = 10111
        // 16 = 10000
    }

    private List<Node.Direction> getRandomDirections() {
        List<Node.Direction> dirs = new ArrayList<>(Node.Direction.DIRECTIONS);
        Collections.shuffle(dirs, r);

        return dirs;
    }

    /*
    private List<Integer> getRandomNumbers() {
        List<Integer> dirs = new ArrayList<>(List.of(0, 1, 2, 3));
        Collections.shuffle(dirs, r);

        return dirs;



        // Generates a shuffled array of 1 through 4
        int[] dirs = new int[]{-1, -1, -1, -1};
        int index = 0, nextNum;
        while (index < 4) {
            nextNum = r.nextInt(4 - index);

            while (dirs[0] == nextNum || dirs[1] == nextNum || dirs[2] == nextNum || dirs[3] == nextNum)
                nextNum++;

            dirs[index] = nextNum;
            index++;
        }


    }*/
}
