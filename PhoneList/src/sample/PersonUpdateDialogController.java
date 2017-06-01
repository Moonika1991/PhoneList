package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Person;

public class PersonUpdateDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneNumberField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Person person) {
        this.person = person;

        nameField.setText(person.getName());
        phoneNumberField.setText(person.getPhoneNumber());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setName(nameField.getText());
            person.setPhoneNumber(phoneNumberField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (phoneNumberField.getText() == null || phoneNumberField.getText().length() == 0) {
            errorMessage += "No valid phone number!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(phoneNumberField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid phone number (must be a number)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
