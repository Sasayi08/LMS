package org.example.dashboard;

import com.google.common.collect.ImmutableList;
import io.javalin.http.Context;
import org.example.app.db.DataRepository;
import org.example.app.model.Claim;
import org.example.app.model.Person;

import java.util.Map;

public class Dashboard {

    public static final String PATH = "/home";

    public static void renderHomePage(Context context){

        System.out.println("__renderHomePage__");
        Person currentPerson = context.sessionAttribute("user");

        ImmutableList<Claim> currentClaim = DataRepository.getInstance().getClaimsBy(currentPerson, true);

        System.out.println("allLibBooks: " + DataRepository.getInstance().getAllLibraryBooks());


        Map<String, Object> viewModel =
                Map.of( "libraryBooks", DataRepository.getInstance().getAllLibraryBooks(),
                        "books", DataRepository.getInstance().getAllBooks(),
                        "expenses", DataRepository.getInstance().getExpenses(currentPerson),
//                       "totalExpenses", DataRepository.getInstance().getTotalExpensesFor(currentPerson),
                       "owedToOthers", DataRepository.getInstance().getClaimsFrom(currentPerson, true),
                       "totalIOwe", DataRepository.getInstance().getTotalUnsettledClaimsClaimedFrom(currentPerson),
                       "owedToMe", DataRepository.getInstance().getClaimsBy(currentPerson, true),
//                       "totalOwedToMe", DataRepository.getInstance().getTotalUnsettledClaimsClaimedBy(currentPerson),
                       "nettExpenses", DataRepository.getInstance().getNettExpensesFor(currentPerson));

        context.render("home.html", viewModel);
    }
}