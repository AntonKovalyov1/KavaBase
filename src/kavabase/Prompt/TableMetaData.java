package kavabase.Prompt;

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
        if (columns.contains(column))
            return false;
        columns.add(column);
        return true;
    }
    
    /**
     * @return the columns
     */
    public ArrayList<Column> getColumns() {
        return columns;
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
}
