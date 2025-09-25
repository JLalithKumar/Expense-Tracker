import java.io.*;
import java.util.*;

// Expense class
class Expense implements Serializable {
    int id;
    String category;
    double amount;
    String date; // format: YYYY-MM-DD

    public Expense(int id, String category, double amount, String date) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String toString() {
        return "ID: " + id + ", Category: " + category + ", Amount: ₹" + amount + ", Date: " + date;
    }
}

// Main class
public class ExpenseTracker {
    static Scanner sc = new Scanner(System.in);
    static List<Expense> expenses = new ArrayList<>();
    static final String FILE_NAME = "expenses.dat";

    public static void main(String[] args) {
        loadData();
        int choice;
        do {
            System.out.println("\n--- Personal Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. View Total & Category-wise Summary");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> updateExpense();
                case 4 -> deleteExpense();
                case 5 -> summary();
                case 0 -> saveData();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    static void addExpense() {
        System.out.print("Enter Expense ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Category (Food/Travel/Bills/etc.): ");
        String category = sc.nextLine();
        System.out.print("Enter Amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        expenses.add(new Expense(id, category, amount, date));
        System.out.println("Expense added successfully!");
    }

    static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("\n--- Expense List ---");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    static void updateExpense() {
        System.out.print("Enter Expense ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Expense e : expenses) {
            if (e.id == id) {
                System.out.print("Enter new Category: ");
                e.category = sc.nextLine();
                System.out.print("Enter new Amount: ₹");
                e.amount = sc.nextDouble();
                sc.nextLine();
                System.out.print("Enter new Date (YYYY-MM-DD): ");
                e.date = sc.nextLine();
                System.out.println("Expense updated!");
                return;
            }
        }
        System.out.println("Expense not found!");
    }

    static void deleteExpense() {
        System.out.print("Enter Expense ID to delete: ");
        int id = sc.nextInt();
        boolean removed = expenses.removeIf(e -> e.id == id);
        if (removed) System.out.println("Expense deleted!");
        else System.out.println("Expense not found!");
    }

    static void summary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        double total = 0;
        Map<String, Double> categoryMap = new HashMap<>();
        for (Expense e : expenses) {
            total += e.amount;
            categoryMap.put(e.category, categoryMap.getOrDefault(e.category, 0.0) + e.amount);
        }
        System.out.println("\n--- Expense Summary ---");
        System.out.println("Total Expenses: ₹" + total);
        System.out.println("Category-wise breakdown:");
        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            System.out.println(entry.getKey() + ": ₹" + entry.getValue());
        }
    }

    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(expenses);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            expenses = (ArrayList<Expense>) ois.readObject();
        } catch (Exception e) {
            expenses = new ArrayList<>();
        }
    }
}