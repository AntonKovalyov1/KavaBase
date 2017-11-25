package davisbase.fileFormat;

import java.util.ArrayList;
import davisbase.DataFormat.DataType;

/**
 *
 * @author Anton
 */

public abstract class Cell {
    
    public abstract short getSize();
    public abstract int getKey();
   
    public static class InteriorCell extends Cell {
        
        private int leftChildPointer;
        private final int key;
        
        public InteriorCell(final int leftChildPointer, 
                            final int key) {
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
        @Override
        public int getKey() {
            return key;
        }

        /**
         * @param leftChildPointer the leftChildPointer to set
         */
        public void setLeftChildPointer(int leftChildPointer) {
            this.leftChildPointer = leftChildPointer;
        }
    }
    
    public static class LeafCell extends Cell {

        private final ArrayList<DataType> records;

        public LeafCell(final ArrayList<DataType> records) {
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
        @Override
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
