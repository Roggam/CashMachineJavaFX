package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<Integer, Account> accounts = new HashMap<>();

    public Bank() {
        accounts.put(1000, new BasicAccount(new AccountData(
                1000, "Dolio Durant", "dolio@gangstagrass.com", 5000F
        )));
        accounts.put(1200, new BasicAccount(new AccountData(
                1200, "John Doe", "john@gmail.com", 700F
        )));
        accounts.put(1500, new BasicAccount(new AccountData(
                1500, "Mike Smith", "mike@outlook.com", 5000F
        )));
        accounts.put(2000, new PremiumAccount(new AccountData(
                2000, "Leon Hunter", "leon@gmail.com", 200F
        )));

        accounts.put(3000, new PremiumAccount(new AccountData(
                3000, "Rogelio Gamboa Jr", "junior@gmail.com", 100F
        )));

        accounts.put(4000, new PremiumAccount(new AccountData(
                4000, "Kris Younger", "kris@zipcode.com", 50000F
        )));
    }

    public ActionResult<AccountData> getAccountById(int id) {
        Account account = accounts.get(id);

        if (account != null) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("No account with id: " + id + "\nTry account 1000 or 2000");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, float amount) {
        Account account = accounts.get(accountData.getId());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, float amount) {
        Account account = accounts.get(accountData.getId());
        boolean ok = account.withdraw(amount);

        if (ok) {
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + account.getBalance());
        }
    }
}
