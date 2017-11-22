package kavabase.Query;

import kavabase.DataFormat.Operator;

/**
 *
 * @author Anton
 */
public abstract class Comparison {
    
    private final int columnIndex;
    private final Operator operator;
    
    public Comparison(final int columnIndex, final Operator operator) {
        this.columnIndex = columnIndex;
        this.operator = operator;
    }

    /**
     * @return the columnIndex
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }
    
    public static class NumberComparison extends Comparison {

        private final double number;
        
        public NumberComparison(final int columnIndex, 
                                final double number, 
                                final Operator operator) {
            super(columnIndex, operator);
            this.number = number;
        }

        public double getNumber() {
            return number;
        }
    }
    
    public static class TextComparison extends Comparison {

        private final String text;
        
        public TextComparison(final int columnIndex, 
                                final String text, 
                                final Operator operator) {
            super(columnIndex, operator);
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
    
}
