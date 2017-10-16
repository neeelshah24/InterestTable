package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import java.text.NumberFormat;
import model.Calculations;

@SuppressWarnings("restriction")

public class InterestTableGUI extends Application {
	private Slider yearSlider;
	private Label principalLabel, rateLabel, numYearsLabel;
	private TextArea displayArea;
	private TextField principal, rate;
	private int sceneWidth, sceneHeight;
	private static double principalValue;
	private static double rateValue;
	private static double yearValue;

	
	public static double getPrincipalValue() {
		return principalValue;
	}

	public void setPrincipalValue(double principalValue) {
		InterestTableGUI.principalValue = principalValue;
	}

	public static double getRateValue() {
		return rateValue;
	}

	public void setRateValue(double rateValue) {
		InterestTableGUI.rateValue = rateValue;
	}

	public static double getYearValue() {
		return yearValue;
	}

	public void setYearValue(double yearValue) {
		InterestTableGUI.yearValue = yearValue;
	}
	
	//this is the method that puts everything together, all the panes
	//and the spacing is done in this method
	public void start(Stage primaryStage) {
		sceneWidth = 620;
		sceneHeight = 350;
	
		// a textarea is used to print out text, the top half with be this screen
		displayArea = new TextArea();
		// the size of the display area will be set to half the height of the scene
		displayArea.setPrefSize(sceneWidth, sceneHeight / 2);
		// the text will wrap around the window and the area will not be editiable
		displayArea.setWrapText(true);
		displayArea.setEditable(false);

	
		//this method will make the labels for the principal and the 
		//rate as well as the textfields
		VBox allign = new VBox();
		allign.setPadding(new Insets(10, 20, 0, 20));
		allign.setSpacing(15);
		
		
	    HBox hboxTop = new HBox();
	    //sets the spacing
	    hboxTop.setPadding(new Insets(10, 12, 0, 17));
	    hboxTop.setSpacing(10);
	    
	    HBox hboxBelow = new HBox();
	    //sets the spacing of the box in comparison to the border around it
	    hboxBelow.setPadding(new Insets(10, 0, 0 , 160));
	    
		// principal label and the principal entry point
		principalLabel = new Label("Principal: ");
		principal = new TextField();
	
		// rate label and the rate entry point
		rateLabel = new Label("Rate(Percentage): ");
		rate = new TextField();
		hboxTop.getChildren().addAll(principalLabel, principal, rateLabel, rate);
		//assigns the elemnts in the first hBox to the first row in the vBox
		allign.getChildren().addAll(hboxTop);
		
		
		// number of years label the new slider associated with it
		numYearsLabel = new Label("Number of Years: ");
		// this is making a new slider to represent the number of years
		yearSlider = new Slider();
		yearSlider.setSnapToTicks(true);
		// minimum of 1 year on the slider
		yearSlider.setMin(1);
		// maximum of 25 years on the slider
		yearSlider.setMax(25);
		yearSlider.setMajorTickUnit(4);
		yearSlider.setShowTickLabels(true);
		yearSlider.setShowTickMarks(true);
		

		//adds elements into the next horizontal box
		hboxBelow.getChildren().addAll(numYearsLabel, yearSlider);
		//horizontal box gets added to the next line in the vBox
		allign.getChildren().addAll(hboxBelow);
		

		//this method is only making the buttons with the labels on the 
		//buttons
	    HBox hboxButtons = new HBox();
	    //this is the spacing around the horizontal box in comparison
	    //to the window
	    hboxButtons.setPadding(new Insets(0, 50, 20, 50));
	    hboxButtons.setSpacing(25);
	    
	    
	    //add the simple interest button
	    Button simpleButton = new Button("Simple Interest");
	    //size of the button
	    simpleButton.setPrefSize(150, 20);
	    //calls the class that will set the displayArea
	    simpleButton.setOnAction(new calcSimpleInterest());
	    
	    
	    //add the compound interest button 
	    Button compoundButton = new Button("Compund Interest");
	    compoundButton.setPrefSize(150, 20);
	    //anon inner class that implements the method that prints the data;
	    compoundButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//the values from the textfield are parsed into doubles
				setPrincipalValue(Double.parseDouble(principal.getText()));
				setRateValue(Double.parseDouble(rate.getText()));
				//the value from the slider is rounded to the nearest whole number
				setYearValue((double) Math.round(yearSlider.getValue()));
				
				displayArea.setText(Calculations.getCompound(getPrincipalValue(), 
								getRateValue(), getYearValue()));	
			}	
	    });
	   
	    //this is the creation of the both button
	    Button bothButton = new Button("Both Interests");
	    bothButton.setPrefSize(150, 20);
		// makes a new horizontal box with all the buttons
		hboxButtons.getChildren().addAll(simpleButton, compoundButton,
									bothButton);
		//lamda expression
		bothButton.setOnAction(e -> {
				//the values from the textfield are parsed into doubles
				setPrincipalValue(Double.parseDouble(principal.getText()));
				setRateValue(Double.parseDouble(rate.getText()));
				//the value from the slider is rounded to the nearest whole number
				setYearValue((double) Math.round(yearSlider.getValue()));
				
				displayArea.setText(Calculations.getBoth(getPrincipalValue(), 
								getRateValue(), getYearValue()));		
	    });
		
		
		/* Adding elements */
		BorderPane borderPane = new BorderPane();
		// the top of the window is going to be set as the textarea
		borderPane.setTop(displayArea);
		// the center is where all the labels and fields will be set
		borderPane.setCenter(allign);
		// the bottom of the window is where all the buttons will lay
		borderPane.setBottom(hboxButtons);

		/* Display the stage */
		Scene scene = new Scene(borderPane, sceneWidth, sceneHeight);
		primaryStage.setTitle("Interest Calculator");
		//the size of the window cannot be adjusted
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	
	}
	
	private class calcSimpleInterest implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//string that adds all the data
			String dataText = "";
			setPrincipalValue(Double.parseDouble(principal.getText()));
			setRateValue(Double.parseDouble(rate.getText()));
			Double yearValue = (double) Math.round(yearSlider.getValue());
			
			dataText = ("Principal: " + NumberFormat.getCurrencyInstance().format(getPrincipalValue()) + ", Rate: "
					+ getRateValue() + "\n" + "Year, Simple Interest Amount");
			double yearValueIterator = 1;

			// for loop will print the value on a new line until the
			// year count reaches the max years
			for (int index = 1; index <= yearValue; index++) {
				dataText += "\n" + index + " --> " + NumberFormat.getCurrencyInstance().format(
						getPrincipalValue() + (getPrincipalValue() * (getRateValue() / 100) * yearValueIterator));
				yearValueIterator++;

			}
			displayArea.setText(dataText);

		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
