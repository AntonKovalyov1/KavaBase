package kavabase.Query;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import kavabase.DataFormat.DataType;
import kavabase.DataFormat.DataType.BigInt;
import kavabase.DataFormat.DataType.CustomDouble;
import kavabase.DataFormat.DataType.CustomText;
import kavabase.DataFormat.DataType.Int;
import kavabase.DataFormat.DataType.Real;
import kavabase.DataFormat.DataType.SmallInt;
import kavabase.DataFormat.DataType.TinyInt;
import kavabase.fileFormat.FileOperations;
import kavabase.Commons.Helper;
import kavabase.DataFormat.Operator;

/**
 *
 * @author axk176431
 */
public class DML {

    public static boolean insertInto(String query,
            final ArrayList<TableMetaData> metaData) {
        String tableName = Helper.getWord(query);
        int index = metaData.indexOf(new TableMetaData(tableName));
        if (index == -1) {
            Error.tableDoesNotExistError(tableName);
            return false;
        }
        if (Helper.reservedTableName(tableName)) {
            Error.reservedTableNameError(tableName);
            return false;
        }
        query = query.substring(tableName.length()).trim();
        TableMetaData table = metaData.get(index);
        if (query.startsWith("values")) {
            query = query.substring("values".length()).trim();
            if (query.length() < 3
                    || !query.startsWith("(") && !query.endsWith(")")) {
                Error.syntaxError();
                return false;
            }
            query = query.substring(1, query.length() - 1).trim();
            if (query.startsWith(",") || query.endsWith(",")) {
                Error.syntaxError();
                return false;
            }
            String[] tokens = query.split("\\s*,\\s*");
            if (tokens == null) {
                return false;
            }
            ArrayList<Column> columns = table.getColumns();
            if (tokens.length > table.getColumns().size()) {
                Error.columnDefinitionError();
                return false;
            }
            //validate column input
            for (int i = 0; i < tokens.length; i++) {
                if (!validateColumnInput(tokens[i], columns.get(i))) {
                    Error.columnDefinitionError();
                    return false;
                }
            }
            //Get data
            ArrayList<DataType> row = new ArrayList<>();
            for (int i = 0; i < tokens.length; i++) {
                row.add(getDataType(tokens[i], columns.get(i).getDataType()));
            }
            //if columns size > tokens size add null rows if null accepted
            for (int i = tokens.length; i < columns.size(); i++) {
                if (columns.get(i).isNullable().equals("NO")) {
                    Error.notNullError(columns.get(i).getColumnName());
                    return false;
                }
                row.add(getDataType("null", columns.get(i).getDataType()));
            }
            if (!insert(row, tableName)) {
                System.out.println("Record exists.");
                System.out.println("Insertion unsuccesful.");
                return false;
            }
            System.out.println("Insertion succesful.");
            return true;
        }
        if (!query.startsWith("(") || !query.endsWith(")")) {
            Error.syntaxError();
            return false;
        }
        query = query.substring(1, query.length() - 1);
        String[] enclosedValues = query.split("\\s*\\)\\s*values\\s*\\(\\s*");
        if (enclosedValues.length != 2) {
            Error.syntaxError();
            return false;
        }
        String[] columnTokens = enclosedValues[0].split("\\s*,\\s*");
        String[] valueTokens = enclosedValues[1].split("\\s*,\\s*");
        if (columnTokens.length != valueTokens.length) {
            Error.columnDefinitionError();
            return false;
        }
        ArrayList<String> columnNames = table.getColumnNames();
        for (int i = 0; i < columnTokens.length; i++) {
            if (!columnNames.contains(columnTokens[i])) {
                Error.columnDoesNotExist(columnTokens[i]);
                return false;
            }
        }
        HashMap<String, String> userInput = new HashMap<>();
        for (int i = 0; i < columnTokens.length; i++) {
            userInput.put(columnTokens[i], valueTokens[i]);
        }
        ArrayList<DataType> row = new ArrayList<>();
        ArrayList<Column> columns = table.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            String input = userInput.get(columnNames.get(i));
            if (input == null) {
                if (columns.get(i).isNullable().equals("NO")) {
                    Error.notNullError(columnNames.get(i));
                    return false;
                }
                row.add(getDataType("null", columns.get(i).getDataType()));
            } 
            else {
                if (!validateColumnInput(input, columns.get(i))) {
                    Error.columnDefinitionError();
                    return false;
                }
                row.add(getDataType(input, columns.get(i).getDataType()));
            }
        }
        if (!insert(row, tableName)) {
            System.out.println("Record exists.");
            System.out.println("Insertion unsuccesful.");
            return false;
        }
        System.out.println("Insertion succesful.");
        return true;
    }

    public static void deleteFrom(String query,
            final ArrayList<TableMetaData> metaData) {
        String[] tokens = query.split("\\s+");
        if (tokens.length != 5) {
            Error.syntaxError();
            return;
        }
        int index = metaData.indexOf(new TableMetaData(tokens[0]));
        if (index == -1) {
            Error.tableDoesNotExistError(tokens[0]);
            return;
        }
        TableMetaData table = metaData.get(index);
        if (Helper.reservedTableName(table.getTableName())) {
            Error.reservedTableNameError(table.getTableName());
            return;
        }
        if (!tokens[1].equals("where")) {
            Error.syntaxError();
            return;
        }
        if (!table.getColumns().get(0).getColumnName().equals(tokens[2])) {
            Error.columnDoesNotExist(tokens[2]);
            return;
        }
        Operator operator = Operator.parseComparison(tokens[3]);
        if (operator != Operator.EQUAL) {
            Error.notValidOperator(tokens[3]);
            return;
        }
        if (!validateIntInput(tokens[4])) {
            Error.notValidInput(tokens[4]);
            System.out.println("Integer expected.");
            return;
        }
        int key = getInt(tokens[4]).getData();
        try {
            RandomAccessFile raf = new RandomAccessFile(
                    FileOperations.getPathToTable(table.getTableName()), "rw");
            if (FileOperations.delete(raf, key)) {
                System.out.println("Deletion succesful.");
            }
            else {
                System.out.println("Record does not exist.");
            }
        }
        catch (IOException ex) {
            System.out.println("IOException is thrown when deleting.");
        }
    }

    public static void update(String query,
            final ArrayList<TableMetaData> metaData) {
        //TODO
    }

    public static boolean validateColumnInput(String input, Column column) {
        // check for null
        if (input.equals("null")) {
            if (column.isNullable().equals("YES")) {
                return true;
            }
            Error.notNullError(column.getColumnName());
            return false;
        }
        boolean result;
        switch (column.getDataType()) {
            case "TINYINT":
                result = validateTinyIntInput(input);
                break;
            case "SMALLINT":
                result = validateSmallIntInput(input);
                break;
            case "INT":
                result = validateIntInput(input);
                break;
            case "BIGINT":
                result = validateBigIntInput(input);
                break;
            case "REAL":
                result = validateRealInput(input);
                break;
            case "DOUBLE":
                result = validateDoubleInput(input);
                break;
            case "DATETIME":
                result = validateDateTimeInput(input);
                break;
            case "DATE":
                result = validateDateInput(input);
                break;
            case "TEXT":
                result = validateTextInput(input);
                break;
            default:
                result = false;
        }
        return result;
    }

    public static boolean validateTinyIntInput(String input) {
        try {
            Byte.parseByte(input);
        } catch (NumberFormatException ex) {
            System.out.println("Expected a TINYINT instead of " + input);
            return false;
        }
        return true;
    }

    private static boolean validateSmallIntInput(String input) {
        try {
            Short.parseShort(input);
        } catch (NumberFormatException ex) {
            System.out.println("Expected a SMALLINT instead of " + input);
            return false;
        }
        return true;
    }

    private static boolean validateIntInput(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("Expected an INT instead of " + input);
            return false;
        }
        return true;
    }

    private static boolean validateBigIntInput(String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException ex) {
            System.out.println("Expected a BIGINT instead of " + input);
            return false;
        }
        return true;
    }

    private static boolean validateRealInput(String input) {
        try {
            Float.parseFloat(input);
        } catch (NumberFormatException ex) {
            System.out.println("Expected a REAL instead of " + input);
            return false;
        }
        return true;
    }

    private static boolean validateDoubleInput(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            System.out.println("Expected a DOUBLE instead of " + input);
            return false;
        }
        return true;
    }

    private static boolean validateDateTimeInput(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static boolean validateDateInput(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static boolean validateTextInput(String input) {
        return (input.startsWith("\"") && input.endsWith("\"")
                || input.startsWith("\'") && input.endsWith("\'"));
    }

    public static DataType getDataType(String input, String column) {
        DataType result;
        switch (column) {
            case "TINYINT":
                result = getTinyInt(input);
                break;
            case "SMALLINT":
                result = getSmallInt(input);
                break;
            case "INT":
                result = getInt(input);
                break;
            case "BIGINT":
                result = getBigInt(input);
                break;
            case "REAL":
                result = getReal(input);
                break;
            case "DOUBLE":
                result = getCustomDouble(input);
                break;
            case "DATETIME":
                result = getDateTime(input);
                break;
            case "DATE":
                result = getDate(input);
                break;
            default:
                result = getText(input);
        }
        return result;
    }

    private static TinyInt getTinyInt(String input) {
        return input.equals("null")
                ? new TinyInt() : new TinyInt(Byte.parseByte(input));
    }

    private static SmallInt getSmallInt(String input) {
        return input.equals("null")
                ? new SmallInt() : new SmallInt(Short.parseShort(input));
    }

    private static Int getInt(String input) {
        return input.equals("null")
                ? new Int() : new Int(Integer.parseInt(input));
    }

    private static BigInt getBigInt(String input) {
        return input.equals("null")
                ? new BigInt() : new BigInt(Long.parseLong(input));
    }

    private static Real getReal(String input) {
        return input.equals("null")
                ? new Real() : new Real(Float.parseFloat(input));
    }

    private static CustomDouble getCustomDouble(String input) {
        return input.equals("null") ? new CustomDouble()
                : new CustomDouble(Double.parseDouble(input));
    }

    private static DataType getDateTime(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static DataType getDate(String input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static CustomText getText(String input) {
        return input.equals("null") ? new CustomText()
                : new CustomText(input.substring(1, input.length() - 1));
    }

    private static boolean insert(ArrayList<DataType> row, String tableName) {
        try {
            RandomAccessFile raf = new RandomAccessFile(
                    FileOperations.USER_DATA_PATH + tableName
                    + FileOperations.TABLE_EXTENSION, "rw");
            boolean insert = FileOperations.insert(raf, row);
            raf.close();
            return insert;
        } catch (IOException ex) {
            System.out.println("IOException for insertion.");
            return false;
        }
    }
}
