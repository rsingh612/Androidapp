package teamosiris.liquorrush;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity{

    private Button bt_register;
    private EditText name, license, email, password,passCheck,address, city, zip;
    private Spinner state;
    private String registerName, registerLicense, registerEmail, registerPassword, registerPassCheck,
            registerAddress, registerCity, registerState, registerZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        bt_register = (Button)findViewById(R.id.registerButton);
        name = (EditText)findViewById(R.id.registerName);
        license =(EditText)findViewById(R.id.registerPhone);
        email = (EditText)findViewById(R.id.registerEmail);
        password = (EditText)findViewById(R.id.registerPassword);
        passCheck = (EditText)findViewById(R.id.registerReenter);
        address = (EditText)findViewById(R.id.registerAddress);
        city = (EditText)findViewById(R.id.registerCity);
        state = (Spinner)findViewById(R.id.registerState);
        zip = (EditText)findViewById(R.id.registerZip);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_register.setEnabled(false);
                final ProgressDialog progress =new ProgressDialog(Register.this);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setMessage("Registering...");

                registerName = name.getText().toString();
                registerLicense = license.getText().toString();
                registerEmail = email.getText().toString();
                registerPassword = password.getText().toString();
                registerPassCheck = passCheck.getText().toString();
                registerAddress = address.getText().toString();
                registerCity = city.getText().toString();
                registerState = state.getSelectedItem().toString();
                registerZip = zip.getText().toString();

                if (validate()) {
                    progress.show();
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success) {
                                        progress.dismiss();
                                        Intent intent = new Intent(Register.this, Login.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        Register.this.startActivity(intent);
                                    } else {
                                        progress.dismiss();
                                        bt_register.setEnabled(true);
                                        Toast.makeText(Register.this, "Registration Failed! Email in use", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                    };

                    RegisterRequest registerRequest = new RegisterRequest(registerName, registerLicense,
                            registerEmail, registerPassword, registerAddress, registerCity, registerState,
                            registerZip, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Register.this);
                    queue.add(registerRequest);
                }

            }
        });
    }

    private boolean validate(){
        boolean valid = true;
            if(registerName.isEmpty() || registerName.length() < 3){
                name.setError("Enter name");
                Toast.makeText(Register.this, "Enter name", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                name.setError(null);
            }
            if(registerLicense.isEmpty()){
                license.setError("Enter license");
                Toast.makeText(Register.this, "Enter license", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                license.setError(null);
            }
            if(registerEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(registerEmail).matches()){
                email.setError("Enter a valid email address");
                Toast.makeText(Register.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                email.setError(null);
            }
            if(registerPassword.isEmpty() || registerPassword.length() < 8){
                password.setError("8 character minimum");
                Toast.makeText(Register.this, "Password must be 8 character minimum", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                password.setError(null);
            }
            if(!registerPassCheck.contentEquals(registerPassword)){
                passCheck.setError("Passwords must match");
                Toast.makeText(Register.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                passCheck.setError(null);
            }
            if(registerAddress.isEmpty()){
                address.setError("Enter address");
                Toast.makeText(Register.this, "Enter address", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                address.setError(null);
            }
            if(registerCity.isEmpty()){
                city.setError("Enter city");
                Toast.makeText(Register.this, "Enter city", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                city.setError(null);
            }
            if(registerState.contentEquals("Select State...")){
                Toast.makeText(Register.this, "Select a valid state", Toast.LENGTH_LONG).show();
                valid = false;
            }
            if(registerZip.isEmpty()){
                zip.setError("Enter zip");
                Toast.makeText(Register.this, "Enter zip", Toast.LENGTH_LONG).show();
                valid = false;
            } else {
                zip.setError(null);
            }

        bt_register.setEnabled(true);
        return valid;
    }

}
