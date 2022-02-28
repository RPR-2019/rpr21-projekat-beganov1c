package ba.unsa.etf.rpr.model;

public class User extends Person {

    private int id;
    private String address;
    private String city;
    private int zipCode;

    public User(int id, String name, String telephoneNumber, String address, String city, int zipCode) {
        super(name, telephoneNumber);
        this.id = id;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String
    toString() {
        return getName();
    }
}
