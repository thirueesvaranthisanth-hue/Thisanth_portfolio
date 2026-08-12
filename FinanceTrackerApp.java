import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// ==========================================
// ENUMS & MODELS
// ==========================================
enum TransactionType {
    INCOME, EXPENSE
}

class Transaction {
    private final String id;
    private final double amount;
    private final String category;
    private final TransactionType type;
    private final LocalDate date;

    public Transaction(String id, double amount, String category, TransactionType type, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
    }

    public String getId() { return id; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public TransactionType getType() { return type; }
    public LocalDate getDate() { return date; }

    public String toCsv() {
        return String.join(",", id, String.valueOf(amount), category, type.name(), date.toString());
    }

    public static Transaction fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        return new Transaction(
            parts[0],
            Double.parseDouble(parts[1]),
            parts[2],
            TransactionType.valueOf(parts[3]),
            LocalDate.parse(parts[4])
        );
    }

    @Override
    public String toString() {
        return String.format("[%s] ID: %s | %-7s | $%-8.2f | Category: %-12s | Date: %s",
                type == TransactionType.INCOME ? "+" : "-", id, type, amount, category, date);
    }
}

// ==========================================
// CORE BUSINESS LOGIC
// ==========================================
class FinanceManager {
    private final List<Transaction> transactions = new ArrayList<>();
    private final Map<String, Double> categoryBudgets = new HashMap<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
        checkBudgetAlert(t.getCategory());
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void setBudget(String category, double limit) {
        categoryBudgets.put(category.toLowerCase(), limit);
    }

    public double getTotalIncome() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpense() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getNetBalance() {
        return getTotalIncome() - getTotalExpense();
    }

    public Map<String, Double> getExpensesByCategory() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().toLowerCase(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));
    }

    private void checkBudgetAlert(String category) {
        String catKey = category.toLowerCase();
        if (categoryBudgets.containsKey(catKey)) {
            double totalSpent = getExpensesByCategory().getOrDefault(catKey, 0.0);
            double limit = categoryBudgets.get(catKey);
            if (totalSpent > limit) {
                System.out.printf("%n⚠️  BUDGET ALERT: You have exceeded your budget for '%s'! (Spent: $%.2f / Limit: $%.2f)%n",
                        category, totalSpent, limit);
            }
        }
    }

    public void loadFromCsv(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    transactions.add(Transaction.fromCsv(line));
                }
            }
            System.out.println("Loaded " + transactions.size() + " saved transactions.");
        } catch (IOException e) {
            System.out.println("Error reading data file: " + e.getMessage());
        }
    }

    public void saveToCsv(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Transaction t : transactions) {
                writer.write(t.toCsv());
                writer.newLine();
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data file: " + e.getMessage());
        }
    }
}

// ==========================================
// CLI USER INTERFACE
// ==========================================
public class FinanceTrackerApp {
    private static final String FILE_PATH = "transactions.csv";
    private static final Scanner scanner = new Scanner(System.in);
    private static final FinanceManager manager = new FinanceManager();

    public static void main(String[] args) {
        manager.loadFromCsv(FILE_PATH);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addTransactionUI(TransactionType.INCOME);
                case "2" -> addTransactionUI(TransactionType.EXPENSE);
                case "3" -> viewTransactionsUI();
                case "4" -> viewSummaryUI();
                case "5" -> setBudgetUI();
                case "6" -> {
                    manager.saveToCsv(FILE_PATH);
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n==========================================");
        System.out.println("      PERSONAL FINANCE & EXPENSE TRACKER   ");
        System.out.println("==========================================");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View All Transactions");
        System.out.println("4. View Financial Summary & Breakdown");
        System.out.println("5. Set Category Budget");
        System.out.println("6. Save & Exit");
        System.out.print("Choose an option (1-6): ");
    }

    private static void addTransactionUI(TransactionType type) {
        System.out.print("Enter amount: $");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter category (e.g., Salary, Food, Rent): ");
        String category = scanner.nextLine().trim();

        String id = "TXN" + (manager.getTransactions().size() + 1);
        Transaction transaction = new Transaction(id, amount, category, type, LocalDate.now());
        
        manager.addTransaction(transaction);
        System.out.println("Transaction recorded successfully!");
    }

    private static void viewTransactionsUI() {
        List<Transaction> list = manager.getTransactions();
        if (list.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }
        System.out.println("\n--- TRANSACTION HISTORY ---");
        list.forEach(System.out::println);
    }

    private static void viewSummaryUI() {
        System.out.println("\n--- FINANCIAL SUMMARY ---");
        System.out.printf("Total Income:  $%.2f%n", manager.getTotalIncome());
        System.out.printf("Total Expense: $%.2f%n", manager.getTotalExpense());
        System.out.printf("Net Balance:   $%.2f%n", manager.getNetBalance());

        System.out.println("\n--- EXPENSES BY CATEGORY ---");
        Map<String, Double> breakdown = manager.getExpensesByCategory();
        if (breakdown.isEmpty()) {
            System.out.println("No expenses logged yet.");
        } else {
            breakdown.forEach((cat, total) -> 
                System.out.printf("- %-15s: $%.2f%n", cat.toUpperCase(), total)
            );
        }
    }

    private static void setBudgetUI() {
        System.out.print("Enter category name: ");
        String category = scanner.nextLine().trim();

        System.out.print("Enter monthly budget limit: $");
        double limit = Double.parseDouble(scanner.nextLine());

        manager.setBudget(category, limit);
        System.out.printf("Budget limit of $%.2f set for category '%s'.%n", limit, category);
    }
}