package kavabase.fileFormat;

import java.util.ArrayList;
import kavabase.fileFormat.Column;

/**
 *
 * @author axk176431
 */
public class Table {
    
    private final ArrayList<Column> columns = new ArrayList<>();
    private final String tableName;
    
    public Table(final String tableName) {
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
}
