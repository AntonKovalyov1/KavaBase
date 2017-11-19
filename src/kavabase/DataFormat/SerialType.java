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
    };
    
    public abstract byte getCode();
    public abstract byte getContentSize();
    @Override
    public abstract String toString();
}
