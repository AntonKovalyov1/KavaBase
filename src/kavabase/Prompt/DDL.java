package kavabase.Prompt;

import java.io.File;
import kavabase.fileFormat.Constants;

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
        if (!isNameValid(tableName)) {
            Error.notValidTableName();
            return false;
        }
        else if (tableExists(tableName)) {
            Error.tableExistsError(tableName);
            return false;
        }
        query = query.substring(tableName.length());
        if (!query.startsWith("(")) {
            Error.syntaxError();
            return false;
        }
        return true;
    }
    
    public static void dropTable(String query) {
        //TODO
    }
    
    private static boolean tableExists(String tableName) {
        File file = new File(tableName + Constants.TABLE_FILE_EXTENSION);
        if (file.exists() && !file.isDirectory())
            return true;
        return false;
    }
    
    private static String getTableName(String query) {
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == ' ' || query.charAt(i) == '(')
                return getTableName(query.substring(0, i));
        }
        return query;
    }
    
    private static boolean isNameValid(String name) {
        if (name.isEmpty() || name.matches(".*[!@#$%^&*()+=<>?].*"))
            return false;
        return true;
    }
}
