package kavabase.fileFormat;

import java.util.Objects;
import kavabase.DataFormat.DataType;

/**
 *
 * @author Anton
 */
public class Column {
    
    private final String name;
    private final boolean isPrimaryKey;
    private final boolean isNullable;
    private final int ordinalPosition;
    private final DataType dataType;

    public Column(final String name,
                  final boolean isPrimaryKey,
                  final boolean isNullable,
                  final int ordinalPosition, 
                  final DataType dataType) {
        this.name = name;
        this.isPrimaryKey = isPrimaryKey;
        this.isNullable = isNullable;
        this.ordinalPosition = ordinalPosition;
        this.dataType = dataType;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the isPrimaryKey
     */
    public boolean isIsPrimaryKey() {
        return isPrimaryKey;
    }

    /**
     * @return the isNullable
     */
    public boolean isIsNullable() {
        return isNullable;
    }

    /**
     * @return the ordinalPosition
     */
    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    /**
     * @return the dataType
     */
    public DataType getDataType() {
        return dataType;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Column))
            return false;
        Column other = (Column)o;
        return this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
