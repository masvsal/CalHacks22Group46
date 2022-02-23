package GUI.Controllers;

import java.io.IOException;

import javafx.event.ActionEvent;

public class EventsPageController extends Controller {
	public void goToHome(ActionEvent event) throws IOException {
		goToHomePage(event);
	}
	public void goToProfile(ActionEvent event) throws IOException {
		goToProfilePage(event);
	}
}
