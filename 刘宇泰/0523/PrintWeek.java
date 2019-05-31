import java.util.Scanner;
public class PrintWeek{
    public static void main(String[] args){
        Scanner in =new Scanner(System.in);
        int x=in.nextInt();
        if(x==1){
            System.out.println("今天是星期一");
        } else if(x==2){
            System.out.println("今天是星期二");
        } else if(x==3){
            System.out.println("今天是星期三");
        } else if(x==4){
            System.out.println("今天是星期四");
        } else if(x==5){
            System.out.println("今天是星期五");
        } else if(x==6){
            System.out.println("今天是星期六");
        } else if(x==7){
            System.out.println("今天是星期天");
        }
    }
}