package Controllers;

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

public class OpenScreenController
{

    @FXML
    private TextField username;
    @FXML
    private TextField passwd;
    @FXML
    private Button go;

    Handler handler;
    Security sec = new Security();

    public void login() throws Exception
    {

        handler.login(username.getText(), passwd.getText());
    }

    public void inject(Handler handler)
    {
        this.handler = handler;
    }



}
