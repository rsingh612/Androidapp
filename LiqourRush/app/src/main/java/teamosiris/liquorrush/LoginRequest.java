package teamosiris.liquorrush;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsingh on 9/6/2016.
 */
public class LoginRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://osirisproject.net/Login.php";
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
