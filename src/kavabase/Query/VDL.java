package kavabase.Query;

import java.io.IOException;
import java.util.ArrayList;
import kavabase.DataFormat.DataType;
import kavabase.DataFormat.DataType.TinyInt;
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
        if (tokens.length != 3 && tokens.length != 7 && tokens.length != 8) {
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
        if (tokens.length == 3) {
            try {
                FileOperations.selectAll(table);
            } 
            catch (IOException ex) {
                System.out.println("IOException, table not selected.");
            }
            return;
        }
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
        //special check for the "IS NOT NULL" operator
        if (tokens.length == 8) {
            if (!(tokens[5].toLowerCase().equals("is") &&
                  tokens[6].toLowerCase().equals("not") &&
                  tokens[7].toLowerCase().equals("null"))) {
                Error.notValidInput(tokens[5] + " " + tokens[6] + " " 
                        + tokens[7]);
                return;
            }
            Comparison comparison = new Comparison(index, new TinyInt(), 
                    Operator.IS_NOT_NULL);
            try {
                FileOperations.selectAll(table, comparison);
            } 
            catch (IOException ex) {
                System.out.println("IOException is thrown during is "
                        + "null selection.");
            }
            return;            
        }
        //special check for the "IS NULL" operator
        if (tokens[5].toLowerCase().equals("is")) {
            if (!tokens[6].toLowerCase().equals("null")) {
                Error.notValidOperator("is " + tokens[6]);
                return;
            }
            Comparison comparison = new Comparison(index, new TinyInt(), 
                    Operator.IS_NULL);
                            try {
            FileOperations.selectAll(table, comparison);
            } 
            catch (IOException ex) {
                System.out.println("IOException is thrown during is "
                        + "null selection.");
            }
            return;
        }
        Operator operator = Operator.parseComparison(tokens[5]);
        if (!operator.isValid()) {
            Error.notValidOperator(tokens[5]);
            return;
        }
        if (!DML.validateColumnInput(tokens[6], column)) {
            Error.notValidInput(tokens[6]);
            return;
        }
        DataType input = DML.getDataType(tokens[6], column.getDataType());
        Comparison comparison = new Comparison(index, input, operator);
        try {
            FileOperations.selectAll(table, comparison);
        } 
        catch (IOException ex) {
            System.out.println("IOException is thrown.");
        }
    }

    public static void exit() {
        System.exit(0);
    }
}
