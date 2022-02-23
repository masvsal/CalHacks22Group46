package GUI.Events;

public class Activity {

    private int numP;
    private String desc;
    private String activityType;
    private double distance;
    private double elevation;
    private String difficulty;
    private String type; // for bike: indoor etc
    private int duration;
    private double cost;
    private String style; // indoor for climbing sql database
    private String place;

    public Activity() {
        numP = 1;
        desc = "desc";
        activityType = "Hiking";
    }

    public Activity(int num) {
        this();
        numP = num;
    }

    public Activity(int num, String des, String at) {
        desc = des;
        numP = num;
        activityType = at;
    }

    //Setters
    public void set(int num, String des, String at) {
        numP = num;
        desc = des;
        activityType = at;
    }

    public void setNum(int num) {
        numP = num;
    }

    public void setDes(String d) {
        desc = d;
    }

    public void setActivityType(String at) {
        activityType = at;
    }

    public void set(int num, String desc, double d, double e, String diff, String t) {
        this.set(num, desc, "Biking");
        distance = d;
        elevation = e;
        difficulty = diff;
        type = t;
    }

    public void set(int num, String desc, int dur, double c) {
        set(num, desc, "Camping");
        duration = dur;
        cost = c;
    }

    public void set(int num, String desc, String t, String d, String st) {
        set(num, desc, "Climbing");
        type = t;
        difficulty = d;
        style = st;
    }

    public void set(int num, String desc, double d, double e, String diff) {
        set(num, desc, "Hiking");
        distance = d;
        elevation = e;
        difficulty = diff;
    }

    public void set(int num, String desc, String t, String p) {
        set(num, desc, "Skiing");
        type = t;
        place = p;
    }

    public void setPlace(String p) {
        place = p;
    }

    public void setDistance(double d) {
        distance = d;
    }

    public void setIndoor(String s) {
        style = s;
    }

    public void setDuration(int d) {
        duration = d;
    }

    public void setCost(double c) {
        cost = c;
    }

    public void setDifficulty(String d) {
        difficulty = d;
    }

    public void setType(String t) {
        type = t;
    }

    public void setElevation(double e) {
        elevation = e;
    }

    //Getters
    public int getNum() {
        return numP;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getDes() {
        return desc;
    }

    public double getDistance() {
        return distance;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getType() {
        return type;
    }

    public double getElevation() {
        return elevation;
    }

    public int getDuration() {
        return duration;
    }

    public double getCost() {
        return cost;
    }

    public String getStyle() {
        return style;
    }

    public String getPlace() {
        return place;
    }

    // to strings
    public String toString() {
        String s = " Type:" + activityType + " Number of people:" + numP + " Description:" + desc;
        s += " Distance:" + distance + " Elevation:" + elevation;
        s += " Difficulty:" + difficulty + " Type:" + type;
        s += " Duration:" + duration + " Cost:" + cost;
        s += " Style:" + style + " Place:" + place;
        return s;
    }

    public String bikeToString() {
        String s = "Biking:" + getNum() + ":" + getDes() + ":";
        s += distance + ":" + difficulty + ":" + type + ":" + elevation;
        return s;
    }
    public String hikeToString() {
        String s = "Hiking:" + getNum() + ":" + getDes() + ":";
        s += distance + ":" + elevation + ":" + difficulty;
        return s;
    }

}
