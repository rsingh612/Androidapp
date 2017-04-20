package teamosiris.liquorrush;

import android.content.Intent;
import android.view.View;

/**
 * Created by rsing on 12/6/2016.
 */

public class OrderListener implements View.OnClickListener {
    private Order order;


    public OrderListener(Order temp) {
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
        Intent intent = new Intent(v.getContext(), OrderDescription.class);
        intent.putExtra("placed", getOrder().getOrder_placed());
        intent.putExtra("items", getOrder().getOrder_items());
        intent.putExtra("status", getOrder().getOrder_status());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        v.getContext().startActivity(intent);
    }
}