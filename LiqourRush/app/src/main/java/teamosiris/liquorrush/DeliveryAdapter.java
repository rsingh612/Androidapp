package teamosiris.liquorrush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rsingh on 12/6/2016.
 */

public class DeliveryAdapter extends ArrayAdapter {
    Order order;

    public DeliveryAdapter(Context context, int resource) {
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
        final DeliveryAdapter.DataHandler handler;
        order = (Order) this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.delivery_listview, parent,false);
        handler = new DeliveryAdapter.DataHandler();
        handler.orderid = (TextView) convertView.findViewById(R.id.deliveryAddress);
        handler.orderid.setText(order.getOrder_address());
        handler.orderid.setOnClickListener(new DeliveryListener(order));
        convertView.setTag(handler);
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
        TextView orderid;
    }
}