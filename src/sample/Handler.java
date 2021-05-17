package sample;

import Controllers.ElectionCommitteeController;
import Controllers.OpenScreenController;
import Controllers.VotingScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class Handler
{

    Hashtable<String, User> users;
    Ballot ballot;
    Security sec = new Security();
    String elecCommi = null;
    String masterhash = null;


    public Handler() throws NoSuchAlgorithmException, IOException
    {
        //create new pair of keys here
        sec.writeKeys();
        //get the users from the txt
        users = readUsers();
        //sent to their emails their password
        sentPassword();
        //initiate the election committee
        try
        {
            initiateCommittee();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        //create the final users txt file with the data we need
        try
        {
            finalusers();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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

    private void finalusers() throws IOException
    {
        FileWriter cleaner = new FileWriter("usersfinal.txt", false);
        cleaner.write("");
        cleaner.close();

        users.forEach((key, value) ->
        {
            FileWriter wr = null;
            try
            {
                wr = new FileWriter("usersfinal.txt", true);
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                wr.write(value.name + ";" + value.email + ";" + Base64.getEncoder().encodeToString(value.encryptedpasswd) + ";" + Base64.getEncoder().encodeToString(value.salt) + "\n");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                wr.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });


    }

    private void initiateCommittee() throws Exception
    {
        //most of the stuff is karfwta alla ginontai kai tyxaia izi
        users.get("bob@hotmail.com").salt = sec.getSalt();
        System.out.println("bobs salt" + Arrays.toString(users.get("bob@hotmail.com").salt));
        //todo at the end change the "123" password
        users.get("bob@hotmail.com").encryptedpasswd = sec.encrypt(sec.sha256("123", users.get("bob@hotmail.com").getSalt()), sec.getPublKey());


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


    public void login(String emailtx, String passwdtx) throws Exception
    {
        System.out.println("in login");
        System.out.println(emailtx);
        System.out.println(passwdtx);
        File myObj = new File("usersfinal.txt");
        String name = null;
        String email = null;
        byte[] encryptedpasswd = null;
        byte[] salt = null;

        try
        {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                String data = myReader.nextLine();
                String[] tmpinfo = data.split(";");
                if (tmpinfo[1].equals(emailtx))
                {
                    name = tmpinfo[0];
                    email = tmpinfo[1];
                    //these are decoded back to byte[]
                    encryptedpasswd = Base64.getDecoder().decode(tmpinfo[2]);
                    salt = Base64.getDecoder().decode(tmpinfo[3]);
                    break;
                }
            }
            myReader.close();
        } catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if (email.equals(emailtx) && sec.decrypt(encryptedpasswd, sec.getPrivKey()).equals(sec.sha256(passwdtx, salt)))
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
                String[] info = data.split(";");
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
        users.forEach((key, value) ->
        {
            String passwd = generatePassword();  //generate passwd for each user
            try
            {
                System.out.println("Name: " + value.name + " Original Passwd: " + passwd);
                value.salt = sec.getSalt();  //give each user a salt
                value.setPassword(sec.encrypt(sec.sha256(passwd, value.salt), sec.getPublKey()));  //save the passwd as a hash for using the premade salt
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        //users.forEach((key, value) -> System.out.println(value.getEmail() + " " + value.getPassword() + "from unhashed: " + passwd));


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


    public void openvotewindow() throws IOException
    {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLS/OpenScreen.fxml"));
        Parent root = loader.load();
        OpenScreenController c = loader.getController();
        c.inject(this);
        primaryStage.setTitle("ValNik Voting System");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
