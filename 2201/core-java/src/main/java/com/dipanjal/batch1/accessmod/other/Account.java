package com.dipanjal.batch1.accessmod.other;

import com.dipanjal.batch1.accessmod.pub.User;
import static java.lang.System.out;

public class Account {
    public String accountNo;
    public User accountHolder;

    public Account() {}

    public void printDetails(Account account) {
        out.println(account.accountNo);
        out.println(account.accountHolder.name);
        out.println(account.accountHolder.age);
    }

    public void printDetails() {
        System.out.println(this.accountNo);
        System.out.println(this.accountHolder.name);
        System.out.println(this.accountHolder.age);
    }

    public static void main(String[] args) {
        Account account = new Account(); //copy 1
        account.accountNo = "1234XYZ";

        User user = new User();
        user.name = "Khalid";
        user.age = 23;
        account.accountHolder = user;

        account.printDetails(account);
        account.printDetails();

        Account account2 = new Account(); //copy 2
        account2.accountNo = "1234GFD";
        account2.accountHolder = new User();
        account2.accountHolder.name = "Dipanjal";
        account2.accountHolder.age = 27;
        account2.printDetails();

    }
}
