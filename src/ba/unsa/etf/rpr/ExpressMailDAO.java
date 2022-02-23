package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.model.Courier;
import ba.unsa.etf.rpr.model.Package;
import ba.unsa.etf.rpr.model.User;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpressMailDAO {

    private static ExpressMailDAO instance;
    private Connection conn;

    private PreparedStatement getPackagesQuery;
    private PreparedStatement getCourierQuery;
    private PreparedStatement getUserQuery;
    private PreparedStatement deletePackageQuery;
    private PreparedStatement addCourierQuery;
    private PreparedStatement getMaxIdCourierQuery;
    private PreparedStatement getCouriersQuery;
    private PreparedStatement updatePackageQuery;
    private PreparedStatement updateCourierQuery;
    private PreparedStatement updateUserQuery;
    private PreparedStatement createPackageQuery;
    private PreparedStatement getMaxIdPackageQuery;
    private PreparedStatement deleteCourierQuery;
    private PreparedStatement addUserQuery;
    private PreparedStatement getMaxIdUserQuery;
    private PreparedStatement createRegisterQuery;
    private PreparedStatement updateRegisterQuery;
    private PreparedStatement deleteFromRegisterQuery;

    public static ExpressMailDAO getInstance() {
        if(instance==null)
            instance = new ExpressMailDAO();
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

    private ExpressMailDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:baza.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            prepareQueries();
        } catch (SQLException e) {
            regenerateDatabase();
            try {
                prepareQueries();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    }

    public List<Courier> couriers() {

        ArrayList<Courier> couriers = new ArrayList<>();
        try {
            ResultSet rs = getCouriersQuery.executeQuery();
            while(rs.next())
                couriers.add(getCourierFromResultSet(rs.getInt(1)));
            return couriers;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    private void prepareQueries() throws  SQLException{

        getPackagesQuery = conn.prepareStatement("SELECT * FROM package");
        getCourierQuery = conn.prepareStatement("SELECT * FROM courier WHERE id=?");
        getUserQuery = conn.prepareStatement("SELECT * FROM user WHERE id=?");
        deletePackageQuery = conn.prepareStatement("DELETE FROM package WHERE id=?");
        addCourierQuery = conn.prepareStatement("INSERT INTO courier VALUES (?,?,?,?,?)");
        getMaxIdCourierQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM courier");
        getCouriersQuery = conn.prepareStatement("SELECT * FROM courier");
        updatePackageQuery = conn.prepareStatement("UPDATE package SET description=?,address=?,sender=?,receiver=?,courier=?,weight=?,delivery_price=?,city=?,zip_code=?, sending_time=?, delivery_time=?, order_status=? WHERE id=?");
        updateUserQuery= conn.prepareStatement("UPDATE user SET name=?,telephone_number=?,address=?,city=?,zip_code=? WHERE id=?");
        updateCourierQuery = conn.prepareStatement("UPDATE courier SET name=?,telephone_number=?,username=?,password=? WHERE id=?");
        createPackageQuery = conn.prepareStatement("INSERT INTO package VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        getMaxIdPackageQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM package");
        deleteCourierQuery = conn.prepareStatement("DELETE FROM courier WHERE id=?");
        addUserQuery = conn.prepareStatement("INSERT INTO user VALUES (?,?,?,?,?,?)");
        getMaxIdUserQuery = conn.prepareStatement("SELECT MAX(id)+1 FROM user");
        createRegisterQuery = conn.prepareStatement("INSERT INTO register VALUES(?,?)");
        updateRegisterQuery = conn.prepareStatement("UPDATE register SET courier=? WHERE package=?");
        deleteFromRegisterQuery = conn.prepareStatement("DELETE FROM register WHERE package=?");

    }

    private void regenerateDatabase() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream("baza.sql"));
            String sqlQuery = "";
            while (scanner.hasNext()) {
                sqlQuery += scanner.nextLine();
                if ( sqlQuery.length() > 1 && sqlQuery.charAt( sqlQuery.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlQuery);
                        sqlQuery = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Package> packages() {
        ArrayList<Package> packages = new ArrayList<>();
        try {
            ResultSet rs = getPackagesQuery.executeQuery();
            while (rs.next()) {
                packages.add(getPackageFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    private Package getPackageFromResultSet(ResultSet rs) throws SQLException {
        return new Package(rs.getInt(1),rs.getString(2), rs.getString(3),
                getUserFromResultSet(rs.getInt(4)),
                getUserFromResultSet(rs.getInt(5)),
                getCourierFromResultSet(rs.getInt(6)),
                rs.getInt(7),rs.getInt(8), rs.getString(9), rs.getInt(10),
                getLocalDateTimeFromString(rs.getString(11)), getLocalDateTimeFromString(rs.getString(12)),
                getStatusFromString(rs.getString(13)));
    }

    private LocalDateTime getLocalDateTimeFromString(String string) {

        if(string==null) return null;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(string,format);
    }

    private OrderStatus getStatusFromString(String string) {

        if(string==null) return null;
        return switch (string) {
            case "Delivered" -> OrderStatus.DELIVERED;
            case "In transport" -> OrderStatus.IN_TRANSPORT;
            case "Error" -> OrderStatus.ERROR;
            case "In warehouse" -> OrderStatus.IN_WAREHOUSE;
            default -> null;
        };
    }

    private Courier getCourierFromResultSet(int id) throws SQLException {
        getCourierQuery.setInt(1,id);
        ResultSet rs = getCourierQuery.executeQuery();
        if(!rs.next())
            return null;
        return new Courier(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));

    }

    private User getUserFromResultSet(int id) throws SQLException{

        getUserQuery.setInt(1,id);
        ResultSet rs = getUserQuery.executeQuery();
        if(!rs.next())
            return null;
        return new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));
    }

    public void deletePackage(int id) {

        try {
            deletePackageQuery.setInt(1,id);
            deletePackageQuery.executeUpdate();
            deleteFromRegisterQuery.setInt(1,id);
            deleteFromRegisterQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCourier(Courier courier) {

        try {
            int id=0;
            ResultSet rs = getMaxIdCourierQuery.executeQuery();
            if(rs.next())
                id=rs.getInt(1);
            addCourierQuery.setInt(1,id);
            addCourierQuery.setString(2, courier.getName());
            addCourierQuery.setString(3, courier.getTelephoneNumber());
            addCourierQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePackage(Package aPackage) {

        updateCourier(aPackage.getCourier());
        updateUser(aPackage.getSender());
        updateUser(aPackage.getReceiver());
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            updateRegisterQuery.setInt(1, aPackage.getCourier().getId());
            updateRegisterQuery.setInt(2, aPackage.getId());
            updateRegisterQuery.executeUpdate();
            updatePackageQuery.setString(1, aPackage.getDescription());
            updatePackageQuery.setString(2, aPackage.getAddress());
            updatePackageQuery.setInt(3, aPackage.getSender().getId());
            updatePackageQuery.setInt(4, aPackage.getReceiver().getId());
            updatePackageQuery.setInt(5, aPackage.getCourier().getId());
            updatePackageQuery.setInt(6, aPackage.getWeight());
            updatePackageQuery.setInt(7, aPackage.getDeliveryCost());
            updatePackageQuery.setString(8, aPackage.getCity());
            updatePackageQuery.setInt(9, aPackage.getZipCode());
            updatePackageQuery.setString(10, aPackage.getSendingTime().format(format));
            if(aPackage.getDeliveryTime()!=null) updatePackageQuery.setString(11, aPackage.getDeliveryTime().format(format));
            else updatePackageQuery.setString(11,null);
            updatePackageQuery.setString(12, String.valueOf(aPackage.getOrderStatus()));
            updatePackageQuery.setInt(13, aPackage.getId());
            updatePackageQuery.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateUser(User user) {
        try {
            updateUserQuery.setString(1, user.getName());
            updateUserQuery.setString(2, user.getTelephoneNumber());
            updateUserQuery.setString(3, user.getAddress());
            updateUserQuery.setString(4, user.getCity());
            updateUserQuery.setInt(5, user.getZipCode());
            updateUserQuery.setInt(6, user.getId());
            updateUserQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateCourier(Courier courier) {

        try {
            updateCourierQuery.setString(1, courier.getName());
            updateCourierQuery.setString(2, courier.getTelephoneNumber());
            updateCourierQuery.setInt(3, courier.getId());
            updateCourierQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createPackage(Package aPackage) {

        try {
            int id=1;
            ResultSet rs = getMaxIdPackageQuery.executeQuery();
            if(rs.next())
                id=rs.getInt(1);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            if(aPackage.getSender().getZipCode()!=-1) addUser(aPackage.getSender());
            else aPackage.getSender().setId(1);
            addUser(aPackage.getReceiver());
            aPackage.setId(id);
            createPackageQuery.setInt(1, aPackage.getId());
            createPackageQuery.setString(2, aPackage.getDescription());
            createPackageQuery.setString(3, aPackage.getAddress());
            createPackageQuery.setInt(4, aPackage.getSender().getId());
            createPackageQuery.setInt(5, aPackage.getReceiver().getId());
            createPackageQuery.setInt(6, aPackage.getCourier().getId());
            createPackageQuery.setInt(7, aPackage.getWeight());
            createPackageQuery.setInt(8, aPackage.getDeliveryCost());
            createPackageQuery.setString(9, aPackage.getCity());
            createPackageQuery.setInt(10, aPackage.getZipCode());
            createPackageQuery.setString(11, aPackage.getSendingTime().format(format));
            createPackageQuery.setString(12,null);
            createPackageQuery.setString(13, String.valueOf(aPackage.getOrderStatus()));
            createPackageQuery.executeUpdate();
            updateCourier(aPackage.getCourier());
            createRegisterQuery.setInt(1, aPackage.getCourier().getId());
            createRegisterQuery.setInt(2, aPackage.getId());
            createRegisterQuery.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUser(User user) {

            try {
                ResultSet rs = getMaxIdUserQuery.executeQuery();
                int id = 1;
                if (rs.next())
                    id = rs.getInt(1);
                user.setId(id);
                addUserQuery.setInt(1, id);
                addUserQuery.setString(2, user.getName());
                addUserQuery.setString(3, user.getTelephoneNumber());
                addUserQuery.setString(4, user.getAddress());
                addUserQuery.setString(5, user.getCity());
                addUserQuery.setInt(6, user.getZipCode());
                addUserQuery.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public void deleteCourier(Courier courier) {

        try {
            deleteCourierQuery.setInt(1, courier.getId());
            deleteCourierQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void writeInFile(File file) {
        if(file!=null){
            FileWriter fileWriter= null;
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            StringBuilder result= new StringBuilder();
            for(Package aPackage : packages()){
                result.append("Package ").append(aPackage.getId()).append("\n");
                result.append("Description: ").append(aPackage.getDescription()).append("\n");
                result.append("Delivery cost: ").append(aPackage.getDeliveryCost()).append("\n");
                result.append("Weight: ").append(aPackage.getWeight()).append("\n");
                result.append("Sender:\n");
                result.append(aPackage.getSender().getName()).append("-").append(aPackage.getSender().getTelephoneNumber()).append("-").append(aPackage.getSender().getAddress()).append("\n");
                result.append("Receiver:\n");
                result.append(aPackage.getReceiver().getName()).append("-").append(aPackage.getReceiver().getTelephoneNumber()).append("-").append(aPackage.getReceiver().getAddress()).append("\n");
                result.append("Courier: ").append(aPackage.getCourier().getId()).append("\n");
                result.append("Name: ").append(aPackage.getCourier().getName()).append("\nTelephone number: ").append(aPackage.getCourier().getTelephoneNumber()).append("\n").append("________________________________\n");
            }

            try {
                if (fileWriter != null) {
                    fileWriter.write(result.toString());
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
