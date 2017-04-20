package teamosiris.liquorrush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DeliveryClerk extends AppCompatActivity {
    Order temp;
    String license, name;
    String orderId, customerId, itemsOrdered, orderPlaced, status;
    ListView view;
    Button update;
    DeliveryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_clerk);
        view = (ListView)findViewById(R.id.deliveryListView);
        adapter = new DeliveryAdapter(getApplicationContext(), R.layout.delivery_listview);
        update = (Button) findViewById(R.id.deliveryButton);
        view.setAdapter(adapter);
        getOrders();
        adapter.notifyDataSetChanged();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOrders();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return true;
    }

    public void logout(MenuItem item){
        Intent logoutIntent = new Intent(DeliveryClerk.this, Login.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DeliveryClerk.this.startActivity(logoutIntent);
        Toast.makeText(DeliveryClerk.this, "GoodBye!", Toast.LENGTH_LONG).show();
    }

    public void getOrders() {
        final ProgressDialog progress = new ProgressDialog(DeliveryClerk.this);
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
                        temp = new Order(orderId, customerId, itemsOrdered, orderPlaced, status);
                        requestAddress(temp, customerId);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        TrackOrderRequest orderRequest = new TrackOrderRequest(false, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DeliveryClerk.this);
        queue.add(orderRequest);
    }

    public void requestAddress(final Order temp, String id){
        final StringBuilder builder= new StringBuilder();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    builder.append(jsonObject.getString("address"));
                    builder.append("\n");
                    builder.append(jsonObject.getString("city"));
                    builder.append(", ");
                    builder.append(jsonObject.getString("state"));
                    builder.append("\n");
                    builder.append(jsonObject.getString("zip"));
                    license = jsonObject.getString("license");
                    name = jsonObject.getString("name");
                    temp.setOrder_name(name);
                    temp.setOrder_license(license);
                    temp.setOrder_address(builder.toString());
                    adapter.add(temp);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DeliveryRequest deliveryRequest = new DeliveryRequest(id, responseListener);
        RequestQueue queue2 = Volley.newRequestQueue(DeliveryClerk.this);
        queue2.add(deliveryRequest);
    }

}
