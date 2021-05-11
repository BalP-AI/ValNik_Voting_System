package sample;

import java.io.Serializable;

public class Voter implements Serializable {

    String name;
    String email;
    boolean hasVoted;
    String salt;


    public Voter(String name, String email){
        this.name = name;
        this.email = email;
        hasVoted = false;
    }

    public void setSalt(String salt){this.salt = salt;}
    public String getSalt(){return salt;}
}
