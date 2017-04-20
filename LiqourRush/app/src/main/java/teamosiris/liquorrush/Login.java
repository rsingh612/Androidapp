package teamosiris.liquorrush;

import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Login extends AppCompatActivity {
    private Button bt_login;
    private EditText email, password;
    private TextView register;
    private ImageView bt_employeeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        bt_login = (Button) findViewById(R.id.loginButton);
        bt_employeeLogin = (ImageView) findViewById(R.id.loginVendor);
        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPassword);
        register = (TextView)findViewById(R.id.loginRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                Login.this.startActivity(registerIntent);
            }
        });

        bt_employeeLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, EmployeeLogin.class);
                Login.this.startActivity(registerIntent);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bt_login.setEnabled(false);
                final ProgressDialog progress =new ProgressDialog(Login.this);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setMessage("Authenticating...");
                progress.show();

                final String e = email.getText().toString();
                final String p = password.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success){
                                progress.dismiss();
                                bt_login.setEnabled(true);
                                String name = jsonObject.getString("name");
                                String email = jsonObject.getString("email");
                                String id = jsonObject.getString("id");
                                String address = jsonObject.getString("address");
                                String city = jsonObject.getString("city");
                                String state = jsonObject.getString("state");
                                String zip = jsonObject.getString("zip");

                                Intent intent = new Intent(Login.this, LoggedIn.class);
                                intent.putExtra("email", email);
                                intent.putExtra("name", name);
                                intent.putExtra("id", id);
                                intent.putExtra("address", address);
                                intent.putExtra("city", city);
                                intent.putExtra("state", state);
                                intent.putExtra("zip", zip);
                                Login.this.startActivity(intent);
                            }
                            else {
                                progress.dismiss();
                                bt_login.setEnabled(true);
                                Toast.makeText(Login.this, "Invalid Username or Password!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(e,p,responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });

    }
}

