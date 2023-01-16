package orders;

import java.util.List;

public class CreatingOrders {
    private List<String> ingredients;

    public CreatingOrders() {
    }
    public CreatingOrders(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
