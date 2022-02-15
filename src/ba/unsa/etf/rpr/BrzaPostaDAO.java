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

    private PreparedStatement dajPoslijkeUpit, dajKuriraUpit, dajKorisnikaUpit;

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

    private void pripremiUpite() throws  SQLException{

        dajPoslijkeUpit = conn.prepareStatement("SELECT * FROM posiljka");
        dajKuriraUpit = conn.prepareStatement("SELECT * FROM kurir WHERE id=?");
        dajKorisnikaUpit = conn.prepareStatement("SELECT * FROM korisnik WHERE id=?");
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
}
