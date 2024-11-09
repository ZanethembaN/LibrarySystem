package za.co.portfolio.tutorial;


import java.util.ArrayList;
import java.util.List;

public class Member {

    private String memberName;
    private int memberID;
    private List<Book> takenBooks;

    public Member(String memberName, int memberID){
        this.memberName =memberName;
        this.memberID = memberID;
        this.takenBooks = new ArrayList<>();
    }

    public void setMemberName(String memberName){
        this.memberName = memberName;
    }

    public void setMemberID(int memberID){
        this.memberID = memberID;
    }

    public String getName(){
        return memberName;
    }

    public int getId(){
        return memberID;
    }

    public void borrowBook(Book book) {
        if(book.checkout() == false){
            takenBooks.add(book);
            book.setCheckout(true);
        }else{
            takenBooks.remove(book);
            book.setCheckout(true);
        }
    }

    public List<Book> getBorrowedBooks() {
        return takenBooks;
    }


    public void returnBook(Book book) {
        if (takenBooks.contains(book)) {
            takenBooks.remove(book);
            book.setCheckout(false);
        }
    }


}
