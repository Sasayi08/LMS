package org.example.claim;

import io.javalin.http.Context;
import org.example.app.db.DataRepository;
import org.example.app.model.Book;
import org.example.app.model.Claim;
import org.example.app.model.Expense;
import org.example.app.model.Person;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Controller for handling API calls for Claims
 */
public class ClaimsApiController {
    public static final String PATH = "/api/claims";
    private static final String admin = "admin@example.com";
    public static void create(@NotNull Context context) {
        ClaimViewModel claimViewModel = context.bodyAsClass(ClaimViewModel.class);
        Book expense = context.sessionAttribute("expense");

        Person p = DataRepository.getInstance().addPerson(new Person(admin));
        // create claim runs calculation to update book
        Claim c = expense.createClaim(p, 00.0, claimViewModel.dueDateAsLocalDate());
        DataRepository.getInstance().addClaim(c);
        DataRepository.getInstance().updateBook(expense);

        Optional<Book> maybeExpense = DataRepository.getInstance().getBook(expense.getId());
        expense = maybeExpense.orElseThrow(() -> new RuntimeException("Could not reload expense"));
        context.sessionAttribute("expense", expense);

        claimViewModel.setId(expense.getClaims().size());
        context.status(201);
        context.json(claimViewModel);
    }
}