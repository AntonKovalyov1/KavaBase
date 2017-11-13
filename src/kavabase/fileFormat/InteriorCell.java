package kavabase.fileFormat;

/**
 *
 * @author Anton
 */
public class InteriorCell {
    
    public static short LEFT_CHILD_POINTER_OFFSET = 0;
    public static short KEY_DELIMITER_OFFSET = 4;
    
    private final short cellOffset;
    private final int leftChildPointer;
    private final int keyDelimiter;
    
    public InteriorCell(final short cellOffset, 
                        final int leftChildPointer, 
                        final int keyDelimiter) {
        this.cellOffset = cellOffset;
        this.leftChildPointer = leftChildPointer;
        this.keyDelimiter = keyDelimiter;
    }

    /**
     * @return the cellOffset
     */
    public short getCellOffset() {
        return cellOffset;
    }

    /**
     * @return the leftChildPointer
     */
    public int getLeftChildPointer() {
        return leftChildPointer;
    }

    /**
     * @return the keyDelimiter
     */
    public int getKeyDelimiter() {
        return keyDelimiter;
    }
}
