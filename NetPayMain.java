import java.util.Scanner;
import java.util.Locale; //library used for variables to recognize commas + comma input error
import java.util.InputMismatchException;

public class NetPayMain {

    // error handling in order to prevent negatives and characters in fields only numbers should be in
    private static double readPositiveDouble(Scanner scanner, String prompt) {
    double value;
    while (true) {
            System.out.print(prompt);
            try {value = scanner.nextDouble();
            if (value < 0) {
            System.out.println("Cannot be negative. Please try again.");
            continue;

                }
            return value;
            } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            scanner.next();
            }
        }
    }
    private static int readNonNegativeInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
            value = scanner.nextInt();
            if (value < 0) {
            System.out.println("Cannot be negative. Please try again.");
            continue;
                }
            return value;
            } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a whole number.");
            scanner.next();
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        boolean RunFlag = true;

        while (RunFlag) {
            System.out.print("Enter employee name: ");
            String EmployeeName = scanner.nextLine();
//            System.out.print("Enter daily rate: ₱");
            double DailyRate = readPositiveDouble(scanner, "Enter daily rate: ₱");
            if (DailyRate > 0) {

                double DailyComp = DailyRate * 261;
                double MonthlyRate = DailyComp / 12;
                double HourlyRate = DailyRate / 8;

                double RegOTMultiplier = 1.25;
                double SpecOTMultiplier = 1.7;

                //regOT
                {
//                    System.out.print("Overtime (No. of overtime hours): ");
                    double RegOTHours = readPositiveDouble(scanner, "Overtime (No. of overtime hours): ");
//                    System.out.print("Holiday overtime (No. of holiday overtime hours): ");
                    double SpecOTHours = readPositiveDouble(scanner, "Holiday overtime (No. of holiday overtime hours): ");

                    double CompRegOTHours = HourlyRate * RegOTMultiplier; //regular working day overtime
                    double CompSpecOTHours = HourlyRate * SpecOTMultiplier; //holiday or rest day overtime

                    double FinRegOTHours = RegOTHours * CompRegOTHours;
                    double FinSpecOTHours = SpecOTHours * CompSpecOTHours;
                    double FinMonthlyRate = MonthlyRate + FinRegOTHours + FinSpecOTHours;


                    //bonus computation
                    double RegOTBonus = (CompRegOTHours * RegOTHours) - (HourlyRate * RegOTHours);
                    double SpecOTBonus = (CompSpecOTHours * SpecOTHours) - (HourlyRate * SpecOTHours);

                    double TotalBonus = RegOTBonus + SpecOTBonus;


                boolean RunFlag2 = true;

                while (RunFlag2) {
                    // Public sector = Y, uses this flag
                    System.out.print("Public Sector employee? (Y/N): ");
                    char PubSector = Character.toUpperCase(scanner.next().charAt(0));
                    if (PubSector == 'Y') {
                        double SSSTax = 0.05;
                        double GSISTax = 0.09; //needs to be optional
                        double deminimis = 0; //depends on the employer (hard code if needed)

                        double CompSSSTax = FinMonthlyRate * SSSTax;
                        double CompPhilTax = 500;
                        double CompPagibigTax = 200;
                        double CompGSISTax = FinMonthlyRate * GSISTax;

                        double TaxedMonthlyRate = FinMonthlyRate - CompPhilTax - CompPagibigTax - CompGSISTax - CompSSSTax - deminimis;
                        double IncomeTax;


                        if (TaxedMonthlyRate <= 20833) {
                            IncomeTax = 0;
                        }
                        else if (TaxedMonthlyRate <= 33332) {
                            IncomeTax = (TaxedMonthlyRate - 20833) * 0.15;
                        }
                        else if (TaxedMonthlyRate <= 66666) {
                            IncomeTax = 2500 + (TaxedMonthlyRate - 33333) * 0.20;
                        }
                        else if (TaxedMonthlyRate <= 166666) {
                            IncomeTax = 10833.33 + (TaxedMonthlyRate - 66667) * 0.25;
                        }
                        else if (TaxedMonthlyRate <= 666666) {
                            IncomeTax = 40833.33 + (TaxedMonthlyRate - 166667) * 0.30;
                        }
                        else {
                            IncomeTax = 200833.33 + (TaxedMonthlyRate - 666667) * 0.35;
                        }

//                        System.out.print("Absence deduction (no. of days absent): ");
                        int AbsentInput = readNonNegativeInt(scanner, "Absence deduction (no. of days absent): ");
                        double AbsenceDeduction = DailyRate * AbsentInput;
//                        System.out.print("Late deduction (no. of hours late): "); //follows the "no work, no pay" rule
                        int LateInput = readNonNegativeInt(scanner, "Late deduction (no. of hours late): ");
                        double LateDeduction = HourlyRate * LateInput;

                        double CompleteMonthlyRate = TaxedMonthlyRate - LateDeduction - AbsenceDeduction - IncomeTax;

                        System.out.print("\n\n");


                        //payslip + PUBLIC SECTOR = Y
                        System.out.println("TRIPLE T DEVELOPMENT COOPERATIVE");
                        System.out.println("\t\t\tGanton");
                        System.out.println("\nEmployee name: " + EmployeeName);
                        System.out.println("\n—————————————————————————");
                        System.out.printf("Gross Salary (monthly): ₱%,.2f%n", MonthlyRate);
                        System.out.printf("Total overtime bonus: ₱%,.2f%n", TotalBonus );
                        System.out.println("\n—————————————————————————");
                        System.out.printf("SSS (5%%): ₱%,.2f%n", CompSSSTax );
                        System.out.printf("PhilHealth (2.5%%): ₱%,.2f%n", CompPhilTax );
                        System.out.printf("Pag-Ibig (2%%): ₱%,.2f%n", CompPagibigTax );
                        System.out.printf("GSIS (9%%): ₱%.2f%n", CompGSISTax);
                        System.out.println("\n—————————————————————————");
                        System.out.printf("Income tax: ₱%,.2f%n", IncomeTax);
                        System.out.printf("Absence deduction (per day): ₱%,.2f%n", AbsenceDeduction );
                        System.out.printf("Late deduction (per hour): ₱%,.2f%n", LateDeduction );
                        System.out.println("\n—————————————————————————");
                        System.out.printf("Net Pay: ₱%,.2f%n", CompleteMonthlyRate );

                        return;
                    }
                    // else (if N) uses this flag
                    else if (PubSector == 'N') {
                        double SSSTax = 0.05;
                        double deminimis = 0; //depends on the employer (hard code if needed)

                        double CompSSSTax = FinMonthlyRate * SSSTax;
                        double CompPhilTax = 500;
                        double CompPagibigTax = 200;

                        double TaxedMonthlyRate = FinMonthlyRate - CompPhilTax - CompPagibigTax - CompSSSTax - deminimis;
                        double IncomeTax;


                        if (TaxedMonthlyRate <= 20833) {
                            IncomeTax = 0;
                        }
                        else if (TaxedMonthlyRate <= 33332) {
                            IncomeTax = (TaxedMonthlyRate - 20833) * 0.15;
                        }
                        else if (TaxedMonthlyRate <= 66666) {
                            IncomeTax = 2500 + (TaxedMonthlyRate - 33333) * 0.20;
                        }
                        else if (TaxedMonthlyRate <= 166666) {
                            IncomeTax = 10833.33 + (TaxedMonthlyRate - 66667) * 0.25;
                        }
                        else if (TaxedMonthlyRate <= 666666) {
                            IncomeTax = 40833.33 + (TaxedMonthlyRate - 166667) * 0.30;
                        }
                        else {
                            IncomeTax = 200833.33 + (TaxedMonthlyRate - 666667) * 0.35;
                        }

//                        System.out.print("Absence deduction (no. of days absent): ");
                        int AbsentInput = readNonNegativeInt(scanner, "Absence deduction (no. of days absent): ");
                        double AbsenceDeduction = DailyRate * AbsentInput;
//                        System.out.print("Late deduction (no. of hours late): "); //follows the "no work, no pay" rule
                        int LateInput = readNonNegativeInt(scanner, "Late deduction (no. of hours late): ");
                        double LateDeduction = HourlyRate * LateInput;

                        double CompleteMonthlyRate = TaxedMonthlyRate - LateDeduction - AbsenceDeduction - IncomeTax;

                        System.out.print("\n\n");

                        //payslip + PUBLIC SECTOR = N
                        System.out.println("TRIPLE T DEVELOPMENT COOPERATIVE");
                        System.out.println("\t\t\tGanton");
                        System.out.println("\nEmployee name: " + EmployeeName);
                        System.out.println("\n—————————————————————————");
                        System.out.printf("Gross Salary (monthly): ₱%,.2f%n", MonthlyRate);
                        System.out.printf("Total overtime bonus: ₱%,.2f%n", TotalBonus);
                        System.out.println("\n—————————————————————————");
                        System.out.printf("SSS (5%%): ₱%,.2f%n", CompSSSTax);
                        System.out.printf("PhilHealth (2.5%%): ₱%,.2f%n", CompPhilTax);
                        System.out.printf("Pag-Ibig (2%%): ₱%,.2f%n", CompPagibigTax);
                        System.out.println("\n—————————————————————————");
                        System.out.printf("Income tax: ₱%,.2f%n", IncomeTax);
                        System.out.printf("Absence deduction (per day): ₱%,.2f%n", AbsenceDeduction);
                        System.out.printf("Late deduction (per hour): ₱%,.2f%n", LateDeduction);
                        System.out.println("\n—————————————————————————");
                        System.out.printf("Net pay: ₱%,.2f%n", CompleteMonthlyRate);

                        return;

                    } else {

                        System.out.println("please enter Y or N");
                        System.out.print("\n\n");
                        RunFlag2 = true;
                    }
                    }
                }
                return;
            } else {
                System.out.println("Enter a valid daily pay amount.");
                System.out.print("\n\n");
                RunFlag = true;
            }
        }
    }
}
