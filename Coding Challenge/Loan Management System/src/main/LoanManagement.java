package main;

import dao.LoanRepositoryImpl;
import entity.*;
import exception.InvalidLoanException;

import java.util.List;
import java.util.Scanner;

public class LoanManagement {

    static Scanner sc = new Scanner(System.in);
    static LoanRepositoryImpl loanRepo = new LoanRepositoryImpl();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Loan Management System ===");
            System.out.println("1. Apply for Loan");
            System.out.println("2. Get All Loans");
            System.out.println("3. Get Loan By ID");
            System.out.println("4. Calculate Interest");
            System.out.println("5. Calculate EMI");
            System.out.println("6. Update Loan Status");
            System.out.println("7. Repay Loan");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
    
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    applyLoan();
                    break;
                case 2:
                    getAllLoans();
                    break;
                case 3:
                    getLoanById();
                    break;
                case 4:
                    calculateInterest();
                    break;
                case 5:
                    calculateEMI();
                    break;
                case 6:
                    updateStatus();
                    break;
                case 7:
                    repayLoan();
                    break;
                case 8:
                    System.out.println("Thank you for using the Loan Management System.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void applyLoan() {
        sc.nextLine(); // Consume leftover newline
        System.out.print("Enter Loan Type (HomeLoan/CarLoan): ");
        String type = sc.nextLine();

        Customer customer = getCustomerInput();
        System.out.print("Enter Loan ID: ");
        int loanId = sc.nextInt();
        System.out.print("Enter Principal Amount: ");
        double principal = sc.nextDouble();
        System.out.print("Enter Interest Rate (%): ");
        double rate = sc.nextDouble();
        System.out.print("Enter Loan Term (months): ");
        int term = sc.nextInt();
        sc.nextLine(); // Consume newline

        Loan loan = switch (type.toLowerCase()) {
            case "homeloan" -> {
                System.out.print("Enter Property Address: ");
                String propAddr = sc.nextLine();
                System.out.print("Enter Property Value: ");
                int propValue = sc.nextInt();
                yield new HomeLoan(loanId, customer, principal, rate, term, "Pending", propAddr, propValue);
            }
            case "carloan" -> {
                System.out.print("Enter Car Model: ");
                String carModel = sc.nextLine();
                System.out.print("Enter Car Value: ");
                int carValue = sc.nextInt();
                yield new CarLoan(loanId, customer, principal, rate, term, "Pending", carModel, carValue);
            }
            default -> {
                System.out.println("Invalid Loan Type.");
                yield null;
            }
        };

        if (loan != null) loanRepo.applyLoan(loan);
    }

    private static Customer getCustomerInput() {
        System.out.print("Enter Customer ID: ");
        int custId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.nextLine();
        System.out.print("Enter Credit Score: ");
        int score = sc.nextInt();
        return new Customer(custId, name, email, phone, address, score);
    }

    private static void getAllLoans() {
        List<Loan> allLoans = loanRepo.getAllLoan();
        if (allLoans.isEmpty()) System.out.println("No loans found.");
        else allLoans.forEach(System.out::println);
    }

    private static void getLoanById() {
        System.out.print("Enter Loan ID: ");
        try {
            System.out.println(loanRepo.getLoanById(sc.nextInt()));
        } catch (InvalidLoanException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void calculateInterest() {
        System.out.print("Enter Loan ID: ");
        try {
            System.out.println("Calculated Interest: " + loanRepo.calculateInterest(sc.nextInt()));
        } catch (InvalidLoanException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void calculateEMI() {
        System.out.print("Enter Loan ID: ");
        try {
            System.out.printf("EMI Amount: %.2f\n", loanRepo.calculateEMI(sc.nextInt()));
        } catch (InvalidLoanException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateStatus() {
        System.out.print("Enter Loan ID to check status: ");
        try {
            loanRepo.loanStatus(sc.nextInt());
        } catch (InvalidLoanException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void repayLoan() {
        System.out.print("Enter Loan ID: ");
        int id = sc.nextInt();
        System.out.print("Enter repayment amount: ");
        double amt = sc.nextDouble();
        try {
            loanRepo.loanRepayment(id, amt);
        } catch (InvalidLoanException e) {
            System.out.println(e.getMessage());
        }
    }
}
