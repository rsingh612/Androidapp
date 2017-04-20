package teamosiris.liquorrush;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsing on 12/6/2016.
 */

public class UpdateRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://osirisproject.net/Update.php";
    private Map<String, String> params;

    public UpdateRequest(String update, String orderId, Response.Listener<String> listener){
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("update", update);
        params.put("orderId", orderId);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
