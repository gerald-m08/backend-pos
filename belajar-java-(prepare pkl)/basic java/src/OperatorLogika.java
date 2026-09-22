public class OperatorLogika {
    static void main(String[] args) {

        /*
&& 	Logical and	Returns true if both statements are true	x < 5 &&  x < 10
|| 	Logical or	Returns true if one of the statements is true	x < 5 || x < 4
!	Logical not	Reverse the result, returns false if the result is true	!(x < 5 && x < 10)
 */



        //contoh nyata : pemeriksaan login

        boolean isLoggedIn = true;
        boolean isAdmin = false;

        System.out.println("Regular user: " + (isLoggedIn && !isAdmin)); //true
        System.out.println("Has access: " + (isLoggedIn || isAdmin));  //true
        System.out.println("Not logged in: " + (!isLoggedIn));  //false






    }
}
/*
Sama seperti operator perbandingan , Anda juga dapat menguji nilai " trueatau" false dengan operator logika.

Operator logika digunakan untuk menentukan logika antar variabel atau nilai, dengan menggabungkan beberapa kondisi.
 */

