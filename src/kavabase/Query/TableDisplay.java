package kavabase.Query;

import java.util.ArrayList;
import kavabase.DataFormat.DataType;

/**
 *
 * @author Anton
 */
public class TableDisplay {
    
    private final ArrayList<String> columns;
    private final ArrayList<ArrayList<DataType>> data = new ArrayList<>();
    private boolean empty = true;

    public TableDisplay(final String columnName) {
        // One column table
        columns = new ArrayList<>();
        columns.add(columnName);
    }
    
    public TableDisplay(final ArrayList<String> columns) {
        this.columns = columns;
    }   
    
    public void addRecord(final ArrayList<DataType> record) {
        empty = false;
        data.add(record);
    }
    
    public int getMaximumSizeColumn(final ArrayList<DataType> column) {
        int max = column.get(0).toString().length();
        for (int i = 1; i < column.size(); i++) {
            int current = column.get(i).toString().length();
            if (current > max)
                max = current;
        }
        return max;
    }
    
    public ArrayList<DataType> getColumn(int index) {
        ArrayList<DataType> column = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            column.add(data.get(i).get(index));            
        }
        return column;
    }
    
    public ArrayList<Integer> getColumnSizes() {
        ArrayList<Integer> columnSizes = new ArrayList<>();
        for (int i = 0; i < data.get(0).size(); i++) {
            columnSizes.add(Integer.max(columns.get(i).length(), 
                            getMaximumSizeColumn(getColumn(i))));           
        }
        return columnSizes;
    }
    
    public void display() {
        if (empty) {
            System.out.println("Empty set.");
            return;
        }
        ArrayList<Integer> columnSizes = getColumnSizes();
        StringBuilder decoration = new StringBuilder();
        for (int i = 0; i < columnSizes.size(); i++) {
            decoration.append("+");
            for (int j = 0; j < columnSizes.get(i) + 2; j++) {
                decoration.append("-");                
            }
        }
        decoration.append("+");
        System.out.println(decoration);
        printColumnNames(columnSizes);
        System.out.println(decoration);
        for (int i = 0; i < data.size(); i++) {
            printRow(columnSizes, data.get(i));
        }
        System.out.println(decoration);
    }
    
    public void printRow(final ArrayList<Integer> columnsSizes, 
                         final ArrayList<DataType> record) {
        for (int i = 0; i < columnsSizes.size(); i++) {
            System.out.print("| ");
            System.out.format("%-" + columnsSizes.get(i) + "s", 
                              record.get(i).toString());
            System.out.print(" ");
        }
        System.out.print("|");
        System.out.println("");
    }
    
    public void printColumnNames(final ArrayList<Integer> columnsSizes) {
        for (int i = 0; i < columnsSizes.size(); i++) {
            System.out.print("| ");
            System.out.format("%-" + columnsSizes.get(i) + "s", 
                    columns.get(i));
            System.out.print(" ");
        }
        System.out.print("|");
        System.out.println("");
    }
}
