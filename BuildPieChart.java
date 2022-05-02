import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BuildPieChart extends Application {
    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Job Total Summary");
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);

        Button btEditJob = new Button("Edit Job");
        btEditJob.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        btEditJob.setFont(Font.font("Times New Roman", 16));
        btEditJob.setTextFill(Color.WHITE);
        btEditJob.setAlignment(Pos.BOTTOM_LEFT);

      	/*
        Button btClose = new Button("Close Job");
        btClose.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        btClose.setFont(Font.font("Times New Roman",16));
        btClose.setTextFill(Color.WHITE);
        btClose.setAlignment(Pos.BOTTOM_RIGHT);
        */
        try {
            User user = User.initializeLastUser();
            Job job = Job.initializeLastJob(user);

            ObservableList<PieChart.Data> data = FXCollections.observableArrayList
                    (new PieChart.Data("Costs - $" + job.getCost(), (job.getCost() / job.getInvoice()) * 100),
                            new PieChart.Data("Net Profits - $" + job.getProfit(), (job.getProfit() / job.getInvoice()) * 100));

            final PieChart pieChart = new PieChart(data);
            pieChart.setTitle("Total Summary for Job#" + job.getJobNum());

            ((Group) scene.getRoot()).getChildren().add(pieChart);
            ((Group) scene.getRoot()).getChildren().add(btEditJob);
            //((Group) scene.getRoot()).getChildren().add(btClose);calc
        }
        catch(Exception ex){ex.printStackTrace();}

        btEditJob.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new BuildEditScreen().start(new Stage());
                        ((Node) (event.getSource())).getScene().getWindow().hide();
                    }
                });
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}