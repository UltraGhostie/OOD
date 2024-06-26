package se.kth.iv1350.startup;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.view.TotalRevenueFileOutput;
import se.kth.iv1350.view.TotalRevenueView;
import se.kth.iv1350.view.View;

/**
 * Contains the main method that starts the app.
 */
public class Main {
    /**
     * Starts the app.
     * 
     * @param args Arguments are ignored.
     */
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        TotalRevenueFileOutput.getInstance().subscribeTo(controller);
        TotalRevenueView.getInstance().subscribeTo(controller);
        View view = new View(controller);
    }
}
