package kavabase.Prompt;

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
}
