package main;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class Statistics extends JFrame {

    private GamePanel gamePanel;
    private ArrayList<Game> games;

    public Statistics(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        this.add(statisticsPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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

}
