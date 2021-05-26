package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.Handler;
import sample.Security;

import java.util.Hashtable;

public class resultsController {

    @FXML
    private ListView listview;
    Hashtable<String,Integer> results = new Hashtable<>();

    public void inject(Handler h ){
        h.getResults();
    }


    public void setResults(Hashtable results){
        this.results = results;
        set(results);

    }

    protected void set(Hashtable results){
        results.forEach((key, value)->listview.getItems().add("name:" + key
                + "\t\t" + "votes : " + value));
    }


}