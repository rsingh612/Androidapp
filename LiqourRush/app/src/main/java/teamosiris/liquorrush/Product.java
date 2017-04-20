package teamosiris.liquorrush;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by rsing on 10/12/2016.
 */

public class Product {
    private String product_id;
    private String product_image;
    private String product_name;
    private String product_description;
    private String product_group;
    private String product_price;
    private int quantity;

    public Product(String id, String image, String name, String desc, String group, String price){
        this.product_id = id;
        this.product_image = image;
        this.product_name = name;
        this.product_description = desc;
        this.product_group = group;
        this.product_price = price;
        this.quantity = 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_description() {
        return product_description;
    }


    public String getProduct_image() {
        return product_image;
    }


    public String getProduct_name() {
        return product_name;
    }


    public String getProduct_price() {
        return product_price;
    }


    public void increment(){
        this.quantity++;
    }

    public void decrement(){
        this.quantity--;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", product_id='" + product_id + '\'' +
                ", product_image='" + product_image + '\'' +
                ", product_name='" + product_name + '\'' +
                "product_description='" + product_description + '\'' +
                ", product_group='" + product_group + '\'' +
                ", product_price='" + product_price + '\'' +
                '}';
    }

}
