package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.model.Person;

import java.io.*;

public class PersonOverviewController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> phoneNumberColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneNumberField;

    private Main main;

    public PersonOverviewController() {
    }

    @FXML
    private void initialize() {

        // Initialize the person table with the two columns.
        nameColumn.setCellValueFactory(
                cellData -> cellData.getValue().nameProperty());
        phoneNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().phoneNumberProperty());
        // Clear person details.
        showPersonDetails(null);
        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

    }

    public void setMain(Main main) {
        this.main = main;
        // Add observable list data to the table
        personTable.setItems(main.getPersonData());
        readFromFile();
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
            dataToFile();
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddPerson() {
        Person tempPerson = new Person();
        boolean okClicked = main.showPersonUpdateDialog(tempPerson);
        if (okClicked) {
            main.getPersonData().add(tempPerson);
            dataToFile();
        }
    }

    @FXML
    private void handleUpdatePerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = main.showPersonUpdateDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
                dataToFile();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            nameField.setText(person.getName());
            phoneNumberField.setText(person.getPhoneNumber());
        } else {
            // Person is null, remove all the text.
            nameField.setText("");
            phoneNumberField.setText("");
        }
    }

    private void dataToFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("PhoneBook.txt"))){
            int size = personTable.getItems().size();
            while(size != 0) {
                --size;
                bw.write(nameColumn.getCellData(size) + "." + phoneNumberColumn.getCellData(size) + "\n");
            }
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("PhoneBook.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    Person temp = new Person();
                    String[] parts = line.split("\\.");
                    temp.setName(parts[0]);
                    temp.setPhoneNumber(parts[1]);
                    main.getPersonData().add(temp);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("ERROR: unable to read file");
            e.printStackTrace();
        }
    }
}
