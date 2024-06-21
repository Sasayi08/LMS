package org.example.app.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import org.example.util.Strings;


// TODO: also introduce ID rather for Claim?

public class Claim extends AbstractTransactionModelBase {

    private final Person claimedBy;
    private final Person claimedFrom;
    private final LocalDate dueDate;
    private final Book expense;
    private boolean settled;
    private String booktitle;
    private String bookauthor;
    private String authorInitial;

    // FIXME: Constructor should not be public
    public Claim(Book originalExpense, Person claimedFrom, Double amount, LocalDate dueDate) {
        super(UUID.randomUUID(), amount);
        this.claimedBy = originalExpense.getPaidBy();
        this.booktitle = originalExpense.getTitle();
        this.bookauthor = originalExpense.getAuthor().getFirstname()+" "+ originalExpense.getAuthor().getSurname();
        this.claimedFrom = claimedFrom;
        this.dueDate = dueDate;
        this.expense = originalExpense;
        this.settled = false;
        this.authorInitial = getInitial();
        System.out.println("___"+authorInitial);

    }

    public Book getExpense() {
        return expense;
    }

    public Person getClaimedBy() {
        return claimedBy;
    }

    public String _getClaimedFrom() {
        return claimedFrom.getEmail();
    }

    public Person getClaimedFrom() {
        return claimedFrom;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Settlement settleClaim(LocalDate settlementDate) {
        settled = true;
        return new Settlement(this, settlementDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Claim claim = (Claim) o;
        return getId().equals(claim.getId()) && claimedBy.equals(claim.claimedBy) && claimedFrom.equals(claim.claimedFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), claimedBy, claimedFrom);
    }

    @Override
    public String toString() {
        return "Claim{" +
                "originalExpense=" + getId() +
                ", claimedBy='" + claimedBy + '\'' +
                ", claimedFrom='" + claimedFrom + '\'' +
                ", amount=" + getAmount() +
                ", dueDate=" + dueDate +
                ", bookTitle" + booktitle +
                ", authorInitial" + authorInitial +
                '}';
    }

    public boolean isSettled() {
        return settled;
    }

    public String getDescription() {
        return expense.getDescription();
    }

    public void setBooktitle() {
        this.booktitle = expense.getTitle();
    }

    public String getTitle() {
        return expense.getTitle();
    }

    public void setBookauthor() {
        System.out.println("this is book author @setBookauthor");
        System.out.println(bookauthor);
        this.bookauthor = (expense.getAuthor().getFirstname() +" "+ expense.getAuthor().getSurname() );
    }

    public String getAuthor() {
        return authorInitial;
    }

    public String getInitial(){
        String author = getAuthor();
        System.out.println(author);
        System.out.println("_____________");
        return Strings.initialiseAuthor( bookauthor);
    }

    public Double getAmount() {
        return amount;
    }
}