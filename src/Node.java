import java.util.List;
import java.util.function.Function;

public record Node(int x, int y) {

//    public static final List<Direction> DIRECTIONS = new ArrayList<>();
//
//    static {
//        DIRECTIONS.add(Node::up);
//        DIRECTIONS.add(Node::down);
//        DIRECTIONS.add(Node::left);
//        DIRECTIONS.add(Node::right);
//    }

    public Node up() {
        return new Node(x, y + 1);
    }

    public Node down() {
        return new Node(x, y - 1);
    }

    public Node left() {
        return new Node(x - 1, y);
    }

    public Node right() {
        return new Node(x + 1, y);
    }

    public double distanceSq(Node other) {
        final double dx = other.x - x;
        final double dy = other.y - y;

        return dx * dx + dy * dy;
    }

    public double distance(Node other) {
        return Math.sqrt(distanceSq(other));
    }

    public int distanceSteps(Node other) {
        return distanceSteps(other.x, other.y);
    }

    public int distanceSteps(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @FunctionalInterface
    public interface Direction extends Function<Node, Node> {
        Direction UP = Node::up;
        Direction DOWN = Node::down;
        Direction LEFT = Node::left;
        Direction RIGHT = Node::right;

        List<Direction> DIRECTIONS = List.of(UP, DOWN, LEFT, RIGHT);
    }
}
