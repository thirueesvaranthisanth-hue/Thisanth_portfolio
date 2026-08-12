import java.util.Scanner;
class Customer {
    String name;
    String contact;
    String checkInDate;
    String checkOutDate;

    Customer(String name, String contact, String checkIn, String checkOut) {
        this.name = name;
        this.contact = contact;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public void displayCustomerDetails() {
        System.out.println("Customer Name  : " + name);
        System.out.println("Contact Number : " + contact);
        System.out.println("Check-in Date  : " + checkInDate);
        System.out.println("Check-out Date : " + checkOutDate);
    }
}
class Room  {
    String roomType;
    int roomRate;
    int daysStayed;

	Room(String roomType,int daysStayed)
	{
	   this.roomType=roomType;
	   //this.roomRate=roomRate;
	   this.daysStayed=daysStayed;
	}
   
	
	public void roomdetails()
	{
		if(roomType.equals("single"))
		{
			return daysStayed*3000;
		}
		else if(roomType.equals("double"))
		{
			return 5000*daysStayed;
		}
		else if(roomType.equals("delux"))
		{
			return 8000*daysStayed;
		}
		else
		{
			return 500*daysStayed;
		}
	}

    public void displayRoomDetails() {
        System.out.println("Room Type      : " + roomType);
        System.out.println("Days Stayed    : " + daysStayed);
        System.out.println("Room Charges   : Rs. " + roomdetails());
    }
}
class Services  {
	int foodCharge=foodCharge;
    int laundryCharge;
    int serviceCharge;

    Services(int foodCharge, int laundryCharge, int serviceCharge) {
        this.foodCharge = foodCharge;
        this.laundryCharge = laundryCharge;
        this.serviceCharge = serviceCharge;
    }

    public int getTotalServiceCharge() {
        return foodCharge + laundryCharge + serviceCharge;
    }

    public void displayServices() {
        System.out.println("-- Extra Services --");
        System.out.println("Food           : Rs. " + foodCharge);
        System.out.println("Laundry        : Rs. " + laundryCharge);
        System.out.println("Room Service   : Rs. " + serviceCharge);
        System.out.println("Service Total  : Rs. " + getTotalServiceCharge());
    }
}
 class Bill {
    int roomCharge;
    int serviceCharge;
    int discount;
    double taxRate = 0.10;

    Bill(int roomCharge, int serviceCharge, int discount) {
        this.roomCharge = roomCharge;
        this.serviceCharge = serviceCharge;
        this.discount = discount;
    }

    public void printFinalBill() {
        int subTotal = roomCharge + serviceCharge;
        double tax = subTotal * taxRate;
        double total = subTotal + tax - discount;

        System.out.println("\n------- FINAL BILL -------");
        System.out.println("Subtotal        : Rs. " + subTotal);
        System.out.println("Tax (10%)       : Rs. " + tax);
        System.out.println("Discount        : Rs. " + discount);
        System.out.println("-----------------------------");
        System.out.println("TOTAL BILL      : Rs. " + total);
        System.out.println("=============================");
        System.out.println("Thank you for staying with us!");
    }
}
public class HotelBillingSystemproject {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

     
        System.out.println("======= HOTEL BILLING SYSTEM =======");

        // Customer Details
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Contact Number: ");
        String contact = sc.nextLine();

        System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
        String checkIn = sc.nextLine();

        System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
        String checkOut = sc.nextLine();
		
		Customer customer1 = new Customer(name,contact,checkIn,checkOut);
		customer1.displayCustomerDetails();

        //customer.setCustomerDetails(name, contact, checkIn, checkOut);

        // Room Details
        System.out.println("\nSelect Room Type:");
        System.out.println("1. Single - Rs. 3000/day");
        System.out.println("2. Double - Rs. 5000/day");
        System.out.println("3. Deluxe - Rs. 8000/day");
        System.out.println("4. Suite  - Rs. 12000/day");
        System.out.print("Enter option (1-4): ");
        String roomType = sc.nextLine();

        System.out.print("Enter Number of Days Stayed: ");
        int days = sc.nextInt();

        //room.selectRoom(option, days);
		Room room = new Room();
		room.displayRoomDetails();
		room.roomdetails();

        // Services
        System.out.println("\nEnter Service Charges:");
        System.out.print("Food Charges: Rs. ");
        int foodCharge = sc.nextInt();
        System.out.print("Laundry Charges: Rs. ");
        int laundryCharge = sc.nextInt();
        System.out.print("Room Service Charges: Rs. ");
        int serviceCharge = sc.nextInt();

        //services.setServices(food, laundry, service);
		Services services = new Services(foodCharge,laundryCharge,serviceCharge);
		services.getTotalServiceCharge();
		services.displayServices();
		

        // Discount
        System.out.print("\nEnter Discount Amount: Rs. ");
        int discount = sc.nextInt();

        // Bill Generation
        int roomCharge = room.getRoomCharge();
        int serviceCharge = services.getTotalServiceCharge();

        //bill.generateBill(roomCharge, serviceCharge, discount);
		Bill bill = new Bill();
		bill.printFinalBill();
		

        // Output
        /*System.out.println("\n======= BILL SUMMARY =======");
        customer.displayCustomerDetails();
        room.displayRoomDetails();
        services.displayServices();
        bill.printFinalBill();*/

        
    }
}
