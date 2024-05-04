
package client.scenes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import commons.FileCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class MainPageCtrl implements Initializable {

    private final MainCtrl mainCtrl;

    private FileCode fileCode;

    @FXML
    private TextArea fileContentTextArea; // Reference to TextArea in FXML

    /**
     *
     * @param mainCtrl
     */
    @Inject
    public MainPageCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * picks the file and creates a fileCode object th it's content
     * @param actionEvent
     * @throws IOException
     */

    @FXML
    public void pickFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Java File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Java Files", "*.java"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            fileCode = new FileCode(selectedFile.toPath());
            // Set the file content to the TextArea
            fileContentTextArea.setText(fileCode.getContent());
        }
    }
}