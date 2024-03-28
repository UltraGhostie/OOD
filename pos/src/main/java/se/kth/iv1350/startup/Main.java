package se.kth.iv1350.startup;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.integration.Integration;
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
        Integration integration = new Integration();
        Controller controller = new Controller(integration);
        View view = new View(controller);
    }
}
