package distancing.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class SocialSimpApp extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
			FXMLLoader loader = new FXMLLoader();
			BorderPane root = (BorderPane) loader.load(getClass().getResource("SocialSimGui.fxml").openStream());
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
