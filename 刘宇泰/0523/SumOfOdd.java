public class SumOfOdd {
    public static void main(String[] args){
        int sum = 0;
        for(int i = 1; i <= 1000; i++){
            if(0 == i % 2){
                sum += i;
            }
        }
        System.out.println("��for��1��1000�У�����ż����Ϊ��"+sum);
    }
}