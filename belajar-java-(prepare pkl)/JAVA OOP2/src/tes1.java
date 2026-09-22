public class tes1 {

    String nama;
    String nim;

    public void setNama (String nama) {
        this.nama = nama;
    }

    public String getNama () {
        return nama;
    }

    public void setNim (String nim) {
        this.nim = nim;
    }

    public String getNim () {
        return nim;
    }


    static void main(String[] args) {
        var introduction = new tes1();
        introduction.setNama("Gerald");
        introduction.setNim("12345");

        System.out.println(introduction.getNama());
        System.out.println(introduction.getNim());


    }

}
