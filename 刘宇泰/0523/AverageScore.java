public class AverageScore{
    public static void main(String[] args) {
        int a[] = {61,57,95,85,75,65,44,66,90,32};
        int score = 0;
        for(int i = 0; i < 10; i++){
	score+=a[i];
        }
        System.out.println(score/10);
    }
}