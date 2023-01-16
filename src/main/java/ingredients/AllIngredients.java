package ingredients;

import java.util.List;

public class AllIngredients {
    private boolean success;
    private List<Data> data;

    public AllIngredients() {
    }

    public AllIngredients(boolean success, List<Data> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public List<Data> getData() {
        return data;
    }
    public void setData(List<Data> data) {
        this.data = data;
    }
}
