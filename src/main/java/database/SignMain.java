package database;

import java.util.Scanner;

public class SignMain {
    private Scanner sc = new Scanner(System.in);
    private Sign sign = new Sign();
    private ConnectDB con = new ConnectDB();
    public static void main(String[] args) {
        SignMain main = new SignMain();
        main.userCreate();
    }

    public void userCreate() {
        System.out.println();
        System.out.println("[user 추가]");
        System.out.println("==============");

        // 새 user를 추가하기 위한 정보 입력받기
        UserDTO insertDto = new UserDTO();
        System.out.print("▶ 아이디 : ");
        insertDto.setUserid(sc.nextLine());
        System.out.print("▶ 비밀번호 : ");
        insertDto.setPassword(sc.nextLine());
        System.out.print("▶ 닉네임 : ");
        insertDto.setDisplayname(sc.nextLine());
        System.out.print("▶ 이름 : ");
        insertDto.setName(sc.nextLine());

        System.out.println();

        int row = sign.insert(con, insertDto);
        System.out.println("====================");

        if (row > 0) {
            System.out.printf("%s 님 등록완료\n", insertDto.getName());
        } else {
            System.out.printf("%s 님 등록실패\n", insertDto.getName());
        }
    }
}
