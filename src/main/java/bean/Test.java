package bean;
// Nhóm class vào package 'bean' – nơi chứa các JavaBean dùng làm dữ liệu
// データ用クラス（JavaBean）をまとめるためのパッケージ宣言

public class Test {
    // Class Test đại diện cho một bản ghi điểm (test result)
    // Test クラスはテスト結果（成績データ）を表す

    private Student student;
    // Đối tượng Student – thông tin học sinh làm bài kiểm tra
    // Student オブジェクト（テストを受けた学生）

    private String classNum;
    // Mã lớp của học sinh
    // 学生のクラス番号

    private Subject subject;
    // Môn học của bài kiểm tra
    // テスト科目（Subject オブジェクト）

    private School school;
    // Trường mà học sinh thuộc về
    // 学生が所属している学校（School オブジェクト）

    private int no;
    // Số thứ tự bài kiểm tra (test_no)
    // テスト番号（test_no）

    private int point;
    // Điểm số của bài kiểm tra
    // テストの点数

    public Student getStudent() { 
        // Trả về đối tượng Student
        // Student オブジェクトを返す
        return student; 
    }

    public void setStudent(Student student) { 
        // Gán đối tượng Student
        // Student オブジェクトをセットする
        this.student = student; 
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

    public Subject getSubject() { 
        // Trả về môn học
        // 科目（Subject）を返す
        return subject; 
    }

    public void setSubject(Subject subject) { 
        // Gán môn học
        // 科目（Subject）をセットする
        this.subject = subject; 
    }

    public School getSchool() { 
        // Trả về trường
        // 学校（School）を返す
        return school; 
    }

    public void setSchool(School school) { 
        // Gán trường
        // 学校（School）をセットする
        this.school = school; 
    }

    public int getNo() { 
        // Trả về số thứ tự bài kiểm tra
        // テスト番号を返す
        return no; 
    }

    public void setNo(int no) { 
        // Gán số thứ tự bài kiểm tra
        // テスト番号をセットする
        this.no = no; 
    }

    public int getPoint() { 
        // Trả về điểm số
        // 点数を返す
        return point; 
    }

    public void setPoint(int point) { 
        // Gán điểm số
        // 点数をセットする
        this.point = point; 
    }
}
