package library;

public class BookLoan {

    private String bookCode;
    private double fine;
    private boolean reference;

    public BookLoan(String bookCode, double fine, boolean reference) {
        this.bookCode = bookCode;
        this.fine = fine;
        this.reference = reference;
    }

    public double getFine() {
        return fine;
    }

    @Override
    public String toString() {
        String type;
        if (this.reference == true)
            type = "Reference Book";
        else
            type = "Regular Book";

        return "Book Code: " + this.bookCode + " | " + type + " | Fine: " + this.fine;
    }
}
