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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;

public class BuildJobScreen extends Application {
    @Override
    public void start(Stage primaryStage) {

        GridPane pane = new GridPane();
        pane.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(30, 30, 30, 30));
        pane.setVgap(10);
        pane.setHgap(20);

        Label hourLabel = new Label("Hours Worked:");
        hourLabel.setFont(Font.font("Arial Black", 15));
        hourLabel.setTextFill(Color.WHITE);
        Label costLabel = new Label("Cost to Complete:");
        costLabel.setFont(Font.font("Arial Black", 15));
        costLabel.setTextFill(Color.WHITE);
        Label invoiceLabel = new Label("Invoice Amount:");
        invoiceLabel.setFont(Font.font("Arial Black", 15));
        invoiceLabel.setTextFill(Color.WHITE);
        Label dateLabel = new Label("Enter Date Below:");
        Label dateLabelM = new Label("(mm):");
        Label dateLabelD = new Label("(dd):");
        Label dateLabelY = new Label("(yyyy):");
        dateLabel.setFont(Font.font("Arial Black", 15));
        dateLabel.setTextFill(Color.WHITE);
        dateLabelM.setFont(Font.font("Arial Black", 15));
        dateLabelM.setTextFill(Color.WHITE);
        dateLabelD.setFont(Font.font("Arial Black", 15));
        dateLabelD.setTextFill(Color.WHITE);
        dateLabelY.setFont(Font.font("Arial Black", 15));
        dateLabelY.setTextFill(Color.WHITE);

        javafx.scene.control.TextField hourField = new javafx.scene.control.TextField();
        javafx.scene.control.TextField costField = new javafx.scene.control.TextField();
        javafx.scene.control.TextField invoiceField = new javafx.scene.control.TextField();
        javafx.scene.control.TextField dateFieldM = new javafx.scene.control.TextField();
        javafx.scene.control.TextField dateFieldD = new javafx.scene.control.TextField();
        javafx.scene.control.TextField dateFieldY = new javafx.scene.control.TextField();

        hourField.setPrefColumnCount(2);
        costField.setPrefColumnCount(4);
        invoiceField.setPrefColumnCount(4);
        dateFieldM.setPrefColumnCount(2);
        dateFieldD.setPrefColumnCount(2);
        dateFieldY.setPrefColumnCount(4);

        pane.add(hourLabel, 0, 0);
        pane.add(hourField, 1, 0);
        pane.add(costLabel, 0, 1);
        pane.add(costField, 1, 1);
        pane.add(invoiceLabel, 0, 2);
        pane.add(invoiceField, 1, 2);
        pane.add(dateLabel, 0, 3);
        pane.add(dateLabelM, 0, 4);
        pane.add(dateFieldM, 1, 4);
        pane.add(dateLabelD, 0, 5);
        pane.add(dateFieldD, 1, 5);
        pane.add(dateLabelY, 0, 6);
        pane.add(dateFieldY, 1, 6);

        /*
        javafx.scene.control.Button btViewJob = new javafx.scene.control.Button("View Job");
        btViewJob.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        btViewJob.setFont(Font.font("Arial Black", 15));
        btViewJob.setTextFill(Color.WHITE);
        pane.add(btViewJob, 0, 7);
        GridPane.setHalignment(btViewJob, HPos.LEFT);
        */

        javafx.scene.control.Button btTotals = new javafx.scene.control.Button("Job Info");
        btTotals.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        btTotals.setFont(Font.font("Arial Black", 15));
        btTotals.setTextFill(Color.WHITE);
        pane.add(btTotals, 0, 7);
        GridPane.setHalignment(btTotals, HPos.CENTER);

        javafx.scene.control.Button btAddJob = new Button("Add Job");
        btAddJob.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        btAddJob.setFont(Font.font("Arial Black", 15));
        btAddJob.setTextFill(Color.WHITE);
        pane.add(btAddJob, 2, 7);
        GridPane.setHalignment(btAddJob, HPos.RIGHT);

        btTotals.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new BuildTotalsScreen().start(new Stage());
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    }
                });
            }
        });
        btAddJob.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int hoursWorked = Integer.parseInt(hourField.getText());
                double jobCost = Double.parseDouble(costField.getText());
                double jobInvoice = Double.parseDouble(invoiceField.getText());
                String month = dateFieldM.getText();
                String day = dateFieldD.getText();
                String year = dateFieldY.getText();
                //must use database to get highest value in column and add one or auto increment and have query to get last job # for each User
                //int jobNum = 1;
                try {
                    User user = User.initializeLastUser();
                    int m = Integer.parseInt(month);
                    int d = Integer.parseInt(day);
                    int y = Integer.parseInt(year);
                    java.sql.Date userDate = new java.sql.Date(m, d, y);
                    Job job = new Job(user, hoursWorked, jobCost, jobInvoice, userDate);

                    job.insertDatabase();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Job Added!");
                    alert.setHeaderText(null);
                    alert.setContentText("The job was added with a Reference Number of " + '\n' +
                            Job.refNum() + " under account " + job.getCurrentUser().getUserName() + ".");
                    alert.showAndWait();

                    hourField.clear();
                    costField.clear();
                    invoiceField.clear();
                    dateFieldY.clear();
                    dateFieldD.clear();
                    dateFieldM.clear();
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(pane);
        primaryStage.setTitle("10-9-Nifty New Job");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }

}
