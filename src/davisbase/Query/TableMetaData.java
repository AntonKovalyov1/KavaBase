package davisbase.Query;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author axk176431
 */
public class TableMetaData {
    
    private final ArrayList<Column> columns = new ArrayList<>();
    private final String tableName;
    private final int tableKey;
    private final int columnsKey;
    
    public TableMetaData(final String tableName) {
        this.tableName = tableName;
        this.tableKey = 0;
        this.columnsKey = 0;
    }
    
    public TableMetaData(final String tableName, 
                         final int tableKey, 
                         final int columnsKey) {
        this.tableName = tableName;
        this.tableKey = tableKey;
        this.columnsKey = columnsKey;
    }
    
    public boolean addColumn(Column column) {
        if (columns.contains(column)) {
            System.out.println("add failed");
            return false;
        }
        columns.add(column);
        return true;
    }
    
    /**
     * @return the columns
     */
    public ArrayList<Column> getColumns() {
        return columns;
    }
    
    public ArrayList<String> getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>();
        for (Column column : columns) {
            columnNames.add(column.getColumnName().toLowerCase());
        }
        return columnNames;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TableMetaData))
            return false;
        TableMetaData other = (TableMetaData)o;
        return this.getTableName().toLowerCase().equals(
                other.getTableName().toLowerCase());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.tableName);
        return hash;
    }
    
    @Override
    public String toString() {
        String s = tableName;
        s += "\n";
        for (Column column : getColumns()) {
            s += column.getColumnName() + " ";
        }
        return s;
    }
    
    /**
     * @return the tableKey
     */
    public int getTableKey() {
        return tableKey;
    }

    /**
     * @return the columnsKey
     */
    public int getColumnsKey() {
        return columnsKey;
    }
}