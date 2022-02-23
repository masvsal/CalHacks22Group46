package GUI.Controllers;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//abstract class that defines functionality for moving between pages.
public abstract class Controller {
    //REQUIRES:
    //MODIFIES: initializes and displays home page
    //EFFECTS:
	public void goToHomePage(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/home/HomePage.fxml");
        System.out.println(url);
		FXMLLoader loader = new FXMLLoader(url);
		Parent parent = loader.load();
		Scene page = new Scene(parent);
	
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	
		primaryStage.setScene(page);
		primaryStage.show();
	}
    //REQUIRES:
    //MODIFIES: initializes and displays events page
    //EFFECTS:
	public void goToEventsPage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/events/EventsPage.fxml"));
		Parent parent = loader.load();
		Scene page = new Scene(parent);
	
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	
		primaryStage.setScene(page);
		primaryStage.show();
	}
    //REQUIRES:
    //MODIFIES: initializes and displays profile page
    //EFFECTS:
	public void goToProfilePage(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/ProfilePage.fxml"));
		Parent parent = loader.load();
		Scene page = new Scene(parent);
	
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
	
		primaryStage.setScene(page);
		primaryStage.show();
	}
}
