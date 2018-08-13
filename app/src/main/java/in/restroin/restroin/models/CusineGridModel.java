package in.restroin.restroin.models;

public class CusineGridModel {
    private String cusine_name, cusine_count, cusine_image;

    public CusineGridModel(String cusine_name, String cusine_count, String cusine_image) {
        this.cusine_name = cusine_name;
        this.cusine_count = cusine_count;
        this.cusine_image = cusine_image;
    }

    public String getCusine_name() {
        return cusine_name;
    }

    public void setCusine_name(String cusine_name) {
        this.cusine_name = cusine_name;
    }

    public String getCusine_count() {
        return cusine_count;
    }

    public void setCusine_count(String cusine_count) {
        this.cusine_count = cusine_count;
    }

    public String getCusine_image() {
        return cusine_image;
    }

    public void setCusine_image(String cusine_image) {
        this.cusine_image = cusine_image;
    }
}
