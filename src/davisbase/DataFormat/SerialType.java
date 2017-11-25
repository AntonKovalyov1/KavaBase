package davisbase.DataFormat;

/**
 *
 * @author Anton
 */
public enum SerialType {

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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return true;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return false;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return false;
        }

        @Override
        public boolean isText() {
            return false;
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

        @Override
        public boolean isNumber() {
            return false;
        }

        @Override
        public boolean isText() {
            return true;
        }
    };

    public abstract byte getCode();

    public abstract byte getContentSize();

    @Override
    public abstract String toString();

    public abstract int getDisplayOffset(String columnName);

    public abstract boolean isNumber();

    public abstract boolean isText();

    public static SerialType getSerialTypeFromText(String text) {
        switch (text) {
            case "TINYINT":
                return SerialType.TINYINT;
            case "SMALLINT":
                return SerialType.SMALLINT;
            case "INT":
                return SerialType.INT;
            case "BIGINT":
                return SerialType.BIGINT;
            case "REAL":
                return SerialType.REAL;
            case "DOUBLE":
                return SerialType.DOUBLE;
            case "DATETIME":
                return SerialType.DATETIME;
            case "DATE":
                return SerialType.DATE;
            case "TEXT":
                return SerialType.TEXT;
            default:
                return null;
        }
    }
}
