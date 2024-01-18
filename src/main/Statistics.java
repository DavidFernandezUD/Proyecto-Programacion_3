package main;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.JButton;

public class Statistics extends JFrame {

    private GamePanel gamePanel;
    private ArrayList<Game> games;

    public Statistics(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        this.setResizable(false);
        this.setTitle("Shadows Of Despair");

        // Load the games
        this.games = gamePanel.gameManager.loadGames();

        JPanel statisticsPanel = new JPanel();

        // Create the statistics table
        String[] columnNames = { "CODE", "NAME", "DATE" };
        StatisticsModel statisticsModel = new StatisticsModel(columnNames, games);
        JTable statisticsTable = new JTable(statisticsModel);
        JScrollPane statisticsScrollPane = new JScrollPane(statisticsTable);
        statisticsScrollPane.setBounds(0, 0, 500, 500);
        statisticsPanel.add(statisticsScrollPane);

        // Button to go back to the main menu
        JButton backToMenuButton = new JButton("Back To Menu");
        backToMenuButton.addActionListener(e -> {
            this.setVisible(false);
        });

        // Button to see statistics of a game
        JButton seeStatisticsButton = new JButton("See Statistics");
        seeStatisticsButton.addActionListener(e -> {
            int selectedRow = statisticsTable.getSelectedRow();
            if (selectedRow != -1) {
                Game game = games.get(selectedRow);
                InnerStatistics innerStatistics = new InnerStatistics(game);
                ;
            }
        });

        statisticsPanel.add(seeStatisticsButton);
        statisticsPanel.add(backToMenuButton);
        this.add(statisticsPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public class StatisticsModel implements TableModel {

        private String[] columnNames;
        private ArrayList<Game> games;

        public StatisticsModel(String[] columnNames, ArrayList<Game> games) {
            super();
            this.columnNames = columnNames;
            this.games = games;
        }

        @Override
        public int getRowCount() {
            return games.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {

            switch (columnIndex) {
                case 0:
                    return Integer.class;
                case 1:
                    return String.class;
                case 2:
                    return String.class;
                default:
                    return null;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            switch (columnIndex) {
                case 0:
                    return games.get(rowIndex).gameCode;
                case 1:
                    return games.get(rowIndex).gameName;
                case 2:
                    return games.get(rowIndex).date;
                default:
                    return null;
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        @Override
        public void addTableModelListener(TableModelListener l) {
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
        }
    }

    public class InnerStatistics extends JFrame {

        private Game game;

        public InnerStatistics(Game game) {

            this.game = game;
            this.setResizable(false);
            this.setTitle(game.gameName + " Statistics");
            JPanel innerStatisticsPanel = new JPanel();

            // Create the statistics information
            JLabel gameCodeLabel = new JLabel("Game Code: " + game.gameCode);
            JLabel gameNameLabel = new JLabel("Game Name: " + game.gameName);

            // Button to go back to the main statistics menu
            JButton backToMenuButton = new JButton("Back To Menu");
            backToMenuButton.addActionListener(e -> {
                this.setVisible(false);
            });

            innerStatisticsPanel.add(backToMenuButton);
            this.add(innerStatisticsPanel);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);
        }
    }

}
