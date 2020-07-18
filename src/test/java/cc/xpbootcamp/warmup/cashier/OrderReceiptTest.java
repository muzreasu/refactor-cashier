package cc.xpbootcamp.warmup.cashier;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class OrderReceiptTest {
    @Test
    public void shouldPrintLineItemAndSalesTaxInformationGivenNotInWednesday() {
        LocalDate date = LocalDate.of(2020, 7, 17);
        List<LineItem> lineItems = new ArrayList<LineItem>() {{
            add(new LineItem("milk", 10.0, 2));
            add(new LineItem("biscuits", 5.0, 5));
            add(new LineItem("chocolate", 20.0, 1));
        }};
        OrderReceipt receipt = new OrderReceipt(new Order(new Customer(null, null), lineItems, date));

        String output = receipt.printReceipt();

        assertThat(output, containsString("milk,10.0*2,20.0\n"));
        assertThat(output, containsString("biscuits,5.0*5,25.0\n"));
        assertThat(output, containsString("chocolate,20.0*1,20.0\n"));
        assertThat(output, containsString("Sales Tax:6.5"));
        assertThat(output, containsString("Total Amount:71.5"));
    }

    @Test
    public void shouldPrintHeader() {
        Order order = new Order(new Customer(null, null), new ArrayList<>(), LocalDate.now());
        OrderReceipt receipt = new OrderReceipt(order);

        String output = receipt.printReceipt();

        assertThat(output, containsString("===== 老王超市，值得信赖 ====="));
    }

    @Test
    public void shouldPrintSeparator() {
        Order order = new Order(new Customer(null, null), new ArrayList<>(), LocalDate.now());
        OrderReceipt receipt = new OrderReceipt(order);

        String output = receipt.printReceipt();

        assertThat(output, containsString("----------------------"));
    }

    @Test
    public void shouldPrintTime() {
        LocalDate date = LocalDate.of(2020, 7, 17);
        Order order = new Order(new Customer(null, null), new ArrayList<>(), date);
        OrderReceipt receipt = new OrderReceipt(order);

        String output = receipt.printReceipt();

        assertThat(output, containsString("2020年7月17日，周五"));
    }

    @Test
    public void shouldPrintLineItemAndSalesTaxInformationAndDiscountGivenInWednesday() {
        LocalDate date = LocalDate.of(2020, 7, 15);
        List<LineItem> lineItems = new ArrayList<LineItem>() {{
            add(new LineItem("milk", 10.0, 2));
            add(new LineItem("biscuits", 5.0, 5));
        }};
        OrderReceipt receipt = new OrderReceipt(new Order(new Customer(null, null), lineItems, date));

        String output = receipt.printReceipt();

        assertThat(output, containsString("Sales Tax:4.5"));
        assertThat(output, containsString("Total Discount:0.9"));
        assertThat(output, containsString("Total Amount:48.6"));
    }

}
