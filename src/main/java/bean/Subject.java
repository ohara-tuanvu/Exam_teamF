package bean;

// Nhóm class vào package 'bean' – nơi chứa các lớp dữ liệu (JavaBean)
// データ用クラスをまとめるためのパッケージ宣言（bean グループ）

import java.io.Serializable;

// Cho phép đối tượng Subject được tuần tự hóa (serialize) – cần khi lưu session hoặc truyền dữ liệu
// Subject オブジェクトを保存・読み込みできるようにするための Serializable をインポート

public class Subject implements Serializable {
    private String cd;       // Mã môn học (CD)
                            // 科目コード（CD）

    private String name;     // Tên môn học (NAME)
                            // 科目名（NAME）

    private School school;   // Đối tượng chứa SCHOOL_CD
                            // SCHOOL_CD を保持する School オブジェクト

    public Subject() {}
    // Constructor mặc định
    // デフォルトコンストラクタ

    public String getCd() { 
        // Trả về mã môn học
        // 科目コードを返す
        return cd; 
    }

    public void setCd(String cd) { 
        // Gán mã môn học
        // 科目コードをセットする
        this.cd = cd; 
    }

    public String getName() { 
        // Trả về tên môn học
        // 科目名を返す
        return name; 
    }

    public void setName(String name) { 
        // Gán tên môn học
        // 科目名をセットする
        this.name = name; 
    }

    public School getSchool() { 
        // Trả về đối tượng School
        // School オブジェクトを返す
        return school; 
    }

    public void setSchool(School school) { 
        // Gán trường cho môn học
        // School オブジェクトをセットする
        this.school = school; 
    }
}
