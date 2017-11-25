package kavabase.Commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kavabase.DataFormat.DataType;
import kavabase.DataFormat.SerialType;
import kavabase.Query.Column;
import kavabase.Query.TableMetaData;

/**
 *
 * @author axk176431
 */
public class Helper {
    
    public static final short PAGE_SIZE = 512;
    
    public static final short CELLS_START_OFFSET = 2;
    public static final short LEAF_CELL_HEADER_SIZE = 6;
    
    public static final byte INTERIOR_INDEX_PAGE_TYPE = 0x02;
    public static final byte INTERIOR_TABLE_PAGE_TYPE = 0x05;
    public static final byte LEAF_INDEX_PAGE_TYPE = 0x0a;
    public static final byte LEAF_TABLE_PAGE_TYPE = 0x0d;
    
    //Reserved words:
    public static final ArrayList<String> RESERVED_WORDS = initReservedWords();
    
    public static final String SUCCESSFUL_QUERY = "Query succesful.";
    public static final String TABLE_FILE_EXTENSION = ".tbl";
    public static final Map<String, DataType> TEXT_TO_DATATYPE_MAP = 
            initTextToDataTypeMap();

    private static Map<String, DataType> initTextToDataTypeMap() {
        Map<String, DataType> map = new HashMap<>();
        map.put("tinyint", new DataType.TinyInt());
        map.put("smallint", new DataType.SmallInt());
        map.put("int", new DataType.Int());
        map.put("bigint", new DataType.BigInt());
        map.put("real", new DataType.Real());
        map.put("double", new DataType.CustomDouble());
        map.put("datetime", new DataType.CustomDate());
        map.put("date", new DataType.CustomDateTime());
        map.put("text", new DataType.CustomText());
        return map;
    }
    
    public static boolean isReservedWord(String s) {
        return RESERVED_WORDS.contains(s.toLowerCase());
    }
    
        
    public static boolean isNameValid(String name) {
        return !(name.isEmpty() ||
                 name.contains("//s+") ||
                 name.matches(".*[!@#$%^*()+=<>?.,].*") || 
                 isReservedWord(name) ||
                 !Character.isAlphabetic(name.charAt(0)));
    }
    
    private static ArrayList<String> initReservedWords() {
        ArrayList<String> list = new ArrayList<>();
        list.add("show");
        list.add("create");
        list.add("table");
        list.add("drop");
        list.add("insert");
        list.add("into");
        list.add("delete");
        list.add("from");
        list.add("update");
        list.add("select");
        list.add("from");
        list.add("where");
        list.add("exit");
        list.add("values");
        list.add("null");
        list.add("not");
        list.add("or");
        list.add("and");
        list.add("primary");
        list.add("key");
        
        return list;
    }
    
    public static String getWord(String query) {
        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == ' ' || 
                query.charAt(i) == '(' ||
                query.charAt(i) == ',')
                return query.substring(0, i).trim();
        }
        return query;
    }
    
    public static boolean isDataType(String dataType) {
        return TEXT_TO_DATATYPE_MAP.containsKey(dataType);
    }
    
    public static DataType getDataType(String dataType) {
        return TEXT_TO_DATATYPE_MAP.get(dataType);
    }   
    
    public static boolean validateTextInput(String input) {
        return (input.startsWith("\"") && input.endsWith("\"")
                || input.startsWith("\'") && input.endsWith("\'"));
    }
      
    public static boolean reservedTableName(String tableName) {
        return tableName.equals("davisbase_tables")
                || tableName.equals("davisbase_columns");
    }
    
    public static int getNextKeyColumns(ArrayList<TableMetaData> metadata) {
        int index = metadata.size() - 1;
        if (index == -1)
            return 1;
        TableMetaData lastTable = metadata.get(index);
        int key = lastTable.getColumnsKey() + lastTable.getColumns().size();
        return key;
    }
    
    public static int getNextKeyTables(ArrayList<TableMetaData> metaData) {
        int index = metaData.size() - 1;
        if (index == -1) {
            return 1;
        }
        TableMetaData lastTable = metaData.get(index);
        int key = lastTable.getTableKey() + 1;
        return key;
    }
}
