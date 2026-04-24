package bean;  
// Khai báo package để tổ chức các class theo nhóm (ở đây là nhóm 'bean')
// クラスを分類するためのパッケージ宣言（bean グループ）

import java.io.Serializable;  
// Import Serializable để cho phép đối tượng ClassNum có thể được ghi/đọc (ví dụ: lưu session)
// オブジェクトを保存・読み込みできるようにするための Serializable をインポート

public class ClassNum implements Serializable {  
    // Khai báo class ClassNum, implements Serializable để có thể tuần tự hóa (serialize)
    // ClassNum クラスの宣言（シリアライズ可能）

    private String class_num;  
    // Thuộc tính lưu mã lớp (class number)
    // クラス番号を保持するフィールド

    private School school;  
    // Thuộc tính lưu thông tin trường (School là một class khác)
    // School クラスの情報を保持するフィールド

    public School getSchool() {  
        // Getter: trả về đối tượng School hiện tại
        // getter：現在の school の値を返す
        return school;
    }

    public void setSchool(School school) {  
        // Setter: gán giá trị mới cho thuộc tính school
        // setter：school に新しい値をセットする
        this.school = school;
    }

    public String getClass_num() {  
        // Getter: trả về giá trị mã lớp
        // getter：class_num の値を返す
        return class_num;
    }

    public void setClass_num(String class_num) {  
        // Setter: gán giá trị mới cho mã lớp
        // setter：class_num に新しい値をセットする
        this.class_num = class_num;
    }

}
