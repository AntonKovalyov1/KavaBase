package kavabase.Prompt;

import java.io.File;
import kavabase.fileFormat.Helper;

/**
 *
 * @author axk176431
 */
public class DDL {
    
    public static void showTables() {
        //TODO
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
        if (!isPrimaryKeyColumnValid(columns[0])) {
            Error.noPrimaryKeyError();
            return false;
        }
        for (int i = 1; i < columns.length; i++) {
            if (!isNonPrimaryKeyColumnValid(columns[i])) {
                Error.syntaxError();
                return false;
            }
        }
        //TODO
        //check for duplicate names
        //Create table
        System.out.println("Table " + tableName + " created.");
        System.out.println("Query successful.");
        return true;
    }
    
    public static void dropTable(String query) {
        //TODO
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
    
    private static boolean isPrimaryKeyColumnValid(String primaryKeyColumn) {
        String[] tokens = primaryKeyColumn.trim().split("\\s+");
        if (tokens.length != 4)
            return false;
        return Helper.isNameValid(tokens[0]) && 
               tokens[1].equals("int") &&
               tokens[2].equals("primary") &&
               tokens[3].equals("key");
    }
    
    public static boolean isNonPrimaryKeyColumnValid(String column) {
        String[] tokens = column.trim().split("\\s+");
        if (tokens.length == 4)
            return Helper.isNameValid(tokens[0]) && 
                   Helper.isDataType(tokens[1]) &&
                   tokens[2].equals("not") &&
                   tokens[3].equals("null"); 
        if (tokens.length == 2) {
            return Helper.isNameValid(tokens[0]) && 
                   Helper.isDataType(tokens[1]);
        }
        return false;
    }
}
