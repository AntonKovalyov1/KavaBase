package kavabase.Query;

/**
 *
 * @author axk176431
 */
public class Error {
    
    public static void tableExistsError(String tableName) {
        System.out.println("Error: table " + tableName + " already exists.");
    }
    
    public static void notValidTableName() {
        System.out.println("Error: not a valid table name.");
    }
    
    public static void syntaxError() {
        System.out.println("Error: syntax error.");
    }
    
    public static void noPrimaryKeyError() {
        System.out.println("Error: no primary key declared.");
    }
    
    public static void columnDefinitionError() {
        System.out.println("Error: check your column definition.");
    }
    
    public static void tableDoesNotExistError(String tableName) {
        System.out.println("Error: table " + tableName + " does not exist.");
    }
    
    public static void notNullError(String input) {
        System.out.println("Error: " + input + " cannot be null.");
    }
    
    public static void reservedTableNameError(String tableName) {
        System.out.println("Error: " + tableName + " is a reserved table.");
    }
}
