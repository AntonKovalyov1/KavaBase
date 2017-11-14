package kavabase.Prompt;

/**
 *
 * @author axk176431
 */
public class QueryExecutor {
    
    public void execute(String query) {
        if (isShowTablesCommand(query)) {
            DDL.showTables();
        }
        else if (isCreateTableCommand(query)) {
            DDL.createTable(query.substring(Command.CREATE_TABLE.length()).trim());
        }
        else if (isDropTableCommand(query)) {
            DDL.dropTable(query.substring(Command.DROP_TABLE.length()).trim());
        }
        else if (isInsertIntoCommand(query)) {
            DML.insertInto(query.substring(Command.INSERT_INTO.length()).trim());
        }
        else if (isDeleteFromCommand(query)) {
            DML.deleteFrom(query.substring(Command.DELETE_FROM.length()).trim());
        }                                
        else if (isUpdateCommand(query)) {
            DML.update(query.substring(Command.UPDATE.length()).trim());
        }
        else if (isSelectCommand(query)) {
            VDL.select(query.substring(Command.SELECT.length()).trim());
        }
        else if (isExitCommand(query)) {
            VDL.exit();
        }
        else {
            Error.syntaxError();
        }
    }
    
    private boolean isShowTablesCommand(String query) {
        return query.equals(Command.SHOW_TABLES);
    }
    
    private boolean isCreateTableCommand(String query) {
        return query.startsWith(Command.CREATE_TABLE);
    }
    
    private boolean isDropTableCommand(String query) {
        return query.startsWith(Command.DROP_TABLE);
    }
    
    private boolean isInsertIntoCommand(String query) {
        return query.startsWith(Command.INSERT_INTO);
    }
    
    private boolean isDeleteFromCommand(String query) {
        return query.startsWith(Command.DELETE_FROM);
    }
    
    private boolean isUpdateCommand(String query) {
        return query.startsWith(Command.UPDATE);
    }
    
    private boolean isSelectCommand(String query) {
        return query.startsWith(Command.SELECT);
    }
    
    private boolean isExitCommand(String query) {
        return query.equals(Command.EXIT);
    }
}
