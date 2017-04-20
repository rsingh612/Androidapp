package teamosiris.liquorrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class OrderDescription extends AppCompatActivity {
    private TextView items, status, placed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_description);
        getSupportActionBar().hide();
        DisplayMetrics dimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int width = dimensions.widthPixels;
        int height = dimensions.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height *0.50));

        items = (TextView)findViewById(R.id.orderDescriptionItems);
        status = (TextView)findViewById(R.id.orderDescriptionStatus);
        placed = (TextView)findViewById(R.id.orderDescriptionPlaced);

        Intent intent = this.getIntent();
        String orderItems = intent.getStringExtra("items");
        String orderStatus = intent.getStringExtra("status");
        String orderPlaced = intent.getStringExtra("placed");


        items.setText(orderItems);
        status.setText(orderStatus);
        placed.setText(orderPlaced);

    }
}
