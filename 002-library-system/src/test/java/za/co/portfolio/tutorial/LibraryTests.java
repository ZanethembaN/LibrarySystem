package za.co.portfolio.tutorial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.io.Io;
import za.co.wethinkcode.io.StandardIo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTests {
    private Library library;
    private Io io;

    @BeforeEach
    public void setUp() {
        io = new StandardIo();
        library = new Library(io);
    }

    @Test
    public void testAddBook() {
        assertEquals("Book added successfully!", library.addBook("1984", "George Orwell"));
        List<Book> books = library.viewAllBooks();
        assertEquals(1, books.size());
        assertEquals("1984", books.get(0).getTitle());
        assertEquals("George Orwell", books.get(0).getAuthor());
    }

    @Test
    public void testRegisterMember() {
        assertEquals("Member registered successfully!", library.registerMember("Bob", 1));
        List<Member> members = library.viewAllMembers();
        assertEquals(1, members.size());
        assertEquals("Bob", members.get(0).getName());
        assertEquals(1, members.get(0).getId());
    }

    @Test
    public void testLendBook() {
        library.addBook("1984", "George Orwell");
        library.registerMember("Bob", 1);

        assertEquals("Book lent successfully!", library.lendBook(1, "1984"));

        List<Member> members = library.viewAllMembers();
        Member member = members.get(0);
        assertEquals(1, member.getBorrowedBooks().size());
        assertEquals("1984", member.getBorrowedBooks().get(0).getTitle());

        List<Book> books = library.viewAllBooks();
        Book book = books.get(0);
        assertTrue(book.checkout());
    }

    @Test
    public void testLendBookMemberNotFound() {
        library.addBook("1984", "George Orwell");
        assertEquals("Member not found!", library.lendBook(1, "1984"));
    }

    @Test
    public void testLendBookBookNotFound() {
        library.registerMember("Bob", 1);
        assertEquals("Book not found!", library.lendBook(1, "1984"));
    }

    @Test
    public void testAcceptReturnedBook() {
        library.addBook("1984", "George Orwell");
        library.registerMember("Bob", 1);

        library.lendBook(1, "1984");
        assertEquals("Book returned successfully!", library.acceptReturnedBook(1, "1984"));

        List<Member> members = library.viewAllMembers();
        Member member = members.get(0);
        assertEquals(0, member.getBorrowedBooks().size());

        List<Book> books = library.viewAllBooks();
        Book book = books.get(0);
        assertFalse(book.checkout());
    }

    @Test
    public void testAcceptReturnedBookMemberNotFound() {
        library.addBook("1984", "George Orwell");
        assertEquals("Member not found!", library.acceptReturnedBook(1, "1984"));
    }

    @Test
    public void testAcceptReturnedBookBookNotFound() {
        library.registerMember("Bob", 1);
        assertEquals("Book not found!", library.acceptReturnedBook(1, "1984"));
    }

    @Test
    public void testViewAllBooks() {
        library.addBook("1984", "George Orwell");
        List<Book> books = library.viewAllBooks();
        assertEquals(1, books.size());
        assertEquals("1984", books.get(0).getTitle());
        assertEquals("George Orwell", books.get(0).getAuthor());
    }

    @Test
    public void testViewAllMembers() {
        library.registerMember("Bob", 1);
        List<Member> members = library.viewAllMembers();
        assertEquals(1, members.size());
        assertEquals("Bob", members.get(0).getName());
        assertEquals(1, members.get(0).getId());
    }

    @Test
    public void testRegisterDuplicateMember() {
        library.registerMember("Bob", 1);
        assertEquals("Member registered successfully!", library.registerMember("Alice", 2));
        assertEquals("Member registered successfully!", library.registerMember("Charlie", 3));

        // Attempt to register a member with an existing ID
        assertEquals("Member with ID 1 already exists!", library.registerMember("David", 1));

        List<Member> members = library.viewAllMembers();
        assertEquals(3, members.size());
    }

    @Test
    public void testLendBookNotAvailable() {
        library.addBook("1984", "George Orwell");
        library.registerMember("Bob", 1);

        library.lendBook(1, "1984");
        assertEquals("Book is not available for lending.", library.lendBook(1, "1984"));

        List<Member> members = library.viewAllMembers();
        Member member = members.get(0);
        assertEquals(1, member.getBorrowedBooks().size());

        List<Book> books = library.viewAllBooks();
        Book book = books.get(0);
        assertTrue(book.checkout());  // Book should still be checked out
    }

    @Test
    public void testAcceptBookNotBorrowed() {
        library.addBook("1984", "George Orwell");
        library.registerMember("Bob", 1);

        assertEquals("Cannot accept returned book: Either book is already available or not borrowed by member.", library.acceptReturnedBook(1, "1984"));
    }
}
