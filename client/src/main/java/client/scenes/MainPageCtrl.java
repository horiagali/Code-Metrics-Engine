
package client.scenes;

import com.google.inject.Inject;
import commons.FileCode;
import commons.Method;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class MainPageCtrl implements Initializable {

    private final MainCtrl mainCtrl;

    private FileCode fileCode;

    @FXML
    private TextArea fileContentTextArea; // Reference to TextArea in FXML

    @FXML
    private TextArea complexities;
    @FXML
    private TextArea percentage;
    @FXML
    private TextArea otherIssuesText;
    @FXML
    private TextField maxLinesInput;
    @FXML
    private TextField maxCharsInput;

    private int maxLength = 50;
    private int maxChars = 60;

    /**
     * @param mainCtrl
     */
    @Inject
    public MainPageCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        maxLinesInput.setText(String.valueOf(maxLength));
        maxCharsInput.setText(String.valueOf(maxChars));
    }

    /**
     * picks the file and creates a fileCode object th it's content
     *
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
            fileContentTextArea.setText(addLineNumbering(fileCode.getContent()));
        }
        displayComplexities();
        double n = 100 - fileCode.percentageOfMethodsInCamelCase();
        DecimalFormat df = new DecimalFormat("#.##");
        String roundedPercentage = df.format(n);
        percentage.setText("Percentage of methods that are not in camelCase is: " + roundedPercentage + "%");
        otherIssuesText.setText(createIssuesText());
    }

    /**
     * Adds line numbering to the code file.
     * @param content The original content of the code file as a string.
     * @return The content with line numbers added before each line.
     */
    private String addLineNumbering(String content) {
        String[] lines = content.split("\n");

        StringBuilder numberedContent = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            numberedContent.append(i + 1)
                    .append(".     ")
                    .append(lines[i])
                    .append("\n");
        }

        return numberedContent.toString();
    }


    /**
     * displays the top3complexities
     */
    public void displayComplexities() {
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

    /**
     * Creates the text with extra issues
     *
     * @return returns the text with the extra issues
     */
    public String createIssuesText() {
        String out = "";
        for (Method m : fileCode.getMethods()) {
            if (m.length() > maxLength)
                out += "Method " + m.getName() + " is too long (" + m.length() + " lines)\n";
        }
        List<Integer> lines = linesWithMoreThanXCharacters();
        if (!lines.isEmpty()) {
            out += "These lines have too many characters:\n";
            for (int i = 0; i < lines.size() - 1; i++) {
                out += lines.get(i) + ", ";
            }
            out += lines.get(lines.size() - 1);
        }
        return out;
    }


    /**
     * Returns the lines with more than 100 characters. Does not count comments
     *
     * @return List of lines with more than 100 characters
     */
    public List<Integer> linesWithMoreThanXCharacters() {
        int index = 1;
        List<Integer> longLines = new ArrayList<>();
        String[] lines = fileCode.getContent().split("\\r?\\n");

        for (String line : lines) {
            // Remove everything after the //
            String lineWithoutComments = line.replaceAll("//.*", "");

            if (lineWithoutComments.trim().length() > maxChars) {
                longLines.add(index);
            }
            index++;
        }

        return longLines;
    }



    /**
     * Applies the max number of chars and lines inputed by the user
     * @param actionEvent
     */
    @FXML
    public void applySettings(ActionEvent actionEvent) {
        try {
            maxLength = Integer.parseInt(maxLinesInput.getText());
            maxChars = Integer.parseInt(maxCharsInput.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Input", "Please enter valid integer values.");
        }
        otherIssuesText.setText(createIssuesText());

    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

