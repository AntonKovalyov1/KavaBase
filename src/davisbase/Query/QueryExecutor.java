package davisbase.Query;

import java.util.ArrayList;
import davisbase.fileFormat.FileOperations;

/**
 *
 * @author axk176431
 */
public class QueryExecutor {
    
    private final ArrayList<TableMetaData> metaData = initMetadata();
    
    public void execute(String query) {
        if (isShowTablesCommand(query)) {
            DDL.showTables(metaData);
        }
        else if (isCreateTableCommand(query)) {
            DDL.createTable(
                    query.substring(Command.CREATE_TABLE.length()).trim(), 
                    getMetaData());
        }
        else if (isDropTableCommand(query)) {
            DDL.dropTable(query.substring(Command.DROP_TABLE.length()).trim(),
                    getMetaData());
        }
        else if (isInsertIntoCommand(query)) {
            DML.insertInto(query.substring(Command.INSERT_INTO.length()).trim(),
                           getMetaData());
        }
        else if (isDeleteFromCommand(query)) {
            DML.deleteFrom(query.substring(Command.DELETE_FROM.length()).trim(),
                           getMetaData());
        }                                
        else if (isUpdateCommand(query)) {
            DML.update(query.substring(Command.UPDATE.length()).trim(),
                       getMetaData());
        }
        else if (isSelectCommand(query)) {
            VDL.select(query.substring(Command.SELECT.length()).trim(),
                       getMetaData());
        }
        else if (isExitCommand(query)) {
            VDL.exit();
        }
        else {
            Error.syntaxError();
        }
    }
    
    private boolean isShowTablesCommand(String query) {
        return query.toLowerCase().equals(Command.SHOW_TABLES);
    }
    
    private boolean isCreateTableCommand(String query) {
        return query.toLowerCase().startsWith(Command.CREATE_TABLE);
    }
    
    private boolean isDropTableCommand(String query) {
        return query.toLowerCase().startsWith(Command.DROP_TABLE);
    }
    
    private boolean isInsertIntoCommand(String query) {
        return query.toLowerCase().startsWith(Command.INSERT_INTO);
    }
    
    private boolean isDeleteFromCommand(String query) {
        return query.toLowerCase().startsWith(Command.DELETE_FROM);
    }
    
    private boolean isUpdateCommand(String query) {
        return query.toLowerCase().startsWith(Command.UPDATE);
    }
    
    private boolean isSelectCommand(String query) {
        return query.toLowerCase().startsWith(Command.SELECT);
    }
    
    private boolean isExitCommand(String query) {
        return query.toLowerCase().equals(Command.EXIT);
    }

    private ArrayList<TableMetaData> initMetadata() {
        if (!FileOperations.fileExists(FileOperations.TABLES_PATH) ||
            !FileOperations.fileExists(FileOperations.COLUMNS_PATH)) {
            FileOperations.createMetaData();
        }
        return FileOperations.getMetaData();
    }

    /**
     * @return the metaData
     */
    public ArrayList<TableMetaData> getMetaData() {
        return metaData;
    }
}
