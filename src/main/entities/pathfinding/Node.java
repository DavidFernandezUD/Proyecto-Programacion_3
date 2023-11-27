package main.entities.pathfinding;

class Node implements Comparable<Node> {

    int[] position;
    double distance;
    Node prev;

    Node(int[] position, double distance, Node prev) {
        this.position = position;
        this.distance = distance;
        this.prev = prev;
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(distance, o.distance);
    }
}
