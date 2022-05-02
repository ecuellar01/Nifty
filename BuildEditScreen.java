import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.geometry.*;
public class BuildEditScreen  extends Application{
	@Override
	public void start(Stage primaryStage) {

		GridPane pane = new GridPane();
		pane.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, null, null)));
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(30,30,30,30));
		pane.setVgap(10);
		pane.setHgap(20);
		
		Label hourLabel = new Label("Hours Worked:");
		hourLabel.setFont(Font.font("Arial Black",18));
		hourLabel.setTextFill(Color.WHITE);
		Label costLabel = new Label("Cost to Complete:");
		costLabel.setFont(Font.font("Arial Black",18));
		costLabel.setTextFill(Color.WHITE);
		Label invoiceLabel = new Label("Invoice Amount:");
		invoiceLabel.setFont(Font.font("Arial Black",18));
		invoiceLabel.setTextFill(Color.WHITE);
		Label dateLabel = new Label("Date:");
		Label dateLabelM = new Label("(mm):");
		Label dateLabelD = new Label("(dd):");
		Label dateLabelY = new Label("(yyyy):");
		dateLabel.setFont(Font.font("Arial Black",16));
		dateLabel.setTextFill(Color.WHITE);
		dateLabelM.setFont(Font.font("Arial Black",16));
		dateLabelM.setTextFill(Color.WHITE);
		dateLabelD.setFont(Font.font("Arial Black",16));
		dateLabelD.setTextFill(Color.WHITE);
		dateLabelY.setFont(Font.font("Arial Black",16));
		dateLabelY.setTextFill(Color.WHITE);
	
		TextField hourField = new TextField();
		TextField costField = new TextField();
		TextField invoiceField = new TextField();
		TextField dateFieldM = new TextField();
		TextField dateFieldD = new TextField();
		TextField dateFieldY = new TextField();
		
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
		Button btViewJob = new Button("View Job");
		btViewJob.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
		btViewJob.setFont(Font.font("Arial Black",15));
		btViewJob.setTextFill(Color.WHITE);
		pane.add(btViewJob, 0, 7);
		GridPane.setHalignment(btViewJob, HPos.LEFT);
		*/
		
		Button btEditJob = new Button("Save Job");
		btEditJob.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
		btEditJob.setFont(Font.font("Arial Black",15));
		btEditJob.setTextFill(Color.WHITE);
		pane.add(btEditJob, 2, 7);
		GridPane.setHalignment(btEditJob, HPos.RIGHT);


		btEditJob.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int hoursWorked = Integer.parseInt(hourField.getText());
				double jobCost = Double.parseDouble(costField.getText());
				double jobInvoice = Double.parseDouble(invoiceField.getText());
				String month = dateFieldM.getText();
				String day = dateFieldD.getText();
				String year = dateFieldY.getText();
				//must use database to get highest value in column and add one
				//int jobNum = 1;
				try {
					int m = Integer.parseInt(month);
					int d = Integer.parseInt(day);
					int y = Integer.parseInt(year);
					java.sql.Date userDate = new java.sql.Date(m,d,y);
					User user = User.initializeLastUser();
					Job job = new Job(user, hoursWorked, jobCost, jobInvoice, userDate);
					job.updateDatabase(Job.lastrefNum());

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Job Saved!");
					alert.setHeaderText(null);
					alert.setContentText("The job was saved with a Reference Number of " + '\n' +
							Job.refNum() + " under account " + job.getCurrentUser().getUserName() + ".");
					alert.showAndWait();

					((Node) (event.getSource())).getScene().getWindow().hide();
				}
				catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		Scene scene = new Scene(pane);
		primaryStage.setTitle("10-9-Nifty Edit Job");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
public static void main(String[] args) {
		launch(args);
	}
} 


