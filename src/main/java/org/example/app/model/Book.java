package org.example.app.model;

import org.example.util.Strings;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

/**
 * DO NOT MODIFY THIS CODE
 *
 * A Book
 */
public class Book extends AbstractTransactionModelBase {
//    private static final Integer NO_ID = -1;
    private final String title;
    private final Genre genre;
    private final Author author;
//    private final Flow.Publisher publisher;
    private final int bluePoints;
    private Person paidBy;
    private final LocalDate date;

    private final Set<Claim> claims;

    private final Double amount;

    private String bookauthor;
    private String authorInitial;

    private String bookDescription;
    private String bookCover;


//    private Integer id;

    /**
     * Create a new book.
     * Note that a newly created book does not have a valid id.
     * There should be assigned by the data access object that saves the book to the database.
     *
     * @param title The title of the book
     * @param genre The genre of the book
     */
    public Book(String title, Genre genre, Author author, Person paidBy) {
        super( UUID.randomUUID(), 0.13 );
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.bluePoints = 5;
        this.paidBy = paidBy;
        this.claims = new HashSet<>();
        this.date = LocalDate.now();
        this.amount = 00.0;
        this.bookauthor = getAuthor().getFirstname()+" "+ getAuthor().getSurname();
        this.authorInitial = getInitial();

//        this.id = NO_ID;
    }
    //Overload
    public Book(String title, Genre genre, Author author, Person paidBy, Boolean isAllLibraryBooks, String bDescription, String bCover) {
        super( UUID.randomUUID(), 0.13 );
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.bluePoints = 5;
        this.paidBy = paidBy;
        this.claims = new HashSet<>();
        this.date = LocalDate.now();
        this.amount = 00.0;
        this.bookauthor = getAuthor().getFirstname()+" "+ getAuthor().getSurname();
        this.authorInitial = getInitial();
        this.bookDescription = bDescription;
        this.bookCover = bCover;


//        this.id = NO_ID;
    }

    /**
     * Assign an id to the book.
     * This is usually an id value obtained from the database.
     *
     * @param id The id for the book
     */
//    public void assignId(int id) {
//        this.id = id;
//    }

    /**
     * Check if the book has an assigned id
     *
     * @return true if the book has an assigned id
     */
//    public boolean hasId() {
//        return !Objects.equals(id, NO_ID);
//    }



    public String getTitle() {
        return Strings.capitaliseFirstLetter(title);
    }

    public Genre getGenre() {
        return genre;
    }

    public Author getAuthor() {
        return author;
    }

    public int getBluePoints() {
        return bluePoints;
    }

    public Person getPaidBy() {
        return paidBy;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return genre.getDescription();
    }

    public LocalDate getDate() {
        return date;
    }


//    public Integer getId() { return id; }
    /**
     * Get the id.
     * If the id has not yet been assigned, it throws an unchecked exception.
     *
     * @return the id
     */

    // person
    // due date

    public Claim createClaim( Person claimedFrom, Double amount, LocalDate dueDate ){
        Double currentTotalClaimed = this.claims.stream().mapToDouble(Claim::getAmount).sum();
        if (currentTotalClaimed + amount > this.getAmount()) {
            throw new RuntimeException("Total claims exceeds the amount of the expense");
        }
        Claim claim = new Claim( this, claimedFrom, amount, dueDate );
        this.claims.add(claim);
        return claim;
    }

    public Set<Claim> getClaims() {
        return claims.stream()
                .sorted(Comparator.comparing(Claim::getDueDate))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public String getAuthorInitial() {
        return authorInitial;
    }

    public String getInitial(){
        String author = getAuthorInitial();
        return Strings.initialiseAuthor( bookauthor);
    }

    public String getBookCover() {
        return bookCover;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!Objects.equals(id, book.id)) return false;
        if (!title.equals(book.title)) return false;
        return genre.equals(book.genre);
    }

//    @Override
//    public int hashCode() {
//        int result = id;
//        result = 31 * result + title.hashCode();
//        result = 31 * result + genre.hashCode();
//        return result;
//    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", date=" + date +
                ", authorInitial=" + authorInitial +
                ", bookDescription=" + bookDescription +
                ", bookCover=" + bookCover +
                '}';

    }
}
