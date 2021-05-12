package Controllers;

import javafx.fxml.FXML;

import java.awt.*;
import java.sql.SQLOutput;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import sample.Handler;

public class OpenScreenController
{

    @FXML
    private TextField username;
    @FXML
    private TextField passwd;
    @FXML
    private Button go;

    Handler handler;


    public void login() {
        handler.login(username.getText(),passwd.getText());
    }

    public void inject(Handler handler){this.handler = handler;}


}
