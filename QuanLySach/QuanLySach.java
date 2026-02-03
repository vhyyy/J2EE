import java.util.ArrayList;
import java.util.Scanner;

public class QuanLySach {
    private ArrayList<Sach> list = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void themSach() {
        System.out.println("--- THEM SACH MOI ---");
        System.out.print("Nhap ma sach: ");
        int ma = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap ten sach: ");
        String ten = sc.nextLine();
        System.out.print("Nhap tac gia: ");
        String tg = sc.nextLine();
        System.out.print("Nhap don gia: ");
        double gia = Double.parseDouble(sc.nextLine());

        Sach s = new Sach(ma, ten, tg, gia);
        list.add(s);
        System.out.println(">> Da them thanh cong!");
    }

    public void xoaSach() {
        System.out.print("Nhap ma sach can xoa: ");
        int ma = Integer.parseInt(sc.nextLine());
        boolean isRemoved = list.removeIf(s -> s.getMaSach() == ma);
        if (isRemoved) {
            System.out.println(">> Da xoa thanh cong!");
        } else {
            System.out.println(">> Khong tim thay ma sach nay!");
        }
    }

    public void suaSach() {
        System.out.print("Nhap ma sach can sua: ");
        int ma = Integer.parseInt(sc.nextLine());
        boolean found = false;
        for (Sach s : list) {
            if (s.getMaSach() == ma) {
                System.out.print("Nhap ten moi: ");
                s.setTenSach(sc.nextLine());
                System.out.print("Nhap tac gia moi: ");
                s.setTacGia(sc.nextLine());
                System.out.print("Nhap gia moi: ");
                s.setDonGia(Double.parseDouble(sc.nextLine()));
                System.out.println(">> Cap nhat thanh cong!");
                found = true;
                break;
            }
        }
        if (!found) System.out.println(">> Khong tim thay ma sach!");
    }


    public void xuatDanhSach() {
        System.out.println("\n--- DANH SACH SACH ---");
        // Sửa tiêu đề cột thành không dấu
        System.out.printf("| %-5s | %-25s | %-15s | %-10s |\n", "Ma", "Ten Sach", "Tac Gia", "Gia");
        for (Sach s : list) {
            s.hienThi();
        }
    }

    public void timSachLapTrinh() {
        System.out.println("\n--- KET QUA TIM 'Lap trinh' ---");
        boolean found = false;
        for (Sach s : list) {
            String tenSach = s.getTenSach().toLowerCase();
            if (tenSach.contains("lập trình") || tenSach.contains("lap trinh")) {
                s.hienThi();
                found = true;
            }
        }
        if (!found) System.out.println("Khong co cuon nao.");
    }

    public void locSachTheoGia() {
        System.out.print("Nhap so luong K can lay: ");
        int k = Integer.parseInt(sc.nextLine());
        System.out.print("Nhap muc gia tran P: ");
        double p = Double.parseDouble(sc.nextLine());

        System.out.println("\n--- DANH SACH THOA MAN ---");
        int count = 0;
        for (Sach s : list) {
            if (s.getDonGia() <= p) {
                s.hienThi();
                count++;
                if (count == k) break; 
            }
        }
        if (count == 0) System.out.println("Khong co sach nao thoa man gia <= " + p);
    }

    public void timTheoDSTacGia() {
        System.out.print("Nhap cac tac gia (cach nhau dau phay, vd: Huy,Lan): ");
        String input = sc.nextLine();
        String[] arrTacGia = input.split(",");

        System.out.println("\n--- KET QUA TIM THEO TAC GIA ---");
        boolean found = false;
        for (Sach s : list) {
            for (String tg : arrTacGia) {
                if (s.getTacGia().trim().equalsIgnoreCase(tg.trim())) {
                    s.hienThi();
                    found = true;
                    break; 
                }
            }
        }
        if (!found) System.out.println("Khong tim thay sach cua cac tac gia nay.");
    }
}