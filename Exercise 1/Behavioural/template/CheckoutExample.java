// Abstract class defining the template
abstract class CheckoutProcess {
    // Template method
    public final void checkout() {
        addItemsToCart();
        enterPaymentInformation();
        confirmOrder();
    }

    // Common steps
    void addItemsToCart() {
        System.out.println("Items added to cart.");
    }

    void confirmOrder() {
        System.out.println("Order confirmed.");
    }

    // Abstract method for payment
    abstract void enterPaymentInformation();
}

// Concrete class for Credit Card payment
class CreditCardCheckout extends CheckoutProcess {
    @Override
    void enterPaymentInformation() {
        System.out.println("Entering credit card information.");
        // Additional logic for credit card processing
    }
}

// Concrete class for PayPal payment
class PayPalCheckout extends CheckoutProcess {
    @Override
    void enterPaymentInformation() {
        System.out.println("Logging into PayPal.");
        // Additional logic for PayPal processing
    }
}

// Client code
public class CheckoutExample {
    public static void main(String[] args) {
        // Checkout using Credit Card
        CheckoutProcess creditCardCheckout = new CreditCardCheckout();
        System.out.println("Credit Card Checkout:");
        creditCardCheckout.checkout();
        
        System.out.println("\nPayPal Checkout:");
        // Checkout using PayPal
        CheckoutProcess payPalCheckout = new PayPalCheckout();
        payPalCheckout.checkout();
    }
}
