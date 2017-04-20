package teamosiris.liquorrush;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsingh on 9/6/2016.
 */
public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://osirisproject.net/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String license, String email, String password, String address, String city, String state, String zip, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("license", license);
        params.put("email", email);
        params.put("password", password);
        params.put("address", address);
        params.put("city", city);
        params.put("state", state);
        params.put("zip", zip);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
