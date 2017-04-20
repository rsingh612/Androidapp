package teamosiris.liquorrush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FulfillmentClerk extends AppCompatActivity {
    private String username;
    String orderId, customerId, itemsOrdered, orderPlaced, status;
    ListView view;
    TextView user;
    FulfillmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfillment_clerk);
        view = (ListView)findViewById(R.id.clerkListView);
        Intent intent = this.getIntent();
        username = intent.getStringExtra("userName");
        adapter = new FulfillmentAdapter(getApplicationContext(), R.layout.clerk_listview);
        user = (TextView)findViewById(R.id.clerkUsername);
        user.setText(username);
        view.setAdapter(adapter);
        getOrders();
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return true;
    }

    public void logout(MenuItem item){
        Intent logoutIntent = new Intent(FulfillmentClerk.this, Login.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FulfillmentClerk.this.startActivity(logoutIntent);
        Toast.makeText(FulfillmentClerk.this, "GoodBye!", Toast.LENGTH_LONG).show();
    }

    public void getOrders() {
        final ProgressDialog progress = new ProgressDialog(FulfillmentClerk.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setMessage("Searching....");
        progress.show();
        adapter.clear();
        adapter.notifyDataSetChanged();
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
        TrackOrderRequest orderRequest = new TrackOrderRequest(true, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FulfillmentClerk.this);
        queue.add(orderRequest);
    }
}
