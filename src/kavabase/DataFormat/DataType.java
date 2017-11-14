package kavabase.DataFormat;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Anton
 * @param <T>
 */
public abstract class DataType<T> {
    
    private final T data;
    private final SerialType serialType;
    protected final boolean isNull;
    
    public DataType(T data, SerialType serialType) {
        this.data = data;
        this.serialType = serialType;
        this.isNull = data == null;
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
    
    public abstract byte[] toByteArray();
    public abstract boolean compare(T other, Comparison comparison);
    
    public static class TinyInt extends DataType<Byte> {
        
        public TinyInt() {
            super(null, SerialType.NULL_1);
        }
        
        public TinyInt(byte data) {
            super(data, SerialType.TINYINT);
        }

        @Override
        public boolean compare(Byte other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .put(getData())
                             .array();
        }
    }
    
    public static class SmallInt extends DataType<Short> {
        
        public SmallInt() {
            super(null, SerialType.NULL_2);
        }
        
        public SmallInt(short data) {
            super(data, SerialType.SMALLINT);
        }
        
        @Override
        public boolean compare(Short other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putShort(getData())
                             .array();
        }
    }
    
    public static class Int extends DataType<Integer> {

        public Int() {
            super(null, SerialType.NULL_4);
        }
        
        public Int(int data) {
            super(data, SerialType.INT);
        }

        @Override
        public boolean compare(Integer other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putInt(getData())
                             .array();
        }        
    }
    
    public static class BigInt extends DataType<Long> {

        public BigInt() {
            super(null, SerialType.NULL_8);
        }
        
        public BigInt(long data) {
            super(data, SerialType.BIGINT);
        }

        @Override
        public boolean compare(Long other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putLong(getData())
                             .array();
        }
    }
    
    public static class Real extends DataType<Float> {

        public Real() {
            super(null, SerialType.NULL_4);
        }
        
        public Real(float data) {
            super(data, SerialType.REAL);
        }

        @Override
        public boolean compare(Float other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putFloat(getData())
                             .array();
        }
    }
    
    public static class CustomDouble extends DataType<Double> {

        public CustomDouble() {
            super(null, SerialType.NULL_8);
        }
        
        public CustomDouble(double data) {
            super(data, SerialType.DOUBLE);
        }
        
        @Override
        public boolean compare(Double other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putDouble(getData())
                             .array();
        }
    }
    
    public static class CustomDateTime extends DataType<Long> {

        public CustomDateTime() {
            super(null, SerialType.NULL_8);
        }
        
        public CustomDateTime(long data) {
            super(data, SerialType.DATETIME);
        }

        @Override
        public boolean compare(Long other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        @Override 
        public String toString() {
            Date date = new Date(getData());
            return date.toString();
        }
        
        @Override
        public byte[] toByteArray() {
            return ByteBuffer.allocate(getContentSize())
                             .order(ByteOrder.BIG_ENDIAN)
                             .putLong(getData())
                             .array();
        }
    }
    
    public static class CustomDate extends DataType<Long> {
        
        public CustomDate() {
            super(null, SerialType.NULL_8);
        }
        
        public CustomDate(long data) {
            super(data, SerialType.DATE);
        }

        @Override
        public boolean compare(Long other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        @Override 
        public String toString() {
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
    }
    
    public static class CustomText extends DataType<String> {
        
        private byte n = 0;
        
        public CustomText() {
            super("", SerialType.TEXT);
        }
        
        public CustomText(String data, SerialType serialType) {
            super(data, serialType);
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
        public boolean compare(String other, Comparison comparison) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } 
        
        @Override
        public byte[] toByteArray() {
            return getData().getBytes();
        }
    }
}
