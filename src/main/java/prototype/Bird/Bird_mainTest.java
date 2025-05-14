package prototype.Bird;

import java.util.Scanner;

public class Bird_mainTest {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BirdManagement management = new BirdManagement();
        
        System.out.println("포인트를 입력하세요 (0 입력 시 종료):");
        
        while (true) {
            System.out.print("포인트 입력: ");
            int input = scanner.nextInt();
            
            if (input == 0) {
                break;
            }
            
            management.addPoint(input);
            management.currentBird();
            
        }
        
    }

}
