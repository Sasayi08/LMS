package org.example.login;

import com.google.common.collect.ImmutableList;
import io.javalin.http.Context;
import org.example.app.InitializeApp;
import org.example.app.db.DataRepository;
import org.example.app.model.Genre;
import org.example.app.model.Person;
import org.example.nettexpenses.NettExpensesController;
import org.example.app.model.Author;
import org.example.app.model.Book;

public class LoginController {
    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";
    public static final String ROOT_PATH = "/index.html";

    public static void handleLogin(Context context) {
        String username = context.formParam("email");
        if (username == null) {
            context.redirect(ROOT_PATH);
            return;
        }

        final Person person = DataRepository.getInstance().addPerson(new Person(username));
        person.setCurrent_person(person);
        context.sessionAttribute("user", person);


        context.redirect(NettExpensesController.PATH);
        InitializeApp __app_init = new InitializeApp();
        __app_init.loadTheBooks(person);
        addBooks(person);
    }

    public static void handleLogout(Context context) {
        context.sessionAttribute("user", null);
        context.redirect(ROOT_PATH);
    }

    public static void addBooks(Person person) {
        System.out.println(" This is person in add books: "+ person);
        Author author = new Author("Hnnie", "Hamsworth");
        Genre genre = new Genre("Sci", "Science");
        Author author2 = new Author("me", "alle");
        Genre genre2 = new Genre("Bio", "Biography");
        DataRepository.getInstance().addBook(new Book("how water flows", genre, author, person ));
        DataRepository.getInstance().addBook(new Book("amazed", genre2, author2, person ));

        ImmutableList<Book> allBooks = DataRepository.getInstance().getAllBooks();

    }
}