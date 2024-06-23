package org.example.app;

import com.google.common.collect.ImmutableList;
import org.example.app.db.DataRepository;
import org.example.app.model.Author;
import org.example.app.model.Book;
import org.example.app.model.Genre;
import org.example.app.model.Person;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InitializeApp {


    public void addBooks(Person person) {
          Author author2 = new Author("me", "alle");
//        DataRepository.getInstance().addBook(new Book("amazed", genre2, author2, person ));

        ImmutableList<Book> allBooks = DataRepository.getInstance().getAllBooks();

    }


    public void loadTheBooks(Person person) {

        if (DataRepository.getInstance().getAllLibraryBooks().isEmpty()) {
            try {
                // Read the JSON file into a String
                String jsonString = new String(Files.readAllBytes(Paths.get("src/main/java/org/example/util/allBooks.json")));

                // Parse the JSON string
                JSONObject jsonObject = new JSONObject(jsonString);

                // Extract the array of books
                JSONArray booksArray = jsonObject.getJSONArray("books");

                // Loop through each book in the array
                for (int i = 0; i < booksArray.length(); i++) {
                    JSONObject book = booksArray.getJSONObject(i);


                    // Extract book details
                    String title = book.getString("title");
                    JSONObject authorObj = book.getJSONObject("author");
                    String firstName = authorObj.getString("firstname");
                    String lastName = authorObj.getString("lastname");
                    Author author = new Author(firstName, lastName);
                    JSONObject genreObj = book.getJSONObject("genre");
                    String genreCode = genreObj.getString("code");
                    String genreDescription = genreObj.getString("description");
                    Genre genre = new Genre(genreCode, genreDescription);
                    String description = book.getString("description");
                    String coverImg = book.getString("cover_image");

                    DataRepository.getInstance().addBook(new Book(title, genre, author, person, true , description, coverImg ), description, coverImg);

                    System.out.println("all lib books________ ");
                    System.out.println(DataRepository.getInstance().getAllLibraryBooks());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
