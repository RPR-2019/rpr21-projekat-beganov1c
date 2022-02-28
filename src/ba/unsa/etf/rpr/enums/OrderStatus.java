package ba.unsa.etf.rpr.enums;

public enum OrderStatus {

    DELIVERED("Delivered"), IN_TRANSPORT("In transport"), IN_WAREHOUSE("In warehouse"), ERROR("Error");

    private final String naziv;

    OrderStatus(String s){

        this.naziv=s;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
