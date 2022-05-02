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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BuildTotalsScreen extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(30, 30, 30, 30));
        pane.setVgap(10);
        pane.setHgap(20);

        Label singleLabel = new Label("View single job by Ref#:");
        singleLabel.setFont(Font.font("Arial Black", 16));
        singleLabel.setTextFill(Color.WHITE);
        Label dateLabelEnter = new Label("Enter Date Range:");
        Label dateLabel = new Label("Start:");
        Label dateLabelM = new Label("(mm):");
        Label dateLabelD = new Label("(dd):");
        Label dateLabelY = new Label("(yyyy):");

        dateLabelEnter.setFont(Font.font("Arial Black", 16));
        dateLabelEnter.setTextFill(Color.WHITE);
        dateLabel.setFont(Font.font("Arial Black", 16));
        dateLabel.setTextFill(Color.WHITE);
        dateLabelM.setFont(Font.font("Arial Black", 16));
        dateLabelM.setTextFill(Color.WHITE);
        dateLabelD.setFont(Font.font("Arial Black", 16));
        dateLabelD.setTextFill(Color.WHITE);
        dateLabelY.setFont(Font.font("Arial Black", 16));
        dateLabelY.setTextFill(Color.WHITE);

        Label dateLabel2 = new Label("End:");
        Label dateLabelM2 = new Label("(mm):");
        Label dateLabelD2 = new Label("(dd):");
        Label dateLabelY2 = new Label("(yyyy):");

        dateLabel2.setFont(Font.font("Arial Black", 16));
        dateLabel2.setTextFill(Color.WHITE);
        dateLabelM2.setFont(Font.font("Arial Black", 16));
        dateLabelM2.setTextFill(Color.WHITE);
        dateLabelD2.setFont(Font.font("Arial Black", 16));
        dateLabelD2.setTextFill(Color.WHITE);
        dateLabelY2.setFont(Font.font("Arial Black", 16));
        dateLabelY2.setTextFill(Color.WHITE);

        TextField hourField = new javafx.scene.control.TextField();
        TextField costField = new javafx.scene.control.TextField();
        TextField invoiceField = new javafx.scene.control.TextField();
        TextField dateFieldM = new javafx.scene.control.TextField();
        TextField dateFieldD = new javafx.scene.control.TextField();
        TextField dateFieldY = new javafx.scene.control.TextField();
        TextField dateFieldM2 = new javafx.scene.control.TextField();
        TextField dateFieldD2 = new javafx.scene.control.TextField();
        TextField dateFieldY2 = new javafx.scene.control.TextField();
        TextField singleField = new TextField();

        hourField.setPrefColumnCount(2);
        costField.setPrefColumnCount(4);
        invoiceField.setPrefColumnCount(4);
        dateFieldM.setPrefColumnCount(2);
        dateFieldD.setPrefColumnCount(2);
        dateFieldY.setPrefColumnCount(4);
        dateFieldM2.setPrefColumnCount(2);
        dateFieldD2.setPrefColumnCount(2);
        dateFieldY2.setPrefColumnCount(4);

        pane.add(singleLabel, 0, 0);
        pane.add(singleField, 1, 0);
        pane.add(dateLabelEnter, 0, 2);
        pane.add(dateLabel, 0, 3);
        pane.add(dateLabelM, 1, 2);
        pane.add(dateFieldM, 1, 3);
        pane.add(dateLabelD, 2, 2);
        pane.add(dateFieldD, 2, 3);
        pane.add(dateLabelY, 3, 2);
        pane.add(dateFieldY, 3, 3);
        pane.add(dateLabel2, 0, 4);
        pane.add(dateFieldM2, 1, 4);
        pane.add(dateFieldD2, 2, 4);
        pane.add(dateFieldY2, 3, 4);

        Button btSingle = new Button("View Summary");
        btSingle.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        btSingle.setFont(Font.font("Arial Black", 16));
        btSingle.setTextFill(Color.WHITE);
        pane.add(btSingle, 2, 0);
        GridPane.setHalignment(btSingle, HPos.CENTER);

        Button btTotal = new Button("View Totals");
        btTotal.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        btTotal.setFont(Font.font("Arial Black", 16));
        btTotal.setTextFill(Color.WHITE);
        pane.add(btTotal, 3, 3);
        GridPane.setHalignment(btTotal, HPos.CENTER);

        Button btClose = new Button("Log Off");
        btTotal.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        btTotal.setFont(Font.font("Arial Black", 16));
        btTotal.setTextFill(Color.WHITE);
        pane.add(btClose, 3, 5);
        GridPane.setHalignment(btClose, HPos.CENTER);

        btSingle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String ref = singleField.getText();
                    int refNum = Integer.parseInt(ref);
                    User user1 = User.initializeLastUser();
                    Job job1 = Job.initializeJob(refNum, user1);
                    job1.insertLastJob();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new BuildPieChart().start(new Stage());
                    }
                });
            }
        });

        btTotal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String monthS = dateFieldM.getText();
                    String dayS = dateFieldD.getText();
                    String yearS = dateFieldY.getText();
                    String monthF = dateFieldM.getText();
                    String dayF = dateFieldD.getText();
                    String yearF = dateFieldY.getText();
                    int mS = Integer.parseInt(monthS);
                    int dS = Integer.parseInt(dayS);
                    int yS = Integer.parseInt(yearS);
                    int mF = Integer.parseInt(monthF);
                    int dF = Integer.parseInt(dayF);
                    int yF = Integer.parseInt(yearF);
                    java.sql.Date userDateS = new java.sql.Date(mS, dS, yS);
                    java.sql.Date userDateF = new java.sql.Date(mF, dF, yF);
                    User user = User.initializeLastUser();
                    Job job = new Job(user, 0, 0, 0, userDateS);
                    int hours = job.queryHours(userDateS, userDateF);
                    double profits = job.queryProfits(userDateS, userDateF);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Job Summary");
                    alert.setHeaderText(null);

                    if (hours == 0) {
                        alert.setContentText(user.getFirstName() + " " +
                                user.getLastName() + " did not work any hours.");
                        alert.showAndWait();
                    } else {
                         double hourly = profits / hours;

                        alert.setContentText(user.getFirstName() + " " + user.getLastName() +
                                " made $" + profits + " in profits from " + userDateS + " to " + userDateF + " with " +
                                "an hourly rate of $" + hourly + ".");
                        alert.showAndWait();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Logging Off");
                alert.setHeaderText(null);
                alert.setContentText("Thank you for using Nifty ver. 2.0! We hope to see you again!");

                ((Node) (event.getSource())).getScene().getWindow().hide();
            }
        });


        Scene scene = new Scene(pane);
        primaryStage.setTitle("10-9-Nifty Totals");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}