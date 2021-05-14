package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class VotingScreen {

    @FXML
    protected ListView<String> listview;
    @FXML
    protected Label title;
    ArrayList<String> candidates;

    public void inject(ArrayList<String> candidates){
        this.candidates = candidates;
        for(String candidate : this.candidates)
            listview.getItems().add(candidate);
    }


    public void setTitle(String title)
    {
         this.title.setText(title);
    }
}
