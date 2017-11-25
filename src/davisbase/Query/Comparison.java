package davisbase.Query;

import davisbase.DataFormat.DataType;
import davisbase.DataFormat.Operator;

/**
 *
 * @author Anton
 */
public class Comparison {
    
    private final int columnIndex;
    private final DataType input;
    private final Operator operator;
    
    public Comparison(final int columnIndex, 
                      final DataType input, 
                      final Operator operator) {
        this.columnIndex = columnIndex;
        this.input = input;
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

    /**
     * @return the input
     */
    public DataType getInput() {
        return input;
    }
}
