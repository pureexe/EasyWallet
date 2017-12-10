package th.in.pureapp.easywallet.model;

/**
 * Created by pakkapon on 10/12/2560.
 */

public class InfoRecord {
    public int id;
    public String description;
    public double amount;

    public InfoRecord(int id,String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.id = id;
    }
    public InfoRecord(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }
}
