package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDate {

    private Date date1;
    private Date date2;

    // Tests fixture setup with two sample dates, date1 and date2
    @BeforeEach
    public void runBefore() {
        date1 = new Date(8, 28, 1986);
        date2 = new Date(10, 4, 2022);
    }

    // Tests that the constructor sets month, day, and year correctly
    @Test
    public void constructorTest() {
        assertEquals(8, date1.getMonth());
        assertEquals(28, date1.getDay());
        assertEquals(1986, date1.getYear());

        assertEquals(10, date2.getMonth());
        assertEquals(4, date2.getDay());
        assertEquals(2022, date2.getYear());
    }

    // Tests that printDate formats the date as MM/DD/YYYY
    @Test
    public void printDateTest() {
        assertEquals("08/28/1986", date1.printDate());
        assertEquals("10/04/2022", date2.printDate());
    }

    // Tests that addPrefix pads single-digit numbers with a leading zero
    @Test
    public void addPrefixTest() {
        assertEquals("00", date1.addPrefix(0));
        assertEquals("01", date1.addPrefix(1));
        assertEquals("02", date1.addPrefix(2));
        assertEquals("03", date1.addPrefix(3));
        assertEquals("04", date1.addPrefix(4));
        assertEquals("05", date1.addPrefix(5));
        assertEquals("06", date1.addPrefix(6));
        assertEquals("07", date1.addPrefix(7));
        assertEquals("08", date1.addPrefix(8));
        assertEquals("09", date1.addPrefix(9));
        assertEquals("10", date1.addPrefix(10));
        assertEquals("11", date1.addPrefix(11));
        assertEquals("12", date1.addPrefix(12));
        assertEquals("-1", date1.addPrefix(-1));
    }

    // Tests that setMonth updates the month value
    @Test
    public void setMonthTest() {
        assertEquals(8, date1.getMonth());
        date1.setMonth(12);
        assertEquals(12, date1.getMonth());
    }

    // Tests that setDay updates the day value
    @Test
    public void setDayTest() {
        assertEquals(28, date1.getDay());
        date1.setDay(7);
        assertEquals(7, date1.getDay());
    }

    // Tests that setYear updates the year value
    @Test
    public void setYearTest() {
        assertEquals(1986, date1.getYear());
        date1.setYear(2011);
        assertEquals(2011, date1.getYear());
    }
    
}
