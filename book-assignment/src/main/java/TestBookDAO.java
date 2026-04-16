import java.sql.SQLException;

import database.BookDAO;
import models.Book;

public class TestBookDAO {
    public static void main(String[] args) {

        BookDAO dao = new BookDAO();
        
        try {
            System.out.println("Search results for 'Harry':");

            for (Book b : dao.searchbooks("Harry")) {
                System.out.println(
                    b.getId() + " - " +
                    b.getTitle() + " - " +
                    b.getAuthor()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}