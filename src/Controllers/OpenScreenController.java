package Controllers;

import javafx.fxml.FXML;

import java.awt.*;
import java.sql.SQLOutput;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class OpenScreenController
{

    @FXML
    private TextField username;
    @FXML
    private TextField passwd;
    @FXML
    private Button go;


    public void data_check()
    {
            System.out.println(username.getText());
            System.out.println(passwd.getText());
    }


}
