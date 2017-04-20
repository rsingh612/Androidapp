package teamosiris.liquorrush;

/**
 * Created by rsing on 12/7/2016.
 */

public class Customer {
    private static Customer instance;
    private String customerId, customerName, customerAddress, customerCity, customerState, customerZip;
    private boolean onceOnly = true;

    private Customer(){}

    public String getCustomerId(){return customerId;}

    public String getCustomerAddress() {return customerAddress;}

    public String getCustomerCity() {return customerCity;}

    public String getCustomerName() {return customerName;}

    public String getCustomerState() {return customerState;}

    public String getCustomerZip() {return customerZip;}

    public void setCustomer(String id, String name, String address, String city, String state, String zip){
        if(onceOnly){
            this.customerId = id;
            this.customerName = name;
            this.customerAddress = address;
            this.customerCity = city;
            this.customerState = state;
            this.customerZip = zip;
            onceOnly = false;
        }
    }

    public static synchronized Customer getInstance(){
        if(instance == null){
            instance = new Customer();
        }
        return instance;
    }
}
