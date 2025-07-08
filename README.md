Quantum Book Store System (Java)

A modular OOP-based bookstore simulation built in Java. This project demonstrates real-world application of core object-oriented programming principles including inheritance, abstraction, polymorphism, and exception handling. Designed for educational use and scalable architecture training.

 🚀 Features

- Supports multiple book types:
  - Paperbook: Stock-limited physical books with shipping
  - EBook: Digital books emailed to user (1 copy per transaction)
  - AudioBook: Streamable/downloadable audio format sent via email
  - ShowcaseBook: Not for sale, view-only item
- Dynamic inventory management using `HashMap`
- Validates purchase logic based on book type
- Simulated email and shipping services
- Removes outdated books based on year threshold

 🧱 Project Structure

QuantumBookStore/
├── Main.java                  # Entry point (formerly fawrytask2.java)
├── book.java                  # Abstract base class
├── Paperbook.java             # Concrete paper book class
├── Ebook.java                 # Concrete eBook class
├── AudioBook.java             # Concrete audiobook class
├── Showcasebook.java          # Not-for-sale, view-only book
├── Quantumbookstore.java      # Core inventory and sales logic
├── ShippingService.java       # Simulates shipping
└── MailService.java           # Simulates email sending

 🧪 How to Run

1. Ensure Java 8 or higher is installed.
2. Compile the main class:
   javac Main.java
3. Run the system:
   java Main

 📦 Sample Output

Quantum book store: Book added -> Java Mastery
Quantum book store: Total paid for PaperBook: $50.0

Quantum book store: EBooks can only be bought one at a time.

Quantum book store: SHOWCASE
Title: Rare Quantum Notes
Author: Einstein
Year: 1905
ISBN: ISBN004

🧠 Key Concepts Demonstrated

- Abstract classes & method overriding
- Polymorphism via dynamic dispatch
- Exception handling with validation
- Object-oriented encapsulation & inheritance
- Realistic simulations with static services

 🔧 Possible Enhancements

- Add persistence layer (JDBC or ORM)
- Build a GUI using JavaFX or Swing
- Introduce REST APIs for external integrations
- Write unit tests using JUnit
- Use Maven or Gradle for build automation

 👨‍💻 Author

Ahmed Esam Mohamed Lotfy  
📧 Email: ahmedesam307@gmail.com  
💼 LinkedIn: [linkedin.com/in/ahmed-lotfy ](https://www.linkedin.com/in/ahmed-esam-009983277?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app) 
🐙 GitHub: [github.com/ahmedlotfy](https://github.com/AhmedEsammohmaed)
