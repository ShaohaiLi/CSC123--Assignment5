//Shaohai Li (sli34@toromail.csudh.edu)

import java.util.HashMap;

import java.util.Map;

public class Bank {

    private static Map<Integer, Account> accounts = new HashMap<>();
    private static int accountNumbers = 100;

    private Bank() {}

    public static Account openAccount(String firstName, String lastName, String email, String SSN, String accountType) {
        Person customer = new Person(firstName, lastName,email,SSN);
        Account account = new Account(accountNumbers++, accountType, customer);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public static Account openAccount(String firstName, String lastName, String email, String SSN, String accountType, double overdraftLimit) {
        Person customer = new Person(firstName, lastName, email, SSN);
        Account account = new Account(accountNumbers++, accountType, customer, overdraftLimit);
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public static Account getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public static Account findAccount(int accountNumber) {
        for (Account account : accounts.values()) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public static class AccountClosedException extends Exception {
        public AccountClosedException(String message) {
            super(message);
        }
    }

    public static class InsufficientBalanceException extends Exception {
        public InsufficientBalanceException(String message) {
            super(message);
        }
    }

    public static class NoSuchAccountException extends Exception {
        public NoSuchAccountException(String message) {
            super(message);
        }
    }

    public static boolean deposit(int accountNumber, double amount) throws AccountClosedException, InsufficientBalanceException,NoSuchAccountException {
        Account account = findAccount(accountNumber);
        if (account == null) {
            throw new NoSuchAccountException("Account does not exist");
        }
        if (!account.isOpen()) {
            throw new AccountClosedException("Cannot deposit to a closed account");
        }
        if (((account.getType().equals("Saving")) || (account.getType().equals("Checking")) ) &&  (account.getBalance() < 0 || amount < 0)) {
            throw new InsufficientBalanceException("Cannot deposit a negative amount or deposit to an account with negative balance");
        }
        account.deposit(amount);
        return true;
    }

    public static boolean withdrawal(int accountNumber, double amount) throws AccountClosedException, InsufficientBalanceException,NoSuchAccountException{
        Account account = findAccount(accountNumber);
        if (account == null) {
            throw new NoSuchAccountException("Account does not exist");
        }
        if (!account.isOpen()) {
            throw new AccountClosedException("Cannot withdraw from a closed account");
        }
        if (((account.getType().equals("Saving")) || (account.getType().equals("Checking")) ) && ((account.getBalance() > amount) || (account.getBalance() - amount < 0)) ){
            throw new InsufficientBalanceException("Cannot withdraw more than the available balance or withdraw from an account with negative balance");
        }
        account.withdrawal(amount);
        return true;
    }

    public static boolean closeAccount(int accountNumber) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return false;
        }
        accounts.remove(accountNumber);
        return true;
    }

    public static double getBalance(int accountNumber) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return 0.0;
        }
        return account.getBalance();




/*//The format and layout of the transactions in your file should be exactly the
//same as the printout you see when you choose option 4 – Account Statement.
private static final String TRANSACTION_FILE_NAME = "transactions.txt";
    List<String> lines = new ArrayList<>();
   *//* public static boolean saveTransactions(int accountNumber) {
        try {
            File file = new File(TRANSACTION_FILE_NAME);
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            Account account = findAccount(accountNumber);
            if (account != null) {
                for (Transaction transaction : account.getTransactions()) {
                    String type = transaction.getType().equals("Credit") ? "Credit" : "Debit";
                    bw.write(transaction.getId() + " : " + type + " : " + transaction.getAmount());
                    bw.newLine();
                }
                bw.close();
                return true;
            } else {
                System.out.println("Account not found.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }*/

    }

}