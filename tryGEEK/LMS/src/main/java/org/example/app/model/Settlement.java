package org.example.app.model;

import java.time.LocalDate;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class Settlement extends AbstractTransactionModelBase {

    private final Book generatedExpense;
    private final Claim claim;
    private final LocalDate settlementDate;

    Settlement( Claim claim, LocalDate settlementDate ){
        super( UUID.randomUUID(), claim.getAmount() );
        this.claim = claim;
//        this.generatedExpense = new Book(claim.getAmount(), settlementDate, claim.getExpense().getDescription(), claim.getClaimedFrom());
        this.generatedExpense = new Book(claim.getTitle(), claim.getExpense().getGenre(), claim.getExpense().getAuthor(), claim.getExpense().getPaidBy());
        this.settlementDate = checkNotNull( settlementDate );

        System.out.println("Settlement: gExp: "+ generatedExpense);
    }

    public Book getGeneratedExpense() {
        return generatedExpense;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public Claim getClaim() {
        return claim;
    }
}