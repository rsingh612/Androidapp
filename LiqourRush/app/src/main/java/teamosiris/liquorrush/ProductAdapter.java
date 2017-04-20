package teamosiris.liquorrush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by rsing on 10/13/2016.
 */

public class ProductAdapter extends ArrayAdapter{

    public ProductAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void clear(){
        super.clear();
    }

    @Override
    public void add(Object object){
        super.add(object);
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DataHandler handler;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_listview, parent,false);
            handler = new DataHandler();
            handler.image = (WebView) convertView.findViewById(R.id.productImage);
            handler.name = (TextView) convertView.findViewById(R.id.productName);
            handler.description = (TextView) convertView.findViewById(R.id.productDescription);
            handler.price = (TextView) convertView.findViewById(R.id.productPrice);
            handler.currency = (TextView) convertView.findViewById(R.id.dollarSign);
            handler.bt_add = (ImageView)convertView.findViewById(R.id.bt_addToCart);
            convertView.setTag(handler);
        }
        else{
            handler = (DataHandler) convertView.getTag();
            Product temp;
            temp = (Product) this.getItem(position);
            handler.image.loadUrl(temp.getProduct_image());
            handler.bt_add.setImageResource(R.mipmap.ic_add_to_cart);
            handler.name.setText(temp.getProduct_name());
            handler.description.setText(temp.getProduct_description());
            handler.currency.setText("$");
            handler.price.setText(temp.getProduct_price());
            handler.bt_add.setOnClickListener(new AddToCartListener(temp));
            handler.image.setOnTouchListener(new DescriptionListener(temp));
            handler.image.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            handler.image.getSettings().setLoadWithOverviewMode(true);
            handler.image.getSettings().setUseWideViewPort(true);
        }
        return convertView;
    }

    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public Object getItem(int position){
        return super.getItem(position);
    }

    class DataHandler {
        WebView image;
        TextView name;
        TextView description;
        TextView price;
        TextView currency;
        ImageView bt_add;
    }
}
