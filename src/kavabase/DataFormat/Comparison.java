package kavabase.DataFormat;

/**
 *
 * @author Anton
 */
public enum Comparison {
    
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
    };
    
    public abstract byte getCode();
    public abstract boolean isValid();
    @Override
    public abstract String toString();
    
    public Comparison parseComparison(String comparison) {
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
