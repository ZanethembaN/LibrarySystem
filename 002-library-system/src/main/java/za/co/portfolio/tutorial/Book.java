package za.co.portfolio.tutorial;

public class Book {

    private String bookTittle;
    private String bookAuthor;
    private boolean checkout;

    public Book(String bookTittle, String bookAuthor){

        if (bookTittle.isEmpty()){
            throw new IllegalArgumentException("Title cannot be empty");
        } else if (bookAuthor.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        this.bookTittle = bookTittle;
        this.bookAuthor = bookAuthor;
        this.checkout = false;
    }


    public void setCheckout(boolean checkout){
        this.checkout = checkout;
    }

    public String getTitle() {
        return bookTittle;
    }

    public String getAuthor(){
        return bookAuthor;
    }

    public boolean checkout(){
        return checkout;
    }

    public boolean isAvailable(){
        return checkout;
    }
}
