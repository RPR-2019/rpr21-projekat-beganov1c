package ba.unsa.etf.rpr.model;

public abstract class Person {

    private String name;
    private String telephoneNumber;

    protected Person(String ime, String brojTelefona) {
        this.name = ime;
        this.telephoneNumber = brojTelefona;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }
}
