package GUI.Events;


public class Event {
	
	private Activity ac;
	private String name; //name of trip
	private String date;
	private String location;
	private String startPoint;
    private int ID;
	
	public Event() {
		ac = new Activity();
		name = "New Event";
		date = "";
		location = "n/a";
		startPoint = "n/a";
        ID++;
	}
	
	public Event(int id, Activity a, String n, String d, String lo, String sp) {
        ID = id;
		ac = a;
		name = n;
		date = d;
		location = lo;
		startPoint = sp;
	}

    public Event(Activity a, String n, String d, String lo, String sp) {
        ac = a;
        name = n;
        date = d;
        location = lo;
        startPoint = sp;
    }

    /*public String toString() {
        String s = "ID:" + ID + " Name:" + name + " Date:" + date;
        s += " Location:" + location + " Start:" + startPoint;
        s += ac.toString() + "\n";
		
		return s;
	}*/

    public String toString(){
        return name + " | " + ac.getActivityType() + " | Location: " + location + " | Date: " + date;
    }
	
	//Setters
	public void setActivity(Activity a) {
		ac = a;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setDate(String d) {
		date = d;
	}
	
	public void setLocation(String l) {
		location = l;
	}
	
	public void setStart(String s) {
		startPoint = s;
	}
	
	
	//Getters
    public int getID() {
        return ID;
    }

	public Activity getActivity() { 
		return ac;
	}
	public String getName() {
		return name;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getStart() {
		return startPoint;
	}
}
