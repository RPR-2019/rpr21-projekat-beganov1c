package ba.unsa.etf.rpr.model;

public class Osoba {

    private String naziv;
    private String brojTelefona;

    public Osoba(String ime, String brojTelefona) {
        this.naziv = ime;
        this.brojTelefona = brojTelefona;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }
}
