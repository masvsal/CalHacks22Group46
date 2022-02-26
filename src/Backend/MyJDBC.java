package Backend;

import java.sql.*;
import java.util.ArrayList;

public class MyJDBC {
    private static String url = "jdbc:mysql://34.94.104.113/RootBase";
    private static String user = "root";
    private static String pass = "12345";
    private Statement statement;
    private ResultSet resultSet;

    public MyJDBC(){
        EstablishConnection();
    }

    private void EstablishConnection() {
        try  { //establish connection
            Connection connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("the link didn't work");
            e.printStackTrace();
        }
    }

    private void closeResultSet(){
        try {
            if (!(resultSet == null) && !(resultSet.isClosed())){resultSet.close();}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void write(String SQLCommand) {
        //Statement statement = EstablishConnection();
        try {
            statement.execute(SQLCommand);
        } catch (SQLException e){
            System.out.println("the write command didn't work");
            e.printStackTrace();
        }
    }

    public ResultSet read(String SQLCommand) {
        //Statement statement = EstablishConnection();
        try {
            resultSet = statement.executeQuery(SQLCommand);
            return resultSet;
        } catch (SQLException e){
            System.out.println("the read command didn't work");
            return null;
        }
    }

    public ArrayList<String> resultSetToStringArray(String index, ResultSet resultSet) {
        ArrayList<String> strArr = new ArrayList<>();
        try {
            while(resultSet.next()){
                strArr.add(resultSet.getString(index));
            }
        } catch (SQLException e){
            System.out.println("SQLException");
        }
        return strArr;
    }

    public String resultSetToString(String index, ResultSet resultSet) {
        String s = "";
        try {
            while(resultSet.next()){
                s += resultSet.getString(index);
            }
        } catch (SQLException e){
            System.out.println("SQLException");
        }
        return s;
    }

//    public static void main(String[] args) {
//        MyJDBC myJDBC = new MyJDBC();
//        // "select field from Database" - * means all
//        // select * from Event
//        // to string(id, result)
//        // loop through array where id = array of one above until empty
//        // "select * from Bike where ID='1';
//        ResultSet resultSet = myJDBC.read("select * from bike");
//        ArrayList<String> listOfDistances = myJDBC.resultSetToStringArray("elevation", resultSet);
//        System.out.println(listOfDistances);
//    }

}
