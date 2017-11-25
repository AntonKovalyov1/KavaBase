package kavabase.Query;

import java.io.IOException;
import java.util.ArrayList;
import kavabase.Commons.Helper;
import kavabase.DataFormat.DataType;
import kavabase.DataFormat.Operator;
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
        if (!"*".equals(tokens[0].toLowerCase()) || 
            !"from".equals(tokens[1].toLowerCase())) {
            Error.syntaxError();
            return;
        }
        String tableName = tokens[2];
        int index = metaData.indexOf(new TableMetaData(tableName));
        if (index == -1) {
            Error.tableDoesNotExistError(tableName);
            return;
        }
        TableMetaData table = metaData.get(index);
        switch (tokens.length) {
            case 3:
                try {
                    FileOperations.selectAll(table);
                } catch (IOException ex) {
                    System.out.println("IOException, table not selected.");
                }
                break;
            case 7:
                if (!tokens[3].toLowerCase().equals("where")) {
                    Error.syntaxError();
                    return;
                }
                ArrayList<String> columnNames = table.getColumnNames();
                index = columnNames.indexOf(tokens[4].toLowerCase());
                if (index == -1) {
                    Error.columnDoesNotExist(tokens[4]);
                    return;
                }
                Column column = table.getColumns().get(index);
                Operator operator = Operator.parseComparison(tokens[5]);
                if (!operator.isValid()) {
                    Error.notValidOperator(tokens[5]);
                    return;
                }
                if (!DML.validateColumnInput(tokens[6], column)) {
                    Error.notValidInput(tokens[6]);
                    return;
                }
                DataType input = DML.getDataType(tokens[6], 
                        column.getDataType());
                Comparison comparison = new Comparison(index,
                        input,
                        operator);
                try {
                    FileOperations.selectAll(table, comparison);
                } 
                catch (IOException ex) {
                    System.out.println("IOException is thrown.");
                }
                break;
            default:
                Error.syntaxError();
                break;
        }
    }

    public static void exit() {
        System.exit(0);
    }
}
