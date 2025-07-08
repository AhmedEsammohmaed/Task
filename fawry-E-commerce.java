package fawry_intern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


interface Shippable {
    String getname();
    double getWeight();
}

class product{
	 protected String name;
	 private double price;
	 private double quantity;
	 private LocalDate expiryDate;
	 
	 public product(String name,double price,double quantity,LocalDate expiryDate)
	 {
		 this.name = name;
	        this.price = price;
	        this.quantity = quantity;
	        this.expiryDate = expiryDate;
	 }
	 public String getname() {return name;}
	 public double getprice() {return price;}
	 public double getquantity() {return quantity;}
	 public LocalDate getexprry() {return expiryDate;}
	 
	 public boolean isexpired() {
		 return expiryDate != null && expiryDate.isBefore(LocalDate.now());
	 }
	 public void reducequantity(double amount)
	 {
		 this.quantity-=amount;
	 }
	 
	
}
 class Shipping extends product implements Shippable {
	private double weight;
	 public Shipping(String name,double price,double quantity,
			 LocalDate expiryDate,
	 double weight)
	 {
		 super(name,price,quantity,expiryDate);
		 this.weight=weight;
	 }
	 @Override
	 public double getWeight() {return weight;}
	 @Override
	 public String getname()
	 {
		 return this.name ;
	 }
	 
	
}
class customer{
private double balance;
  public customer(double balance)
  {
	  this.balance=balance;
  }
  public double getbalance() {return balance;}
  public void deductamount(double amount)
  {
	  this.balance-=amount;
  }
}
class cart{
	private Map<product,Integer>items=new HashMap<>();
    
	public void add(product product,int quantity)
	{
		if(product.getquantity()<quantity)
		{
			throw new  IllegalArgumentException("Not enough amount in stock for"
		+product.getname());
		}
		if(product.isexpired()) {
			throw new  IllegalArgumentException(product.getname()+ " is expired");
		}
		items.merge(product,quantity,Integer::sum);
	}
	public Map<product,Integer>getitems () {return items;}
	public boolean isEmpty() {return items.isEmpty();}
}

class shippingservices {
    public void ship(List<Shipping> items) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        for (Shipping item : items) {
            System.out.println(item.getname() + " " + item.getWeight() + "g");
            totalWeight += item.getWeight();
        }
        System.out.printf("Total package weight %.1fkg\n", totalWeight / 1000.0);
    }
}

class CheckoutService {
    private static final double SHIPPING_FEES_PER_KG = 30;

    public void checkout(customer customer, cart cart) {
        if (cart.isEmpty())
            throw new IllegalStateException("Cart is empty");

        double subtotal = 0;
        Map<String, Integer> itemQuantities = new LinkedHashMap<>();
        Map<String, Double> itemPrices = new LinkedHashMap<>();
        Map<String, Double> shippingItemUnitWeights = new LinkedHashMap<>();
        Map<String, Integer> shippingItemQuantities = new LinkedHashMap<>();


        for (Map.Entry<product, Integer> entry : cart.getitems().entrySet()) {
            product product = entry.getKey();
            int quantity = entry.getValue();

            if (product.isexpired())
                throw new IllegalStateException(product.getname() + " is expired");
            if (product.getquantity() < quantity)
                throw new IllegalStateException(product.getname() + " is out of stock");

            double price = product.getprice();
            double totalPrice = price * quantity;
            subtotal += totalPrice;

            itemQuantities.put(product.getname(), quantity);
            itemPrices.put(product.getname(), price);

            if (product instanceof Shipping) {
                Shipping shipping = (Shipping) product;
                shippingItemQuantities.merge(shipping.getname(), quantity, Integer::sum);
                shippingItemUnitWeights.put(shipping.getname(), shipping.getWeight());
            }
        }

        // Calculate total weight
        double totalWeightGrams = 0;
        for (String name : shippingItemQuantities.keySet()) {
            int qty = shippingItemQuantities.get(name);
            double unitWeight = shippingItemUnitWeights.get(name);
            totalWeightGrams += qty * unitWeight;
        }

        double shippingCost = (totalWeightGrams / 1000.0) * SHIPPING_FEES_PER_KG;
        double totalAmount = subtotal + shippingCost;

        if (customer.getbalance() < totalAmount)
            throw new IllegalStateException("Insufficient balance");

        // Deduct amount
        customer.deductamount(totalAmount);

        // Reduce stock
        for (Map.Entry<product, Integer> entry : cart.getitems().entrySet()) {
            entry.getKey().reducequantity(entry.getValue());
        }

        //  Shipment notice
        if (!shippingItemQuantities.isEmpty()) {
            System.out.println("** Shipment notice **");
            for (Map.Entry<String, Integer> entry : shippingItemQuantities.entrySet()) {
                String name = entry.getKey();
                int qty = entry.getValue();
                double unitWeight = shippingItemUnitWeights.getOrDefault(name, 0.0);
                double price = itemPrices.getOrDefault(name, 0.0);
                System.out.printf("%dx %s %.0fg %.0f EGP\n", qty, name, qty * unitWeight, qty * price);
            }
            System.out.printf("Total package weight %.1fkg\n\n", totalWeightGrams / 1000.0);
        }

       
     // Checkout receipt
        System.out.println("** Checkout receipt **");
        for (String name : itemQuantities.keySet()) {
            int qty = itemQuantities.get(name);
            double price = itemPrices.get(name);
            System.out.printf("%dx %s %.0f EGP\n", qty, name, qty * price);
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f EGP\n", subtotal);
        System.out.printf("Shipping %.0f EGP\n", shippingCost);
        System.out.printf("Amount %.0f EGP\n", totalAmount);
        System.out.printf("Customer balance after payment: %.0f EGP\n", customer.getbalance());
    }
}




public class fawry {
    public static void main(String[] args) {
        //🔹 Create some products combination between the shipment and not shipment product
    	
    	// those are a shipment case in the next two lines
        product cheese = new Shipping("Cheese", 100, 5, LocalDate.now().plusDays(2), 200);
        product tv = new Shipping("TV", 1000, 3, null, 5000);
        // this line is not a shipment product , it will not appear in the shipment part but will be added in the receipt
        product scratchCard = new product("Mobile Scratch Card", 50, 10, null);

        //  Create a customer with balance
        customer customer = new customer(4000);

        // Create a shopping cart
        cart cart = new cart();

        // Add items to cart
        try {
            cart.add(cheese, 2);               // 2 x Cheese (100 EGP)
            cart.add(tv, 3);                   // 3 x TV (1000 EGP)
            cart.add(scratchCard, 1);          // 1 x Mobile Scratch Card (50 EGP)

            //  Checkout
            CheckoutService checkout = new CheckoutService();
            checkout.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
