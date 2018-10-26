package com.juja.oop;

/**
 * Необходимо реализовать метод toPrint()
 * для класса Book, который выводит на печать
 * информацию про книгу в следующем формате
 * Book{name=<name>,countPages=<countPages>,author=<authorBook>}
 */
public class Issue {
    private String name;
    private int countPages;

    public Issue(String name, int countPages) {
        this.name = name;
        this.countPages = countPages;
    }

    public String toPrint() {
        return String.format("Book{name=%s,countPages=%s", name, countPages);
    }

}

class Book extends Issue {
    private String authorBook;

    public Book(String name, int countPages, String authorBook) {
        super(name, countPages);
        this.authorBook = authorBook;
    }

    @Override
    public String toPrint() {
        return String.format("%s,author=%s}", super.toPrint(), authorBook);
    }

    public static void main(String[] args) {
        String testNameBook = "TestNameBook";
        String testAuthorBook = "TestBookAuthor";
        int countPages = 100;
        String expectedBookPrint = "Book{name=TestNameBook,countPages=100,author=TestBookAuthor}";

        Issue book = new Book(testNameBook, countPages, testAuthorBook);

        String actualBookPrint = book.toPrint();

        //check
        if (actualBookPrint == null)
            throw new AssertionError("Result cannot be null");

        if (actualBookPrint.equals(expectedBookPrint))
            System.out.print("OK");
        else
            throw new AssertionError("Expected " + expectedBookPrint + " but found " + actualBookPrint);

    }
}