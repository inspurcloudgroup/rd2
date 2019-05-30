public class MaxAndMin{
    public static void main(String[] args) {
        int a[] = {313,89,123,323,313,15,90,56,39};
        int max = a[0];
        int min = a[0];
        for(int i = 0; i < 9; i++){
	if(max<a[i]){
	     max = a[i];
	}
	if(min>a[i]){
	     min = a[i];
	}
        }
        System.out.println(max+"*****"+min);
    }
}