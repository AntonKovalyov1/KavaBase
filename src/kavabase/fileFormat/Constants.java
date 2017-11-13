package kavabase.fileFormat;

import java.util.HashMap;
import java.util.Map;
import kavabase.DataFormat.DataType;

/**
 *
 * @author axk176431
 */
public class Constants {
    
    public static final String TABLE_FILE_EXTENSION = ".tbl";
    public static final Map<String, DataType> TEXT_TO_DATATYPE_MAP = initTextToDataTypeMap();

    private static Map<String, DataType> initTextToDataTypeMap() {
        Map<String, DataType> map = new HashMap<>();
        map.put("TINYINT", new DataType.TinyInt());
        map.put("SMALLINT", new DataType.SmallInt());
        map.put("INT", new DataType.Int());
        map.put("BIGINT", new DataType.BigInt());
        map.put("REAL", new DataType.Real());
        map.put("DOUBLE", new DataType.CustomDouble());
        map.put("DATETIME", new DataType.CustomDate());
        map.put("DATE", new DataType.CustomDateTime());
        map.put("TEXT", new DataType.CustomText());
        return map;
    }
}
