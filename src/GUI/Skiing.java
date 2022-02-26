package GUI;

import GUI.Events.Activity;

public class Skiing extends Activity {
	
	private String type; // skiing, snow boarding
	private String place; // back country, resort
	
	public Skiing() {
		super.setActivityType("Camping");
		type = "";
		place = "";
	}
	
	public Skiing(int np, String desc, String t, String p) {
		super(np, desc, "Camping");
		type = t;
		place = p;
	}
	
	// setters
	public void setType(String t) {
		type = t;
	}
	
	public void setPlace(String p) {
		place = p;
	}
	
	// getters
	public String getType() {
		return type;
	}
	
	public String getPlace() {
		return place;
	}
}
