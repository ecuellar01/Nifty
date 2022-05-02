import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
public class BuildHourlyScreen extends Application{
	@Override
	public void start(Stage primaryStage){
		    
		    Double hourly = BuildTotalsScreen.hourly;
			StackPane pane = new StackPane();
			pane.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
			Label label = new Label("Hourly pay for: " + Nifty.user.getUserName()
			+ '\n' + "was: $" + hourly);
			label.setFont(Font.font("Arial Black",30));
			label.setTextFill(Color.WHITE);
			
			pane.getChildren().add(label);
			Scene scene = new Scene(pane,500,500);
			primaryStage.setTitle("Nifty Hourly Pay");
			primaryStage.setScene(scene);
			primaryStage.show();

	}
public static void main(String[] args) {
	launch(args);
}

}
