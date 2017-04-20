package teamosiris.liquorrush;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsingh on 12/7/2016.
 */

public class DeliveryRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://osirisproject.net/GetAddress.php";
    private Map<String, String> params;


    public DeliveryRequest(String customerId, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("customerId", customerId);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}