import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
static class booking{
    String pn,dn,date,time;
    booking(String a,String b,String c,String d){
        pn=a;
        dn=b;
        date=c;
        time=d;
    }
    void display(){
        System.out.println("Patient name: "+pn);
        System.out.println("Doctor Name: "+dn);
        System.out.println("Date: "+date);
        System.out.println("Time: "+time);
    }
}
public static class clinic {
   static  ArrayList<String>dl=new ArrayList<>();
    static Scanner sc=new Scanner(System.in);
    static ArrayList<booking> pb=new ArrayList<>();
    private void patientmenu(){
        System.out.println("Welcome !");
        System.out.println("1.Booking a Appointment");
        System.out.println("2.View All your Appointments");
        System.out.print("enter your choice: ");
        int nc= sc.nextInt();
        sc.nextLine();
        switch (nc){
            case 1:
                System.out.print("Enter your name:");
                String a=sc.nextLine();
                System.out.print("Enter the date (e.g., 2025-04-20): ");
                String b= sc.nextLine();
                System.out.println("Enter time (e.g., 10:30 AM):");
                String t=sc.nextLine();
                System.out.print("Enter the doctor name from whom appoitment should be booked: ");
                String c=sc.nextLine();
                booking p=new booking(a,c,b,t);
                pb.add(p);
                break;
            case 2:
                System.out.print("Enter your name:");
                String a1=sc.nextLine();
                System.out.println("all your Appointments");
                for(booking an:pb){
                    if(an.pn.equalsIgnoreCase(a1)){
                        an.display();
                    }
                }
                break;
            default:
                System.out.println("Enter a valid choice !");
                break;
        }

    }
    private void doctormenu(){
        System.out.print("Enter your name: ");
        String n=sc.nextLine();
        boolean a_b=false;
        for(String docn:dl){
         if(docn.equalsIgnoreCase(n)){
             a_b=true;
         }
        }
        if(a_b){
        while(true){
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View All Appointments");
            System.out.println("2. Search for Patient");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAppointments for Dr. " + n+ ":");
                    for (booking b : pb) {
                        if (b.dn.equalsIgnoreCase(n)) {
                            b.display();
                            System.out.println("---------------------");
                        }
                    }
                    break;

                case 2:
                    System.out.print("Enter patient name to search: ");
                    String pname = sc.nextLine();
                    boolean found = false;
                    for (booking b : pb) {
                        if (b.dn.equalsIgnoreCase(n)&& b.pn.equalsIgnoreCase(pname)) {
                            b.display();
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("No appointment found for " + pname);
                    }
                    break;
                case 3:
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        }else{
            System.out.println("Acces denied !");
    }
    }
        public static void adminmenu(){
            System.out.print("Enter your Name : ");
            String na= sc.nextLine();
            System.out.println("Enter password: ");
            String p= sc.nextLine();
            if((na.equals("abc")&&p.equals("1234")||(na.equals("xyz")&&p.equals("45678")))){
                System.out.println("Welcome "+na);
                System.out.println("1.View All Appointments");
                System.out.println("2.View All Doctor");
                System.out.println("3.Delete Appointments");
                System.out.println("4.Add Doctor");
                System.out.print("Enter your choice number:");
                int n=sc.nextInt();
                switch(n){
                    case 1:
                        System.out.println("All Appointments");
                        for(booking b:pb){
                            b.display();
                        }
                        break;
                    case 2:
                        System.out.println("All the Doctors");
                        if(dl.isEmpty()){
                            System.out.println("No Doctor Available");
                        }else{
                            for(String d:dl) {
                                System.out.println(d);
                            }
                        }
                        break;
                    case 3:
                        sc.nextLine();
                        System.out.print("Enter name of patient: ");
                        String p_n= sc.nextLine();
                        System.out.print("Enter name of doctor: ");
                        String d_n1= sc.nextLine();
                        Iterator<booking> itr = pb.iterator();
                        while (itr.hasNext()) {
                            booking a = itr.next();
                            if (a.pn.equalsIgnoreCase(p_n) && a.dn.equalsIgnoreCase(d_n1)) {
                                itr.remove();  // safely remove the booking
                                System.out.println("Appointment removed successfully.");
                                break;
                            }
                        }
                        break;

                    case 4:
                        System.out.print("Enter the number of doctor to be added in list:");
                        int dn1= sc.nextInt();
                        sc.nextLine();
                        for(int i=0;i<dn1;i++){
                            System.out.print("Enter the doctor name: ");
                            String dn2= sc.nextLine();
                            dl.add(dn2);
                            System.out.println("Doctor added succesfully !");
                        }
                        break;

                }
            }else{
                System.out.println("Acces denied !");
            }
        }
    }
    public static void main(String[] args) {
      /*the code should ask the user for it's type of user if patient show a different gui
*/
        Scanner sc=new Scanner(System.in);
        clinic bo=new clinic();
while(true) {
    System.out.print("Welcome to Sushrut Clinic! please choose the following options :\n 1.Patient \n 2.Doctor \n 3.Admin \n Enter your choice(ex-Patient):");
    String c = sc.nextLine();
    switch (c) {
        case "Patient":
            bo.patientmenu();
            break;
        case "Doctor":
            bo.doctormenu();
            break;
        case "Admin":
            bo.adminmenu();

        default:
            System.out.println("Enter valid choice !");
            break;
    }
}
}

