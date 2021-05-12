//Icsd18174 Chrysovalantis Pateiniotis
//Icsd18218 Nikos Tzekas
package sample;

import Controllers.OpenScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Security sec = new Security();
        sec.writeKeys();  //Intitializes the keys

        byte[] cipher =sec.encrypt("HellO Tallia", sec.getPublKey());
        System.out.println("Cipher "+ Arrays.toString(cipher) );
        System.out.println( "Decrypted " + sec.decrypt(cipher, sec.getPrivKey()));

        Handler handler = new Handler();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/OpenScreen.fxml"));
        Parent root = loader.load();
        OpenScreenController c = loader.getController();
        c.inject(handler);
        primaryStage.setTitle("ValNik Voting System");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
