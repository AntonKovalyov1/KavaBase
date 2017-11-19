package kavabase.fileFormat;

import java.util.ArrayList;
import kavabase.DataFormat.DataType;

/**
 *
 * @author Anton
 */

public abstract class Cell {
    
    public abstract short getSize();
    
    private short offset;
    
    public Cell(short offset) {
        this.offset = offset;
    }

    /**
     * @return the offset
     */
    public short getOffset() {
        return (short)(offset - getSize());
    }
    
    public static class InteriorCell extends Cell {
        
        private final int leftChildPointer;
        private final int key;
        
        public InteriorCell(final int leftChildPointer, 
                            final int key, 
                            final short offset) {
            super(offset);
            this.leftChildPointer = leftChildPointer;
            this.key = key;
        }
        
        @Override
        public short getSize() {
            return 8;
        }

        /**
         * @return the leftChildPointer
         */
        public int getLeftChildPointer() {
            return leftChildPointer;
        }

        /**
         * @return the key
         */
        public int getKey() {
            return key;
        }
    }
    
    public static class LeafCell extends Cell {

        private final ArrayList<DataType> records;

        public LeafCell(final ArrayList<DataType> records, final short offset) {
            super(offset);
            this.records = records;
        }

        /**
         * @return the payloadSize
         */
        public short getPayloadSize() {
            int payLoadSize = 1 + getNumberOfRecords();
            for (DataType record : records) {
                payLoadSize += record.getContentSize();
            }
            return (short)payLoadSize;
        }

        /**
         * @return the key
         */
        public int getKey() {
            return (int)records.get(0).getData();
        }

        /**
         * @return the numberOfRecords
         */
        public byte getNumberOfRecords() {
            return (byte)records.size();
        }

        /**
         * @return the records
         */
        public ArrayList<DataType> getRecords() {
            return records;
        }

        @Override
        public short getSize() {
            return (short)(getPayloadSize() + 6);
        }
    }
}
