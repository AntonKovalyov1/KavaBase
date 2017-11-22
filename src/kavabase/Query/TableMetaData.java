package kavabase.Query;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author axk176431
 */
public class TableMetaData {
    
    private final ArrayList<Column> columns = new ArrayList<>();
    private final String tableName;
    
    public TableMetaData(final String tableName) {
        this.tableName = tableName;
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
            columnNames.add(column.getColumnName());
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
        return this.getTableName().equals(other.getTableName());
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
}
