package teamosiris.liquorrush;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by rsing on 12/6/2016.
 */

public class FulfillmentAdapter extends ArrayAdapter {

    public FulfillmentAdapter(Context context, int resource) {
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
        final FulfillmentAdapter.DataHandler handler;
        Order temp;
        temp = (Order) this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.clerk_listview, parent,false);
        handler = new FulfillmentAdapter.DataHandler();
        handler.orderid = (TextView) convertView.findViewById(R.id.clerkOrderNum);
        handler.orderid.setText(temp.getOrder_id());
        handler.status = (TextView) convertView.findViewById(R.id.clerkOrderStatus);
        handler.status.setText(temp.getOrder_status());
        handler.orderid.setOnClickListener(new FulfillmentListener(temp));
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
        TextView status;
    }
}