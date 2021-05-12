package sample;

import java.io.Serializable;

public class User
{

    String name;
    String email;
    String password;
    boolean hasVoted;
    String salt;


    public User(String name, String email){
        this.name = name;
        this.email = email;
        hasVoted = false;
    }
    public void setPassword(String password){this.password = password;}

    public String getPassword() {
        return password;
    }

    public String  getEmail(){return email;}

    public void setSalt(String salt){this.salt = salt;}
    public String getSalt(){return salt;}
}
