package kavabase.Prompt;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import kavabase.DataFormat.DataType;
import kavabase.fileFormat.FileOperations;
import kavabase.fileFormat.Helper;

/**
 *
 * @author axk176431
 */
public class DDL {
    
    public static void showTables(
            final ArrayList<TableMetaData> metaData) {
        TableDisplay td = new TableDisplay("Tables");
        for (TableMetaData current : metaData) {
            ArrayList<DataType> row = new ArrayList<>();
            row.add(new DataType.CustomText(current.getTableName()));
            td.addRecord(row);
        }
        td.display();
    }
    
    public static boolean dropTable(String query, 
            final ArrayList<TableMetaData> metadata) {
        TableMetaData table = new TableMetaData(query);
        if (metadata.contains(table)) {
            //TODO
            System.out.println(Helper.SUCCESSFUL_QUERY);
            System.out.println("Table " + query + " dropped.");
            return true;
        }
        Error.notValidTableName();
        return false;
    }
    
    public static boolean createTable(String query, 
            final ArrayList<TableMetaData> metadata) {
        System.out.println("Metadata size is: " + metadata.size());
        String tableName = getTableName(query);
        if (!Helper.isNameValid(tableName)) {
            Error.notValidTableName();
            return false;
        }
        TableMetaData table = new TableMetaData(tableName);
        if (metadata.contains(table)) {
            Error.tableExistsError(tableName);
            return false;
        }        
        query = query.substring(tableName.length()).trim();
        if (!(query.startsWith("(") && query.endsWith(")"))) {
            Error.syntaxError();
            return false;
        }
        query = query.substring(1, query.length() - 1).trim();
        if (query.startsWith(",") || query.endsWith(",")) {
            Error.syntaxError();
            return false;
        }
        String[] columns = query.split(",");
        if (columns.length < 1) {
            Error.syntaxError();
            return false;
        }
        if (!isPrimaryKeyColumnValid(columns[0], table)) {
            Error.noPrimaryKeyError();
            return false;
        }
        
        for (int i = 1; i < columns.length; i++) {
            if (!isNonPrimaryKeyColumnValid(columns[i], table, (byte)(i + 1))) {
                Error.columnDefinitionError();
                return false;
            }
        }
        insertNewEntryInTables(tableName, metadata);
        insertEntriesInColumns(table, metadata);
        metadata.add(table);
        System.out.println("Table " + tableName + " created.");
        System.out.println(Helper.SUCCESSFUL_QUERY);
        return true;
    }
    
    private static void insertNewEntryInTables(final String tableName,
            final ArrayList<TableMetaData> metaData) {
        DataType rowid = new DataType.Int(metaData.size() + 1);
        DataType name = new DataType.CustomText(tableName);
        ArrayList<DataType> row = new ArrayList<>();
        row.add(rowid);
        row.add(name);
        try {
            RandomAccessFile raf = new RandomAccessFile(
                    FileOperations.TABLES_PATH, "rw");
            FileOperations.insert(raf, row);
            raf.close();
        }
        catch (IOException ex) {
            System.out.println("Table insertion IOEception is thrown.");
        }
    }
    
    private static void insertEntriesInColumns(final TableMetaData table, 
            final ArrayList<TableMetaData> metaData) {
        try {
            RandomAccessFile raf = new RandomAccessFile(
                    FileOperations.COLUMNS_PATH, "rw");
            ArrayList<Column> columns = table.getColumns();
            System.out.println("Columns size: " + columns.size());
            int rowid = getNumberOfRowsFromColumnsTable(metaData) + 1;
            for (Column current : columns) {
                System.out.println("Row id: " + rowid);
                ArrayList<DataType> row = new ArrayList<>();
                row.add(new DataType.Int(rowid));
                row.add(new DataType.CustomText(current.getTableName()));
                row.add(new DataType.CustomText(current.getColumnName()));
                row.add(new DataType.CustomText(current.getDataType()));
                row.add(new DataType.TinyInt(current.getOrdinalPosition()));
                row.add(new DataType.CustomText(current.isNullable()));
                rowid++;
                FileOperations.insert(raf, row);
            }
            raf.close();
        }
        catch (IOException ex) {
            System.out.println("Column insertion IOEception is thrown."); 
        }
    }
    
    private static int getNumberOfRowsFromColumnsTable(
            final ArrayList<TableMetaData> metaData) {
        int size = 0;
        for (TableMetaData current : metaData) {
            size += current.getColumns().size();
        }
        return size;
    }
    
    private static String getTableName(String query) {
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == ' ' || query.charAt(i) == '(')
                return getTableName(query.substring(0, i).trim());
        }
        return query;
    }
    
    private static boolean isPrimaryKeyColumnValid(String primaryKeyColumn, 
            TableMetaData table) {
        String[] tokens = primaryKeyColumn.trim().split("\\s+");
        if (tokens.length != 4)
            return false;
        if (Helper.isNameValid(tokens[0]) && 
               tokens[1].equals("int") &&
               tokens[2].equals("primary") &&
               tokens[3].equals("key")) {
            Column column = new Column(table.getTableName(), 
                                       tokens[0], 
                                       "INT", 
                                       (byte)1, 
                                       "NO");
            return table.addColumn(column);
        }
        return false;
    }
    
    public static boolean isNonPrimaryKeyColumnValid(String column, 
            TableMetaData table, byte ordinalPosition) {
        String[] tokens = column.trim().split("\\s+");
        if (tokens.length == 4)
            if (Helper.isNameValid(tokens[0]) && 
                Helper.isDataType(tokens[1]) &&
                tokens[2].equals("not") &&
                tokens[3].equals("null")) {
                Column c = new Column(table.getTableName(),
                                      tokens[0], 
                                      tokens[1].toUpperCase(), 
                                      ordinalPosition, 
                                      "NO");
                return table.addColumn(c);
            } 
        if (tokens.length == 2) {
            if (Helper.isNameValid(tokens[0]) && 
                Helper.isDataType(tokens[1])) {
                Column c = new Column(table.getTableName(), 
                                      tokens[0], 
                                      tokens[1].toUpperCase(), 
                                      ordinalPosition, 
                                      "YES");
                return table.addColumn(c);                
            }
        }
        return false;
    }
}
