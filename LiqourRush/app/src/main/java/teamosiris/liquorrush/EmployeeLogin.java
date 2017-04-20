package teamosiris.liquorrush;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class EmployeeLogin extends AppCompatActivity {
    private EditText user, pass;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_login);
        getSupportActionBar().hide();
        DisplayMetrics dimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int width = dimensions.widthPixels;
        int height = dimensions.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height *0.23));

        bt_login = (Button) findViewById(R.id.employeeLoginButton);
        user = (EditText) findViewById(R.id.employeeId);
        pass = (EditText) findViewById(R.id.employeePassword);

        bt_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bt_login.setEnabled(false);
                final ProgressDialog progress =new ProgressDialog(EmployeeLogin.this);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setMessage("Authenticating...");
                progress.show();

                final String username = user.getText().toString();
                final String password = pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success){
                                progress.dismiss();
                                String userName = jsonObject.getString("userName");
                                String userType = jsonObject.getString("userType");

                                if(userType.contentEquals("clerk")) {
                                    Intent intent = new Intent(EmployeeLogin.this, FulfillmentClerk.class);
                                    intent.putExtra("userName", userName);
                                    EmployeeLogin.this.startActivity(intent);
                                }
                                else if(userType.contentEquals("delivery")){
                                    Intent intent = new Intent(EmployeeLogin.this, DeliveryClerk.class);
                                    intent.putExtra("userName", userName);
                                    EmployeeLogin.this.startActivity(intent);
                                }
                                bt_login.setEnabled(true);
                            }
                            else {
                                progress.dismiss();
                                bt_login.setEnabled(true);
                                Toast.makeText(EmployeeLogin.this, "Unauthrozied user!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                EmployeeLoginRequest loginRequest = new EmployeeLoginRequest(username,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(EmployeeLogin.this);
                queue.add(loginRequest);
            }
        });
    }
}
