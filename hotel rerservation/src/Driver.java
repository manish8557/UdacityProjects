import model.Customer;

public class Driver {

    public static void main(String[] args) {

        Customer customer = new Customer("first", "second", "j@domain.com");
        Customer customer1 = new Customer("first", "second", "email");

        System.out.println(customer);
        System.out.println(customer1);

    }

}