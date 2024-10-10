package javainuse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Products {
    private List<Product> product = new ArrayList<Product>();
    public List<Product> getProduct() {
        return product;
    }
    public void setProduct(List<Product> product) {
        this.product = product;
    }
}