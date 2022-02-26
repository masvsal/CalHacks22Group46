package GUI.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import Backend.Events_DB;
import GUI.Events.Event;
import GUI.Events.EventList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class HomePageController extends Controller {
    //list of events
	private EventList events;

    //filtering field for events by event type
	@FXML private CheckBox BikingBox;
	@FXML private CheckBox CampingBox;
	@FXML private CheckBox ClimbingBox;
	@FXML private CheckBox HikingBox;
	@FXML private CheckBox SkiingBox;
    @FXML private ListView listView;
    private ChangeListener<Event> changeListener;
    private Event currentlySelectedEvent;
    @FXML private Button loadEvents;
    @FXML private Button newEvent;
    @FXML private Button next;
    @FXML private Button clear;
    @FXML private AnchorPane leftHandSide;
	
	public HomePageController() {
		events = new EventList();
	}

    private void setUpList(){
        changeListener = new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observableValue, Event event, Event t1) {
                currentlySelectedEvent = t1;
                System.out.println(t1);
            }
        };
        listView.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    //FUNCTIONS FOR MOVING BETWEEN PAGES:
    //home->profile
	public void goToProfile(ActionEvent event) throws IOException {goToProfilePage(event);}
	//home->events
	public void goToEvent(ActionEvent event) throws IOException {
        System.out.println(currentlySelectedEvent);
        goToEventsPage(event, currentlySelectedEvent);
    }
    public void newEvent(ActionEvent event) throws IOException {goToNewEventPage(event);}

    //returns an eventlist containing events in the event field that match currently selected filters
    //if no filters selected, returns all events.
	public EventList filter() {
        EventList filteredEvents = new EventList();
        ArrayList<CheckBox> selectedFilters = modifyFilters();

        if (selectedFilters.isEmpty()) {
            for (Event e: events) {filteredEvents.addItem(e);}
            return filteredEvents;
        }

		for (Event e : events) {
			for (CheckBox c: selectedFilters) {
                if (Objects.equals(c.getText(), e.getActivity().getActivityType())) {
                    filteredEvents.addItem(e);
                }
            }
		}
        return filteredEvents;
	}

    //returns list of currently selected filters
    public ArrayList<CheckBox> modifyFilters(){
        ArrayList<CheckBox> selectedFilters = initializeFilters();
        selectedFilters.removeIf(c -> !c.isSelected());
        return selectedFilters;
    }

    //returns a list of all filters
    private ArrayList<CheckBox> initializeFilters(){
        ArrayList<CheckBox> selectedFilters = new ArrayList<>();
        selectedFilters.add(BikingBox);
        selectedFilters.add(CampingBox);
        selectedFilters.add(ClimbingBox);
        selectedFilters.add(HikingBox);
        selectedFilters.add(SkiingBox);
        return selectedFilters;
    }

    //adds current events from database to eventList
    private void loadEvents(){
        setUpList();
        Events_DB events_db = new Events_DB();
        ArrayList<Event>  eventArrayList = events_db.getAll();
        for (Event e: eventArrayList){
            events.addItem(e);
        }
        loadEvents.setText("Refresh");
    }

    //loads, filters, and displays current events to the user.
	public void addEvents() {
		listView.getItems().clear();
        loadEvents();
		EventList filteredEvents = filter();
		displayEvents2(filteredEvents);
	}

    public void filterEvents(){
        listView.getItems().clear();
        EventList filteredEvents = filter();
        displayEvents2(filteredEvents);
    }

    private void displayEvents2(EventList filteredEvents){
        for (Event e: filteredEvents){
            listView.getItems().add(e);
        }
    }
 }
