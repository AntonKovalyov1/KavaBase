package kavabase.fileFormat;

/**
 *
 * @author Anton
 */
public class KeyPointer {
    
    private final int key;
    private final int leftPointer;
    
    public KeyPointer(final int key, final int leftPointer) {
        this.key = key;
        this.leftPointer = leftPointer;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * @return the leftPointer
     */
    public int getLeftPointer() {
        return leftPointer;
    }
}
