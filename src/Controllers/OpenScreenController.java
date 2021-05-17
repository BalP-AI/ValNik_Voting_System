package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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


    public void login()
    {

        handler.login(username.getText(), passwd.getText());
    }

    public void inject(Handler handler)
    {
        this.handler = handler;
    }


}
