package teamosiris.liquorrush;

/**
 * Created by rsingh on 12/6/2016.
 */

public class Order {
    private String order_id;
    private String order_for;
    private String order_items;
    private String order_placed;
    private String order_status;
    private String order_address;
    private String order_license;
    private String order_name;

    public Order(String order_id, String order_for, String order_items, String order_placed, String order_status) {
        this.order_id = order_id;
        this.order_for = order_for;
        this.order_items = order_items;
        this.order_placed = order_placed;
        this.order_status = order_status;
        this.order_address =null;
        this.order_license =null;
        this.order_name = null;
    }
    public void setOrder_name(String name){ this.order_name = name;}

    public void setOrder_license(String license){ this.order_license = license;}

    public void setOrder_address(String city){ this.order_address = city;}

    public String getOrder_name(){return order_name;}

    public String getOrder_license(){return order_license;}

    public String getOrder_address(){return order_address;}

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_for() {
        return order_for;
    }

    public String getOrder_items() {
        return order_items;
    }

    public String getOrder_placed() {
        return order_placed;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String status){
        this.order_status = status;
    }


}
