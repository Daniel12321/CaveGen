import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class Node {

    private final int x, y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Node) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
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
