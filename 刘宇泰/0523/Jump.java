public class Jump{
    public static void main(String[] args){
        for(int i = 1; i <= 10; i++){
            System.out.println("ѭ����"+i+"��");
            if(0 == i % 3){
                break;
            }
            if(0 == i % 5){
                System.out.println("�ҽ����ˣ�");
            }
        }
        for(int i = 1; i <= 10; i++){
            if(0 == i % 2)
                continue;
            System.out.println(i);
        }
    }
}