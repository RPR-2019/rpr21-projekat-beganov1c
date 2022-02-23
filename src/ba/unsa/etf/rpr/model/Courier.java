package ba.unsa.etf.rpr.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Courier extends Person implements Comparable<Courier> {

    private int id;
    private ArrayList<Package> packages;
    private String username;
    private String password;


    public Courier(int id, String name, String telephoneNumber, String username, String password) {
        super(name, telephoneNumber);
        this.id = id;
        this.username=username;
        this.password=password;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String
    toString() {
        return getName();
    }

    @Override
    public int compareTo(Courier o) {
        return Integer.compare(id,o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Courier courier)) return false;
        return getId() == courier.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
