package models;

import java.util.ArrayList;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "books")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookList {

    @XmlElement(name = "book")
    private ArrayList<Book> books;

    public BookList() {}

    public BookList(ArrayList<Book> books) {
        this.books = books;
    }
}