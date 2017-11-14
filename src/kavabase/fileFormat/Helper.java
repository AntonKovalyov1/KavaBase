package kavabase.fileFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kavabase.DataFormat.DataType;

/**
 *
 * @author axk176431
 */
public class Helper {
    
    //Reserved words:
    public static final ArrayList<String> reservedWords = initReservedWords();
    
    public static final String SUCCESSFUL_QUERY = "Query succesful.";
    public static final String TABLE_FILE_EXTENSION = ".tbl";
    public static final Map<String, DataType> TEXT_TO_DATATYPE_MAP = initTextToDataTypeMap();

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
        return reservedWords.contains(s);
    }
    
        
    public static boolean isNameValid(String name) {
        return !(name.isEmpty() ||
                 name.contains("//s+") ||
                 name.matches(".*[!@#$%^&*()+=<>?.,].*") || 
                 isReservedWord(name) ||
                 Character.isDigit(name.charAt(0)));
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
    
    public static boolean isDataType(String dataType) {
        return TEXT_TO_DATATYPE_MAP.containsKey(dataType);
    }
    
    public static DataType getDataType(String dataType) {
        return TEXT_TO_DATATYPE_MAP.get(dataType);
    }
}
