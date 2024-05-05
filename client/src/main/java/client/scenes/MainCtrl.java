
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private MainPageCtrl mainPage;
    private Scene overview;

    /**
     * initialises the app
     * @param primaryStage
     * @param overview
     */
    public void initialize(Stage primaryStage, Pair<MainPageCtrl, Parent> overview) {
        this.primaryStage = primaryStage;
        this.mainPage = overview.getKey();
        this.overview = new Scene(overview.getValue());


        showMainPage();
        primaryStage.show();
    }

    /**
     * opens the main page
     */
    public void showMainPage() {
        primaryStage.setTitle("Main Page");
        primaryStage.setFullScreen(true);

        primaryStage.setScene(overview);
    }


}