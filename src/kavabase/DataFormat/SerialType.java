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
    };
    
    public abstract byte getCode();
    public abstract byte getContentSize();
}
