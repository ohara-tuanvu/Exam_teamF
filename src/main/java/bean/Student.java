package bean;
// Nhóm class vào package 'bean' – nơi chứa các lớp dữ liệu (JavaBean)
// データ用クラスをまとめるためのパッケージ宣言（bean グループ）

import java.io.Serializable;
// Cho phép đối tượng Student được tuần tự hóa (serialize) – cần khi lưu session hoặc truyền dữ liệu
// Student オブジェクトを保存・読み込みできるようにするための Serializable をインポート

public class Student implements Serializable {
    // Class Student đại diện cho thông tin một học sinh
    // Student クラスは学生情報を表す

    private String no;
    // Mã số học sinh (student number)
    // 学生番号

    private String name;
    // Tên học sinh
    // 氏名

    private int entYear;
    // Năm nhập học (entry year)
    // 入学年度

    private String classNum;
    // Mã lớp mà học sinh thuộc về
    // 所属クラス番号

    private boolean isAttend;
    // Trạng thái đi học (true = đang theo học, false = nghỉ học)
    // 在籍状態（true＝在籍、false＝退学）

    private School school;
    // Trường mà học sinh thuộc về (đối tượng School)
// 所属している学校（School オブジェクト）

    public String getNo() {
        // Trả về mã số học sinh
        // 学生番号を返す
        return no;
    }

    public void setNo(String no) {
        // Gán mã số học sinh
        // 学生番号をセットする
        this.no = no;
    }

    public String getName() {
        // Trả về tên học sinh
        // 氏名を返す
        return name;
    }

    public void setName(String name) {
        // Gán tên học sinh
        // 氏名をセットする
        this.name = name;
    }

    public int getEntYear() {
        // Trả về năm nhập học
        // 入学年度を返す
        return entYear;
    }

    public void setEntYear(int entYear) {
        // Gán năm nhập học
        // 入学年度をセットする
        this.entYear = entYear;
    }

    public String getClassNum() {
        // Trả về mã lớp
        // クラス番号を返す
        return classNum;
    }

    public void setClassNum(String classNum) {
        // Gán mã lớp
        // クラス番号をセットする
        this.classNum = classNum;
    }

    public boolean isAttend() {
        // Trả về trạng thái đi học (true/false)
        // 在籍状態（true/false）を返す
        return isAttend;
    }

    public void setAttend(boolean isAttend) {
        // Gán trạng thái đi học
        // 在籍状態をセットする
        this.isAttend = isAttend;
    }

    public School getSchool() {
        // Trả về đối tượng School của học sinh
        // 所属学校（School オブジェクト）を返す
        return school;
    }

    public void setSchool(School school) {
        // Gán trường học cho học sinh
        // 所属学校をセットする
        this.school = school;
    }
}
