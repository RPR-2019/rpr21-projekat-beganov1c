package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.model.Korisnik;
import ba.unsa.etf.rpr.model.Kurir;
import ba.unsa.etf.rpr.model.Posiljka;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BrzaPostaDAO {

    private static BrzaPostaDAO instance;
    private Connection conn;

    private PreparedStatement dajPoslijkeUpit, dajKuriraUpit, dajKorisnikaUpit,
            obrisiPosiljkuUpit, dodajKuriraUpit,dajMaxIdKurirUpit,
            dajKurireUpit, azurirajPosiljkuUpit, azurirajKuriraUpit,
            azurirajKorisnikaUpit, kreirajPosiljkuUpit, dajMaxIdPosiljkaUpit,
            obrisiKuriraUpit,obrisiPosiljkuZaKuriraUpit, dodajKorisnikaUpit,
            dajMaxIdKorisnikUpit;

    public static BrzaPostaDAO getInstance() {
        if(instance==null)
            instance = new BrzaPostaDAO();
        return instance;
    }

    public static void removeInstance() {
        if(instance!=null) {
            try {
                instance.conn.close();
                instance=null;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private BrzaPostaDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pripremiUpite();
        } catch (SQLException e) {
            regenerisiBazu();
            try {
                pripremiUpite();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    }

    public ArrayList<Kurir> kuriri() {

        ArrayList<Kurir> kuriri = new ArrayList<>();
        try {
            ResultSet rs = dajKurireUpit.executeQuery();
            while(rs.next())
                kuriri.add(dajKuriraIzResultSeta(rs.getInt(1)));

            return kuriri;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void pripremiUpite() throws  SQLException{

        dajPoslijkeUpit = conn.prepareStatement("SELECT * FROM posiljka");
        dajKuriraUpit = conn.prepareStatement("SELECT * FROM kurir WHERE id=?");
        dajKorisnikaUpit = conn.prepareStatement("SELECT * FROM korisnik WHERE id=?");
        obrisiPosiljkuUpit = conn.prepareStatement("DELETE FROM posiljka WHERE id=?");
        dodajKuriraUpit = conn.prepareStatement("INSERT INTO kurir VALUES (?,?,?)");
        dajMaxIdKurirUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM kurir");
        dajKurireUpit = conn.prepareStatement("SELECT * FROM kurir");
        azurirajPosiljkuUpit = conn.prepareStatement("UPDATE posiljka SET opis=?,adresa=?,posiljaoc=?,primalac=?,kurir=?,tezina=?,cijena_dostave=? WHERE id=?");
        azurirajKorisnikaUpit= conn.prepareStatement("UPDATE korisnik SET naziv=?,broj_telefona=?,adresa=? WHERE id=?");
        azurirajKuriraUpit = conn.prepareStatement("UPDATE kurir SET naziv=?,broj_telefona=? WHERE id=?");
        kreirajPosiljkuUpit = conn.prepareStatement("INSERT INTO posiljka VALUES(?,?,?,?,?,?,?,?)");
        dajMaxIdPosiljkaUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM posiljka");
        obrisiKuriraUpit = conn.prepareStatement("DELETE FROM kurir WHERE id=?");
        obrisiPosiljkuZaKuriraUpit = conn.prepareStatement("DELETE FROM posiljka WHERE kurir=?");
        dodajKorisnikaUpit = conn.prepareStatement("INSERT INTO korisnik VALUES (?,?,?,?)");
        dajMaxIdKorisnikUpit = conn.prepareStatement("SELECT MAX(id)+1 FROM korisnik");
    }

    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Posiljka> posiljke() {
        ArrayList<Posiljka> rezultat = new ArrayList<>();
        try {
            ResultSet rs = dajPoslijkeUpit.executeQuery();
            while (rs.next()) {
                rezultat.add(dajPosiljkuIzResultSeta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    private Posiljka dajPosiljkuIzResultSeta(ResultSet rs) throws SQLException {
        return new Posiljka(rs.getInt(1),rs.getString(2),rs.getString(3),dajKorisnikaIzResultSeta(rs.getInt(4)),dajKorisnikaIzResultSeta(rs.getInt(5)),dajKuriraIzResultSeta(rs.getInt(6)),rs.getInt(7),rs.getInt(8));
    }

    private Kurir dajKuriraIzResultSeta(int id) throws SQLException {
        dajKuriraUpit.setInt(1,id);
        ResultSet rs = dajKuriraUpit.executeQuery();
        if(!rs.next())
            return null;
        return new Kurir(rs.getInt(1),rs.getString(2),rs.getString(3));

    }

    private Korisnik dajKorisnikaIzResultSeta(int id) throws SQLException{

        dajKorisnikaUpit.setInt(1,id);
        ResultSet rs = dajKorisnikaUpit.executeQuery();
        if(!rs.next())
            return null;
        return new Korisnik(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
    }

    public void obrisiPosiljku(int id) {

        try {
            obrisiPosiljkuUpit.setInt(1,id);
            obrisiPosiljkuUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dodajKurira(Kurir kurir) {

        try {
            int id=0;
            ResultSet rs = dajMaxIdKurirUpit.executeQuery();
            if(rs.next())
                id=rs.getInt(1);
            dodajKuriraUpit.setInt(1,id);
            dodajKuriraUpit.setString(2,kurir.getNaziv());
            dodajKuriraUpit.setString(3,kurir.getBrojTelefona());
            dodajKuriraUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void azurirajPosiljku(Posiljka posiljka) {

        azurirajKurira(posiljka.getKurir());
        azurirajKorisnika(posiljka.getPosiljaoc());
        azurirajKorisnika(posiljka.getPrimaoc());
        try {
            azurirajPosiljkuUpit.setString(1,posiljka.getOpis());
            azurirajPosiljkuUpit.setString(2,posiljka.getAdresa());
            azurirajPosiljkuUpit.setInt(3,posiljka.getPosiljaoc().getId());
            azurirajPosiljkuUpit.setInt(4,posiljka.getPrimaoc().getId());
            azurirajPosiljkuUpit.setInt(5,posiljka.getKurir().getId());
            azurirajPosiljkuUpit.setInt(6,posiljka.getTezina());
            azurirajPosiljkuUpit.setInt(7,posiljka.getCijenaDostave());
            azurirajPosiljkuUpit.setInt(8,posiljka.getId());
            azurirajPosiljkuUpit.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void azurirajKorisnika(Korisnik korisnik) {
        try {
            azurirajKorisnikaUpit.setString(1,korisnik.getNaziv());
            azurirajKorisnikaUpit.setString(2,korisnik.getBrojTelefona());
            azurirajKorisnikaUpit.setString(3,korisnik.getAdresa());
            azurirajKorisnikaUpit.setInt(4,korisnik.getId());
            azurirajKuriraUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void azurirajKurira(Kurir kurir) {

        try {
            azurirajKuriraUpit.setString(1,kurir.getNaziv());
            azurirajKuriraUpit.setString(2,kurir.getBrojTelefona());
            azurirajKuriraUpit.setInt(3,kurir.getId());
            azurirajKuriraUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void kreirajPosiljku(Posiljka posiljka) {

        try {
            int id=1;
            ResultSet rs = dajMaxIdPosiljkaUpit.executeQuery();
            if(rs.next())
                id=rs.getInt(1);

            dodajKorisnika(posiljka.getPosiljaoc());
            dodajKorisnika(posiljka.getPrimaoc());
            posiljka.setId(id);
            kreirajPosiljkuUpit.setInt(1,posiljka.getId());
            kreirajPosiljkuUpit.setString(2,posiljka.getOpis());
            kreirajPosiljkuUpit.setString(3,posiljka.getAdresa());
            kreirajPosiljkuUpit.setInt(4,posiljka.getPosiljaoc().getId());
            kreirajPosiljkuUpit.setInt(5,posiljka.getPrimaoc().getId());
            kreirajPosiljkuUpit.setInt(6,posiljka.getKurir().getId());
            kreirajPosiljkuUpit.setInt(7,posiljka.getTezina());
            kreirajPosiljkuUpit.setInt(8,posiljka.getCijenaDostave());
            kreirajPosiljkuUpit.executeUpdate();
            azurirajKurira(posiljka.getKurir());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dodajKorisnika(Korisnik korisnik) {

        try {
            ResultSet rs = dajMaxIdKorisnikUpit.executeQuery();
            int id = 1;
            if(rs.next())
                id = rs.getInt(1);
            korisnik.setId(id);
            dodajKorisnikaUpit.setInt(1,id);
            dodajKorisnikaUpit.setString(2, korisnik.getNaziv());
            dodajKorisnikaUpit.setString(3, korisnik.getBrojTelefona());
            dodajKorisnikaUpit.setString(4, korisnik.getAdresa());
            dodajKorisnikaUpit.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void obrisiKurira(Kurir kurir) {

        try {
            obrisiPosiljkuZaKuriraUpit.setInt(1,kurir.getId());
            obrisiKuriraUpit.setInt(1,kurir.getId());
            /*obrisiPosiljkuZaKuriraUpit.executeUpdate();
             */
            obrisiKuriraUpit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
