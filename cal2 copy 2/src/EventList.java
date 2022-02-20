import java.sql.ResultSet;
import java.util.ArrayList;

public class EventList {
	
	private ArrayList<Event> events;
    private ArrayList<Event> newEvents;
    private MyJDBC server;
    // on adding a new event, write it to SQL

    public static void main(String [] args) {
        EventList list = new EventList();
//        ArrayList<Event> evs = list.getAll();
//        for(Event e : evs) {
//            System.out.println(e.toString());
//        }
        Event e = new Event();
        e.setID(18);
        e.getActivity().set(1, "hike desc", 12.1, 12.2, "3");
        list.addItem(e);
    }
	
	public EventList() {
		events = new ArrayList<Event>();
        newEvents = new ArrayList<Event>();
        server = new MyJDBC();
	}
	
	public ArrayList<Event> getAll() {
		loadFromServer();
		return events;
	}
	
	public ArrayList<Event> getByType(String act) {
		loadFromServer();
        ArrayList<Event> eventType = new ArrayList<Event>();
		for(int x = 0; x < events.size(); x++) {
			if((events.get(x).getActivity().getActivityType().equals(act))) {
				eventType.add(events.get(x));
			}
		}
		return eventType;
	}

    // "insert into Events(ID, activity, name, date, location, startpoint) values()";
    // add parameters
    public void addItem(Event event) {
        newEvents.add(event);
        int size = newEvents.size();
        for(int x = size - 1; x >= 0; x--) {
            for(Event e : events) {
                if(e.getID() == newEvents.get(x).getID()) {
                    newEvents.remove(x);
                }
            }
        }

        for(int x = 0; x < newEvents.size(); x++) {
            Event curr = newEvents.get(x);
            int id = curr.getID();
            String act = curr.getActivity().getActivityType();
            String name = curr.getName();
            String date = curr.getDate();
            String loc = curr.getLocation();
            String sP = curr.getStart();
            Activity a = curr.getActivity();
            server.write("insert into Events(ID, activity, name, date, location, startpoint) " +
                    "values(" + id + ", '" + act + "', '" + name + "', '" + date + "', '" + loc + "', '" + sP + "')");
            writeToActivityServer(a, id);
        }
    }

    private void writeToActivityServer(Activity a, int id) {
        String type = a.getActivityType();
        switch(type) {
            case "Hiking":
                writeHiking(a, id);
                break;
            case "Climbing":
                writeClimb(a, id);
                break;
            case "Biking":
                writeBike(a, id);
                break;
            case "Camping":
                writeCamp(a, id);
                break;
            case "Skiing":
                writeSki(a,  id);
                break;
        }
    }

    // write helper functions
    private void writeHiking(Activity a, int id) {
        int num = a.getNum();
        String desc = a.getDes();
        double dis = a.getDistance();
        double elev = a.getElevation();
        String diff = a.getDifficulty();
        server.write("insert into Hiking(ID, numppl, description, distance, elevation, difficulty) " +
                "values(" + id + ", " + num + ", '" + desc + "', " + dis + ", " + elev + ", '" + diff + "')");
    }

    private void writeClimb(Activity a, int id) {
        int num = a.getNum();
        String desc = a.getDes();
        String type = a.getType();
        String diff = a.getDifficulty();
        String style = a.getStyle();
        server.write("insert into Climbing(ID, numppl, description, type, difficulty, indoor) " +
                "values(" + id + ", " + num + ", '" + desc + "', '" + type + "', '" + diff + "', '" + style + "')");
    }

    private void writeBike(Activity a, int id) {
        int num = a.getNum();
        String desc = a.getDes();
        double dis = a.getDistance();
        double elev = a.getElevation();
        String diff = a.getDifficulty();
        String type = a.getType();
        server.write("insert into Biking(ID, numppl, description, distance, elevation, difficulty, type) " +
                "values(" + id + ", " + num + ", '" + desc + "', " + dis + ", " + elev + ", " + diff + ", '" + type + "')");
    }

    private void writeCamp(Activity a, int id) {
        int num = a.getNum();
        String desc = a.getDes();
        double dur = a.getDuration();
        double cost = a.getCost();
        server.write("insert into Camping(ID, numppl, description, duration, cost) " +
                "values(" + id + ", " + num + ", '" + desc + "', " + dur + ", " + cost + ")");
    }

    private void writeSki(Activity a, int id) {
        int num = a.getNum();
        String desc = a.getDes();
        String type = a.getType();
        String place = a.getPlace();
        server.write("insert into Skiing(ID, numppl, description, type, place) " +
                "values(" + id + ", " + num + ", '" + desc + "', '" + type + "', '" + place + "')");
    }

    private void loadFromServer() {
        ResultSet resultSet = server.read("select * from Events");
        ArrayList<String> ids = server.resultSetToStringArray("ID", resultSet);
        for(int x = 0; x < ids.size(); x++) {
            int iD = Integer.parseInt(ids.get(x));
            ResultSet r = server.read("select * from Events where ID = " + iD);
//            System.out.println("ID: " + iD);
            String acType = server.resultSetToString("activity", r);
//            System.out.println("Ac: " + acType);
            r = server.read("select * from Events where ID = " + iD);
            String name = server.resultSetToString("name", r);
//            System.out.println("name: " + name);
            r = server.read("select * from Events where ID = " + iD);
            String date = server.resultSetToString("date", r);
//            System.out.println("date: " + date);
            r = server.read("select * from Events where ID = " + iD);
            String loc = server.resultSetToString("location", r);
//            System.out.println("loc: " + loc);
            r = server.read("select * from Events where ID = " + iD);
            String sP = server.resultSetToString("startpoint", r);
//            System.out.println("sP: " + sP);
            Activity a = processAct(acType, iD);
            Event temp = new Event(iD, a, name, date, loc, sP);
            System.out.println(temp.toString());
            events.add(temp);
        }
    }

    //load helper functions
    private Activity loadHiking(int id) {
        Activity a = new Activity();
        a.setActivityType("Hiking");

        ResultSet result = server.read("select * from Hiking where ID = " + id);
        String numS = server.resultSetToString("numppl", result);
        int num = Integer.parseInt(numS);
        result = server.read("select * from Hiking where ID = " + id);
        String desc = server.resultSetToString("description", result);

        result = server.read("select * from Hiking where ID = " + id);
        String distanceS = server.resultSetToString("distance", result);
        double distance = Double.parseDouble(distanceS);
        result = server.read("select * from Hiking where ID = " + id);
        String elevationS = server.resultSetToString("elevation", result);
        double elevation = Double.parseDouble(elevationS);
        result = server.read("select * from Hiking where ID = " + id);
        String difficulty = server.resultSetToString("difficulty", result);

        a.set(num, desc, distance, elevation, difficulty);
        return a;
    }

    private Activity loadClimb(int id) {
        Activity a = new Activity();
        a.setActivityType("Climbing");

        ResultSet result = server.read("select * from Climbing where ID = " + id);
        String numS = server.resultSetToString("numppl", result);
        int num = Integer.parseInt(numS);
        result = server.read("select * from Climbing where ID = " + id);
        String desc = server.resultSetToString("description", result);

        result = server.read("select * from Climbing where ID = " + id);
        String type = server.resultSetToString("type", result);
        result = server.read("select * from Climbing where ID = " + id);
        String difficulty = server.resultSetToString("difficulty", result);
        result = server.read("select * from Climbing where ID = " + id);
        String style = server.resultSetToString("indoor", result);

        a.set(num, desc, type, difficulty, style);
        return a;
    }

    private Activity loadBike(int id) {
        Activity a = new Activity();
        a.setActivityType("Biking");

        ResultSet result = server.read("select * from Biking where ID = " + id);
        String numS = server.resultSetToString("numppl", result);
        int num = Integer.parseInt(numS);
        result = server.read("select * from Biking where ID = " + id);
        String desc = server.resultSetToString("description", result);

        result = server.read("select * from Biking where ID = " + id);
        String distanceS = server.resultSetToString("distance", result);
        double distance = Double.parseDouble(distanceS);
        result = server.read("select * from Biking where ID = " + id);
        String elevationS = server.resultSetToString("elevation", result);
        double elevation = Double.parseDouble(elevationS);
        result = server.read("select * from Biking where ID = " + id);
        String difficulty = server.resultSetToString("difficulty", result);
        result = server.read("select * from Biking where ID = " + id);
        String type = server.resultSetToString("type", result);

        a.set(num, desc, distance, elevation, difficulty, type);
        return a;
    }

    private Activity loadCamp(int id) {
        Activity a = new Activity();
        a.setActivityType("Camping");

        ResultSet result = server.read("select * from Camping where ID = " + id);
        String numS = server.resultSetToString("numppl", result);
        int num = Integer.parseInt(numS);
        result = server.read("select * from Camping where ID = " + id);
        String desc = server.resultSetToString("description", result);

        result = server.read("select * from Camping where ID = " + id);
        String durationS = server.resultSetToString("duration", result);
        int duration = Integer.parseInt(durationS);
        result = server.read("select * from Camping where ID = " + id);
        String costS = server.resultSetToString("cost", result);
        double cost = Double.parseDouble(costS);

        a.set(num, desc, duration, cost);
        return a;
    }

    private Activity loadSki(int id) {
        Activity a = new Activity();
        a.setActivityType("Skiing");

        ResultSet result = server.read("select * from Skiing where ID = " + id);
        String numS = server.resultSetToString("numppl", result);
        int num = Integer.parseInt(numS);
        result = server.read("select * from Skiing where ID = " + id);
        String desc = server.resultSetToString("description", result);

        result = server.read("select * from Skiing where ID = " + id);
        String type = server.resultSetToString("type", result);
        result = server.read("select * from Skiing where ID = " + id);
        String place = server.resultSetToString("place", result);

        a.set(num, desc, type, place);
        return a;
    }
	
	private Activity processAct(String type, int id) {
		Activity a = new Activity();
		switch(type) {
			case "Hiking":
				a = loadHiking(id);
				break;
			case "Climbing":
				a = loadClimb(id);
				break;
			case "Biking":
				a = loadBike(id);
				break;
			case "Camping":
				a = loadCamp(id);
				break;
			case "Skiing":
				a = loadSki(id);
				break;
		}
		return a;
	}

}
