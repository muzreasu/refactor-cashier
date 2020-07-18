package cc.xpbootcamp.warmup.cashier;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.nonNull;

public class Order {
    double INITIAL_NUMBER = 0d;
    double SALE_TAX_RATE = .10;
    double PER_DISCOUNT = .02;
    public static double INVALID_DISCOUNT = -1d;

    Customer customer;
    List<LineItem> lineItems;
    LocalDate localDate;

    public Order(Customer customer, List<LineItem> lineItems, LocalDate localDate) {
        this.customer = customer;
        this.lineItems = lineItems;
        this.localDate = localDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public String getTime() {
        this.localDate = nonNull(localDate) ? localDate : LocalDate.now();
        return localDate.format(DateTimeFormatter.ofPattern("yyyy年M月dd日，EEE\n").withLocale(Locale.CHINA));
    }

    public Double getTotalSalesTax() {
        return lineItems.stream().map(LineItem::getTotalAmount).reduce(INITIAL_NUMBER,
                (subtotal, lineItem2) -> subtotal + lineItem2 * SALE_TAX_RATE);
    }

    public double calculateTotalAmount() {
        double totalAmount = INITIAL_NUMBER;
        for (LineItem lineItem : getLineItems()) {
            totalAmount += lineItem.getTotalAmount() + lineItem.getTotalAmount() * SALE_TAX_RATE;
        }
        return totalAmount;
    }

    public double calculateDiscount() {
        double totalDiscount = INITIAL_NUMBER;
        if (localDate.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
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
