package com.example.android.justjava;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //global variable
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * increment and decrement
     * methods
     */

    public void increment(View view) {

        if (quantity == 100){
            Toast.makeText(this,"100 cups of coffee is way to high you " +
                    "need to cut it!", Toast.LENGTH_SHORT ).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {

        if (quantity == 1){
            Toast.makeText(this,"1 cup of coffee is way to low you " +
                    "need to add it!", Toast.LENGTH_SHORT ).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //checking if checkbox is checked
        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        //get name
        EditText nameEditText = findViewById(R.id.name_EditText);
        String name = nameEditText.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        //displayMessage(priceMessage);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        //TextView priceTextView = findViewById(R.id.price_text_view);
        //priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Calculates the price of an order
     * @param addWhippedCream
     * @param addChocolate
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        //Price per cup of coffee
        int basePrice = 5;

        //add 1 if whipped cream topping is selected
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }
        //add 2 is chocolate is selected
        if (addChocolate){
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     *
     * @param price from calculatePrice() method
     * @param addWhippedCream is whether the user wants whipped cream topping
     * @param addChocolate is whether the user wants chocolate topping
     * @param customerName is the customer's name
     * @return the order summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate,
                                      String customerName){
        String priceMessage = "Name: " + customerName;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: Ksh. " + price + "\nThank you!";

        Intent intent  = new Intent(Intent.ACTION_SENDTO);
        //intent.setType("*/*");
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        return priceMessage;





    }
}
