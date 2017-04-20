package teamosiris.liquorrush;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rsingh on 9/6/2016.
 */
public class OrderRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://osirisproject.net/SubmitOrder.php";
    private Map<String, String> params;


    public OrderRequest(String orderId, String customerId, String orderedItems, String date, String status, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);
        params.put("itemsOrdered", orderedItems);
        params.put("timePlaced", date);
        params.put("status", status);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
