package teamosiris.liquorrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Delivery extends AppCompatActivity {
    TextView orderId, customer, address;
    EditText license;
    Button verify;
    String id, name, location, lno, temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        getSupportActionBar().hide();
        DisplayMetrics dimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int width = dimensions.widthPixels;
        int height = dimensions.heightPixels;
        getWindow().setLayout((int)(width*0.9),(int)(height *0.90));
        Intent intent = this.getIntent();
        id = intent.getStringExtra("orderId");
        name = intent.getStringExtra("name");
        location  = intent.getStringExtra("address");
        lno  = intent.getStringExtra("license");

        orderId = (TextView)findViewById(R.id.deliveryOrderId);
        orderId.setText(id);

        customer = (TextView)findViewById(R.id.deliveryCustomer);
        customer.setText(name);

        address = (TextView)findViewById(R.id.deliveryAddress);
        address.setText(location);

        license =(EditText)findViewById(R.id.deliveryLnoVerify);
        verify = (Button)findViewById(R.id.deliveryVerify);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = license.getText().toString();
                if(temp.equals(lno)){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    UpdateRequest updateRequest = new UpdateRequest("delivery", id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Delivery.this);
                    queue.add(updateRequest);
                    Intent logoutIntent = new Intent(Delivery.this, DeliveryClerk.class);
                    logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Delivery.this.startActivity(logoutIntent);
                }
                else{
                    Toast.makeText(Delivery.this,"License does not match!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
