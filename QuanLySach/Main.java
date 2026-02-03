import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        QuanLySach ql = new QuanLySach(); 
        int chon = 0;

        do {
            
            System.out.println("\n=== MENU QUAN LY SACH (huytv) ===");
            System.out.println("1. Them 1 cuon sach");
            System.out.println("2. Xoa 1 cuon sach");
            System.out.println("3. Thay doi thong tin sach");
            System.out.println("4. Xuat thong tin tat ca sach");
            System.out.println("5. Tim sach co tua de 'Lap trinh'");
            System.out.println("6. Lay toi da K sach co gia <= P");
            System.out.println("7. Tim sach theo danh sach tac gia");
            System.out.println("0. Thoat");
            System.out.print(">> Moi ban chon: ");
            
            try {
                chon = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                chon = -1; 
            }

            switch (chon) {
                case 1: ql.themSach(); break;
                case 2: ql.xoaSach(); break;
                case 3: ql.suaSach(); break;
                case 4: ql.xuatDanhSach(); break;
                case 5: ql.timSachLapTrinh(); break;
                case 6: ql.locSachTheoGia(); break;
                case 7: ql.timTheoDSTacGia(); break;
                case 0: System.out.println("Da thoat chuong trinh!"); break;
                default: System.out.println("Vui long chon tu 0 den 7!");
            }
        } while (chon != 0);
    }
}