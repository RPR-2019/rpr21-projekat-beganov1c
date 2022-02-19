package ba.unsa.etf.rpr.model;

public class Posiljka {

    private int id;
    private String opis;
    private String adresa;
    private Korisnik primaoc;
    private Korisnik posiljaoc;
    private Kurir kurir;
    private int tezina;
    private int cijenaDostave;

    public Posiljka() {
    }

    public Posiljka(int id, String opis, String adresa, Korisnik primaoc, Korisnik posiljaoc, Kurir kurir, int tezina, int cijenaDostave) {
        this.id = id;
        this.opis = opis;
        this.adresa = adresa;
        this.primaoc = primaoc;
        this.posiljaoc = posiljaoc;
        this.kurir = kurir;
        this.tezina = tezina;
        this.cijenaDostave = cijenaDostave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Korisnik getPrimaoc() {
        return primaoc;
    }

    public void setPrimaoc(Korisnik primaoc) {
        this.primaoc = primaoc;
    }

    public Korisnik getPosiljaoc() {
        return posiljaoc;
    }

    public void setPosiljaoc(Korisnik posiljaoc) {
        this.posiljaoc = posiljaoc;
    }

    public Kurir getKurir() {
        return kurir;
    }

    public void setKurir(Kurir kurir) {
        this.kurir = kurir;
    }

    public int getTezina() {
        return tezina;
    }

    public void setTezina(int tezina) {
        this.tezina = tezina;
    }

    public int getCijenaDostave() {
        return cijenaDostave;
    }

    public void setCijenaDostave(int cijenaDostave) {
        this.cijenaDostave = cijenaDostave;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
