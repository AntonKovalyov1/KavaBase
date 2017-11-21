package kavabase.DataFormat;

/**
 *
 * @author Anton
 */
public enum SerialType {
//    
//    public static final byte NULL_1 = 0x00;
//    public static final byte NULL_2 = 0x01;
//    public static final byte NULL_4 = 0X02;
//    public static final byte NULL_8 = 0x03;
//    public static final byte TINYINT = 0x04;
//    public static final byte SMALLINT = 0x05;
//    public static final byte INT = 0x06;
//    public static final byte BIGINT = 0x07;
//    public static final byte REAL = 0x08;
//    public static final byte DOUBLE = 0x09;
//    public static final byte DATETIME = 0x0A;
//    public static final byte DATE = 0x0B;
//    public static final byte TEXT = 0x0C;
    
    NULL_1 {
        @Override
        public byte getCode() {
            return 0x00;
        }

        @Override
        public byte getContentSize() {
            return 1;
        }

        @Override
        public String toString() {
            return "NULL_1";
        }

        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(5, columnName.length());
        }
    },
    NULL_2 {
        @Override
        public byte getCode() {
            return 0x01;
        }

        @Override
        public byte getContentSize() {
            return 2;
        }

        @Override
        public String toString() {
            return "NULL_2";
        }
        
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(5, columnName.length());
        }
    },
    NULL_4 {
        @Override
        public byte getCode() {
            return 0x02;
        }

        @Override
        public byte getContentSize() {
            return 4;
        }

        @Override
        public String toString() {
            return "NULL_4";
        }
        
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(5, columnName.length());
        }
    },
    NULL_8 {
        @Override
        public byte getCode() {
            return 0x03;
        }

        @Override
        public byte getContentSize() {
            return 8;
        }

        @Override
        public String toString() {
            return "NULL_8";
        }
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(5, columnName.length());
        }
    },
    TINYINT {
        @Override
        public byte getCode() {
            return 0x04;
        }

        @Override
        public byte getContentSize() {
            return 1;
        }
        
        @Override
        public String toString() {
            return "TINYINT";
        }
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(4, columnName.length());
        }
    },
    SMALLINT {
        @Override
        public byte getCode() {
            return 0x05;
        }

        @Override
        public byte getContentSize() {
            return 2;
        }
        @Override
        public String toString() {
            return "SMALLINT";
        }
        
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(6, columnName.length());
        }
    },
    INT {
        @Override
        public byte getCode() {
            return 0x06;
        }

        @Override
        public byte getContentSize() {
            return 4;
        }
        @Override
        public String toString() {
            return "INT";
        }
        
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(11, columnName.length());
        }
    },
    BIGINT {
        @Override
        public byte getCode() {
            return 0x07;
        }

        @Override
        public byte getContentSize() {
            return 8;
        }
        
        @Override
        public String toString() {
            return "BIGINT";
        }
        
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(20, columnName.length());
        }
    },
    REAL {
        @Override
        public byte getCode() {
            return 0x08;
        }

        @Override
        public byte getContentSize() {
            return 4;
        }
        
        @Override
        public String toString() {
            return "REAL";
        }
        
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(11, columnName.length());
        }
    },
    DOUBLE {
        @Override
        public byte getCode() {
            return 0x09;
        }

        @Override
        public byte getContentSize() {
            return 8;
        }
        
        @Override
        public String toString() {
            return "DOUBLE";
        }
        
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(21, columnName.length());
        }
    },
    DATETIME {
        @Override
        public byte getCode() {
            return 0x0A;
        }

        @Override
        public byte getContentSize() {
            return 8;
        }
        
        @Override
        public String toString() {
            return "DATETIME";
        }
        
        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(20, columnName.length());
        }
    },
    DATE {
        @Override
        public byte getCode() {
            return 0x0B;
        }

        @Override
        public byte getContentSize() {
            return 8;
        }
        
        @Override
        public String toString() {
            return "DATE";
        }

        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(11, columnName.length());
        }
    },
    TEXT {
        @Override
        public byte getCode() {
            return 0x0C;
        }

        @Override
        public byte getContentSize() {
            return 1;
        }
        
        @Override
        public String toString() {
            return "TEXT";
        }

        @Override
        public int getDisplayOffset(String columnName) {
            return Integer.max(30, columnName.length());
        }
    };
    
    public abstract byte getCode();
    public abstract byte getContentSize();
    @Override
    public abstract String toString();
    public abstract int getDisplayOffset(String columnName);
    
    public SerialType getSerialTypeFromText(String text) {
        switch(text) {
            case "TINYINT" : return SerialType.TINYINT;
            case "SMALLINT" : return SerialType.SMALLINT;
            case "INT" : return SerialType.INT;
            case "BIGINT" : return SerialType.BIGINT;
            case "REAL" : return SerialType.REAL;
            case "DOUBLE" : return SerialType.DOUBLE;
            case "DATETIME" : return SerialType.DATETIME;
            case "DATE" : return SerialType.DATE;
            case "TEXT" : return SerialType.TEXT;
            default: return null;
        }
    }
}
