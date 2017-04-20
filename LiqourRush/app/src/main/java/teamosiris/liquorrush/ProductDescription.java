package teamosiris.liquorrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class ProductDescription extends AppCompatActivity {

    private TextView description;
    private WebView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        getSupportActionBar().hide();
        DisplayMetrics dimensions = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimensions);
        int width = dimensions.widthPixels;
        int height = dimensions.heightPixels;
        getWindow().setLayout((int)(width*0.8),(int)(height *0.50));

        description= (TextView) findViewById(R.id.descriptionInfo);
        image = (WebView) findViewById(R.id.descriptionImage);

        Intent intent = this.getIntent();
        String textDescription = intent.getStringExtra("description");
        String url = intent.getStringExtra("image");
        image.loadUrl(url);
        image.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        image.getSettings().setLoadWithOverviewMode(true);
        image.getSettings().setUseWideViewPort(true);

        description.setText(textDescription);
    }
}
