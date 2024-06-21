package org.example.claim;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import org.example.app.db.DataRepository;
import org.example.app.model.Book;
import org.example.app.model.Claim;
import org.example.app.model.Expense;
import org.example.app.model.Person;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling calls from form submits for Claims
 */
public class ClaimExpenseController {
    public static final String PATH = "/claimbook";

    public static void renderClaimExpensePage(Context context){
        System.out.println("__RenderClaimExpensePage__");
        UUID expenseId = UUID.fromString(context.queryParam("expenseId"));
        System.out.println("Book ID: " + expenseId);

        Optional<Book> maybeExpense = DataRepository.getInstance().getBook(expenseId, true); //book id
        System.out.println("book: " + maybeExpense);
        if (maybeExpense.isEmpty()) {
            context.status(HttpCode.BAD_REQUEST);
            context.result("Invalid expense");
            return;
        }

        Book expense = maybeExpense.get();

        Claim newClaim = new Claim( expense, new Person("5842 your home"), 0.0, LocalDate.now());
        context.sessionAttribute("expense", expense);
        context.render("claimbook.html",
                Map.of("newClaim", newClaim,
                        "expense", expense));
    }
}