package Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Ballot;
import sample.Handler;
import java.util.ArrayList;

public class VotingScreen
{
    @FXML
    protected Button vote;
    @FXML
    protected ListView<CheckBox> listview;
    @FXML
    protected Label title;
    @FXML
    protected CheckBox candi;

    Handler handler ;
    ArrayList<String> candidates;
    int maxnumofvotes;
    String useremail;
    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    public void inject(ArrayList<String> candidates)
    {
        this.candidates = candidates;
        listview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (String candidate : this.candidates)
        {
            candi = new CheckBox();
            candi.setText(candidate);
            listview.getItems().add(candi);
        }
    }

    public void submit()  //todo might move to the handler if needed now it works from here as well
    {

        if (numcheck() > maxnumofvotes)
        {
            System.out.println("more items selected");
        }
        //this check is already implemetned on the window beofre the voting one but the check is added just to make sure
        else if (handler.getUsers().get(useremail).getHasVoted()){
            System.out.println("you have already voted once ");

        }
        else
        {
            Ballot bal = new Ballot(useremail, voted(), maxnumofvotes);

            handler.getUsers().get(useremail).setHasVoted();
            handler.addtoballotbox(bal);    //todo the ballot needs to be verified and the other stuff
            handler.shufflebox();
            System.out.println("Vote submitted , ballot box suffled!");

            Stage stage = (Stage) vote.getScene().getWindow();
            stage.close();
        }
    }

    private int numcheck()
    {
        int selected=0;
        for (CheckBox box : this.listview.getItems())
        {
            if(box.isSelected())
            {
                selected++;
            }
        }
        return selected;
    }

    private ArrayList<String> voted()
    {
        ArrayList<String> list = new ArrayList<String>();
        for (CheckBox box : this.listview.getItems())
        {
            if(box.isSelected())
            {
                list.add(box.getText());
            }
        }
        return list;
    }


    public void setUseremail(String useremail)
    {
        this.useremail="";
        this.useremail = useremail;
    }

    public void setMaxnumofvotes(int maxnumofvotes)
    {
        this.maxnumofvotes = maxnumofvotes;
    }

    public void setTitle(String title)
    {
        this.title.setText(title);

    }
}
