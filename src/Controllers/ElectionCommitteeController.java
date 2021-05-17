package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Handler;
import sample.Security;
import sample.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLOutput;
import java.util.Base64;
import java.util.Hashtable;
import java.util.Scanner;

public class ElectionCommitteeController
{

    @FXML
    private TextField username;
    @FXML
    private TextField mastercode;
    @FXML
    private Button start;

    Handler handler;
    Security sec = new Security();

    public void initiate()
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

}
