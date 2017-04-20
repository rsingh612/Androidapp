package teamosiris.liquorrush;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static android.content.Context.VIBRATOR_SERVICE;
import static teamosiris.liquorrush.LoggedIn.userCart;

/**
 *
 * Created by rsing on 10/17/2016.
 */

public class UpdateCartListener implements View.OnClickListener{
    private Product product;


    public UpdateCartListener(Product temp){
        this.product = temp;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cartPlusButton:
                product.increment();
                restartActivity(v);
                break;
            case R.id.cartMinusButton:
                if(product.getQuantity() > 1) {
                    product.decrement();
                }
                else if(product.getQuantity()==1) {
                    remove();
                }
                restartActivity(v);
                break;
        }
    }

    public void restartActivity(View v){
        Intent intent = new Intent(v.getContext(),Cart.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        v.getContext().startActivity(intent);
    }

    public void remove(){
        for(int i =0; i<userCart.size();i++){
            if(userCart.get(i).equals(product)){
                userCart.remove(i);
            }
        }
    }
}
