package teamosiris.liquorrush;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsing on 12/6/2016.
 */

public class TrackOrderRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://osirisproject.net/GetOrders.php";
    private Map<String, String> params;

    public TrackOrderRequest(String customerId, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("customerId", customerId);
    }

    public TrackOrderRequest(boolean t, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        if(t) {
            String customerId = "!@#*#&#q3)i*gd;^w%m^";
            String job = "clerk";
            params = new HashMap<>();
            params.put("customerId", customerId);
            params.put("job", job);
        }else{
            String customerId = "!@#*#&#q3)i*gd;^w%m^";
            String job = "delivery";
            params = new HashMap<>();
            params.put("customerId", customerId);
            params.put("job", job);
        }
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
