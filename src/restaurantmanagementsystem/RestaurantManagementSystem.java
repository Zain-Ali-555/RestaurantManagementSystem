import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Abstract class to define common properties of a menu item
abstract class MenuItem {
    protected String name;
    protected double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - $" + price;
    }
}

// Concrete class for Food items
class FoodItem extends MenuItem {
    public FoodItem(String name, double price) {
        super(name, price);
    }
}

// Concrete class for Drink items
class DrinkItem extends MenuItem {
    public DrinkItem(String name, double price) {
        super(name, price);
    }
}

// Class to manage the menu
class Menu {
    private List<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
        addDefaultItems();
    }

    private void addDefaultItems() {
        items.add(new FoodItem("Burger", 5.99));
        items.add(new FoodItem("Pizza", 8.99));
        items.add(new DrinkItem("Coke", 1.99));
        items.add(new DrinkItem("Water", 0.99));
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(String name) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    public void displayMenu() {
        for (MenuItem item : items) {
            System.out.println(item);
        }
    }

    public MenuItem getItemByName(String name) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}

// Class to manage orders
class Order {
    private List<MenuItem> orderedItems;

    public Order() {
        orderedItems = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        orderedItems.add(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : orderedItems) {
            total += item.getPrice();
        }
        return total;
    }

    public void displayOrder() {
        for (MenuItem item : orderedItems) {
            System.out.println(item);
        }
        System.out.println("Total: $" + calculateTotal());
    }
}

// Main class to interact with the user
public class RestaurantManagementSystem {
    private static Menu menu = new Menu();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addMenuItem();
                    break;
                case 2:
                    removeMenuItem();
                    break;
                case 3:
                    menu.displayMenu();
                    break;
                case 4:
                    takeOrder();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\nRestaurant Management System");
        System.out.println("1. Add Menu Item");
        System.out.println("2. Remove Menu Item");
        System.out.println("3. Display Menu");
        System.out.println("4. Take Order");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addMenuItem() {
        System.out.print("Enter item type (food/drink): ");
        String type = scanner.nextLine();
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter item price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        MenuItem item;
        if (type.equalsIgnoreCase("food")) {
            item = new FoodItem(name, price);
        } else if (type.equalsIgnoreCase("drink")) {
            item = new DrinkItem(name, price);
        } else {
            System.out.println("Invalid item type.");
            return;
        }
        menu.addItem(item);
        System.out.println("Item added successfully.");
    }

    private static void removeMenuItem() {
        System.out.print("Enter the name of the item to remove: ");
        String name = scanner.nextLine();
        menu.removeItem(name);
        System.out.println("Item removed successfully.");
    }

    private static void takeOrder() {
        Order order = new Order();
        boolean ordering = true;
        while (ordering) {
            System.out.println("\nMenu:");
            menu.displayMenu();
            System.out.print("Enter the name of the item to order (or 'done' to finish): ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("done")) {
                ordering = false;
            } else {
                MenuItem item = menu.getItemByName(name);
                if (item != null) {
                    order.addItem(item);
                    System.out.println("Item added to order.");
                } else {
                    System.out.println("Item not found.");
                }
            }
        }
        System.out.println("\nYour Order:");
        order.displayOrder();
    }
}
