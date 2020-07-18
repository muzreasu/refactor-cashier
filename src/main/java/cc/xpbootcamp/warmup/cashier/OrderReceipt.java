package cc.xpbootcamp.warmup.cashier;

import static cc.xpbootcamp.warmup.cashier.Order.INVALID_DISCOUNT;

public class OrderReceipt {
    private Order order;

    public OrderReceipt(Order order) {
        this.order = order;
    }

    public String printReceipt() {
        return String.valueOf(
                addHeader()) +
                addTime() +
                buildLineItemReceipt() +
                "----------------------\n" +
                buildTotalTaxAndAmount();
    }

    private StringBuilder addHeader() {
        StringBuilder output = new StringBuilder();
        return output.append("====== 老王超市，值得信赖 ======\n\n");
    }

    private StringBuilder addTime() {
        StringBuilder output = new StringBuilder();
        return output.append(order.getTime());
    }

    private StringBuilder buildTotalTaxAndAmount() {
        StringBuilder output = new StringBuilder();
        output.append("Sales Tax").append(':').append(order.getTotalSalesTax() + "\n");
        double totalAmount = order.calculateTotalAmount();
        double totalDiscount = order.calculateDiscount();
        if (!(totalDiscount == INVALID_DISCOUNT)) {
            output.append("Total Discount").append(':').append(totalDiscount + "\n");
            totalAmount = totalAmount - totalDiscount;
        }
        return output.append("Total Amount").append(':').append(totalAmount);
    }

    private StringBuilder buildLineItemReceipt() {
        StringBuilder output = new StringBuilder();
        order.getLineItems().forEach(lineItem -> output.append(lineItem.getReceiptLine()));
        return output;
    }
}
