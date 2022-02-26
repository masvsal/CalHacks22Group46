package GUI.Controllers;

import Backend.Events_DB;
import GUI.Events.Activity;
import GUI.Events.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;

public class NewEvent extends Controller {
    @FXML
    private GridPane addEventPane;
    @FXML
    BorderPane borderPane;
    @FXML
    private ComboBox<String> dropdown;
    @FXML
    private GridPane activityPane;
    //_________________________________
    @FXML
    private TextField name;
    @FXML
    private TextField date;
    @FXML
    private TextField loc;
    @FXML
    private TextField startPoint;
    //_________________________________
    @FXML
    private TextField numPplTxt;
    @FXML
    private TextField distanceTxt;
    @FXML
    private TextField elevationTxt;
    @FXML
    private TextField descrTxt;
    @FXML
    private TextField typeTxt;
    @FXML
    private TextField durationTxt;
    @FXML
    private TextField costTxt;
    @FXML
    private TextField difficultyTxt;
    @FXML
    private TextField placeTxt;
    @FXML
    private ComboBox<String> isIndoorChoice;

    public NewEvent() {
        borderPane = new BorderPane();
        activityPane = new GridPane();
    }

    public void goToHome(ActionEvent event) throws IOException {
        goToHomePage(event);
    }

    public ComboBox<String> setupDropdown() {
        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("Banking");
        comboBox.getItems().add("Climbing");
        comboBox.getItems().add("Camping");
        comboBox.getItems().add("Skiing");
        comboBox.getItems().add("Hiking");

        return comboBox;
    }

    public void showActivity() {
        activityPane.getChildren().clear();
        displayNumPpl();
        processAct(dropdown.getSelectionModel().getSelectedItem());
        displayDescr();
    }

    private void processAct(String type) {
        switch (type) {
            case "Hiking":
                displayHiking();
                break;
            case "Climbing":
                displayClimbing();
                break;
            case "Biking":
                displayBiking();
                break;
            case "Camping":
                displayCamping();
                break;
            case "Skiing":
                displaySkiing();
                break;
        }
    }

    private void displayHiking() {
        displayDistance(1, 0);
        displayElevation(2, 0);
        displayDifficulty(3, 0);
    }

    private void displayClimbing() {
        displayType(1, 0);
        displayDifficulty(2, 0);
        displayIndoor(3, 0);
    }

    private void displayBiking() {
        displayHiking();
        displayType(4, 0);
    }

    private void displayCamping() {
        displayDuration(1, 0);
        displayCost(2, 0);
    }

    private void displaySkiing() {
        displayType(1, 0);
        displayPlace(2, 0);
    }

    private void displayNumPpl() {
        Label numPpl = new Label("Maximum number of people: ");
        numPplTxt = new TextField();
        activityPane.add(numPpl, 0, 0);
        activityPane.add(numPplTxt, 1, 0);
    }

    private void displayDistance(int row, int col) {
        Label numPpl = new Label("Distance: ");
        distanceTxt = new TextField();
        activityPane.add(numPpl, col, row);
        activityPane.add(distanceTxt, col + 1, row);
    }

    private void displayElevation(int row, int col) {
        Label numPpl = new Label("Elevation: ");
        elevationTxt = new TextField();
        activityPane.add(numPpl, col, row);
        activityPane.add(elevationTxt, col + 1, row);
    }

    private void displayType(int row, int col) {
        Label numPpl = new Label("Type: ");
        typeTxt = new TextField();
        activityPane.add(numPpl, col, row);
        activityPane.add(typeTxt, col + 1, row);
    }

    private void displayDifficulty(int row, int col) {
        Label numPpl = new Label("Difficulty: ");
        difficultyTxt = new TextField();
        activityPane.add(numPpl, col, row);
        activityPane.add(difficultyTxt, col + 1, row);
    }

    private void displayDuration(int row, int col) {
        Label numPpl = new Label("Duration: ");
        durationTxt = new TextField();
        activityPane.add(numPpl, col, row);
        activityPane.add(durationTxt, col + 1, row);
    }

    private void displayCost(int row, int col) {
        Label numPpl = new Label("Cost: ");
        costTxt = new TextField();
        activityPane.add(numPpl, col, row);
        activityPane.add(costTxt, col + 1, row);
    }

    private void displayIndoor(int row, int col) {
        Label numPpl = new Label("Indoor?: ");
        isIndoorChoice = new ComboBox<>();
        isIndoorChoice.getItems().add("Yes");
        isIndoorChoice.getItems().add("No");
        activityPane.add(numPpl, col, row);
        activityPane.add(isIndoorChoice, col + 1, row);
    }

    private void displayPlace(int row, int col) {
        Label numPpl = new Label("Place: ");
        placeTxt = new TextField();
        activityPane.add(numPpl, col, row);
        activityPane.add(placeTxt, col + 1, row);
    }

    private void displayDescr() {
        Label numPpl = new Label("Description: ");
        descrTxt = new TextField();
        activityPane.add(numPpl, 0, 11);
        activityPane.add(descrTxt, 1, 11, 7, 3);
    }

    public void saveEvent(ActionEvent event) throws IOException {
        try {
            storeEvent();
            goToHome(event);
        } catch (NumberFormatException | NullPointerException | SQLException n) {
            n.printStackTrace();
            System.out.println("please ensure information has been entered correctly");
        }
    }

    private void storeEvent() throws NumberFormatException,NullPointerException, SQLException {
        Events_DB events_db = new Events_DB();
        String n = name.getText().toString();
        String l = loc.getText().toString();
        String sp = startPoint.getText();
        String d = date.getText();
        Activity ac = parseActivity(dropdown.getSelectionModel().getSelectedItem());
        Event newEvent = new Event(ac, n, d, l, sp);
        events_db.addItem(newEvent);
    }

    private Activity parseActivity(String type) throws NumberFormatException{
        Activity activity = new Activity();
            activity.setNum(Integer.parseInt(numPplTxt.getText()));
            activity.setDes(descrTxt.getText());
            switch (type) {
                case "Hiking":
                    saveHiking(activity);
                    break;
                case "Climbing":
                    saveClimbing(activity);
                    break;
                case "Biking":
                    saveBiking(activity);
                    break;
                case "Camping":
                    saveCamping(activity);
                    break;
                case "Skiing":
                    saveSkiing(activity);
                    break;
            }
        return activity;
    }

    private void saveHiking(Activity ac) {
        ac.setDistance(Double.parseDouble(distanceTxt.getText()));
        ac.setElevation(Double.parseDouble(elevationTxt.getText()));
        ac.setDifficulty(difficultyTxt.getText());
    }

    private void saveClimbing(Activity ac) {
        ac.setType(typeTxt.getText());
        ac.setDifficulty(difficultyTxt.getText());
        ac.setIndoor(isIndoorChoice.getSelectionModel().getSelectedItem());
    }

    private void saveBiking(Activity ac) {
        saveHiking(ac);
        ac.setType(typeTxt.getText());
    }

    private void saveCamping(Activity ac) {
        ac.setDuration(Integer.parseInt(durationTxt.getText()));
        ac.setCost(Double.parseDouble(costTxt.getText()));
    }

    private void saveSkiing(Activity ac) {
        ac.setType(typeTxt.getText());
        ac.setPlace(placeTxt.getText());
    }

}
