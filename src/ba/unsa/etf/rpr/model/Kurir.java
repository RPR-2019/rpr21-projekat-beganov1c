package ba.unsa.etf.rpr.model;

import java.util.ArrayList;

public class Kurir extends Osoba {

    private int id;
    private ArrayList<Posiljka> posiljke;


    public Kurir(int id,String ime, String brojTelefona) {
        super(ime, brojTelefona);
        this.id = id;
        posiljke = new ArrayList<>();
    }

    public Kurir(int id,String ime, String prezime, String brojTelefona, ArrayList<Posiljka> posiljke) {
        super(ime, brojTelefona);
        this.id = id;
        this.posiljke = posiljke;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Posiljka> getPosiljke() {
        return posiljke;
    }

    public void setPosiljke(ArrayList<Posiljka> posiljke) {
        this.posiljke = posiljke;
    }

    @Override
    public String
    toString() {
        return getNaziv();
    }
}
