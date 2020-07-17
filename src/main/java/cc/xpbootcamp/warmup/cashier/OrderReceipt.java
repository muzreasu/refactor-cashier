package cc.xpbootcamp.warmup.cashier;

import java.util.Calendar;
import java.util.List;

/**
 * OrderReceipt prints the details of order including customer name, address, description, quantity,
 * price and amount. It also calculates the sales tax @ 10% and prints as part
 * of order. It computes the total order amount (amount of individual lineItems +
 * total sales tax) and prints it.
 */
public class OrderReceipt {
    private Order order;

    double INITIAL_NUMBER = 0d;
    double SALE_TAX_RATE = .10;
    double PER_DISCOUNT = .98;
    double INVALID_DISCOUNT = -1d;

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        StringBuilder output = new StringBuilder();

        output = addHeader(output);
        addTime(output);
        addCustomerInfo(output);

        List<LineItem> lineItems = order.getLineItems();
        double totalAmount = INITIAL_NUMBER;

        buildLineItemReceipt(output, lineItems);
        output.append("----------------------");

        Double totalSalesTax = lineItems.stream().map(LineItem::getTotalAmount).reduce(INITIAL_NUMBER,
                (subtotal, lineItem2) -> subtotal + lineItem2 * SALE_TAX_RATE);
        totalAmount = calculateTotalAmount(totalAmount);
        double totalDiscount = calculateDiscount();

        buildTotalTaxAndAmount(output, totalSalesTax, totalAmount, totalDiscount);

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


    private double calculateTotalAmount(double totalAmount) {
        for (LineItem lineItem : order.getLineItems()) {
            double salesTax = lineItem.getTotalAmount() * SALE_TAX_RATE;
            totalAmount += lineItem.getTotalAmount() + salesTax;
        }
        return totalAmount;
    }

    private double calculateDiscount() {
        double totalDiscount = INITIAL_NUMBER;
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
            for (LineItem lineItem : order.getLineItems()) {
                double discount = lineItem.getQuantity() * lineItem.getPrice() * PER_DISCOUNT;
                totalDiscount += discount;
            }
        } else {
            return INVALID_DISCOUNT;
        }
        return totalDiscount;
    }

    private void buildTotalTaxAndAmount(StringBuilder output, double totalSalesTax, double totalAmount, double totalDiscount) {
        output.append("Sales Tax").append(':').append(totalSalesTax + "\n");
        if (totalDiscount == INVALID_DISCOUNT) {
            output.append("Total Discount").append(':').append(totalDiscount + "\n");
        }
        output.append("Total Amount").append(':').append(totalAmount);
    }

    private void buildLineItemReceipt(StringBuilder output, List<LineItem> lineItems) {
        lineItems.forEach((lineItem -> {
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
