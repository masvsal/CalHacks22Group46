package GUI.Controllers;

import GUI.Events.Activity;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import java.io.IOException;

import GUI.Events.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EventsPageController extends Controller {
    @FXML private javafx.scene.control.Label label;
    @FXML private GridPane evD;


//    public EventsPageController(Event e){
//        Label date = new Label(e.getDate());
//        Label location = new Label(e.getLocation());
//        Label start = new Label(e.getStart());
//        evD.add(date,0,0);
//        evD.add(location,0,1);
//        evD.add(start,0,2);
//    }


	public void goToHome(ActionEvent event) throws IOException {
		goToHomePage(event);
	}
	public void goToProfile(ActionEvent event) throws IOException {
		goToProfilePage(event);
	}

    public void initialize(Event e){
        label.setText(e.getName());
        label.alignmentProperty().set(Pos.CENTER);
        popDetails(e);
        popActivity(e);
    }

    private void popDetails(Event e){
        evD.getChildren().clear();

        Label date = new Label("Date: " + e.getDate());
        Label location = new Label("Location: " + e.getLocation());
        Label start = new Label("Start Point: " + e.getStart());
        date.setFont(new Font(20));
        location.setFont(new Font(20));
        start.setFont(new Font(20));

        evD.add(date,0,0);
        evD.add(location,0,1);
        evD.add(start,0,2);
    }

    private void popActivity(Event e){
        Activity ac = e.getActivity();
        Label label = new Label("Max Group Size: " + ac.getNum());
        label.setFont(new Font(20));
        evD.add(label,0 ,3);
        int i = 4;
        i = addIfNotNull(i, ac.getActivityType(), "Activity: ");
        i = addIfNotNull(i, ac.getDifficulty(), "Dificulty: ");
        i = addIfNotNull(i, ac.getStyle(), "Style: ");
        i = addIfNotNull(i, String.valueOf(ac.getDistance()), "Distance: ");
        i = addIfNotNull(i, String.valueOf(ac.getElevation()), "Elevation: ");
        i = addIfNotNull(i, String.valueOf(ac.getCost()), "Cost: ");
        i = addIfNotNull(i, String.valueOf(ac.getDuration()), "Duration: ");
        i = addIfNotNull(i, ac.getDes(), "Description: ");
    }

    private int addIfNotNull(int i, String check, String Label){
        if (!(check == null || check.isEmpty())){
            Label label = new Label(Label + check);
            label.setFont(new Font(20));
            evD.add(label, 0, i);
            i++;
        }
        return i;
    }
}
