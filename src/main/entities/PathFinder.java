package main.entities;

import main.GamePanel;

import java.util.ArrayList;
import java.util.PriorityQueue;

/** A* pathfinding algorithm implementation.
 * @author david.f@opendeusto.es*/
public class PathFinder {

    private Node[][] map;
    private final int[][] DIRECTIONS = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    GamePanel gamePanel;

    /** Creates PathFinder object.*/
    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadMap();
    }

    /** Loads the collision map into a node representation
     * for the algorithm to work.*/
    public void loadMap() {

        if(map == null) {
            map = new Node[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        }

        int[][] collisionMap = gamePanel.collisionChecker.collisionMap;
        for(int row = 0; row < collisionMap.length; row++) {
            for(int col = 0; col < collisionMap[0].length; col++) {
                boolean solid = collisionMap[row][col] != -1;
                this.map[row][col] = new Node(row, col, solid);
            }
        }

        resetNodes();
    }

    /** Returns a Node list with the path between two main.entities.
     * @param origin Origin entity.
     * @param destination Destination entity.
     * @return ArrayList of Nodes with the path from origin to destination or
     * null if no path exists.*/
    public ArrayList<Node> search(Entity origin, Entity destination) {

        resetNodes(); // Resetting modes from last search

        int startRow = (origin.worldY + gamePanel.tileSize/2) / gamePanel.tileSize;
        int startCol = (origin.worldX + gamePanel.tileSize/2) / gamePanel.tileSize;
        int endRow = (destination.worldY + gamePanel.tileSize/2) / gamePanel.tileSize;
        int endCol = (destination.worldX + gamePanel.tileSize/2) / gamePanel.tileSize;

        Node start = map[startRow][startCol]
                .setCost(0, euclideanDistance(startRow, startCol, endRow, endCol))
                .setVisited();

        Node end = map[endRow][endCol]
                .setCost(0, 0);

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(start);

        while(!queue.isEmpty()) {
            Node current = queue.poll();
            for(Node option : getOption(current, end)) {
                if(option == end) {
                    return getPath(option, new ArrayList<>());
                } else {
                    queue.add(option);
                }
            }
        }
        return null;
    }

    /** Helper method that returns a list with the available neighbouring
     * nodes of a given node. It also updates the visited state, previous node
     * and costs of the new available nodes.
     * @param current Current node.
     * @param end Destination node.
     * @return List of not solid, unvisited neighbour nodes.*/
    private ArrayList<Node> getOption(Node current, Node end) {
        ArrayList<Node> options = new ArrayList<>();
        for(int[] direction : DIRECTIONS) {
            int destinationRow = current.row + direction[0];
            int destinationCol = current.col + direction[1];
            if(inBounds(destinationRow, destinationCol)) {
                Node newNode = map[destinationRow][destinationCol];
                if((!newNode.solid || newNode == end) && !newNode.visited) { // Doesn't check if end node is solid
                    newNode
                            .setPrev(current)
                            .setVisited()
                            .setCost(
                                    current.gCost + 1,
                                    euclideanDistance(
                                        newNode.row, newNode.col,
                                        end.row, end.col));
                    options.add(map[destinationRow][destinationCol]);
                }
            }
        }
        return options;
    }

    /** Helper method that checks if a pair of coordinates are
     * within the bounds of the node map.
     * @param row Row index to access.
     * @param col Column index to access.
     * @return true if the indicated coordinates are in bounds of the
     * map.*/
    private boolean inBounds(int row, int col) {
        return row < map.length && row >= 0 && col < map[0].length && col >= 0;
    }

    /** Calculates Manhattan distance heuristic between a and b.
     * @param aRow Row of point a.
     * @param aCol Column of point a.
     * @param bRow Row of point b.
     * @param bCol Column of point b.
     * @return Manhattan distance between points a and b.*/
    private double manhattanDistance(int aRow, int aCol, int bRow, int bCol) {
        return Math.abs(aRow - bRow) + Math.abs(aCol - bCol);
    }

    /** Calculates Manhattan distance heuristic between a and b.
     * @param aRow Row of point a.
     * @param aCol Column of point a.
     * @param bRow Row of point b.
     * @param bCol Column of point b.
     * @return Manhattan distance between points a and b.*/
    private double euclideanDistance(int aRow, int aCol, int bRow, int bCol) {
        return Math.sqrt((aRow - bRow) * (aRow - bRow) + (aCol - bCol) * (aCol - bCol));
    }

    /** Recursively backtracks the previous node of the
     * destination node and adds it to a list, finally returning
     * a list with the path from the origin to the destination.
     * @param node Current node.
     * @param path Path from the current node to the destination node
     * excluding current node.
     * @returns the list with the path from origin to destination
     * excluding origin node.*/
    private ArrayList<Node> getPath(Node node, ArrayList<Node> path) {
        if(node.prev == null) {
            return path; // The returned path excludes the first node
        }
        path.add(0, node);
        return getPath(node.prev, path);
    }

    /** Helper method that resets all nodes cost,
     * visited status and prev node.*/
    private void resetNodes() {
        for(Node[] row : map) {
            for(Node node : row) {
                node.visited = false;
                node.hCost = Double.POSITIVE_INFINITY;
                node.gCost = 0;
                node.prev = null;
            }
        }
    }

    /** Node class for A* algorithm.
     * @author david.f@opendeusto.es*/
    public class Node implements Comparable<Node> {

        int row;
        int col;
        boolean solid;
        boolean visited;
        double gCost;
        double hCost;
        Node prev;

        /** Creates a Node with a given position and solid state.
         * @param row Row of the node.
         * @param col Column of the node.
         * @param solid True if the node has a collision.*/
        Node(int row, int col, boolean solid) {
            this.row = row;
            this.col = col;
            this.solid = solid;
        }

        /** Sets the g and h cost of the node. g is the known
         * cost at that node, or the nodes from it to the origin,
         * and h is the cost predicted by the heuristic.
         * @param g Known cost.
         * @param h Heuristic cost.
         * @return itself to allow method chaining.*/
        protected Node setCost(double g, double h) {
            this.gCost = g;
            this.hCost = h;
            return this;
        }

        /** Marks the node as visited, to avoid
         * visiting it again.
         * @return itself to allow method chaining.*/
        protected Node setVisited() {
            this.visited = true;
            return this;
        }

        /** Sets the previous node to allow backtracking the
         * path after the algorithm has found the path.
         * @param prev Previous node.
         * @return itself to allow method chaining.*/
        protected Node setPrev(Node prev) {
            this.prev = prev;
            return this;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(gCost + hCost, o.gCost + o.hCost);
        }
    }
}
