package rocks.zipcode.atm;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;

import java.io.FileNotFoundException;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField login = new TextField();
    private TextField depositField = new TextField();
    private TextField withdrawField = new TextField();

    private TextField signUpAcctNum = new TextField();
    private TextField signUpName = new TextField();
    private TextField signUpDeposit = new TextField();
    private TextField signUpEmail = new TextField();

    private TextField signUpAcctNum2 = new TextField();
    private TextField signUpName2 = new TextField();
    private TextField signUpDeposit2 = new TextField();
    private TextField signUpEmail2 = new TextField();

    private Menu listOfAccounts = new Menu("List Of Accounts");
    private MenuBar acctMenuBar = new MenuBar();

    private CashMachine cashMachine = new CashMachine(new Bank());
    private String buttonStyle = "-fx-background-color: #20B2AA; -fx-background-radius: 15px; -fx-text-fill: #ffffff";
    private String background = "-fx-background-color: linear-gradient(from 45% 25% to 100% 50%, #00ff87, #0f68a9)";
    Stage window;
    Scene scene1, scene2;

    private Parent createContent() throws FileNotFoundException {

        cashMachine.getBank().createAccountPremium(5000, "Brian Price", "brian@gmail.com", 1400F); // Test to create account
        cashMachine.getBank().createAccountPremium(6000, "Josh Green", "josh@gmail.com", 25000F); // Test to create account


        VBox vbox = new VBox(10);
        vbox.setPrefSize(750, 700);
        TextArea areaInfo = new TextArea();
        areaInfo.setPrefSize(500, 400);
        Alert overDraftAlert = new Alert(Alert.AlertType.WARNING); //OverDraft Alert Warning
        Alert negativeAlert = new Alert(Alert.AlertType.WARNING); //OverDraft Alert Warning


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
            if (cashMachine.getBank().getAccountById(id).getData() != null) { //checks if account is in bank
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
            if(amount < 0){
                negativeAlert.setContentText("Please enter a positive number");
                negativeAlert.showAndWait();
            }
            areaInfo.setText(cashMachine.toString());

        });


        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setStyle(buttonStyle); // changes button style
        btnWithdraw.setOnAction(e -> {
            Float amount = Float.parseFloat(withdrawField.getText());
            cashMachine.withdraw(amount);

            if (cashMachine.getAccountData().getBalance() < 0) {

                overDraftAlert.setContentText("You Are Broke!!! your Balance is " + cashMachine.getAccountData().getBalance());
                overDraftAlert.showAndWait();
            }
            else if(amount < 0){
               negativeAlert.setContentText("Please enter a positive number");
                negativeAlert.showAndWait();
            }

            areaInfo.setText(cashMachine.toString());

        });

        Button btnExit = new Button("Logout ");
        btnExit.setStyle(buttonStyle); // changes button style
        btnExit.setOnAction(e -> {
            cashMachine.exit();
            depositAndWithdrawGrouped.setDisable(true); //Disables to logout
            areaInfo.setText(cashMachine.logout());
        });


//        Button btnShowAccts = new Button("List Accounts ");
//        btnShowAccts.setStyle(buttonStyle);
//
//        btnShowAccts.setOnAction(e -> {
//            for (Integer acct : cashMachine.listOfAccounts()) {
//
//                areaInfo.setText(acct.toString());
//            }
//
//
//        });

////This takes care of getting all accounts
        acctMenuBar.getMenus().add(listOfAccounts);
        for (Integer acct : cashMachine.listOfAccounts()) {
            MenuItem addAcct = new MenuItem(acct.toString());
            listOfAccounts.getItems().add(addAcct);
        }
////

        Button btnSignUp = new Button("Sign Up ");
        btnSignUp.setStyle(buttonStyle);
        btnSignUp.setOnAction(e -> {
            areaInfo.setText("Sign Up ");
            window.setScene(scene2);

        });

        Button btnClear = new Button("Clear Fields ");
        btnClear.setStyle(buttonStyle);
        btnClear.setOnAction(e -> {
            login.clear();
            depositField.clear();
            withdrawField.clear();
            areaInfo.clear();
        });

        //Login
        FlowPane loginPane = new FlowPane();
        loginPane.getChildren().addAll(btnSubmit, login, btnExit, btnSignUp, btnClear);
        loginPane.setHgap(25);
        loginPane.setPadding(new Insets(10));
        //Login


        //Deposit
        FlowPane depositPane = new FlowPane();
        depositPane.getChildren().addAll(btnDeposit, depositField);
        depositPane.setHgap(13);
        depositPane.setPadding(new Insets(10));
        //Deposit
        //Withdraw
        FlowPane withdrawPane = new FlowPane();
        withdrawPane.getChildren().addAll(btnWithdraw, withdrawField);
        withdrawPane.setHgap(10);
        withdrawPane.setPadding(new Insets(10));
        //Withdraw

        depositAndWithdrawGrouped.getChildren().addAll(depositPane, withdrawPane); //groups deposit and withdraw
        depositAndWithdrawGrouped.setDisable(true); // disabled buttons

        vbox.getChildren().addAll(acctMenuBar,loginPane, depositAndWithdrawGrouped, areaInfo);

        return vbox;
    }

    private Parent signUpForm() { // SIGN UP FORM


        GridPane signUpPane = new GridPane();
        signUpPane.setPadding(new Insets(15, 15, 15, 15));
        signUpPane.setMinSize(300, 300);
        signUpPane.setVgap(10);
        signUpPane.setHgap(10);



        Text premium = new Text("  Premium Account");
        signUpPane.add(premium, 1,0);
        Text Acct = new Text("Account # ");
        signUpPane.add(Acct, 0, 1);
        signUpAcctNum.setPrefColumnCount(10);
        signUpPane.add(signUpAcctNum, 1, 1);

        Text name = new Text("Name ");
        signUpPane.add(name, 0, 2);
        signUpName.setPrefColumnCount(10);
        signUpPane.add(signUpName, 1, 2);

        Text email = new Text("Email ");
        signUpPane.add(email, 0, 3);
        signUpEmail.setPrefColumnCount(10);
        signUpPane.add(signUpEmail, 1, 3);

        Text deposit = new Text("Initial Deposit");
        signUpPane.add(deposit, 0, 4);
        signUpDeposit.setPrefColumnCount(10);
        signUpPane.add(signUpDeposit, 1, 4);


        Button btnSignUp = new Button("Sign Up For Premium Account");
        btnSignUp.setStyle(buttonStyle);
        btnSignUp.setOnAction(e -> {   // This Takes care of creating new accounts
            int acct = Integer.parseInt(signUpAcctNum.getText());
            String fullName = signUpName.getText();
            String Email = signUpEmail.getText();
            Float depositAmt = Float.parseFloat(signUpDeposit.getText());
            cashMachine.getBank().createAccountPremium(acct, fullName, Email, depositAmt);  // Creates Premium acct
            listOfAccounts.getItems().clear(); //clears list of accounts
            for (Integer acct1 : cashMachine.listOfAccounts()) {
                MenuItem addAcct = new MenuItem(acct1.toString());
                listOfAccounts.getItems().add(addAcct);
            }
            signUpAcctNum.clear();
            signUpName.clear();
            signUpEmail.clear();
            signUpDeposit.clear();
            window.setScene(scene1);
        });

        signUpPane.add(btnSignUp, 0, 5); // added signup button to GridPane

        GridPane signUpPane2 = new GridPane();
        signUpPane2.setPadding(new Insets(15, 15, 15, 15));
        signUpPane2.setMinSize(300, 300);
        signUpPane2.setVgap(10);
        signUpPane2.setHgap(10);



        Text basic = new Text("Basic Account");
        signUpPane2.add(basic, 1,0);
        Text basiAcct = new Text("Account # ");
        signUpPane2.add(basiAcct, 0, 1);
        signUpAcctNum2.setPrefColumnCount(10);
        signUpPane2.add(signUpAcctNum2, 1, 1);

        Text basicName = new Text("Name ");
        signUpPane2.add(basicName, 0, 2);
        signUpName2.setPrefColumnCount(10);
        signUpPane2.add(signUpName2, 1, 2);

        Text basicEmail = new Text("Email ");
        signUpPane2.add(basicEmail, 0, 3);
        signUpEmail2.setPrefColumnCount(10);
        signUpPane2.add(signUpEmail2, 1, 3);

        Text basicDeposit = new Text("Initial Deposit");
        signUpPane2.add(basicDeposit, 0, 4);
        signUpDeposit2.setPrefColumnCount(10);
        signUpPane2.add(signUpDeposit2, 1, 4);


        Button btnBasicSignUp = new Button("Sign Up For Basic Account");
        btnBasicSignUp.setStyle(buttonStyle);
        btnBasicSignUp.setOnAction(e -> {   // This Takes care of creating new accounts
            int acct = Integer.parseInt(signUpAcctNum2.getText());
            String fullName = signUpName2.getText();
            String Email = signUpEmail2.getText();
            Float depositAmt = Float.parseFloat(signUpDeposit2.getText());
            cashMachine.getBank().createAccountBasic(acct, fullName, Email, depositAmt);  // Creates Basic acct

            listOfAccounts.getItems().clear(); //clears list of accounts
            for (Integer acct1 : cashMachine.listOfAccounts()) {
                MenuItem addAcct = new MenuItem(acct1.toString());
                listOfAccounts.getItems().add(addAcct);
            }
            signUpAcctNum2.clear();
            signUpName2.clear();
            signUpEmail2.clear();
            signUpDeposit2.clear();
            window.setScene(scene1);
        });

  signUpPane2.add(btnBasicSignUp, 0,5);





        Button btnGoBack = new Button("Go Back ");
        btnGoBack.setStyle(buttonStyle);
        btnGoBack.setOnAction(e -> window.setScene(scene1));


        VBox signUpForm = new VBox(10);
        signUpForm.setPrefSize(600, 600);
        signUpForm.setStyle(background);
        signUpForm.getChildren().addAll(btnGoBack, signUpPane, signUpPane2);


        return signUpForm;
    }


    @Override
    public void start(Stage primaryStage) throws Exception { // Scenes
        window = primaryStage;
        scene1 = new Scene(createContent());
        scene2 = new Scene(signUpForm());

        primaryStage.setScene(scene1);
        primaryStage.show();
        primaryStage.setTitle("Welcome to ZipCloudBank!!");


//        stage.setScene(new Scene(createContent()));
//        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
