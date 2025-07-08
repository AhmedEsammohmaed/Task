import java.util.*;



abstract class book
{
    protected String ISBN, title,author;
    protected int year;
    protected double price;

    public book(String ISBN, String title,String author,int year,double price)
    {
        this.ISBN=ISBN;
        this.title=title;
        this.author=author;
        this.year=year;
        this.price=price;
    }
    public String getISBN()
    {
        return ISBN;
    }
    public String gettitle()
    {
     return title;
    }
    public String getauther()
    {
        return author;
    }
    public int getyear()
    {
        return year;
    }
    public double getprice()
    {
        return price;
    }
    public abstract boolean isforsale();
    public abstract double buy(int quantity,String email,String address);
}

class Paperbook extends book{
    private int stock;

    public Paperbook(String ISBN, String title,String author,int year,double price,int stock)
    {
        super(ISBN,title,author,year,price);
        this.stock=stock;
    }
    public boolean isforsale(){return true;}
    @Override
    public double buy(int quantity,String email,String address)
    {
        if(quantity<=0)
        {
            throw new IllegalArgumentException("Quantum book store: You must enter a positive quantity");
        }
        if(stock< quantity)
        {
            throw new IllegalArgumentException("Quantum book store: Not enough stock for ISBN: " + ISBN);
        }
        stock-=quantity;
        ShippingService.send(address);
        System.out.println("quantum book store: shipping "+ quantity +" copies of "+title);
        return price*quantity;

    }
}
class Ebook extends book{
    private String filetype;
    public Ebook(String ISBN, String title,String author,int year,double price,String filString)
    {
        super(ISBN,title, author, year, price);
        this.filetype=filetype;    
    }
    public boolean isforsale(){return true;}
    @Override
    public double buy(int quantity,String email,String address)
    {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantum book store: Quantity must be at least 1.");
        }
        if (quantity > 1) {
            throw new IllegalArgumentException("Quantum book store: EBooks can only be bought one at a time.");
        }
          MailService.send(email);
          System.out.println("Quantum book store: Emailing EBook " + title + " to " + email);
          return price;

    }

}
class AudioBook extends book {
    private String format;

    public AudioBook(String isbn, String title, String author, int year, double price, String format) {
        super(isbn, title, author, year, price);
        this.format = format;
    }

    public boolean isforsale() { return true; }

    @Override
    public double buy(int quantity, String email, String address) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantum book store: Quantity must be positive.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Quantum book store: Email is required to send AudioBook.");
        }
        System.out.println("Quantum book store: Sending AudioBook " + title + " (" + format + ") to " + email);
        
        return price * quantity;
    }
}
class showcasebook extends book{
    public showcasebook(String isbn, String title, String author, int year)
    {
        super(isbn, title, author, year,0.0 );
    }
    public boolean isforsale(){return false;}
    public double buy(int quantity,String email,String address)
    {
        throw new UnsupportedOperationException("Quantum book store: Showcase books are not for sale.");
    }
    public void show() {
        System.out.println("Quantum book store: SHOWCASE 📚");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year: " + year);
        System.out.println("ISBN: " + ISBN);
    }
}
class Quantumbookstore
{
    private Map<String,book>inventory=new HashMap<>();
    public void addbook(book b)
    {
      inventory.put(b.getISBN(),b);
      System.out.println("Quantum book store: Book added -> " + b.gettitle());

    }
    public book removeoutdatedbook(int currentyear,int maxyear)
    {
        for(String ISBN :inventory.keySet())
        {
            book b=inventory.get(ISBN);
            if((currentyear-b.getyear())>maxyear)
             {
                inventory.remove(ISBN);
                System.out.println("Quantum book store: Removed outdated book -> " + b.gettitle());
                return b;
             }
        }
        return null;
    }
    public double buybook(String ISBN,int quantity,String email,String address)
    {
        if(!inventory.containsKey(ISBN))
        {
            throw new IllegalArgumentException("Quantum book store: Book with ISBN not found: " + ISBN);
        }
         book b=inventory.get(ISBN);
         if(!b.isforsale())
         {
            throw new IllegalArgumentException("Quantum book store: Book is not for sale: " + b.gettitle());
         }

        return b.buy(quantity,email,address);
    }
    public book getBookByISBN(String isbn) {
        return inventory.get(isbn);
    }
    
}

    


class ShippingService{
  public static void send(String address)
  {
    System.out.println("Quantun book store: shipping to "+address);
  }
}
class MailService
{
    public static void send(String email)
    {
        System.out.println("Quantum book store: sending email to "+email);
    }
}




public class fawrytask2 {
 public static void main(String[] ags)
 {
    Quantumbookstore store=new Quantumbookstore();
    store.addbook(new Paperbook("ISBN001", "Java Masterynull", "Ahmed", 2018, 50.0, 10));
    store.addbook(new Ebook("ISBN002", "AI with Python", "Lotfy", 2021, 30.0, "pdf"));
    store.addbook(new AudioBook("ISBN003", "C++", "Esam", 2019, 30.0, "mp3"));
    store.addbook(new showcasebook("ISBN004", "Rare Quantum Notes", "Einstein", 1905));
   //---------------------------------------------------------
   System.out.println("---------------------------------------------------------------");
    //Error purchase: PaperBook
    try{
        double total1=store.buybook("ISBN001", 0, "buyer1@example.com", "123 Cairo Street");
        System.out.println("Quantum book store: Total paid for PaperBook: $" + total1);
    }
    catch (Exception e) {
            System.out.println(e.getMessage());
   }

     //Successful purchase: PaperBook
    try{
    double total2=store.buybook("ISBN001", 1, "buyer1@example.com", "123 Cairo Street");
    System.out.println("Quantum book store: Total paid for PaperBook: $" + total2);
    }
    catch (Exception e) {
        System.out.println(e.getMessage());
    }
    System.out.println("---------------------------------------------------------------");
   //----------------------------------------------------------------------------------------------------
   // invalid purchase: Ebook
   try{
    double total3=store.buybook("ISBN002", 3, "buyer4@example.com", null);
    System.out.println("Quantum book store: Total paid for PaperBook: $" + total3);
    }
    catch (Exception e) {
        System.out.println(e.getMessage());
    }

    //Successful : more than 1 Ebook
    try{
        double total4=store.buybook("ISBN002", 1, "buyer4@example.com", null);
        System.out.println("Quantum book store: Total paid for PaperBook: $" + total4);
    }
    catch (Exception e) {
            System.out.println(e.getMessage());
    }
    System.out.println("---------------------------------------------------------------");
    //---------------------------------------------------------------------------------------
    //invalid purchase: AudioBook
    try{
    double total5=store.buybook("ISBN003", 2, null,null);
    System.out.println("Quantum book store: Total paid for PaperBook: $" + total5);
    }
    catch (Exception e) {
        System.out.println(e.getMessage());
    }
    //Successful purchase: AudioBook
    try{
    double total6=store.buybook("ISBN003", 1,  "audio_lover@example.com", null);
    System.out.println("Quantum book store: Total paid for PaperBook: $" + total6);
    }
    catch (Exception e) {
        System.out.println(e.getMessage());
    }
    
    System.out.println("---------------------------------------------------------------");
     //--------------------------------------------------------------------------------------
    //invalid showcase book
    try{
        double total7=store.buybook("ISBN001", 2, "buyer4@example.com", null);
        System.out.println("Quantum book store: Total paid for PaperBook: $" + total7); 
    }
    catch (Exception e) {
            System.out.println(e.getMessage()); 
    }

    // Successfulshowcase book
    try{
    double total8=store.buybook("ISBN001", 2, "buyer1@example.com", null);
    System.out.println("Quantum book store: Total paid for PaperBook: $" + total8); 
    }
    catch (Exception e) {
        System.out.println(e.getMessage()); 
    }
    System.out.println("---------------------------------------------------------------");
    //---------------------------------------------------------------------------------------
    book showcase=store.getBookByISBN("ISBN004");
    if(showcase instanceof showcasebook)
    {
        ((showcasebook) showcase).show();
    }

}  
}
