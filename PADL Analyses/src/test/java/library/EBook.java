package library;

public class EBook extends Book {
    public int previewPages() {
        return super.pages() / 10;
    }
}
