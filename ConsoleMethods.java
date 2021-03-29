import java.util.Scanner;

public class ConsoleMethods {
    Scanner sc = new Scanner(System.in);

    public String Read(String s){
        System.out.println("\n" + s);
        String data = sc.nextLine();

        return data;
    }
}
