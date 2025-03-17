import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.Scanner;

public class Amotorph {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String csvFile = "C:\\Users\\yuki\\Desktop\\Employee.csv"; // Path ng CSV File 

        // Search Employee ID
        System.out.print("Enter Employee ID: ");
        String empId = sc.nextLine();

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String[] row;
            boolean found = false;

            // Skip header row
            reader.readNext();

            // Search Employee ID sa CSV
            while ((row = reader.readNext()) != null) {
                if (row[0].equals(empId)) { // Match EmployeeID
                    found = true;

                    // Analyze Employee Data - Row placement ng mga nasa excel
                    String fullName = row[1] + " " + row[2]; // Employee Name + Position
                    double hourlyRate = Double.parseDouble(row[8]);
                    double riceSubsidy = Double.parseDouble(row[4]);
                    double phoneAllowance = Double.parseDouble(row[5]);
                    double clothingAllowance = Double.parseDouble(row[6]);
                    double semiMonthlyAllowance = (riceSubsidy + phoneAllowance + clothingAllowance) / 2;

                    System.out.println("Employee Found!");
                    System.out.printf("Name: %s%n", fullName);
                    System.out.printf("Hourly Rate: PHP %.2f%n%n", hourlyRate);

                    // Input Worked Hours
                    System.out.print("Enter Total Worked Hours: ");
                    double totalHours = sc.nextDouble();
                    System.out.print("Enter Overtime Hours: ");
                    double overtimeHours = sc.nextDouble();

                    // Payroll Calculations
                    double regularPay = totalHours * hourlyRate;
                    double overtimePay = overtimeHours * hourlyRate * 1.5; // OT
                    double grossPay = regularPay + overtimePay + semiMonthlyAllowance;

                    // Deductions sa Pelepens
                    double sss = grossPay * 0.05; // Employee SSS contribution
                    double pagIbig = Math.min(grossPay * 0.02, 100.00); // Pag-IBIG: Max 100.00

                    double monthlySalary = Math.max(Math.min(grossPay, 100000.00), 10000.00);
                    double philHealthTotal = monthlySalary * 0.05;
                    double philHealth = philHealthTotal / 2; // Employee PhilHealth

                    double taxableIncome = grossPay - (sss + pagIbig + philHealth);
                    double withholdingTax = 0.0;

                    if (taxableIncome > 8000000) {
                        withholdingTax = 2410000 + (taxableIncome - 8000000) * 0.35;
                    } else if (taxableIncome > 2000000) {
                        withholdingTax = 490000 + (taxableIncome - 2000000) * 0.30;
                    } else if (taxableIncome > 800000) {
                        withholdingTax = 130000 + (taxableIncome - 800000) * 0.25;
                    } else if (taxableIncome > 400000) {
                        withholdingTax = 30000 + (taxableIncome - 400000) * 0.20;
                    } else if (taxableIncome > 250000) {
                        withholdingTax = (taxableIncome - 250000) * 0.15;
                    }

                    double totalDeductions = sss + pagIbig + philHealth + withholdingTax;
                    double netPay = grossPay - totalDeductions;

                    // Print out
                    System.out.println("-------------------------------------------");
                    System.out.println("|       MOTORPH PAYROLL DETAILS           |");
                    System.out.println("-------------------------------------------");
                    System.out.printf("| Name: %s%n", fullName);
                    System.out.printf("| Hourly Rate: PHP %.2f%n", hourlyRate);
                    System.out.printf("| Total Worked Hours: %.2f hours%n", totalHours);
                    System.out.printf("| Overtime Hours: %.2f hours%n", overtimeHours);
                    System.out.println("-------------------------------------------");
                    System.out.println("| BENEFITS                                 |");
                    System.out.printf("| - Rice Subsidy: PHP %.2f%n", riceSubsidy / 2);
                    System.out.printf("| - Phone Allowance: PHP %.2f%n", phoneAllowance / 2);
                    System.out.printf("| - Clothing Allowance: PHP %.2f%n", clothingAllowance / 2);
                    System.out.printf("| GROSS PAY: PHP %.2f%n", grossPay);
                    System.out.println("-------------------------------------------");
                    System.out.println("| DEDUCTIONS                              |");
                    System.out.printf("| - SSS: PHP %.2f%n", sss);
                    System.out.printf("| - PhilHealth: PHP %.2f%n", philHealth);
                    System.out.printf("| - Pag-IBIG: PHP %.2f%n", pagIbig);
                    System.out.printf("| - Withholding Tax: PHP %.2f%n", withholdingTax);
                    System.out.printf("| TOTAL DEDUCTIONS: PHP %.2f%n", totalDeductions);
                    System.out.println("-------------------------------------------");
                    System.out.printf("| TAKE HOME PAY: PHP %.2f%n", netPay);
                    System.out.println("-------------------------------------------");
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee ID not found.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Error " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}