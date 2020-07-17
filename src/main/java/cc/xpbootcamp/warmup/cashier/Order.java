package cc.xpbootcamp.warmup.cashier;

import java.util.Calendar;
import java.util.List;

public class Order {
    double INITIAL_NUMBER = 0d;
    double SALE_TAX_RATE = .10;
    double PER_DISCOUNT = .98;
    public static double INVALID_DISCOUNT = -1d;

    String customerName;
    String customerAddress;
    List<LineItem> lineItems;

    public Order(String customerName, String customerAddress, List<LineItem> lineItems) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.lineItems = lineItems;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public Double getTotalSalesTax() {
        return lineItems.stream().map(LineItem::getTotalAmount).reduce(INITIAL_NUMBER,
                (subtotal, lineItem2) -> subtotal + lineItem2 * SALE_TAX_RATE);
    }

    public double calculateTotalAmount() {
        double totalAmount = INITIAL_NUMBER;
        for (LineItem lineItem : getLineItems()) {
            double salesTax = lineItem.getTotalAmount() * SALE_TAX_RATE;
            totalAmount += lineItem.getTotalAmount() + salesTax;
        }
        return totalAmount;
    }

    public double calculateDiscount() {
        double totalDiscount = INITIAL_NUMBER;
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            for (LineItem lineItem : getLineItems()) {
                double discount = lineItem.getQuantity() * lineItem.getPrice() * PER_DISCOUNT;
                totalDiscount += discount;
            }
        } else {
            return INVALID_DISCOUNT;
        }
        return totalDiscount;
    }
}
