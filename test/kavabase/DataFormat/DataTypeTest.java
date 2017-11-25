/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kavabase.DataFormat;

import davisbase.DataFormat.DataType.CustomDate;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author axk176431
 */
public class DataTypeTest {

    /**
     * Test of getData method, of class DataType.
     */
    @Test
    public void testGetData() {
        long longDate = Long.parseLong("2015-11-11");
        CustomDate customDate = new CustomDate(longDate);
        assertEquals(customDate.toString(), "2015-11-11");
    }    
}
