package cc.xpbootcamp.warmup.cashier;

import java.util.Calendar;
import java.util.List;

import static cc.xpbootcamp.warmup.cashier.Order.INVALID_DISCOUNT;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 */
public class OrderReceipt {
    private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        StringBuilder output = new StringBuilder();

        output = addHeader(output);
        addTime(output);
        addCustomerInfo(output);
        buildLineItemReceipt(output);
        output.append("----------------------");
        buildTotalTaxAndAmount(output);

        return output.toString();
    }

    private StringBuilder addHeader(StringBuilder output) {
        return output.append("====== 老王超市，值得信赖 ======\n\n");
    }

    private StringBuilder addTime(StringBuilder output) {
        return output.append(getTime());
    }

    private StringBuilder addCustomerInfo(StringBuilder output) {
        output.append(order.getCustomerName());
        output.append(order.getCustomerAddress());
        return output.append(getTime());
    }

    private String getTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月" +
                calendar.get(Calendar.DATE) + "日，星期" + calendar.get(Calendar.DAY_OF_WEEK) + "\n\n";
    }


    private void buildTotalTaxAndAmount(StringBuilder output) {
        output.append("Sales Tax").append(':').append(order.getTotalSalesTax() + "\n");
        double totalAmount = order.calculateTotalAmount();
        double totalDiscount = order.calculateDiscount();
        if (totalDiscount == INVALID_DISCOUNT) {
            output.append("Total Discount").append(':').append(totalDiscount + "\n");
            totalAmount = totalAmount - totalDiscount;
        }
        output.append("Total Amount").append(':').append(totalAmount);
    }

    private void buildLineItemReceipt(StringBuilder output) {
        order.getLineItems().forEach((lineItem -> {
            output.append(lineItem.getDescription());
            output.append(',');
            output.append(lineItem.getPrice());
            output.append('*');
            output.append(lineItem.getQuantity());
            output.append(',');
            output.append(lineItem.getTotalAmount());
            output.append('\n');
        }));
    }
}
