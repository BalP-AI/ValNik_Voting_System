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
import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.util.*;

public class Handler
{

    Hashtable<String, User> users;
    Ballot ballot;
    Security sec = new Security();


    public Handler()
    {
        //create new pair of keys here


        //get the users from the txt
        users = readUsers();

        //sent to their emails their password
        sentPassword();
        //get those stuff from file later but for now i just put em karfwta ;)
        ArrayList<String> candi = new ArrayList<>();
        readCandiData(candi);
        String title = candi.get(0);
        String votesnum = candi.get(1);
        candi.remove(0);
        candi.remove(0);
        System.out.println(candi);
        ballot = new Ballot(title, candi, Integer.parseInt(votesnum));

    }

    private void readCandiData(ArrayList<String> candi)
    {
        File myObj = new File("data.txt");

        try
        {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                candi.add(myReader.nextLine());

            }
            myReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public void login(String email, String passwd)
    {
        User usr = users.get(email);
        if (usr == null) return;
        if (usr.getEmail().equals(email) && usr.getPassword().equals(sec.sha256(passwd,new byte[]{1,2,4})))
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/VotingScreen.fxml"));
                Parent root = loader.load();
                VotingScreen v = loader.getController();

                v.inject(ballot.getCandidates());
                v.setTitle(ballot.getTitle());
                Stage st = new Stage();
                st.setTitle("ValNik Voting System");
                st.setResizable(false);
                st.setScene(new Scene(root));
                st.show();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private Hashtable<String, User> readUsers()
    {
        File myObj = new File("users.txt");
        Hashtable<String, User> tempUsers = new Hashtable<>();
        try
        {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                String data = myReader.nextLine();
                String info[] = data.split(";");
                String name = info[0];
                String email = info[1];
                tempUsers.put(email, new User(name, email));
            }
            myReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return tempUsers;
    }

    private void sentPassword()
    {
        //System.out.println("random pass " + generatePassword());   Todo implement this when the testing is done
        String passwd = generatePassword();
        users.forEach((key, value) -> value.setPassword(sec.sha256(passwd,new byte[]{1,2,4})));
        users.forEach((key, value) -> System.out.println(value.getEmail() + " " + value.getPassword() + "from: " + passwd));
    }

    //Secure random password Generator of 12 char
    private String generatePassword()
    {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        SecureRandom secureRandom = new SecureRandom();
        char[] password = new char[12];
        for (int i = 0; i < 12; i++)
        {
            password[i] = combinedChars.charAt(secureRandom.nextInt(combinedChars.length()));
        }
        return new String(password);
    }

}
