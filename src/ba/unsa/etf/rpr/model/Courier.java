package ba.unsa.etf.rpr.model;

import java.util.ArrayList;

public class Courier extends Person {

    private int id;
    private ArrayList<Package> packages;


    public Courier(int id, String name, String telephoneNumber) {
        super(name, telephoneNumber);
        this.id = id;
        packages = new ArrayList<>();
    }

    public Courier(int id, String name, String telephoneNumber, ArrayList<Package> packages) {
        super(name, telephoneNumber);
        this.id = id;
        this.packages = packages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }

    @Override
    public String
    toString() {
        return getName();
    }
}
