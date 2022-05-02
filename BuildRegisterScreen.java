import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.geometry.*;


public class BuildRegisterScreen extends Application{
	@Override
	public void start(Stage primaryStage) {
		GridPane pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(30,30,30,30));
		pane.setVgap(10);
		pane.setHgap(20);
		
		Label userLabel = new Label("Username:");
		userLabel.setFont(Font.font("Arial Black",16));
		userLabel.setTextFill(Color.WHITE);
		Label passwordLabel = new Label("Password:");
		passwordLabel.setFont(Font.font("Arial Black",16));
		passwordLabel.setTextFill(Color.WHITE);
		Label firstNameLabel = new Label("First Name:");
		firstNameLabel.setFont(Font.font("Arial Black",16));
		firstNameLabel.setTextFill(Color.WHITE);
		Label lastNameLabel = new Label("Last Name");
		lastNameLabel.setFont(Font.font("Arial Black",16));
		lastNameLabel.setTextFill(Color.WHITE);
		
		TextField userField = new TextField();
		TextField passwordField = new PasswordField();
		TextField fNameField = new TextField();
		TextField lNameField = new TextField();
		
		pane.add(userLabel, 0, 0);
		pane.add(userField, 1, 0);
		pane.add(passwordLabel, 0, 1);
		pane.add(passwordField, 1, 1);
		pane.add(firstNameLabel, 0, 2);
		pane.add(fNameField, 1, 2);
		pane.add(lastNameLabel, 0, 3);
		pane.add(lNameField, 1, 3);
		
		Button btReg = new Button("Create Account");
		btReg.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
		btReg.setFont(Font.font("Arial Black",16));
		btReg.setTextFill(Color.WHITE);
		pane.add(btReg, 1, 4);
		GridPane.setHalignment(btReg, HPos.LEFT);

		btReg.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (User.duplicateUser(userField.getText())) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("USERNAME ALREADY IN USE");
						alert.setHeaderText(null);
						alert.setContentText("Username is already in use." +
								"Try another username.");
						alert.showAndWait();
					} else {
						String user = userField.getText();
						String password = passwordField.getText();
						String fName = fNameField.getText();
						String lName = lNameField.getText();

						User newUser = new User(user, password, fName, lName);

						newUser.insertDatabase();

						((Node) (event.getSource())).getScene().getWindow().hide();
					}
				}
				catch(Exception ex){ex.printStackTrace();}
			}});
		
		Scene scene = new Scene(pane);
		primaryStage.setTitle("10-9-Nifty Create Account");
		primaryStage.setScene(scene);
		primaryStage.show();
		Platform.setImplicitExit(false);

}
	
public static void main(String[] args) {
		launch(args);
	}
}