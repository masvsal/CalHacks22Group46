package GUI.Events;

import java.util.ArrayList;
import java.util.Iterator;

public class EventList implements Iterable<Event> {
	
	private ArrayList<Event> events;
    // on adding a new event, write it to SQL
	
	public EventList() {
		events = new ArrayList<Event>();
	}
	
	public ArrayList<Event> getAll() {
		return events;
	}
	
	public void addItem(Event e) {
		events.add(e);
	}
	
	public void empty() {
		events.clear();
	}
	
	public void loadTestItems() {
		Activity a = new Activity();
		a.set(5, "Mount ym", 5.4, 1234, "3");
		Event temp = new Event(1, a, "Hiking together 1!", "feb 21 2022", "Vancouver", "UBC");
		events.add(temp);
		Activity a2 = new Activity();
		a2.set(2, "Hive", "traditional", "v1-12", "indoor");
		temp = new Event(2, a2, "Trip to the hive", "feb 28 2022", "Kits", "UBC");
		events.add(temp);
	}


    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
