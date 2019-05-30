import java.util.Scanner;

public class InputTest {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int i = 0;
        String [] str = new String [100];
        while (!in.hasNext("end")) {
            String s = in.nextLine();
            str[i] = s;
            i++;
        }
        int j=i;
        for(i = 0; i < j; i++){
            System.out.println(str[i]);
        }
        in.close();
    }
}