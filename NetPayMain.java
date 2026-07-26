import java.util.Scanner;
import java.util.Locale; //library used for variables to recognize commas + comma input error

public class NetPayMain {
    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        boolean RunFlag = true;

        while (RunFlag) {
            System.out.print("Enter daily daily rate: ₱");
            double DailyRate = scanner.nextDouble();
            if (DailyRate > 0) {

                double DailyComp = DailyRate * 261;
                double MonthlyRate = DailyComp / 12;
                double HourlyRate = DailyRate / 8;

                double RegOTMultiplier = 1.25;
                double SpecOTMultiplier = 1.7;

                boolean checkpoint1 = true;

                while (checkpoint1) {
                    System.out.print("Number of regular overtime hours: ");
                    double RegOTHours = scanner.nextDouble();
                    System.out.print("Number of holiday overtime hours (holidays): ");
                    double SpecOTHours = scanner.nextDouble();

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
                    System.out.print("Public Sector employee (Y/N) ?: ");
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

                        System.out.print("Absence deduction (No. of days absent): ");
                        int AbsentInput = scanner.nextInt();
                        double AbsenceDeduction = DailyRate * AbsentInput;
                        System.out.print("Late deduction (No. of hours late): "); //follows the "no work, no pay" rule
                        int LateInput = scanner.nextInt();
                        double LateDeduction = HourlyRate * LateInput;

                        double CompleteMonthlyRate = TaxedMonthlyRate - LateDeduction - AbsenceDeduction - IncomeTax;

                        System.out.print("\n\n");


                        //payslip
                        System.out.println("Gross Salary (Monthly): ₱" + MonthlyRate);
                        System.out.println("Total overtime bonus: ₱" + TotalBonus );
                        System.out.println("SSS tax (5%): ₱" + CompSSSTax );
                        System.out.println("Phil Health tax (2.5%): ₱" + CompPhilTax );
                        System.out.println("Pagibig tax (2%): ₱" + CompPagibigTax );
                        System.out.println("GSIS tax (9%): ₱" + CompGSISTax );
                        System.out.println("Income tax: ₱" + IncomeTax);
                        System.out.println("Late deduction (Per Hour): ₱"  + LateDeduction );
                        System.out.println("Absence deduction (Per Day): ₱" + AbsenceDeduction );
                        System.out.println("Net Pay: ₱" + CompleteMonthlyRate );

                        return;
                    }

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

                        System.out.print("Absence deduction (No. of days absent): ");
                        int AbsentInput = scanner.nextInt();
                        double AbsenceDeduction = DailyRate * AbsentInput;
                        System.out.print("Late deduction (No. of hours late): "); //follows the "no work, no pay" rule
                        int LateInput = scanner.nextInt();
                        double LateDeduction = HourlyRate * LateInput;

                        double CompleteMonthlyRate = TaxedMonthlyRate - LateDeduction - AbsenceDeduction - IncomeTax;

                        System.out.print("\n\n");


                        System.out.println("Gross Salary (Monthly): ₱" + MonthlyRate);
                        System.out.println("Total overtime bonus: ₱" + TotalBonus );
                        System.out.println("SSS tax (5%): ₱" + CompSSSTax );
                        System.out.println("Phil Health tax (2.5%): ₱" + CompPhilTax );
                        System.out.println("Pagibig tax (2%): ₱" + CompPagibigTax );
                        System.out.println("Income tax: ₱" + IncomeTax);
                        System.out.println("Late deduction (Per Hour): ₱"  + LateDeduction );
                        System.out.println("Absence deduction (Per Day): ₱" + AbsenceDeduction );
                        System.out.println("Net Pay: ₱" + CompleteMonthlyRate );

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
