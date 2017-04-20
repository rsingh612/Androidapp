package teamosiris.liquorrush;

import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by rsingh on 9/6/2016.
 */
public class ProductRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL ="http://osirisproject.net/GetProducts.php";
    private Map<String, String> params;


    public ProductRequest(String search, String category, String page, String end, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("search", search);
        params.put("category", category);
        params.put("start", page);
        params.put("end",end);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
