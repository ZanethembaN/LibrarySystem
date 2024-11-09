package za.co.portfolio.tutorial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import za.co.wethinkcode.io.Io;
import za.co.wethinkcode.io.StandardIo;

/**
 * The Library class represents a library system that manages books and members.
 * It provides functionality to add books, register members, lend books, accept returned books,
 * and view all books and members.
 */
public class Library {
    public List<Book> books;
    public List<Member> members;
    private final Io io;

    /**
     * Constructs a Library object with default input/output handling.
     */
    public Library() {
        this(new StandardIo());
    }

    /**
     * Constructs a Library object with specified input/output handling.
     *
     * @param io the input/output handler
     */
    public Library(Io io) {
        if (io == null) throw new RuntimeException();
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.io = io;
    }

    /**
     * Runs the library system, displaying a menu and handling user input.
     */
    public void run() {
        io.println("Welcome to the Library System!");

        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = io.anyInteger("Enter your choice: ");
            exit = handleChoice(choice);
        }
    }



    /**
     * Handles the user's menu choice.
     *
     * @param choice the user's choice
     * @return true if the user chose to exit, false otherwise
     */
    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                handleAddBook();
                break;
            case 2:
                handleRegisterMember();
                break;
            case 3:
                handleLendBook();
                break;
            case 4:
                handleAcceptReturnedBook();
                break;
            case 5:
                viewAllBooks();
                break;
            case 6:
                viewAllMembers();
                break;
            case 0:
                io.println("Exiting the Library System. Goodbye!");
                return true;
            default:
                io.println("Invalid choice. Please enter a valid option.");
        }
        return false;
    }

    /**
     * Handles the process of adding a book.
     */
    public void handleAddBook() {
        String bookTitle = io.anyString("Enter book title: ");
        String bookAuthor = io.anyString("Enter book author: ");
        io.println(addBook(bookTitle, bookAuthor));

    }

    /**
     * Handles the process of registering a member.
     */
    void handleRegisterMember() {
        String memberName = io.anyString("Enter member name: ");
        int memberId = io.anyInteger("Enter member ID: ");
        io.println(registerMember(memberName, memberId));
    }

    /**
     * Handles the process of lending a book.
     */
    void handleLendBook() {
        int memberId = io.anyInteger("Enter member ID: ");
        String bookTitle = io.anyString("Enter book title: ");
        io.println(lendBook(memberId, bookTitle));
    }

    /**
     * Handles the process of accepting a returned book.
     */
    void handleAcceptReturnedBook() {
        int memberId = io.anyInteger("Enter member ID: ");
        String bookTitle = io.anyString("Enter book title: ");
        io.println(acceptReturnedBook(memberId, bookTitle));
    }

    /**
     * Displays the menu options to the user.
     */
    private void displayMenu() {
        io.println("\nMenu:");
        io.println("1. Add a book");
        io.println("2. Register a member");
        io.println("3. Lend a book");
        io.println("4. Accept returned book");
        io.println("5. View all books");
        io.println("6. View all members");
        io.println("0. Exit");
    }

    /**
     * Adds a book to the library.
     *
     * @param bookTitle  the title of the book
     * @param bookAuthor the author of the book
     * @return a message indicating the book was added successfully
     */
    public String addBook(String bookTitle, String bookAuthor) {
        Book book = new Book(bookTitle, bookAuthor);
        books.add(book);
        return "Book added successfully!";
    }

    /**
     * Registers a member in the library.
     *
     * @param memberName the name of the member
     * @param memberId   the ID of the member
     * @return a message indicating the member was registered successfully
     */
    public String registerMember(String memberName, int memberId) {
        for (Member member : members) {
            if (member.getId() == memberId) {
                return "Member with ID " + memberId + " already exists!";
            }
        }
        Member member = new Member(memberName, memberId);
        members.add(member);
        return "Member registered successfully!";
    }


    /**
     * Lends a book to a member.
     *
     * @param memberId  the ID of the member
     * @param bookTitle the title of the book
     * @return a message indicating whether the book was lent successfully or not
     */
    public String lendBook(int memberId, String bookTitle) {
        Member member = findMemberById(memberId);
        if (member == null) {
            return "Member not found!";
        }

        Book book = findBookByTitle(bookTitle);
        if (book == null) {
            return "Book not found!";
        }

        return lendBook(book, member);
    }

    /**
     * Lends a book to a member.
     *
     * @param book   the book to be lent
     * @param member the member borrowing the book
     * @return a message indicating whether the book was lent successfully or not
     */
    private String lendBook(Book book, Member member) {
        if (!book.checkout()) {
            member.borrowBook(book);
            book.setCheckout(true);
            return "Book lent successfully!";
        } else {
            return "Book is not available for lending.";
        }
    }

    /**
     * Accepts a returned book from a member.
     *
     * @param memberId  the ID of the member
     * @param bookTitle the title of the book
     * @return a message indicating whether the book was returned successfully or not
     */
    public String acceptReturnedBook(int memberId, String bookTitle) {
        Member member = findMemberById(memberId);
        if (member == null) {
            return "Member not found!";
        }

        Book book = findBookByTitle(bookTitle);
        if (book == null) {
            return "Book not found!";
        }

        return acceptReturnedBook(book, member);
    }

    /**
     * Accepts a returned book from a member.
     *
     * @param book   the book to be returned
     * @param member the member returning the book
     * @return a message indicating whether the book was returned successfully or not
     */
    private String acceptReturnedBook(Book book, Member member) {
        if (book.checkout() && member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.setCheckout(false);
            return "Book returned successfully!";
        } else {
            return "Cannot accept returned book: Either book is already available or not borrowed by member.";
        }
    }

    /**
     * Displays all books in the library.
     *
     * @return
     */
    public List<Book> viewAllBooks() {
        io.println("\nAll Books:");
        for (Book book : books) {
            io.println(book.getTitle() + " by " + book.getAuthor() + " - Available: " + (book.isAvailable() ? "Yes" : "No"));
        }
        return Collections.unmodifiableList(books);
    }

    /**
     * Displays all members of the library.
     *
     * @return
     */
    public List<Member> viewAllMembers() {
        io.println("\nAll Members:");
        for (Member member : members) {
            io.println(member.getName() + " (ID: " + member.getId() + ")");
        }
        return Collections.unmodifiableList(members);
    }

    /**
     * Finds a member by their ID.
     *
     * @param id the ID of the member
     * @return the member with the specified ID, or null if not found
     */
    public Member findMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    /**
     * Finds a book by its title.
     *
     * @param title the title of the book
     * @return the book with the specified title, or null if not found
     */
    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    /**
     * The main method to run the library system.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Library library = new Library();
        library.run();
    }
}
