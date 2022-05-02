import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;

public class BuildLoginScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {
        GridPane pane = new GridPane();
        pane.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.MIDNIGHTBLUE, null, null)));
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(30, 30, 30, 30));
        pane.setVgap(10);
        pane.setHgap(20);

        javafx.scene.control.Label userLabel = new javafx.scene.control.Label("Username:");
        userLabel.setFont(javafx.scene.text.Font.font("Arial Black", 16));
        userLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        javafx.scene.control.Label passwordLabel = new javafx.scene.control.Label("Password:");
        passwordLabel.setFont(javafx.scene.text.Font.font("Arial Black", 16));
        passwordLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        javafx.scene.control.TextField userField = new javafx.scene.control.TextField();
        javafx.scene.control.TextField passwordField = new PasswordField();

        pane.add(userLabel, 0, 0);
        pane.add(userField, 1, 0);
        pane.add(passwordLabel, 0, 1);
        pane.add(passwordField, 1, 1);

        javafx.scene.control.Button btLogin = new javafx.scene.control.Button("Login");
        btLogin.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.ROYALBLUE, null, null)));
        btLogin.setFont(javafx.scene.text.Font.font("Arial Black", 16));
        btLogin.setTextFill(javafx.scene.paint.Color.WHITE);
        pane.add(btLogin, 1, 2);
        GridPane.setHalignment(btLogin, HPos.LEFT);

        javafx.scene.control.Button btReg = new javafx.scene.control.Button("Register for Account");
        btReg.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.ROYALBLUE, null, null)));
        btReg.setFont(javafx.scene.text.Font.font("Arial Black", 12));
        btReg.setTextFill(javafx.scene.paint.Color.WHITE);
        pane.add(btReg, 1, 3);
        GridPane.setHalignment(btReg, HPos.LEFT);

        javafx.scene.control.Label passMessage = new Label();
        passMessage.setText("Username/Password Combination not found!" + '\n' +
                "Try again or Register for Account.");

        passMessage.setFont(javafx.scene.text.Font.font("Arial Black", 12));
        passMessage.setTextFill(javafx.scene.paint.Color.MIDNIGHTBLUE);

        pane.add(passMessage, 1, 4);

        passMessage.setFont(Font.font("Arial Black", 12));

        btReg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new BuildRegisterScreen().start(new Stage());
                    }
                });
            }
        });
        btLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (User.queryUser(userField.getText(), passwordField.getText())) {
                        User user = User.initializeUser(userField.getText(), passwordField.getText());
                        user.insertLastUser();
                        System.out.print(user.getFirstName());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Hello " + user.getFirstName() + ". Welcome to Nifty!");
                        alert.setHeaderText(null);
                        alert.setContentText("Welcome to Nifty ver. 1.2! This app takes job information" +
                                " and calculates income based on said information.");
                        alert.showAndWait();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                new BuildJobScreen().start(new Stage());
                                ((Node) (event.getSource())).getScene().getWindow().hide();
                            }
                        });
                    } else {
                        passMessage.setTextFill(Color.RED);
                        userField.clear();
                        passwordField.clear();

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(pane);
        primaryStage.setTitle("10-9-Nifty Login");
        primaryStage.setScene(scene);
        primaryStage.show();
        Platform.setImplicitExit(false);

    }

    public static void main(String[] args){
        launch(args);
    }
}