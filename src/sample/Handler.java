package sample;

import java.io.File;
import java.io.FileNotFoundException;
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
            System.out.println("you are in");
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
