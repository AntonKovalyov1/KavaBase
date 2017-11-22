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
        public boolean compare(double a, double b) {
            return a == b;
        }
        
        @Override
        public boolean compare(String s1, String s2) {
            return s1.equals(s2);
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
        public boolean compare(double a, double b) {
            return a < b;
        }

        @Override
        public boolean compare(String s1, String s2) {
            return s1.compareTo(s2) < 0;
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
        public boolean compare(double a, double b) {
            return a <= b;
        }

        @Override
        public boolean compare(String s1, String s2) {
            return s1.compareTo(s2) <= 0;
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
        public boolean compare(double a, double b) {
            return a > b;
        }

        @Override
        public boolean compare(String s1, String s2) {
            return s1.compareTo(s2) > 0;
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
        public boolean compare(double a, double b) {
            return a >= b;
        }

        @Override
        public boolean compare(String s1, String s2) {
            return s1.compareTo(s2) >= 0;
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
        public boolean compare(double a, double b) {
            throw new UnsupportedOperationException("Not a valid operator.");
        }

        @Override
        public boolean compare(String s1, String s2) {
            throw new UnsupportedOperationException("Not a valid operator.");
        }
    };
    
    public abstract byte getCode();
    public abstract boolean isValid();
    public abstract boolean compare(double a, double b);
    public abstract boolean compare(String s1, String s2);
    
    @Override
    public abstract String toString();
    
    public Operator parseComparison(String comparison) {
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
