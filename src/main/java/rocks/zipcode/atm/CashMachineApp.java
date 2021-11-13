package rocks.zipcode.atm;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField field = new TextField();
    private TextField depositField = new TextField();
    private TextField withdrawField = new TextField();

    private CashMachine cashMachine = new CashMachine(new Bank());

    Stage window;


    private Parent createContent() throws IOException {

        VBox vbox = new VBox(10);

        vbox.setPrefSize(600, 600);
        TextArea areaInfo = new TextArea();


// Styling
        vbox.setPadding(new Insets(25, 10, 10, 10));
        vbox.setSpacing(20); // add space
        depositField.setPromptText("Enter Amount To Deposit "); //Prompt Text
        withdrawField.setPromptText("Enter Amount To Withdraw "); //Prompt Text
        field.setPromptText("Please Enter Your Account ID "); //Prompt Text
        field.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;"); // add border color
        vbox.setStyle("-fx-background-color: linear-gradient(from 45% 25% to 100% 50%, #00ff87, #0f68a9)"); // Changes background Color
        areaInfo.setFont(Font.font("Poppins", 20)); // font style
//Styling

        Button btnSubmit = new Button("Login ");
        btnSubmit.setStyle("-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff"); // changes button style


        btnSubmit.setOnAction(e -> {


            int id = Integer.parseInt(field.getText());
            cashMachine.login(id);
            areaInfo.setText(cashMachine.toString());



        });

        Button btnDeposit = new Button("Deposit  ");
        btnDeposit.setStyle("-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff"); // changes button style

        btnDeposit.setOnAction(e -> {
            Float amount = Float.parseFloat(depositField.getText());
            cashMachine.deposit(amount);

            areaInfo.setText(cashMachine.toString());
        });


        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setStyle("-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff"); // changes button style
        btnWithdraw.setOnAction(e -> {
            Float amount = Float.parseFloat(withdrawField.getText());
            cashMachine.withdraw(amount);

            areaInfo.setText(cashMachine.toString());
        });

        Button btnExit = new Button("Logout ");
        btnExit.setStyle("-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff"); // changes button style
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            areaInfo.setText(cashMachine.toString());
        });
//Login
        FlowPane loginPane = new FlowPane();
        loginPane.getChildren().addAll(btnSubmit, field, btnExit);
        loginPane.setHgap(25);
        loginPane.setPadding(new Insets(10));
//Login

     VBox depAndWithGroup = new VBox();

        //Deposit
        FlowPane depositPane = new FlowPane();
        depositPane.getChildren().addAll(btnDeposit,depositField);
        depositPane.setHgap(13);
        depositPane.setPadding(new Insets(10));
        //Deposit
        //Withdraw
        FlowPane withdrawPane = new FlowPane();
        withdrawPane.getChildren().addAll(btnWithdraw,withdrawField);
        withdrawPane.setHgap(10);
        withdrawPane.setPadding(new Insets(10));
        //Withdraw

     depAndWithGroup.getChildren().addAll(depositPane, withdrawPane); //groups deposit and withdraw
     depAndWithGroup.setDisable(true);

        vbox.getChildren().addAll(loginPane, depAndWithGroup, areaInfo);

        return vbox;
    }

    @Override
    public void start(Stage primaryStage) throws Exception { // Scenes
        window = primaryStage;
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
//        stage.setScene(new Scene(createContent()));
//        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
