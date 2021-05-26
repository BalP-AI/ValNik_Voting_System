package sample;

import java.io.Serializable;

public class User implements Serializable
{

    String name;
    String email;
    byte[] encryptedpasswd;  //we changed this from string to byte[] because we want
    //the encrypted hash that it is byte[] and not string
    boolean hasVoted;
    byte[] salt;


    public User(String name, String email)
    {
        this.name = name;
        this.email = email;
        hasVoted = false;
    }

    public void setPassword(byte[] password)
    {
        this.encryptedpasswd = password;
    }

    public byte[] getPassword()
    {
        return encryptedpasswd;
    }

    public boolean gethasVoted()
    {
        return hasVoted;
    }

    public String getEmail()
    {
        return email;
    }

    public boolean getHasVoted()
    {
        return hasVoted;
    }

    public void setHasVoted()
    {
        hasVoted = true;
    }

    public void setSalt(byte[] salt)
    {
        this.salt = salt;
    }

    public byte[] getSalt()
    {
        return salt;
    }
}
