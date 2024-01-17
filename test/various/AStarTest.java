package various;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.PriorityQueue;


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


class AStar {

    private final int[][] map = new int[][] {
            {0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 1, 1, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0}
    };

    private final int[] start = new int[] {7, 0};
    private final int[] end = new int[] {0, 7};

    private final int[][] DIRECTIONS = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public Node search() {

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(new Node(start, euclideanDistance(start, end), null));

        while(!queue.isEmpty()) {
            Node current = queue.poll();
            for(Node option : getOption(current)) {
                if(option.position[0] == end[0] && option.position[1] == end[1]) {
                    return option;
                } else {
                    queue.add(option);
                }
            }
        }
        return null;
    }

    private ArrayList<Node> getOption(Node current) {
        ArrayList<Node> options = new ArrayList<>();
        for(int[] direction : DIRECTIONS) {
            int[] destination = new int[] {current.position[0] + direction[0], current.position[1] + direction[1]};
            if(destinationPossible(destination)) {
                if(map[destination[0]][destination[1]] != 1) {
                    Node newNode = new Node(destination, current.distance + euclideanDistance(destination, end), current);
                    options.add(newNode);
                }
            }
        }
        return options;
    }

    private boolean destinationPossible(int[] destination) {
        int x = destination[1];
        int y = destination[0];
        return x < 8 && x >= 0 && y < 8 && y >= 0;
    }

    private double manhattanDistance(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }

    private double euclideanDistance(int[] a, int[] b) {
        return Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]));
    }

    public void setMap(int row, int col, int value) {
        this.map[row][col] = value;
    }

    public int[][] getMap() {
        return map;
    }

    public int[] getStart() {
        return start;
    }

    public int[] getEnd() {
        return end;
    }
}


public class AStarTest extends JFrame {

    private AStar aStar;

    JPanel mapPanel;
    JButton searchButton;

    Node path;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AStarTest();
            }
        });
    }

    AStarTest() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("A Star Algorithm");
        this.setSize(800, 800);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        aStar = new AStar();

        path = null;

        // Panel
        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMap(g);
                drawStartEnd(g);
            }
        };
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                int row = e.getY() / 100;
                int col = e.getX() / 100;

                if(e.getButton() == 1) {
                    aStar.setMap(row, col, 1);
                    mapPanel.repaint();
                } else if(e.getButton() == 3) {
                    aStar.setMap(row, col, 0);
                    mapPanel.repaint();
                }
            }
        });
        mapPanel.setPreferredSize(new Dimension(800, 800));
        this.add(mapPanel, BorderLayout.CENTER);

        // Search button
        JPanel buttonPanel = new JPanel();
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Searching...");
                path = aStar.search();
                if(path != null) {
                    System.out.println("Done!");
                    drawNode(mapPanel.getGraphics(), path);
                    drawStartEnd(mapPanel.getGraphics());
                } else {
                    System.out.println("No path found");
                }
            }
        });
        buttonPanel.add(searchButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

    private void drawMap(Graphics g) {

        int[][] map = aStar.getMap();

        for(int col = 0; col < map.length; col++) {
            for(int row = 0; row < map[0].length; row++) {
                Color color = (map[row][col] == 0) ? Color.LIGHT_GRAY : Color.BLACK;
                g.setColor(color);
                g.fillRect(col * 100, row * 100, 100, 100);
            }
        }
    }

    private void drawStartEnd(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(aStar.getStart()[1] * 100, aStar.getStart()[0] * 100, 100, 100);

        g.setColor(Color.RED);
        g.fillRect(aStar.getEnd()[1] * 100, aStar.getEnd()[0] * 100, 100, 100);
    }

    private void drawNode(Graphics g, Node node) {
        if(node != null) {
            g.setColor(Color.YELLOW);
            g.fillRect(node.position[1] * 100, node.position[0] * 100, 100, 100);
            drawNode(g, node.prev);
        }
    }

}
