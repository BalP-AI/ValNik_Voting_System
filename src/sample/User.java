package sample;

import java.io.Serializable;

public class User
{

    String name;
    String email;
    boolean hasVoted;
    String salt;


    public User(String name, String email){
        this.name = name;
        this.email = email;
        hasVoted = false;
    }

    public void setSalt(String salt){this.salt = salt;}
    public String getSalt(){return salt;}
}
