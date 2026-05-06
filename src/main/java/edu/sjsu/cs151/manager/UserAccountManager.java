package edu.sjsu.cs151.manager;

import edu.sjsu.cs151.util.CipherUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserAccountManager {

    private final List<UserAccount> accounts = new ArrayList<>();
    private static final String FILE = "user_accounts.txt";

    // Reads user_accounts.txt, creates a UserAccount object for each line and then adds it to accounts 
    public void loadAccounts(){
        File f = new File(FILE);
        if (!f.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(f)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                UserAccount account = new UserAccount(parts[0], CipherUtil.decrypt(parts[1]));
                account.setBlackjackScore(Integer.parseInt(parts[2]));
                account.setSnakeScore(Integer.parseInt(parts[3]));
                accounts.add(account);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No accounts file found");
        }
    }

    // Loops through every UserAccount in accounts list and write each account as a line in the file
    public void saveAccounts(){
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE))) {
            for (UserAccount a : accounts) {
                writer.println(a.getUsername() + "," + CipherUtil.encrypt(a.getPasswordHash()) + ","
                        + a.getBlackjackScore() + "," + a.getSnakeScore());
            }
        } catch (IOException e) {
            System.out.println("Could not save accounts.");
        }
    }

    // Creates new UserAccount, adds it to the list and write it to the file
    public boolean createAccount(String username, String password){
        if (accountExists(username)) return false;
        accounts.add(new UserAccount(username, password));
        saveAccounts();
        return true;
    }

    // Loops through accounts list and calls checkPassword() upon finding a matching username 
    public UserAccount login(String username, String password){
        for (UserAccount a : accounts) {
            if (a.getUsername().equals(username) && a.checkPassword(password)) {
                return a;
            }
        }
        return null;
    }

    // Checks accounts list to see if username matches
    public boolean accountExists(String username){
        for (UserAccount a : accounts) {
            if (a.getUsername().equals(username)) return true;
        }
        return false;
    }

    // Returns UserAccount object is account is found, null otherwise
    public UserAccount getAccount(String username){
        for (UserAccount a : accounts) {
            if (a.getUsername().equals(username)) return a;
        }
        return null;
    }
}
