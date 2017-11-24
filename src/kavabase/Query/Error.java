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
        System.out.println("Error: column " + input + " cannot be null.");
    }
    
    public static void reservedTableNameError(String tableName) {
        System.out.println("Error: " + tableName + " is a reserved table.");
    }
    
    public static void columnDoesNotExist(String column) {
        System.out.println("Error: column " + column + " does not exist.");
    }
    
    public static void notValidOperator(String operator) {
        System.out.println("Error: " + operator + " is not a valid operator.");
    }
    
    public static void notValidInput(String input) {
        System.out.println("Error: " + input + " is not a valid input.");
    }
    
    public static void keyUpdateNotSpecified() {
        System.out.println("Error: when updating a key value the key to update "
                + "must be specified with equals operator.");
    }
    
}
