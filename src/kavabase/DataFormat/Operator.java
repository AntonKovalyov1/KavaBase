package kavabase.DataFormat;

/**
 *
 * @author Anton
 */
public enum Operator {
    
    EQUAL {
        @Override
        public byte getCode() {
            return 0;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public String toString() {
            return "=";
        }

        @Override
        public boolean compare(DataType a, DataType b) {
            return a.compareTo(a.getClass().cast(b).getData()) == 0;
        }
    },
    LESS {
        @Override
        public byte getCode() {
            return 1;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public String toString() {
            return "<";
        }

        @Override
        public boolean compare(DataType a, DataType b) {
            return a.compareTo(a.getClass().cast(b).getData()) == -1;
        }
    },
    LESS_OR_EQUAL {
        @Override
        public byte getCode() {
            return 2;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public String toString() {
            return "<=";
        }

        @Override
        public boolean compare(DataType a, DataType b) {
            return a.compareTo(a.getClass().cast(b).getData()) <= 0;
        }
    },
    GREATER {
        @Override
        public byte getCode() {
            return 3;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public String toString() {
            return ">";
        }

        @Override
        public boolean compare(DataType a, DataType b) {
            return a.compareTo(a.getClass().cast(b).getData()) > 0;
        }
    },
    GREATER_OR_EQUAL {
        @Override
        public byte getCode() {
            return 4;
        }

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public String toString() {
            return ">=";
        }

        @Override
        public boolean compare(DataType a, DataType b) {
            return a.compareTo(a.getClass().cast(b).getData()) >= 0;
        }
    },
    NOT_VALID {
        @Override
        public byte getCode() {
            return -1;
        }

        @Override
        public boolean isValid() {
            return false;
        }

        @Override
        public String toString() {
            return "not valid";
        }

        @Override
        public boolean compare(DataType a, DataType b) {
            throw new UnsupportedOperationException("Not a valid operator.");
        }
    };
    
    public abstract byte getCode();
    public abstract boolean isValid();
    public abstract boolean compare(DataType a, DataType b);
    
    @Override
    public abstract String toString();
    
    public static Operator parseComparison(String comparison) {
        switch(comparison) {
            case "=": return EQUAL;
            case "<": return LESS;
            case "<=": return LESS_OR_EQUAL;
            case ">": return GREATER;
            case ">=": return GREATER_OR_EQUAL;
            default: return NOT_VALID;
        }
    }
    
}
