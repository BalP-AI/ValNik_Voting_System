//Icsd18174 Chrysovalantis Pateiniotis
//Icsd18218 Nikos Tzekas
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Security sec = new Security();
        sec.writeKeys();
        sec.getPrivKey();
        sec.getPublKey();
        Parent root = FXMLLoader.load(getClass().getResource("../FXMLS/OpenScreen.fxml"));
        primaryStage.setTitle("ValNik Voting System");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
