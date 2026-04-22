package bean;  
// Khai báo package để tổ chức các class theo nhóm (ở đây là nhóm 'bean')

import java.io.Serializable;  
// Import Serializable để cho phép đối tượng ClassNum có thể được ghi/đọc (ví dụ: lưu session)

public class ClassNum implements Serializable {  
    // Khai báo class ClassNum, implements Serializable để có thể tuần tự hóa (serialize)

    private String class_num;  
    // Thuộc tính lưu mã lớp (class number)

    private School school;  
    // Thuộc tính lưu thông tin trường (School là một class khác)

    public School getSchool() {  
        // Getter: trả về đối tượng School hiện tại
        return school;
    }

    public void setSchool(School school) {  
        // Setter: gán giá trị mới cho thuộc tính school
        this.school = school;
    }

    public String getClass_num() {  
        // Getter: trả về giá trị mã lớp
        return class_num;
    }

    public void setClass_num(String class_num) {  
        // Setter: gán giá trị mới cho mã lớp
        this.class_num = class_num;
    }

}
