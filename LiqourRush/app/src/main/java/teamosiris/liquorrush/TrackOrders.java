package teamosiris.liquorrush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class TrackOrders extends AppCompatActivity {
    ListView orders;
    private TrackOrderAdapter adapter;
    String orderId, customerId, itemsOrdered, orderPlaced, status, userId;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders);
        Intent intent = this.getIntent();
        Customer c = Customer.getInstance();
        userId = c.getCustomerId();
        orders = (ListView) findViewById(R.id.ordersListView);
        update = (Button) findViewById(R.id.orderListUpdate);
        final String userId = getIntent().getStringExtra("customerId");
        adapter = new TrackOrderAdapter(getApplicationContext(), R.layout.track_order_listview);
        orders.setAdapter(adapter);
        getOrders();

        adapter.notifyDataSetChanged();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrders();
                adapter.notifyDataSetChanged();
            }
        });

    }


    public void getOrders() {
        final ProgressDialog progress = new ProgressDialog(TrackOrders.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setMessage("Searching....");
        progress.show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progress.dismiss();
                adapter.clear();
                adapter.notifyDataSetChanged();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int max = jsonObject.length();
                    for (int i = 1; i <= max; ) {
                        orderId = jsonObject.getString(Integer.toString(i++));
                        customerId = jsonObject.getString(Integer.toString(i++));
                        itemsOrdered = jsonObject.getString(Integer.toString(i++));
                        orderPlaced = jsonObject.getString(Integer.toString(i++));
                        status = jsonObject.getString(Integer.toString(i++));
                        Order temp = new Order(orderId, customerId, itemsOrdered, orderPlaced, status);
                        adapter.add(temp);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        TrackOrderRequest orderRequest = new TrackOrderRequest(userId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(TrackOrders.this);
        queue.add(orderRequest);
    }
}

