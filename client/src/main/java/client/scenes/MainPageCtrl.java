
package client.scenes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import commons.FileCode;
import commons.Method;
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

    @FXML
    private TextArea complexities;

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
        displayComplexities();
    }

    /**
     * displays the top3complexities
     */
    public void displayComplexities () {
        List<Method> methods = fileCode.getMethods();
        Collections.sort(methods, Comparator.comparingInt(Method::getComplexity).reversed());
        StringBuilder topMethods = new StringBuilder();
        int count = 0;
        for (Method method : methods) {
            if (count >= 3) break;
            topMethods.append("Method Name: ").append(method.getName()).append("\n");
            topMethods.append("Method Complexity: ").append(method.getComplexity()).append("\n\n");
            count++;
        }
        complexities.setText(topMethods.toString());
    }


}