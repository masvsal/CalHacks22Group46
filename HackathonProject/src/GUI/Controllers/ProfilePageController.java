package GUI.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;

public class ProfilePageController extends Controller {
	private String username;
	private String [] interests;
	
	public void goToHome(ActionEvent event) throws IOException {
		goToHomePage(event);
	}
	public void goToEvents(ActionEvent event) throws IOException {
		goToEventsPage(event);
	}
}
