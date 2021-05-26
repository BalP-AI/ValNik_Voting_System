package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Handler;
import sample.Security;

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
        username.clear();
        passwd.clear();
    }

    public void inject(Handler handler)
    {
        this.handler = handler;
    }



}
