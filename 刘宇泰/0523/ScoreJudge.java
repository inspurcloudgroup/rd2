public class ScoreJudge {
    public static void main(String[] args){
        int score = 78;
        if(score >= 60){
            if(score >= 80){
                if(score >= 90){
                    System.out.println("�ɼ�����");
                }
                else{
                    System.out.println("�ɼ�����");
                }
            }
            else{
                System.out.println("�ɼ�����");
            }
        }
        else{
            System.out.println("��Ҫ����");
        }
    }
}