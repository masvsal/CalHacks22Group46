package GUI.Controllers;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
    //REQUIRES:
    //MODIFIES: initializes and displays welcome page.
    //EFFECTS:
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/welcome/WelcomePage.fxml"));
			Parent root = loader.load();
			
			Scene welcomePage = new Scene(root);
			
			primaryStage.setTitle("App");
			primaryStage.setScene(welcomePage);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
