package cc.xpbootcamp.warmup.cashier;

public class Customer {
    String customerName;
    String customerAddress;

    public Customer(String customerName, String customerAddress) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }
}
