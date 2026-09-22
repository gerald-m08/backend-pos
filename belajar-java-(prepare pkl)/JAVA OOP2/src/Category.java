public class Category {

    private String id;
    private boolean expensive;

    public String getId (){
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public boolean isExpensive () {
        return expensive;
    }

    public void setExpensive (boolean expensive) {
        this.expensive = expensive;
    }


    static void main(String[] args) {
        var category = new Category ();
        category.setId("1");
        System.out.println(category.getId());

        var category2 = new Category ();
        category.setExpensive(true);
        System.out.println(category.isExpensive());

    }

}
