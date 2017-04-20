package teamosiris.liquorrush;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.stripe.android.*;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

public class Payment extends AppCompatActivity {

    EditText cardNumber, expMonth, expYear, cvc, street, city, zip, customerName;
    Spinner state;
    String items, customerId;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        items = this.getIntent().getStringExtra("items");
        customerId = this.getIntent().getStringExtra("id");

        Customer c = Customer.getInstance();


        cardNumber = (EditText)findViewById(R.id.stripeCreditCard);
        expMonth = (EditText)findViewById(R.id.stripeMonth);
        expYear = (EditText)findViewById(R.id.stripeYear);
        cvc = (EditText)findViewById(R.id.stripeCvc);
        submit = (Button)findViewById(R.id.stripeSubmit);
        customerName = (EditText)findViewById(R.id.stripeCustomerName);
        street = (EditText)findViewById(R.id.stripeStreet);
        state = (Spinner)findViewById(R.id.stripeState);
        zip = (EditText)findViewById(R.id.stripeZip);
        city = (EditText)findViewById(R.id.stripeCity);

        customerName.setText(c.getCustomerName());
        street.setText(c.getCustomerAddress());
        zip.setText(c.getCustomerZip());
        city.setText(c.getCustomerCity());
        selectState(state, c.getCustomerState());



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = new Intent(v.getContext(),LoggedIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                Card card = new Card(cardNumber.getText().toString(),
                        (Integer.parseInt(expMonth.getText().toString())),
                        (Integer.parseInt(expYear.getText().toString())),
                        cvc.getText().toString());

                card.setAddressCity(city.getText().toString());
                card.setAddressLine1(street.getText().toString());
                card.setAddressState(state.getSelectedItem().toString());
                card.setAddressZip(zip.getText().toString());



                Stripe strip = null;

                try {
                    strip = new Stripe("pk_test_dcrYbq4ArBxIYiXINya0m60a");
                } catch (AuthenticationException e) {
                    e.printStackTrace();
                }

                strip.createToken(
                        card,
                        new TokenCallback() {
                            @Override
                            public void onError(Exception error) {
                                Toast.makeText(getBaseContext(),"Payment Error! Try Again!",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onSuccess(Token token) {
                                String orderId = token.getId();
                                orderId = orderId.substring(7);
                                String date = String.valueOf(token.getCreated());
                                String customer = customerId;
                                String orderedItems = items;
                                String status = "New Order";

                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try{
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");
                                            if (success){
                                                Toast.makeText(getBaseContext(),"Payment Accepted!",Toast.LENGTH_LONG).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                OrderRequest orderRequest = new OrderRequest(orderId,customer,orderedItems,date,status,responseListener);
                                RequestQueue queue = Volley.newRequestQueue(Payment.this);
                                queue.add(orderRequest);
                                v.getContext().startActivity(intent);
                            }
                        }
                );
            }
        });
    }

    public static void selectState(Spinner spinner, String value){
        for(int p =0; p<spinner.getCount(); p++){
            if(spinner.getItemAtPosition(p).equals(value)){
                spinner.setSelection(p);
                break;
            }
        }
    }

}
