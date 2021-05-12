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
        /**
         * Here we create an new Security object that is going to create the two new keys that we need
         * the public one and the private one then we are going to make then AES-256 key that we want for the judge
         * We create a byte array due to the fact that the encrypted text is always byte array
         * we create the key and immediately encoded it and make it a string so that we can cipher it
         * when we need to re use it as a key we will need to add that byte array to the decrypter and get our encoded String
         * then we simply need to decode it and make a Secret key again.
         **/
        Security sec = new Security();
        sec.writeKeys();  //Intitializes the keys

        byte[] cipher = sec.encrypt(sec.encodedaesKey(), sec.getPublKey());
        System.out.println("Cipher " + Arrays.toString(cipher));
        System.out.println("Decrypted " + sec.decrypt(cipher, sec.getPrivKey()));

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
