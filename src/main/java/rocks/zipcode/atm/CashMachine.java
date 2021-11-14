package rocks.zipcode.atm;

import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {

    private final Bank bank;
    private AccountData accountData = null;

    public CashMachine(Bank bank) {
        this.bank = bank;
    }

    private Consumer<AccountData> update = data -> {
        accountData = data;
    };

    public Set<Integer> listOfAccounts(){
      return bank.bankAccount();

    }


    public void login(int id) {
        tryCall(
                () -> bank.getAccountById(id),
                update
        );
    }

    public void deposit(float amount) {


       if (accountData != null && !(amount < 0)) {
            tryCall(
                    () -> bank.deposit(accountData, amount),
                    update
            );
        }


    }

    public void withdraw(float amount) {
        if (accountData != null && !(amount < 0)) {
            tryCall(
                    () -> bank.withdraw(accountData, amount),
                    update
            );
        }
    }

    public void exit() {
        if (accountData != null) {
            accountData = null;


        }
    }

    @Override
    public String toString() {
        return accountData != null ? accountData.toString() : "Please enter valid account ID";
    }

    public String logout(){

        return "Logged Out";
    }



    private <T> void tryCall(Supplier<ActionResult<T> > action, Consumer<T> postAction) {
        try {
            ActionResult<T> result = action.get();
            if (result.isSuccess()) {
                T data = result.getData();
                postAction.accept(data);
            } else {
                String errorMessage = result.getErrorMessage();
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public Bank getBank(){
        return bank;

    }

    public AccountData  getAccountData(){

        return accountData;
    }


}
