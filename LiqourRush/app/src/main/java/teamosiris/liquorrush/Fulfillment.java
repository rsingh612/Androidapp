package teamosiris.liquorrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Fulfillment extends AppCompatActivity {
    TextView itemsView;
    Button update;
    String orderId, items;
    String role = "clerk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfillment);
        getSupportActionBar().hide();
        DisplayMetrics dimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int width = dimensions.widthPixels;
        int height = dimensions.heightPixels;
        getWindow().setLayout((int)(width*0.9),(int)(height *0.90));
        Intent intent = this.getIntent();
        itemsView = (TextView)findViewById(R.id.fulfillmentItems);
        update = (Button)findViewById(R.id.fulfillmentButton);
        items = intent.getStringExtra("items");
        orderId = intent.getStringExtra("orderId");

        itemsView.setText(items);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(Fulfillment.this,"Order Updated!",Toast.LENGTH_SHORT).show();
                                Intent logoutIntent = new Intent(Fulfillment.this, FulfillmentClerk.class);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                Fulfillment.this.startActivity(logoutIntent);
                            } else {
                                Toast.makeText(Fulfillment.this,"Unexpected Error! Try Again",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                UpdateRequest updateRequest = new UpdateRequest(role,orderId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Fulfillment.this);
                queue.add(updateRequest);
            }
        });


    }
}
