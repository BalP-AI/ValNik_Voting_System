package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Handler;
import sample.Security;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Scanner;

public class ElectionCommitteeController
{
    @FXML
    private Button terminate;
    @FXML
    private TextField username;
    @FXML
    private TextField mastercode;
    @FXML
    private Button start;

    Handler handler;
    Security sec = new Security();

    public void initiate() throws Exception
    {
        handler.login(username.getText(), mastercode.getText());
    }

    public void inject(Handler handler)
    {
        this.handler = handler;
    }

    //todo add checks of empty text fields and notations of wrong passwords

    public void checkdata() throws Exception
    {
        File myObj = new File("usersfinal.txt");

        try
        {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                String data = myReader.nextLine();
                String info[] = data.split(";");

                String name = info[0];
                String email = info[1];
                //these are decoded back to byte[]
                byte[] encryptedpasswd = Base64.getDecoder().decode(info[2]);
                byte[] salt = Base64.getDecoder().decode(info[3]);

                if (email.equals("bob@hotmail.com"))
                {
                    if (sec.decrypt(encryptedpasswd, sec.getPrivKey()).equals(sec.sha256(mastercode.getText(), salt)))
                    {
                        handler.openvotewindow();
                        username.clear();
                        mastercode.clear();
                        start.setVisible(false);
                        terminate.setVisible(true);
                    }

                }
            }
            myReader.close();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void completevoting()
    {
            //todo add the final stage here
        Platform.exit();

    }




}
