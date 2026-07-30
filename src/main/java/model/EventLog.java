package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Referenced https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

// A class representing a log of primary care clinic events. Uses the Singleton Design Pattern to
// ensure there is only one EventLog in the system and that the system has global access to it.
public class EventLog implements Iterable<Event> {
    // The only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private Collection<Event> events;

    // EFFECTS: Constructs an empty event log. Private to prevent external construction (Singleton Design Pattern).
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // EFFECTS: Gets instance of EventLog, creating it first if it doesn't already exist (Singleton Design Pattern).
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: Adds an event to the event log.
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared"));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}