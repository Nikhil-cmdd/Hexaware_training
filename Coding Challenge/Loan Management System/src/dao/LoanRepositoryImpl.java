package dao;

import java.sql.*;
import java.util.*;
import entity.*;
import exception.InvalidLoanException;
import util.DBConnUtil;

public class LoanRepositoryImpl implements ILoanRepository {

    private Connection conn;

    public LoanRepositoryImpl() {
        this.conn = DBConnUtil.getDBConn();
    }

    private void insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (customerId, name, email, phone, address, creditScore) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customer.getCustomerId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());
            ps.setInt(6, customer.getCreditScore());
            ps.executeUpdate();
        }
    }

    @Override
    public boolean applyLoan(Loan loan) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Do you want to proceed with the loan application? (Yes/No): ");
        String confirmation = sc.nextLine();

        if (!confirmation.equalsIgnoreCase("Yes")) {
            System.out.println("Loan application cancelled.");
            return false;
        }

        try {
            // Step 1: Insert Customer
            insertCustomer(loan.getCustomer());

            // Step 2: Insert Loan
            String sql = "INSERT INTO loan (loanId, customerId, principalAmount, interestRate, loanTerm, loanType, loanStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, loan.getLoanId());
                ps.setInt(2, loan.getCustomer().getCustomerId());
                ps.setDouble(3, loan.getPrincipalAmount());
                ps.setDouble(4, loan.getInterestRate());
                ps.setInt(5, loan.getLoanTerm());
                ps.setString(6, loan.getLoanType());
                ps.setString(7, "Pending");
                ps.executeUpdate();
            }

            // Step 3: Insert into specific subclass table
            if (loan instanceof CarLoan carLoan) {
                String carSql = "INSERT INTO CarLoan (loanId, carModel, carValue) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(carSql)) {
                    ps.setInt(1, carLoan.getLoanId());
                    ps.setString(2, carLoan.getCarModel());
                    ps.setInt(3, carLoan.getCarValue());
                    ps.executeUpdate();
                }
            } else if (loan instanceof HomeLoan homeLoan) {
                String homeSql = "INSERT INTO HomeLoan (loanId, propertyAddress, propertyValue) VALUES (?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(homeSql)) {
                    ps.setInt(1, homeLoan.getLoanId());
                    ps.setString(2, homeLoan.getPropertyAddress());
                    ps.setInt(3, homeLoan.getPropertyValue());
                    ps.executeUpdate();
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public double calculateInterest(int loanId) throws InvalidLoanException {
        Loan loan = getLoanById(loanId);
        double interest = (loan.getPrincipalAmount() * loan.getInterestRate() * loan.getLoanTerm()) / 12;
        return interest;
    }

    @Override
    public double calculateInterest(double principal, double rate, int tenure) {
        return (principal * rate * tenure) / 12;
    }

    @Override
    public boolean loanStatus(int loanId) throws InvalidLoanException {
        Loan loan = getLoanById(loanId);
        int score = loan.getCustomer().getCreditScore();
        String status = score >= 650 ? "Approved" : "Rejected";

        String sql = "UPDATE loan SET loanStatus = ? WHERE loanId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, loanId);
            int rows = ps.executeUpdate();
            System.out.println("Loan Status: " + status);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public double calculateEMI(int loanId) throws InvalidLoanException {
        Loan loan = getLoanById(loanId);
        double P = loan.getPrincipalAmount();
        double R = loan.getInterestRate() / 12 / 100;
        int N = loan.getLoanTerm();

        double emi = (P * R * Math.pow(1 + R, N)) / (Math.pow(1 + R, N) - 1);
        return emi;
    }

    @Override
    public double calculateEMI(double principal, double rate, int tenure) {
        double R = rate / 12 / 100;
        double emi = (principal * R * Math.pow(1 + R, tenure)) / (Math.pow(1 + R, tenure) - 1);
        return emi;
    }

    @Override
    public boolean loanRepayment(int loanId, double amount) throws InvalidLoanException {
        double emi = calculateEMI(loanId);
        if (amount < emi) {
            System.out.println("Amount less than one EMI. Payment rejected.");
            return false;
        }

        int noOfEMI = (int)(amount / emi);
        System.out.println("Number of EMIs paid with amount: " + noOfEMI);

        // This is a simulation. You can update remaining balance in DB if needed.

        return true;
    }

    @Override
    public List<Loan> getAllLoan() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM Loan";

        try (Connection conn = DBConnUtil.getDBConn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Loan loan = new Loan();
                loan.setLoanId(rs.getInt("loanId"));
                loan.setPrincipalAmount(rs.getDouble("principalAmount"));
                loan.setInterestRate(rs.getDouble("interestRate"));
                loan.setLoanTerm(rs.getInt("loanTerm"));
                loan.setLoanType(rs.getString("loanType"));
                loan.setLoanStatus(rs.getString("loanStatus"));

                loans.add(loan);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching all loans: " + e.getMessage());
        }

        return loans;
    }

    @Override
    public Loan getLoanById(int loanId) throws InvalidLoanException {
        String sql = "SELECT * FROM loan l JOIN customer c ON l.customerId = c.customerId WHERE l.loanId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer(
                        rs.getInt("customerId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("creditScore")
                    );

                    return new Loan(
                        rs.getInt("loanId"),
                        c,
                        rs.getDouble("principalAmount"),
                        rs.getDouble("interestRate"),
                        rs.getInt("loanTerm"),
                        rs.getString("loanType"),
                        rs.getString("loanStatus")
                    );
                } else {
                    throw new InvalidLoanException("Loan with ID " + loanId + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new InvalidLoanException("Error accessing database.");
        }
    }
}
