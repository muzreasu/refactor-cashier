package cc.xpbootcamp.warmup.cashier;

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

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        StringBuilder output = new StringBuilder();

        output.append("======Printing Orders======\n");

        output.append(order.getCustomerName());
        output.append(order.getCustomerAddress());

        List<LineItem> lineItems = order.getLineItems();
        double totalAmount = INITIAL_NUMBER;

        buildLineItemReceipt(output, lineItems);
        Double totalSalesTax = lineItems.stream().map(LineItem::getTotalAmount).reduce(INITIAL_NUMBER,
                (subtotal, lineItem2) -> subtotal + lineItem2 * SALE_TAX_RATE);
        totalAmount = calculateTotalAmount(totalAmount);
        buildTotalTaxAndAmount(output, totalSalesTax, totalAmount);

        return output.toString();
    }


    private double calculateTotalAmount(double totalAmount) {
        for (LineItem lineItem : order.getLineItems()) {
            double salesTax = lineItem.getTotalAmount() * SALE_TAX_RATE;
            totalAmount += lineItem.getTotalAmount() + salesTax;
        }
        return totalAmount;
    }

    private void buildTotalTaxAndAmount(StringBuilder output, double totalSalesTax, double totalAmount) {
        output.append("Sales Tax").append('\t').append(totalSalesTax);
        output.append("Total Amount").append('\t').append(totalAmount);
    }

    private void buildLineItemReceipt(StringBuilder output, List<LineItem> lineItems) {
        lineItems.forEach((lineItem -> {
            output.append(lineItem.getDescription());
            output.append('\t');
            output.append(lineItem.getPrice());
            output.append('\t');
            output.append(lineItem.getQuantity());
            output.append('\t');
            output.append(lineItem.getTotalAmount());
            output.append('\n');
        }));
    }
}
