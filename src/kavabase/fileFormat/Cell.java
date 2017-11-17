package kavabase.fileFormat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anton
 */

public abstract class Cell {
    
    public abstract short getSize();
    
    public static class InteriorCell extends Cell {
        private final KeyPointer keyPointer;
        
        public InteriorCell(final KeyPointer keyPointer) {
            this.keyPointer = keyPointer;
        }
        
        public KeyPointer getKeyPointer() {
            return keyPointer;
        }
        
        @Override
        public short getSize() {
            return 8;
        }
    }
    
    public static class LeafCell extends Cell {

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

        @Override
        public short getSize() {
            return (short)(getPayloadSize() + 6);
        }
    }
}
