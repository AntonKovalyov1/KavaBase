package kavabase.Query;

import java.io.IOException;
import java.util.ArrayList;
import kavabase.fileFormat.FileOperations;

/**
 *
 * @author axk176431
 */
public class VDL {
    
    public static void select(String query, 
                              final ArrayList<TableMetaData> metaData) {
        String[] tokens = query.split(" ");
        if (tokens.length < 3) {
            Error.syntaxError();
            return;
        }
        if (!"*".equals(tokens[0]) || !"from".equals(tokens[1])) {
            Error.syntaxError();
            return;
        }
        String tableName = tokens[2];
        int index = metaData.indexOf(new TableMetaData(tableName));
        if (index == -1) {
            Error.tableDoesNotExistError(tableName);
            System.out.println(tokens[2]);
            return;
        }
        if (tokens.length == 3) {
            try {
                FileOperations.selectAll(metaData.get(index));
            }
            catch (IOException ex) {
                System.out.println("IOException, table not solected.");
            }
        }
    }
    
    public static void exit() {
        System.exit(0);
    }
}
