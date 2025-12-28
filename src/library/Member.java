package library;

import java.util.ArrayList;

public class Member {

    private String name;
    private String memberId;
    private ArrayList<BookLoan> borrowedBooks;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(BookLoan loan) {
        borrowedBooks.add(loan);
    }

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getBorrowedBooksString() {
        String result = "";
        for (BookLoan loan : borrowedBooks) {
            result += loan.toString() + "\n";
        }
        return result;
    }

    public double getTotalFine() {
        double total = 0;
        for (BookLoan loan : borrowedBooks) {
            total += loan.getFine();
        }
        return total;
    }
}
