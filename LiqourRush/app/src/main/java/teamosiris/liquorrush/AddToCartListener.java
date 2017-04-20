package teamosiris.liquorrush;

import android.view.View;
import android.widget.Toast;

import static teamosiris.liquorrush.LoggedIn.userCart;

/**
 * Created by rsing on 10/18/2016.
 */
public class AddToCartListener implements View.OnClickListener {
    Product temp;

    public AddToCartListener(Product temp) {
        this.temp = temp;
    }

    @Override
    public void onClick(View v) {
        if (!userCart.contains(temp)) {
            Product p = temp;
            if(p.getQuantity()==0) {
                p.increment();
            }
            userCart.add(p);
            Toast.makeText(v.getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(v.getContext(), "Item already in Cart!", Toast.LENGTH_SHORT).show();
        }
    }
}
