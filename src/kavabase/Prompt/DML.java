package kavabase.Prompt;

import kavabase.fileFormat.Table;

/**
 *
 * @author axk176431
 */
public class DML {
    
    public static void insertInto(String query) {
        //TODO
    }
    
    public static void deleteFrom(String query) {
        //TODO
    }
    
    public static void update(String query) {
        //TODO
    }
    
    public static Table getTable(String name) {
        Table table = new Table(name);
        //TODO get table metadata
        return table;
    }
}
