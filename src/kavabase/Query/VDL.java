package kavabase.Query;

import java.io.IOException;
import java.util.ArrayList;
import kavabase.Commons.Helper;
import kavabase.DataFormat.Operator;
import kavabase.Query.Comparison.NumberComparison;
import kavabase.Query.Comparison.TextComparison;
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
        TableMetaData table = metaData.get(index);
        switch (tokens.length) {
            case 3:
                try {
                    FileOperations.selectAll(table);
                } catch (IOException ex) {
                    System.out.println("IOException, table not selected.");
                }   break;
            case 7:
                if (!tokens[3].equals("where")) {
                    Error.syntaxError();
                    return;
                }   ArrayList<String> columnNames = table.getColumnNames();
                for (int i = 0; i < columnNames.size(); i++) {
                    System.out.print(columnNames.get(i) + " ");
                }   index = columnNames.indexOf(tokens[4]);
                if (index == -1) {
                    Error.columnDoesNotExist(tokens[4]);
                    return;
                }   Column column = table.getColumns().get(index);
                Operator operator = Operator.parseComparison(tokens[5]);
                if (!operator.isValid()) {
                    Error.notValidOperator(tokens[5]);
                    return;
                }   
                if (Helper.isColumnNumeric(column)) {
                    try {
                        double input = Double.parseDouble(tokens[6]);
                        Comparison comparison = new NumberComparison(index,
                                input,
                                operator);
                        FileOperations.selectAll(table, comparison);
                    } 
                    catch (NumberFormatException ex) {
                        Error.notValidInput(tokens[6]);
                    } 
                    catch (IOException ex) {
                        System.out.println("IOException is thrown.");
                    }
                }
                if (Helper.isColumnText(column)) {
                    if (!Helper.validateTextInput(tokens[6])) {
                        Error.notValidInput(tokens[6]);
                        return;
                    }
                    String input = tokens[6].
                            substring(1, tokens[6].length() - 1);
                    Comparison comparison = new TextComparison(index, 
                                                               input, 
                                                               operator);
                    try {
                        FileOperations.selectAll(table, comparison);
                    }
                    catch (IOException ex) {
                        System.out.println("IOException is thrown.");
                    }
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
