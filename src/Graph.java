import java.util.*;

public class Graph {

/*    private final List<Vertex> vertices;
    public Graph(List<Node> rooms) {

        this.rooms = rooms;
        this.vertices = new ArrayList<>();

        for (Node room1 : rooms) {
            for (Node room2 : rooms) {
                if (room1 != room2)
                    this.vertices.add(new Vertex(room1, room2, room1.distanceSq(room2), false));
            }
        }
    }*/

    private final Map<Node, List<Node>> map;
    private final Queue<Edge> edges;

    public Graph() {
        this.map = new HashMap<>();
        this.edges = new PriorityQueue<>();
    }

    public Set<Node> getRooms() {
        return this.map.keySet();
    }

    public void addRoom(Node node) {
        this.map.keySet().forEach(vertex -> this.edges.add(new Edge(vertex, node, vertex.distanceSq(node))));
        this.map.put(node, new ArrayList<>());
    }

    // TODO: Don't return ugly map
    public Map<Node, List<Node>> generateCorridors() {
        while (!new GraphWalker().visitedAll()) {
            Edge edge = this.edges.poll();
            if (edge == null)
                break;

            if (new GraphWalker().canReach(edge.v1, edge.v2))
                continue;

            this.map.get(edge.v1).add(edge.v2);
            this.map.get(edge.v2).add(edge.v1);
        }

        return this.map;
    }

    public record Edge(Node v1, Node v2, double distanceSq) implements Comparable<Edge> {

        @Override
        public int compareTo(Edge other) {
            return (int) (this.distanceSq - other.distanceSq);
        }
    }

/*    public record Vertex(Node node1, Node node2, double distanceSq, boolean connected) implements Comparable<Vertex> {

        @Override
        public int compareTo(Vertex vertex) {
            return (int) (this.distanceSq - vertex.distanceSq);
        }
    }*/

    private class GraphWalker {

        private final List<Node> visited = new ArrayList<>(map.size());

        public boolean visitedAll() {
            Node node = (Node) map.keySet().toArray()[0];
            visited.add(node);
            this.visitedAllRec(node);

            return visited.size() == map.size();
        }

        private void visitedAllRec(Node v1) {
            for (Node v2 : map.get(v1)) {
                if (!visited.contains(v2)) {
                    visited.add(v2);
                    visitedAllRec(v2);
                }
            }
        }

        public boolean canReach(Node v1, Node v2) {
            this.visited.add(v1);

            if (v1 == v2)
                return true;

            return map.get(v1)
                    .stream()
                    .filter(node -> !this.visited.contains(node))
                    .anyMatch(node -> canReach(node, v2));
        }
    }
}
