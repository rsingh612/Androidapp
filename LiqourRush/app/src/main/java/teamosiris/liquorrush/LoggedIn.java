package teamosiris.liquorrush;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;

public class LoggedIn extends AppCompatActivity {
    static ArrayList<Product> userCart;
    static StringBuilder items;
    //swipe variables
    static final int MIN_DISTANCE = 450;
    static String customerID;
    private float x1,x2;

    //logged in context variables
    private SearchView search;
    private ImageButton bt_voice;
    private ListView productView;
    private Spinner numOfResults, queryCategory;
    private String querySearch = ".@/[]^*(";
    private String category;
    private String pageNum = "0";
    private String totalResultDisplayed;
    private int check = 0;
    private boolean result=false;
    private ProductAdapter adapter;
    //temp variables
    String id, image, name, description, group, price;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        customerID = this.getIntent().getStringExtra("id");
        String name = this.getIntent().getStringExtra("name");
        String address = this.getIntent().getStringExtra("address");
        String city = this.getIntent().getStringExtra("city");
        String state = this.getIntent().getStringExtra("state");
        String zip = this.getIntent().getStringExtra("zip");

        Customer c = Customer.getInstance();
        c.setCustomer(customerID, name, address, city, state, zip);

        userCart = new ArrayList<>();
        items = new StringBuilder();

        //instantiate all variables onViewCreate
        numOfResults = (Spinner) findViewById(R.id.noOfResultsSelector);
        queryCategory = (Spinner) findViewById(R.id.categorySelector);
        search = (SearchView) findViewById(R.id.searchview);
        search.setBackgroundColor(Color.GRAY);
        search.setQueryHint("Search...");
        productView = (ListView) findViewById(R.id.productDisplayView);
        bt_voice = (ImageButton) findViewById(R.id.searchVoiceBtn);
        productView.setVisibility(View.VISIBLE);
        adapter = new ProductAdapter(getApplicationContext(), R.layout.product_listview);
        productView.setAdapter(adapter);

        category = queryCategory.getSelectedItem().toString();
        totalResultDisplayed = numOfResults.getSelectedItem().toString();

        getProducts();
        productView.invalidateViews();

        queryCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = queryCategory.getSelectedItem().toString();
                pageNum = "0";
                getProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }

        });

        numOfResults.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                totalResultDisplayed = numOfResults.getSelectedItem().toString();
                getProducts();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }

        });

        bt_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Listening...");

                try {
                    startActivityForResult(intent, 100);
                } catch (ActivityNotFoundException a){
                    Toast.makeText(LoggedIn.this,"Device does not support speech input!",Toast.LENGTH_LONG).show();
                }
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length()>3) {
                    querySearch = query;
                    pageNum = "0";
                    category = queryCategory.getSelectedItem().toString();
                    totalResultDisplayed = numOfResults.getSelectedItem().toString();
                    getProducts();
                }
                else{
                    Toast.makeText(LoggedIn.this,"Enter a minimum of 4 characters!",Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        search.setOnCloseListener(new SearchView.OnCloseListener() {

            @Override
            public boolean onClose() {
                querySearch=".@/[]^*(";
                productView.invalidateViews();
                getProducts();
                adapter.notifyDataSetInvalidated();
                return false;
            }
        });

        productView.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;

                        if(Math.abs(deltaX) > MIN_DISTANCE){
                            //go right action
                            if(x2 < x1){
                                if(result) {
                                    int nextPage = (Integer.parseInt(pageNum) + Integer.parseInt(totalResultDisplayed));
                                    pageNum = String.valueOf(nextPage);
                                    getProducts();
                                }
                                else{
                                    Toast.makeText(LoggedIn.this,"End of the List!", Toast.LENGTH_LONG).show();
                                }
                            }
                            //going left
                            else {
                                int nextPage = (Integer.parseInt(pageNum)-Integer.parseInt(totalResultDisplayed));
                                if(nextPage < 0){
                                    pageNum ="0";
                                    getProducts();
                                    Toast.makeText(LoggedIn.this,"Start of the List!", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    pageNum = String.valueOf(nextPage);
                                    getProducts();

                                }
                            }
                        }

                }
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int request, int result, Intent intent){
        super.onActivityResult(request, result, intent);
        switch (request) {
            case 100: {
                if(result == RESULT_OK && intent != null){
                    ArrayList<String> temp = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    querySearch = temp.get(0);
                    pageNum = "0";
                    category = queryCategory.getSelectedItem().toString();
                    totalResultDisplayed = numOfResults.getSelectedItem().toString();
                    getProducts();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        return true;
    }

    public void openCart(MenuItem item) {
        startActivity(new Intent(LoggedIn.this, Cart.class));
    }

    public void logout(MenuItem item) {
        Intent logoutIntent = new Intent(LoggedIn.this, Login.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LoggedIn.this.startActivity(logoutIntent);
        Toast.makeText(LoggedIn.this, "GoodBye!", Toast.LENGTH_LONG).show();
    }

    public void trackOrder(MenuItem item){
        Intent trackIntent = new Intent(LoggedIn.this, TrackOrders.class);
        trackIntent.putExtra("customerId", customerID);
        LoggedIn.this.startActivity(trackIntent);
    }

    public void getProducts() {
        final ProgressDialog progress =new ProgressDialog(LoggedIn.this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setMessage("Searching....");
        progress.show();
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (!response.equals("[]")) {
                    progress.dismiss();
                    result=true;
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int max = jsonObject.length();
                        for (int i = 1; i <= max; ) {
                            id = jsonObject.getString(Integer.toString(i++));
                            image = jsonObject.getString(Integer.toString(i++));
                            name = jsonObject.getString(Integer.toString(i++));
                            description = jsonObject.getString(Integer.toString(i++));
                            group = jsonObject.getString(Integer.toString(i++));
                            price = jsonObject.getString(Integer.toString(i++));
                            Product temp = new Product(id, image, name, description, group, price);
                            adapter.add(temp);
                            adapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    progress.dismiss();
                    /**logic prevent no more results to appear more than once instead on
                     * swipes we indicate the end of list with custom logic below
                    */
                    result = false;
                    int nextPage = (Integer.parseInt(pageNum)-Integer.parseInt(totalResultDisplayed));
                    //prevent page index to go below 0 due to right swipes
                    if(nextPage<0){
                        pageNum ="0";
                        adapter.notifyDataSetChanged();
                    }else{
                        pageNum = String.valueOf(nextPage);
                        adapter.notifyDataSetChanged();
                    }
                    if(!result && response.equals("[]")) {
                        Toast.makeText(LoggedIn.this, ("No Results Found!"), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };

        ProductRequest productRequest = new ProductRequest(querySearch, category, pageNum, totalResultDisplayed, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoggedIn.this);
        queue.add(productRequest);

    }
}