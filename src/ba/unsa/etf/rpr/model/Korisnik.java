package ba.unsa.etf.rpr.model;

public class Korisnik extends Osoba {

    private int id;
    private String adresa;

    public Korisnik(int id, String ime, String brojTelefona, String adresa) {
        super(ime, brojTelefona);
        this.id = id;
        this.adresa = adresa;
    }


    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String
    toString() {
        return getNaziv();
    }
}
