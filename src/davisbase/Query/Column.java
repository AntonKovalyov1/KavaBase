package davisbase.Query;

import java.util.Objects;

/**
 *
 * @author Anton
 */
public class Column {

    private final String tableName;
    private final String columnName;
    private final String isNullable;
    private final byte ordinalPosition;
    private final String dataType;

    public Column(final String tableName,
                  final String columnName,
                  final String dataType,
                  final byte ordinalPosition,
                  final String isNullable) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.isNullable = isNullable;
        this.ordinalPosition = ordinalPosition;
        this.dataType = dataType;
    }

    /**
     * @return the name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @return the isNullable
     */
    public String isNullable() {
        return isNullable;
    }

    /**
     * @return the ordinalPosition
     */
    public byte getOrdinalPosition() {
        return ordinalPosition;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Column)) {
            return false;
        }
        Column other = (Column) o;
        return this.tableName.toLowerCase().equals(
                       other.getTableName().toLowerCase()) &&
               this.columnName.toLowerCase().equals(
                       other.getColumnName().toLowerCase());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.tableName);
        hash = 53 * hash + Objects.hashCode(this.columnName);
        return hash;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }
}
