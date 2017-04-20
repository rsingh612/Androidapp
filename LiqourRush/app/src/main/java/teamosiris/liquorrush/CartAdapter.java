package teamosiris.liquorrush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static teamosiris.liquorrush.LoggedIn.userCart;


/**
 * Created by rsing on 10/17/2016.
 */

public class CartAdapter extends ArrayAdapter {
    int tempPrice;
    int tempQuantity;
    final cartDataHandler handler = new cartDataHandler();

    public CartAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object){
        super.add(object);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
            Product temp;
            temp = (Product) this.getItem(position);
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_listview, parent, false);
            handler.name = (TextView) convertView.findViewById(R.id.cartProductName);
            handler.name.setText(temp.getProduct_name());

            handler.bt_add = (ImageView) convertView.findViewById(R.id.cartPlusButton);
            handler.bt_add.setImageResource(R.mipmap.ic_cart_plus);
            handler.bt_add.setOnClickListener(new UpdateCartListener(temp));
            increment(position, convertView, parent);


            handler.quantity = (TextView) convertView.findViewById(R.id.cartQuantity);
            handler.quantity.setText(String.valueOf(temp.getQuantity()));

            handler.bt_minus = (ImageView) convertView.findViewById(R.id.cartMinusButton);
            handler.bt_minus.setImageResource(R.mipmap.ic_cart_minus);
            handler.bt_minus.setOnClickListener(new UpdateCartListener(temp));


            handler.price = (TextView) convertView.findViewById(R.id.cartProductPrice);
            double tempPrice = Round(Double.valueOf(temp.getProduct_price()) * temp.getQuantity());
            handler.price.setText(String.valueOf(tempPrice));
            convertView.setTag(handler);
        return convertView;
    }

    public View increment(final int position, View convertView, ViewGroup parent){

        return convertView;
    }

    @Override
    public int getCount(){
        return super.getCount();
    }

    public double Round(double val){
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }


    @Override
    public Object getItem(int position){
        return super.getItem(position);
    }

    class cartDataHandler {
        TextView name;
        ImageView bt_add;
        TextView quantity;
        ImageView bt_minus;
        TextView price;

    }


}
