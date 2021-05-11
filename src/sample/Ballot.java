package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Ballot implements Serializable
{
    private static final long serialversionUID = 42L;

    private String title;
    private ArrayList<String> candidates;
    private int maxVotes;

    public Ballot(String title, ArrayList<String> candidates, int maxVotes)
    {
        this.candidates = candidates;
        this.title = title;
        this.maxVotes = maxVotes;

    }


}
