package teamosiris.liquorrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.DecimalFormat;

import static teamosiris.liquorrush.LoggedIn.customerID;
import static teamosiris.liquorrush.LoggedIn.items;
import static teamosiris.liquorrush.LoggedIn.userCart;


public class Cart extends AppCompatActivity {
    ListView cartItems;
    CartAdapter adapter;
    Button pay;
    TextView totalTextView, taxTextView, deliveryTextView;
    double userTotal = 0, userTax=0, userDelivery=0;
    String soldItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();

        DisplayMetrics dimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int width = dimensions.widthPixels;
        int height = dimensions.heightPixels;
        getWindow().setLayout((int)(width*0.9),(int)(height *0.80));

        //setup listview
        cartItems= (ListView) findViewById(R.id.cartListView);
        cartItems.setVisibility(View.VISIBLE);
        adapter = new CartAdapter(getApplicationContext(), R.layout.cart_listview);
        cartItems.setAdapter(adapter);

        //populate listview from cart data
        for(int i=0;i<userCart.size();i++){
            adapter.add(userCart.get(i));
            userTotal = userTotal + Round(Double.valueOf(userCart.get(i).getProduct_price())*userCart.get(i).getQuantity());
        }

        userTax = Round(userTotal * 0.0725);
        userTotal = Round(userTotal + userTax);

        if(userTotal ==0){
            userDelivery = 0;
        }
        else if(userTotal<60 && userTotal>=30){
            userDelivery = Round(2.50);
        }else if(userTotal<30){
            userDelivery = 5;
        }else{
            userDelivery = 0;
        }
        userTotal = Round(userTotal + userTax + userDelivery);

        //intialize total information
        taxTextView =  (TextView) findViewById(R.id.cartTax);
        deliveryTextView = (TextView) findViewById(R.id.cartDelivery);
        totalTextView = (TextView) findViewById(R.id.cartTotal);

        taxTextView.setText(String.valueOf(userTax));
        deliveryTextView.setText(String.valueOf(userDelivery));
        totalTextView.setText(String.valueOf(userTotal));


        pay = (Button) findViewById(R.id.cartPayButton);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldItems = itemsInCart();
                Intent intent = new Intent(Cart.this, Payment.class);
                intent.putExtra("items", soldItems);
                intent.putExtra("id", customerID);
                Cart.this.startActivity(intent);
            }
        });




    }

    public String itemsInCart(){
        Product temp;
        for(int i=0;i<userCart.size();i++){
            temp = userCart.get(i);
            if(i==(userCart.size()-1)) {
                items.append("Item no. " + temp.getProduct_id() + ", " + temp.getProduct_name() + ", Qty: " + temp.getQuantity());
            }
            else{
                items.append("Item no. " + temp.getProduct_id() + ", " + temp.getProduct_name() + ", Qty: " + temp.getQuantity()+"\n");
            }
        }
        return items.toString();
    }

    public double Round(double val){
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }
}
