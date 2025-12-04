package com.pluralsight;

public class Calculations{

    public static double getLoanPayment(double totalAmount, int loanLengthInMonths, double interestRate) {

        double monthlyInterestRate = interestRate / 12;

        double numerator = monthlyInterestRate * Math.pow( 1 + monthlyInterestRate, loanLengthInMonths);

        double denominator = (Math.pow(1 + monthlyInterestRate, loanLengthInMonths) - 1 );

        return totalAmount * (numerator / denominator);
    }
}