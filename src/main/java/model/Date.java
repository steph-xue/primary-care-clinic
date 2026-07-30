package model;

// A class representing a date with a month, day, and year.
public class Date {

    private int month;
    private int day;
    private int year;

    // REQUIRES: 0 < year, 0 < month < 13, 0 < day < 32
    // EFFECTS: Constructs a date with given month, day, and year.
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    // EFFECTS: Returns date in the String format "MM/DD/YYYY".
    public String printDate() {
        StringBuilder result = new StringBuilder();
        result.append(addPrefix(month));
        result.append("/");
        result.append(addPrefix(day));
        result.append("/");
        result.append(year);
        return result.toString();
    }

    // EFFECTS: Adds prefix zero to single digit numbers, returning it in a String format.
    public String addPrefix(int number) {
        if (number >= 0 && number <= 9) {
            return "0" + String.valueOf(number);
        } else {
            return String.valueOf(number);
        }
    }

    // Setters
    // REQUIRES: 0 < month < 13
    // MODIFIES: this
    // EFFECTS: Sets the month to the given month.
    public void setMonth(int month) {
        this.month = month;
    }

    // REQUIRES: 0 < day < 32
    // MODIFIES: this
    // EFFECTS: Sets the day to the given day.
    public void setDay(int day) {
        this.day = day;
    }

    // REQUIRES: 0 < year
    // MODIFIES: this
    // EFFECTS: Sets the year to the given year.
    public void setYear(int year) {
        this.year = year;
    }

    // Getters
    // EFFECTS: Gets the month of the date.
    public int getMonth() {
        return month;
    }

    // EFFECTS: Gets the day of the date.
    public int getDay() {
        return day;
    }

    // EFFECTS: Gets the year of the date.
    public int getYear() {
        return year;
    }
}
