package za.co.portfolio.tutorial;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    @Test
//    @Disabled
    public void testBookCreation() {
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald");
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertFalse(book.checkout());
    }


    @Test
//    @Disabled
    public void testInvalidBookCreationWithoutAuthor() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("The Great Gatsby", "");
        });
        assertEquals("Author cannot be empty", exception.getMessage());
    }

    @Test
//    @Disabled
    public void testInvalidBookCreationWithoutTitle() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "J.T Maphalla");
        });
        assertEquals("Title cannot be empty", exception.getMessage());
    }

    @Test
//    @Disabled
    public void testBookCheckout() {
        Book book = new Book("The Dube Train", "F. Scott");
        assertFalse(book.checkout());
        book.setCheckout(true);
        assertTrue(book.checkout());
    }
}
