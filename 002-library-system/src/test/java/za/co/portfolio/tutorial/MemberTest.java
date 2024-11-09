package za.co.portfolio.tutorial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {
    private Member member;
    private Book book;

    @BeforeEach
    public void setUp() {
        member = new Member("Alice Smith", 123);
        book = new Book("The Great Gatsby", "F. Scott Fitzgerald");
    }

    @Test
//    @Disabled
    public void testGetNameAndId() {
        assertEquals("Alice Smith", member.getName());
        assertEquals(123, member.getId());
    }

    @Test
//    @Disabled
    public void testBorrowBookSuccess() {
        assertFalse(book.checkout());
        member.borrowBook(book);
        assertTrue(book.checkout());
        assertEquals("The Great Gatsby", member.getBorrowedBooks().get(0).getTitle());
    }

    @Test
//    @Disabled
    public void testBorrowBookAlreadyBorrowed() {
        book.setCheckout(true);
        member.borrowBook(book);
        assertEquals(0, member.getBorrowedBooks().size());
    }

    @Test
//    @Disabled
    public void testReturnBookSuccess() {
        assertFalse(book.checkout());
        member.borrowBook(book);
        assertTrue(book.checkout());
        member.returnBook(book);
        assertFalse(book.checkout());
        assertEquals(0, member.getBorrowedBooks().size());
    }

    @Test
//    @Disabled
    public void testReturnBookNotBorrowed() {
        Book anotherBook = new Book("Moby Dick", "Herman Melville");
        member.borrowBook(anotherBook);
        assertTrue(anotherBook.checkout());

        member.returnBook(book);
        assertFalse(book.checkout());
        assertEquals(1, member.getBorrowedBooks().size());
        assertEquals("Moby Dick", member.getBorrowedBooks().get(0).getTitle());
    }
}
