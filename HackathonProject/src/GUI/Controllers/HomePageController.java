package GUI.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import Backend.Events_DB;
import GUI.Events.Event;
import GUI.Events.EventList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
	
	@FXML private GridPane eventGallery;
	
	public HomePageController() {
		events = new EventList();
	}

    //FUNCTIONS FOR MOVING BETWEEN PAGES:
    //home->profile
	public void goToProfile(ActionEvent event) throws IOException {goToProfilePage(event);}
	//home->events
	public void goToEvents(ActionEvent event) throws IOException {goToEventsPage(event);}

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
        Events_DB events_db = new Events_DB();
        ArrayList<Event>  eventArrayList = events_db.getAll();
        for (Event e: eventArrayList){
            events.addItem(e);
        }
    }

    //loads, filters, and displays current events to the user.
	public void addEvents() {
		eventGallery.getChildren().clear();
        loadEvents();
		EventList filteredEvents = filter();
		displayEvents(filteredEvents);
	}

    public void filterEvents(){
        eventGallery.getChildren().clear();
        EventList filteredEvents = filter();
        displayEvents(filteredEvents);
    }

    //displays given events to the user
    private void displayEvents(EventList filteredEvents){
        int i = 0;
        for (Event e: filteredEvents) {
            VBox eventBox = new VBox();
            eventGallery.add(eventBox, 0, i*2);

            Label eventName = new Label(e.getName());
            Label eventLocation = new Label(e.getLocation());
            Label eventStart = new Label("Start at " + e.getStart());
            Label eventDate = new Label(e.getDate());
            Label eventActivity = new Label("\n" + e.getActivity().getActivityType());

            eventBox.getChildren().add(eventName);
            eventBox.getChildren().add(eventLocation);
            eventBox.getChildren().add(eventDate);
            eventBox.getChildren().add(eventStart);
            eventBox.getChildren().add(eventActivity);
            i++;
        }
    }
	
	private int oddOrEven(int i) {
		if (i % 2 == 0) {
			return 2;
		}
		return 1;
	}
 }
