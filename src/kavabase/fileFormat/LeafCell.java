package kavabase.fileFormat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anton
 */
public class LeafCell {
        
    public static final short LEAF_CELL_HEADER_SIZE = 6;
    public static final short PAYLOAD_SIZE_OFFSET = 0;
    public static final short KEY_OFFSET = 2;
    public static final short PAYLOAD_OFFSET = 6;
    public static final short DATA_OFFSET = 7;

    private final List<Column> record;

    public LeafCell(final ArrayList<Column> records) {
        this.record = records;
    }

    /**
     * @return the payloadSize
     */
    public short getPayloadSize() {
        short payLoadSize = (short)(1 + getNumberOfColumns());
        for (Column column : record) {
            payLoadSize += column.getDataType().getContentSize();
        }
        return payLoadSize;
    }

    /**
     * @return the key
     */
    public int getKey() {
        return (int)record.get(0).getDataType().getData();
    }

    /**
     * @return the numberOfColumns
     */
    public byte getNumberOfColumns() {
        return (byte)record.size();
    }

    /**
     * @return the records
     */
    public List<Column> getRecord() {
        return record;
    }
}
