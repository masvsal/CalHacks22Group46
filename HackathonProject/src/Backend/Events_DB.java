package Backend;

import GUI.Events.Activity;
import GUI.Events.Event;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

//make a pull request on this class to return a list of events
public class Events_DB {
    private MyJDBC server;

    public Events_DB(){
        server = new MyJDBC();
    }

    public ArrayList<Event> getAll() {
        ArrayList<Event> events  = loadFromServer();
        return events;
    }

    public ArrayList<Event> getByType(String act) {
        ArrayList<Event> events  = loadFromServer();
        ArrayList<Event> eventType = new ArrayList<Event>();
        for(Event x: events) {
            if (Objects.equals(x.getActivity().getActivityType(), act)) {
                eventType.add(x);
            }
        }
        return eventType;
    }

    // "insert into Events(ID, activity, name, date, location, startpoint) values()";
    // add parameters
    public void addItem(Event event) {
        int id = event.getID();
        String act = event.getActivity().getActivityType();
        String name = event.getName();
        String date = event.getDate();
        String loc = event.getLocation();
        String sP = event.getStart();
        Activity a = event.getActivity();
        server.write("insert into Events(ID, activity, name, date, location, startpoint) " +
                "values(" + id + ", '" + act + "', '" + name + "', '" + date + "', '" + loc + "', '" + sP + "')");
        writeToActivityServer(a, id);

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

    private ArrayList<Event> loadFromServer() {
        ArrayList<Event> events = new ArrayList<>();
        MyJDBC activityJDBC = new MyJDBC();
        try {
            ResultSet resultSet = server.read("select * from Events");
            System.out.println(resultSet.isClosed() + "1");
            while (resultSet.next()) {
                System.out.println(resultSet.isClosed() + "2");
                int ID = Integer.parseInt(resultSet.getString("ID"));
                String actType = resultSet.getString("activity");
                String name = resultSet.getString("name");
                String date = resultSet.getString("date");
                String loc = resultSet.getString("location");
                String sP = resultSet.getString("startpoint");
                Activity a = processAct(actType, ID, activityJDBC);
                Event temp = new Event(ID, a, name, date, loc, sP);
                events.add(temp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return events;
    }

    private Activity processAct(String type, int id, MyJDBC myJDBC) {
        Activity a = new Activity();
        switch(type) {
            case "Hiking":
                a = loadHiking(id, myJDBC);
                break;
            case "Climbing":
                a = loadClimb(id, myJDBC);
                break;
            case "Biking":
                a = loadBike(id, myJDBC);
                break;
            case "Camping":
                a = loadCamp(id, myJDBC);
                break;
            case "Skiing":
                a = loadSki(id, myJDBC);
                break;
        }
        return a;
    }

    //load helper functions
    private Activity loadHiking(int id, MyJDBC myJDBC) {
        Activity a = new Activity();
        a.setActivityType("Hiking");
        ResultSet resultSet = myJDBC.read("select * from Hiking where ID = " + id);
        try {
            while (resultSet.next()) {
                int num = Integer.parseInt(resultSet.getString("numppl"));
                String desc = resultSet.getString("description");
                double distance = Double.parseDouble(resultSet.getString("distance"));
                double elevation = Double.parseDouble(resultSet.getString("elevation"));
                String difficulty = resultSet.getString("difficulty");
                a.set(num, desc, distance, elevation, difficulty);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return a;
    }

    private Activity loadClimb(int id, MyJDBC myJDBC) {
        Activity a = new Activity();
        a.setActivityType("Climbing");
        ResultSet resultSet = myJDBC.read("select * from Climbing where ID = " + id);
        try {
            while (resultSet.next()) {
                int num = Integer.parseInt(resultSet.getString("numppl"));
                String desc = resultSet.getString("description");
                String type = resultSet.getString("type");
                String difficulty = resultSet.getString("difficulty");
                String style = resultSet.getString("indoor");
                a.set(num, desc, type, difficulty, style);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return a;
    }

    private Activity loadBike(int id, MyJDBC myJDBC) {
        Activity a = new Activity();
        a.setActivityType("Biking");
        ResultSet resultSet = myJDBC.read("select * from Biking where ID = " + id);
        try {
            while (resultSet.next()) {
                int num = Integer.parseInt(resultSet.getString("numppl"));
                String desc = resultSet.getString("description");
                double distance = Double.parseDouble(resultSet.getString("distance"));
                double elevation = Double.parseDouble(resultSet.getString("elevation"));
                String type = resultSet.getString("type");
                String difficulty = resultSet.getString("difficulty");
                a.set(num, desc, distance, elevation, difficulty, type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    private Activity loadCamp(int id, MyJDBC myJDBC) {
        Activity a = new Activity();
        a.setActivityType("Camping");
        ResultSet resultSet = myJDBC.read("select * from Camping where ID = " + id);
        try {
            while (resultSet.next()) {
                int num = Integer.parseInt(resultSet.getString("numppl"));
                String desc = resultSet.getString("description");
                double cost = Double.parseDouble(resultSet.getString("cost"));
                int duration = Integer.parseInt(resultSet.getString("duration"));
                a.set(num, desc, duration, cost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    private Activity loadSki(int id, MyJDBC myJDBC) {
        Activity a = new Activity();
        a.setActivityType("Skiing");
        ResultSet resultSet = myJDBC.read("select * from Skiing where ID = " + id);
        try {
            while (resultSet.next()) {
                int num = Integer.parseInt(resultSet.getString("numppl"));
                String desc = resultSet.getString("description");
                String type = resultSet.getString("type");
                String place = resultSet.getString("place");
                a.set(num, desc, type, place);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

//    public static void main(String [] args) {
//        Events_DB events_db = new Events_DB();
//        ArrayList<Event> events = events_db.getAll();
//        for (Event e: events){
//            System.out.println(e);
//        }
//    }

}
