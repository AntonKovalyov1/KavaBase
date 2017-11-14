package kavabase.Prompt;

import java.io.File;
import kavabase.DataFormat.DataType;
import kavabase.fileFormat.Column;
import kavabase.fileFormat.Helper;
import kavabase.fileFormat.Table;

/**
 *
 * @author axk176431
 */
public class DDL {
    
    public static void showTables() {
        //No parsing necessary
        //TODO
    }
    
    public static boolean dropTable(String query) {
        if (Helper.isNameValid(query)) {
            System.out.println(Helper.SUCCESSFUL_QUERY);
            System.out.println("Table " + query + " dropped.");
            return true;
        }
        Error.notValidTableName();
        return false;
    }
    
    public static boolean createTable(String query) {
        String tableName = getTableName(query);
        if (!Helper.isNameValid(tableName)) {
            Error.notValidTableName();
            return false;
        }
        if (tableExists(tableName)) {
            Error.tableExistsError(tableName);
            return false;
        }
        
        Table table = new Table(tableName);
        query = query.substring(tableName.length()).trim();
        if (!(query.startsWith("(") && query.endsWith(")"))) {
            Error.syntaxError();
            return false;
        }
        query = query.substring(1, query.length() - 1).trim();
        if (query.startsWith(",") || query.endsWith(",")) {
            Error.syntaxError();
            return false;
        }
        String[] columns = query.split(",");
        if (columns.length < 1) {
            Error.syntaxError();
            return false;
        }
        if (!isPrimaryKeyColumnValid(columns[0], table)) {
            Error.noPrimaryKeyError();
            return false;
        }
        
        for (int i = 1; i < columns.length; i++) {
            if (!isNonPrimaryKeyColumnValid(columns[i], table, i)) {
                Error.columnDefinitionError();
                return false;
            }
        }
        //TODO
        //Create table
        System.out.println("Table " + tableName + " created.");
        System.out.println(Helper.SUCCESSFUL_QUERY);
        return true;
    }
    
    private static boolean tableExists(String tableName) {
        File file = new File(tableName + Helper.TABLE_FILE_EXTENSION);
        if (file.exists() && !file.isDirectory())
            return true;
        return false;
    }
    
    private static String getTableName(String query) {
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == ' ' || query.charAt(i) == '(')
                return getTableName(query.substring(0, i).trim());
        }
        return query;
    }
    
    private static boolean isPrimaryKeyColumnValid(String primaryKeyColumn, Table table) {
        String[] tokens = primaryKeyColumn.trim().split("\\s+");
        if (tokens.length != 4)
            return false;
        if (Helper.isNameValid(tokens[0]) && 
               tokens[1].equals("int") &&
               tokens[2].equals("primary") &&
               tokens[3].equals("key")) {
            Column column = new Column(tokens[0], true, false, 1, new DataType.Int());
            return table.addColumn(column);
        }
        return false;
    }
    
    public static boolean isNonPrimaryKeyColumnValid(String column, Table table, int ordinalPosition) {
        String[] tokens = column.trim().split("\\s+");
        if (tokens.length == 4)
            if (Helper.isNameValid(tokens[0]) && 
                Helper.isDataType(tokens[1]) &&
                tokens[2].equals("not") &&
                tokens[3].equals("null")) {
                Column c = new Column(tokens[0], false, false, ordinalPosition, Helper.getDataType(tokens[1]));
                return table.addColumn(c);
            } 
        if (tokens.length == 2) {
            if (Helper.isNameValid(tokens[0]) && 
                Helper.isDataType(tokens[1])) {
                Column c = new Column(tokens[0], false, true, ordinalPosition, Helper.getDataType(tokens[1]));
                return table.addColumn(c);                
            }
        }
        return false;
    }
}
