package davisbase.DataFormat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Anton
 * @param <T>
 */
public abstract class DataType<T> implements Comparable<T> {
    
    private final T data;
    private final SerialType serialType;
    private final boolean isNull;
    
    public DataType(T data, SerialType serialType, boolean isNull) {
        this.data = data;
        this.serialType = serialType;
        this.isNull = isNull;
    }
    
    public T getData() {
        return data;
    }
    
    public byte getSerialTypeCode() {
        return serialType.getCode();
    }    
    
    public byte getContentSize() {
        return serialType.getContentSize();
    }
    
    @Override
    public String toString() {
        return isNull ? "NULL" : getData().toString();
    }
    
    public boolean isNull() {
        return isNull;
    }
    
    public abstract byte[] toByteArray();
    
    public static class TinyInt extends DataType<Byte> {
        
        public TinyInt() {
            super((byte)0, SerialType.NULL_1, true);
        }
        
        public TinyInt(byte data) {
            super(data, SerialType.TINYINT, false);
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .put(getData())
                             .array();
        }

        @Override
        public int compareTo(Byte o) {
            return this.getData().compareTo(o);
        }
    }
    
    public static class SmallInt extends DataType<Short> {
        
        public SmallInt() {
            super((short)0, SerialType.NULL_2, true);
        }
        
        public SmallInt(short data) {
            super(data, SerialType.SMALLINT, false);
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putShort(getData())
                             .array();
        }

        @Override
        public int compareTo(Short o) {
            return this.getData().compareTo(o);
        }
    }
    
    public static class Int extends DataType<Integer> {

        public Int() {
            super(0, SerialType.NULL_4, true);
        }
        
        public Int(int data) {
            super(data, SerialType.INT, false);
        }

        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putInt(getData())
                             .array();
        }        

        @Override
        public int compareTo(Integer o) {
            return this.getData().compareTo(o);
        }
    }
    
    public static class BigInt extends DataType<Long> {

        public BigInt() {
            super(0l, SerialType.NULL_8, true);
        }
        
        public BigInt(long data) {
            super(data, SerialType.BIGINT, false);
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putLong(getData())
                             .array();
        }

        @Override
        public int compareTo(Long o) {
            return this.getData().compareTo(o);
        }
    }
    
    public static class Real extends DataType<Float> {

        public Real() {
            super(0f, SerialType.NULL_4, true);
        }
        
        public Real(float data) {
            super(data, SerialType.REAL, false);
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putFloat(getData())
                             .array();
        }

        @Override
        public int compareTo(Float o) {
            return this.getData().compareTo(o);
        }
    }
    
    public static class CustomDouble extends DataType<Double> {

        public CustomDouble() {
            super(0d, SerialType.NULL_8, true);
        }
        
        public CustomDouble(double data) {
            super(data, SerialType.DOUBLE, false);
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putDouble(getData())
                             .array();
        }

        @Override
        public int compareTo(Double o) {
            return this.getData().compareTo(o);
        }
    }
    
    public static class CustomDateTime extends DataType<Long> {

        public CustomDateTime() {
            super(0l, SerialType.NULL_8, true);
        }
        
        public CustomDateTime(long data) {
            super(data, SerialType.DATETIME, false);
        }
        
        @Override 
        public String toString() {
            if (isNull())
                return "NULL";
            Date date = new Date(getData());
            return new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(date);
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putLong(getData())
                             .array();
        }

        @Override
        public int compareTo(Long o) {
            return this.getData().compareTo(o);
        }
    }
    
    public static class CustomDate extends DataType<Long> {
        
        public CustomDate() {
            super(0l, SerialType.NULL_8, true);
        }
        
        public CustomDate(long data) {
            super(data, SerialType.DATE, false);
        }
        
        @Override 
        public String toString() {
            if (isNull())
                return "NULL";
            Date date = new Date(getData());
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putLong(getData())
                             .array();
        }

        @Override
        public int compareTo(Long o) {
            return this.getData().compareTo(o);
        }
    }
    
    public static class CustomText extends DataType<String> {
        
        private byte n = 0;
        
        public CustomText() {
            super("", SerialType.TEXT, true);
        }
        
        public CustomText(String data) {
            super(data, SerialType.TEXT, false);
            n = (byte)data.length();
        }
        
        @Override
        public byte getSerialTypeCode() {
            return (byte)(n + SerialType.TEXT.getCode());
        }
        
        @Override
        public byte getContentSize() {
            return n;
        }
        
        @Override
        public byte[] toByteArray() {
            return getData().getBytes();
        }
        
        @Override
        public String toString() {
            return getData().isEmpty() ? "NULL" : getData();
        }

        @Override
        public int compareTo(String o) {
            return this.getData().toLowerCase().compareTo(o.toLowerCase());
        }
        
        @Override
        public boolean isNull() {
            return getData().isEmpty();
        }
    }
}
