package sample;

import Controllers.OpenScreenController;
import Controllers.VotingScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Handler {

    Hashtable<String,User> users;
    Ballot ballot;




    public Handler(){
        //create new pair of keys here


        //get the users from the txt

        users = readUsers();

        //sent to their emails their password
        sentPassword();
        //get those stuff from file later but for now i just put em karfwta ;)
        ArrayList<String> candi = new ArrayList<>();
        candi.add("nikos");candi.add("valantis");candi.add("tsipras");
        ballot = new Ballot("2021",candi,1);





    }


    public void login(String email,String passwd){
        User usr = users.get(email);
        if(usr == null)
            return;
        if(usr.getEmail().equals(email) && usr.getPassword().equals(passwd)){
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/VotingScreen.fxml"));
                Parent root = loader.load();
                VotingScreen v = loader.getController();

                v.inject(ballot.getCandidates());
                Stage st = new Stage();
                st.setTitle("ValNik Voting System");
                st.setResizable(false);
                st.setScene(new Scene(root));
                st.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Hashtable<String,User> readUsers()  {
        File myObj = new File("users.txt");
        Hashtable<String,User> tempUsers = new Hashtable<>();
        try{
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String info[] = data.split(";");
                String name = info[0];
                String email = info[1];

                tempUsers.put(email,new User(name,email));


            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return tempUsers;
    }
    private void sentPassword(){
        //TODO make a password generator
        users.forEach((key, value)->value.setPassword("123"));
        users.forEach((key, value)->System.out.println(value.getEmail() + " " +value.getPassword()));
    }
}
