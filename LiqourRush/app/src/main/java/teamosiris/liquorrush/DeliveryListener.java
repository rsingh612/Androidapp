package teamosiris.liquorrush;

import android.content.Intent;
import android.view.View;

/**
 * Created by rsing on 12/7/2016.
 */

public class DeliveryListener implements View.OnClickListener {
    private Order order;


    public DeliveryListener(Order temp) {
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
        Intent intent = new Intent(v.getContext(), Delivery.class);
        intent.putExtra("orderId",getOrder().getOrder_id());
        intent.putExtra("name", getOrder().getOrder_name());
        intent.putExtra("license", getOrder().getOrder_license());
        intent.putExtra("address", getOrder().getOrder_address());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        v.getContext().startActivity(intent);
    }
}
