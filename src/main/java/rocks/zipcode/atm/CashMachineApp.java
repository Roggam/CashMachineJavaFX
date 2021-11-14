package rocks.zipcode.atm;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField login = new TextField();
    private TextField depositField = new TextField();
    private TextField withdrawField = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());
    private String buttonStyle = "-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
    private String background = "-fx-background-color: linear-gradient(from 45% 25% to 100% 50%, #00ff87, #0f68a9)";
    Stage window;


    private Parent createContent()  {

     cashMachine.getBank().createAccount(5000, "Brian Price", "brian@gmail.com", 1400F); // Test to create account
     cashMachine.getBank().createAccount(6000, "Josh Green", "josh@gmail.com", 25000F); // Test to create account


        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);
        TextArea areaInfo = new TextArea();
        areaInfo.setPrefSize(500, 600);
       Alert overDraftAlert = new Alert(Alert.AlertType.WARNING);


// Styling
        vbox.setPadding(new Insets(25, 10, 10, 10));
        vbox.setSpacing(20); // add space
        depositField.setPromptText("Enter Amount To Deposit "); //Prompt Text
        withdrawField.setPromptText("Enter Amount To Withdraw "); //Prompt Text
        login.setPromptText("Please Enter Your Account ID "); //Prompt Text
        login.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;"); // add border color
        vbox.setStyle(background); // Changes background Color
        areaInfo.setFont(Font.font("Poppins", 20)); // font style
//Styling

        Button btnSubmit = new Button("Login ");
        btnSubmit.setStyle(buttonStyle); // changes button style
        VBox depositAndWithdrawGrouped = new VBox();

        btnSubmit.setOnAction(e -> {
            int id = Integer.parseInt(login.getText());
            //cashMachine.login(id);
       if(cashMachine.getBank().getAccountById(id).getData() != null){ //checks if account is in bank
           cashMachine.login(id);
          depositAndWithdrawGrouped.setDisable(false); //enables deposit and withdraw fields and buttons
         }
            areaInfo.setText(cashMachine.toString());


        });

        Button btnDeposit = new Button("Deposit  ");
        btnDeposit.setStyle(buttonStyle); // changes button style

        btnDeposit.setOnAction(e -> {
            Float amount = Float.parseFloat(depositField.getText());
            cashMachine.deposit(amount);
            areaInfo.setText(cashMachine.toString());

        });


        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setStyle(buttonStyle); // changes button style
        btnWithdraw.setOnAction(e -> {
            Float amount = Float.parseFloat(withdrawField.getText());


        if(cashMachine.getAccountData().getBalance() < 0){
            overDraftAlert.setContentText("You Are Broke, your Balance is " + cashMachine.getAccountData().getBalance());
            overDraftAlert.showAndWait();
        }
        else {
            cashMachine.withdraw(amount);
            areaInfo.setText(cashMachine.toString());
        }
        });

        Button btnExit = new Button("Logout ");
        btnExit.setStyle(buttonStyle); // changes button style
        btnExit.setOnAction(e -> {
            cashMachine.exit();
           depositAndWithdrawGrouped.setDisable(true); //Disables to logout
            areaInfo.setText(cashMachine.logout());
        });


        Button btnShowAccts = new Button("Show Accounts ");
        btnShowAccts.setStyle(buttonStyle);

       btnShowAccts.setOnAction(e -> {
       areaInfo.setText(cashMachine.getAccountData().toString());

    } );

      //Login
        FlowPane loginPane = new FlowPane();
        loginPane.getChildren().addAll(btnSubmit, login, btnExit,btnShowAccts);
        loginPane.setHgap(25);
        loginPane.setPadding(new Insets(10));
       //Login



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

       depositAndWithdrawGrouped.getChildren().addAll(depositPane, withdrawPane); //groups deposit and withdraw
       depositAndWithdrawGrouped.setDisable(true); // disabled buttons

       vbox.getChildren().addAll(loginPane, depositAndWithdrawGrouped, areaInfo);

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
