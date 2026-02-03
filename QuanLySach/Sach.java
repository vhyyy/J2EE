public class Sach {
    // Thuộc tính (Encapsulation)
    private int maSach;
    private String tenSach;
    private String tacGia;
    private double donGia;

    // Constructor (Hàm khởi tạo)
    public Sach(int maSach, String tenSach, String tacGia, double donGia) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.donGia = donGia;
    }

    // Getter và Setter
    public int getMaSach() { return maSach; }
    public void setMaSach(int maSach) { this.maSach = maSach; }

    public String getTenSach() { return tenSach; }
    public void setTenSach(String tenSach) { this.tenSach = tenSach; }

    public String getTacGia() { return tacGia; }
    public void setTacGia(String tacGia) { this.tacGia = tacGia; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }


    public void hienThi() {
        System.out.printf("| %-5d | %-25s | %-15s | %-10.2f |\n", maSach, tenSach, tacGia, donGia);
    }
}