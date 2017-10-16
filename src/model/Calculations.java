package model;

import java.text.NumberFormat;
import gui.InterestTableGUI;

public class Calculations extends InterestTableGUI {
	
	public static String getCompound(double principal, double rate, double yearValue) {
		String compData = "";
		double yearValueIterator = 1;
		double exponentCalculator;
		
		compData = "Principal: " + 
				NumberFormat.getCurrencyInstance().format(getPrincipalValue()) +
				", Rate: " + getRateValue() + "\n" +
					"Year, Compound Interest Amount";
		
		//Compound Interest Amount = principal * (1 + rate/100)^Years 
		//same for loop that will print the values of the interest
		for (int index = 1; index <= yearValue; index++) {
			exponentCalculator = Math.pow(1 + rate/100, yearValueIterator);
			compData += "\n" + index + " --> " + 
					NumberFormat.getCurrencyInstance().format(getPrincipalValue()
							* (exponentCalculator)); 
			yearValueIterator++;
		}
		
		return compData;
	}
	
	//this method will return the string value of both of the interests
		public static String getBoth(double principal, double rate, double yearsValue) {
			String bothData;
			//iterator int needs to be called to pass through the amount of years 
			double yearIteratorValue = 1;
			double exponentCalcBoth;
			
			//the top portion of the display screen
			bothData = "Principal: " + 
					NumberFormat.getCurrencyInstance().format(getPrincipalValue()) +
					", Rate: " + getRateValue() + "\n" +
						"Year, Simple Interest Amount, Compound Interest Amount";
			
			//loop to print out the values depending on the year
			for (int index = 1; index <= getYearValue(); index++) {
				exponentCalcBoth = Math.pow(1 + rate/100, yearIteratorValue);
				bothData += "\n" + index + " --> " + 
							NumberFormat.getCurrencyInstance().format(getPrincipalValue() + 
								(getPrincipalValue() * (getRateValue()/100) * yearIteratorValue)) + 
								" --> " + NumberFormat.getCurrencyInstance().format(getPrincipalValue() 
										* (exponentCalcBoth));
				yearIteratorValue++;
			
			}
			
			return bothData;
		}

	
	

}
