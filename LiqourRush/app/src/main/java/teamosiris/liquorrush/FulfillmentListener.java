package teamosiris.liquorrush;

import android.content.Intent;
import android.view.View;

/**
 * Created by rsing on 12/6/2016.
 */

public class FulfillmentListener implements View.OnClickListener {
    private Order order;


    public FulfillmentListener(Order temp) {
        this.order = temp;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), Fulfillment.class);
        intent.putExtra("orderId", getOrder().getOrder_id());
        intent.putExtra("items", getOrder().getOrder_items());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        v.getContext().startActivity(intent);
    }
}
