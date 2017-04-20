package teamosiris.liquorrush;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by rsing on 10/14/2016.
 */

public class DescriptionListener implements View.OnTouchListener {
    private Product product;


    public DescriptionListener(Product temp) {
        this.product = temp;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.productImage:
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(v.getContext(), ProductDescription.class);
                    intent.putExtra("description", getProduct().getProduct_description());
                    intent.putExtra("image", getProduct().getProduct_image());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
        }
        return false;
    }
}
