package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.Person;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Phone List");
        this.primaryStage.setMinHeight(450);
        this.primaryStage.setMinWidth(650);

        showPersonOverview();
    }

    private void showPersonOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/PersonOverview.fxml"));
            AnchorPane window = (AnchorPane) loader.load();

            Scene scene = new Scene(window);
            primaryStage.setScene(scene);
            primaryStage.show();

            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Main() {
        // Add some sample data
        personData.add(new Person("Hans, Muster", "333222555"));
        personData.add(new Person("Ruth, Mueller", "666777444"));
        personData.add(new Person("Heinz, Kurz", "333222999"));
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
