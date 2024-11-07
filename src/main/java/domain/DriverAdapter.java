package domain;

import javax.swing.table.AbstractTableModel;

public class DriverAdapter extends AbstractTableModel {

    public DriverAdapter(Driver driver) {
        this.driver = driver;
    }

    private Driver driver;
    private String[] columnNames = {"from", "to", "date", "places", "price"};

    @Override
    public int getRowCount() {
        return driver.getCreatedRides().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return driver.getCreatedRides().get(rowIndex).getFrom();
            case 1:
                return driver.getCreatedRides().get(rowIndex).getTo();
            case 2:
                return driver.getCreatedRides().get(rowIndex).getDate();
            case 3:
                return driver.getCreatedRides().get(rowIndex).getnPlaces();
            case 4:
                return driver.getCreatedRides().get(rowIndex).getPrice();
            default:
                return null;
        }
    }
}
