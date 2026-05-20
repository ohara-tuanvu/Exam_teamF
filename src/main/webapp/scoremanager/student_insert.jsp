<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%-- VN: Khai báo trang JSP sử dụng Java, nội dung HTML với charset UTF-8, và file được lưu UTF-8
    JP: JSPページの言語をJava、コンテンツタイプをHTML・UTF-8、ファイル自体のエンコードもUTF-8に指定 --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%-- VN: Khai báo sử dụng JSTL core với prefix là "c"
    JP: JSTLコアタグライブラリを"c"プレフィックスで利用する宣言 --%>

<c:import url="/common/base.jsp">
<%-- VN: Import template chung base.jsp để dùng layout (header, menu, footer, background)
    JP: 共通レイアウト（ヘッダー・メニュー・フッター・背景）用のbase.jspを読み込む --%>

    <c:param name="title">学生新規登録 - 得点管理システム</c:param>
    <%-- VN: Truyền tham số "title" cho base.jsp để hiển thị tiêu đề trang
        JP: base.jspにページタイトルとして渡す"title"パラメータを設定 --%>

    <c:param name="content">
    <%-- VN: Bắt đầu truyền nội dung chính của trang vào param "content" cho base.jsp
        JP: ページ本体のコンテンツを"content"パラメータとしてbase.jspに渡し始める --%>

        <style>
            .glass-block {
                background: rgba(255,255,255,0.65);
                backdrop-filter: blur(6px);
                border: 1px solid #e5e5e5;
                border-radius: 8px;
                padding: 25px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
            }
            /* VN: Khối form kiểu "glass" với nền trắng mờ, bo góc, đổ bóng nhẹ
               JP: 半透明の白背景と角丸・薄いシャドウを持つガラス風フォームブロック */

            .glass-success {
                background: rgba(200,255,200,0.7);
                backdrop-filter: blur(4px);
                border: 1px solid #b2e5b2;
                padding: 12px;
                border-radius: 6px;
                font-weight: 600;
            }
            /* VN: Khối thông báo thành công với nền xanh nhạt mờ, bo góc, chữ đậm
               JP: 成功メッセージ用の淡い緑色半透明ボックス、角丸・太字テキスト */

            .input-error {
                color: red;
                font-size: 0.9rem;
                margin-top: 4px;
            }
            /* VN: Style cho thông báo lỗi input: chữ đỏ, nhỏ hơn, cách trên một chút
               JP: 入力エラー表示用スタイル：赤文字、小さめフォント、上に少し余白を付与 */
        </style>

        <section class="me-4">
        <%-- VN: Khu vực nội dung chính, có margin-end để cách lề phải
            JP: メインコンテンツ領域。右側に余白（me-4）を付けるセクション --%>

            <c:if test="${not empty message}">
            <%-- VN: Nếu tồn tại biến message (không rỗng) thì hiển thị thông báo hoàn tất
                JP: messageが空でない場合（登録完了などのメッセージがある場合）に表示 --%>

                <div class="glass-success mx-5 mt-2">
                    ${message}
                </div>
                <%-- VN: Hiển thị nội dung message trong khối thông báo thành công, có margin ngang và trên
                    JP: 成功メッセージをガラス風ボックスで表示し、左右と上にマージンを付ける --%>

                <div class="mt-3 text-center">
                    <a href="StudentList.action" class="btn btn-secondary w-25">戻る</a>
                </div>
                <%-- VN: Nút quay lại danh sách học sinh, căn giữa, rộng 25%
                    JP: 学生一覧画面へ戻るボタン。中央寄せで幅25%のセカンダリボタン --%>

            </c:if>

            <c:if test="${empty message}">
            <%-- VN: Nếu chưa có message (chưa đăng ký xong) thì hiển thị form nhập
                JP: messageが空の場合（登録完了していない場合）に入力フォームを表示 --%>

                <form method="post" class="glass-block mx-5">
                <%-- VN: Form POST đăng ký học sinh, dùng style glass-block, có margin ngang
                    JP: 学生登録用のPOSTフォーム。ガラス風スタイルと左右マージンを適用 --%>

                    <div class="form-floating mt-3">
                    <%-- VN: Khối input kiểu form-floating (label nổi), có margin-top
                        JP: フローティングラベル形式の入力ブロック。上にマージンを付与 --%>

                        <select class="form-select" id="entYear" name="entYear">
                        <%-- VN: Select chọn năm nhập học, gắn id và name là entYear
                            JP: 入学年度選択用のセレクトボックス。id/nameはentYear --%>

                            <option value=""></option>
                            <%-- VN: Option rỗng để cho phép trạng thái chưa chọn
                                JP: 未選択状態を表す空のオプション --%>

                            <c:forEach var="year" items="${ent_year_set}">
                            <%-- VN: Lặp qua danh sách năm nhập học được set trong request (ent_year_set)
                                JP: リクエスト属性ent_year_setに格納された入学年度リストをループ --%>

                                <option value="${year}"
                                    <c:if test="${year == entYear}">selected</c:if>>
                                    ${year}
                                </option>
                                <%-- VN: Tạo option cho từng năm, nếu trùng với entYear đã nhập trước đó thì selected
                                    JP: 各年度のオプションを生成し、前回入力値entYearと一致する場合はselectedを付与 --%>

                            </c:forEach>
                        </select>

                        <label>入学年度</label>
                        <%-- VN: Nhãn hiển thị "入学年度" cho select
                            JP: セレクトボックス用のラベル「入学年度」 --%>

                        <c:if test="${not empty errors.entYear}">
                            <div class="input-error">${errors.entYear}</div>
                        </c:if>
                        <%-- VN: Nếu có lỗi cho entYear thì hiển thị thông báo lỗi dưới select
                            JP: entYearに対するバリデーションエラーがあれば、そのメッセージを表示 --%>
                    </div>

                    <div class="form-floating mt-3">
                        <input class="form-control" type="text" name="no" maxlength="10"
                               value="${no}" placeholder="学生番号を入力してください" />
                        <%-- VN: Input mã số sinh viên, tối đa 10 ký tự, giữ lại giá trị đã nhập (no)
                            JP: 学生番号入力欄。最大10文字で、前回入力値noを再表示する --%>

                        <label>学生番号</label>
                        <%-- VN: Nhãn "学生番号" cho input mã số sinh viên
                            JP: 学生番号入力欄のラベル --%>

                        <c:if test="${not empty errors.no}">
                            <div class="input-error">${errors.no}</div>
                        </c:if>
                        <%-- VN: Nếu có lỗi cho no thì hiển thị thông báo lỗi
                            JP: 学生番号に対するエラーメッセージがあれば表示 --%>
                    </div>

                    <div class="form-floating mt-3">
                        <input class="form-control" type="text" name="name" maxlength="20"
                               value="${name}" placeholder="氏名を入力してください" />
                        <%-- VN: Input họ tên sinh viên, tối đa 20 ký tự, giữ lại giá trị name
                            JP: 氏名入力欄。最大20文字で、前回入力値nameを再表示 --%>

                        <label>氏名</label>
                        <%-- VN: Nhãn "氏名" cho input họ tên
                            JP: 氏名入力欄のラベル --%>

                        <c:if test="${not empty errors.name}">
                            <div class="input-error">${errors.name}</div>
                        </c:if>
                        <%-- VN: Nếu có lỗi cho name thì hiển thị thông báo lỗi
                            JP: 氏名に対するエラーメッセージがあれば表示 --%>
                    </div>

                    <div class="form-floating mt-3">
                        <select class="form-select" name="classNum">
                            <option value=""></option>
                            <%-- VN: Select chọn lớp, có option rỗng ban đầu
                                JP: クラス選択用セレクトボックス。最初に空のオプションを配置 --%>

                            <c:forEach var="num" items="${class_num_set}">
                            <%-- VN: Lặp qua danh sách mã lớp (class_num_set) được set trong request
                                JP: リクエスト属性class_num_setに格納されたクラス番号リストをループ --%>

                                <option value="${num}"
                                    <c:if test="${num == classNum}">selected</c:if>>
                                    ${num}
                                </option>
                                <%-- VN: Tạo option cho từng lớp, nếu trùng classNum đã nhập thì selected
                                    JP: 各クラス番号のオプションを生成し、前回入力値classNumと一致する場合はselectedを付与 --%>

                            </c:forEach>
                        </select>

                        <label>クラス</label>
                        <%-- VN: Nhãn "クラス" cho select lớp
                            JP: クラス選択欄のラベル --%>

                        <c:if test="${not empty errors.classNum}">
                            <div class="input-error">${errors.classNum}</div>
                        </c:if>
                        <%-- VN: Nếu có lỗi cho classNum thì hiển thị thông báo lỗi
                            JP: クラス番号に対するエラーメッセージがあれば表示 --%>
                    </div>

                    <div class="form-check mt-3">
                        <input class="form-check-input" type="checkbox" name="isAttend" value="t"
                               <c:if test="${isAttend == 't'}">checked</c:if> />
                        <%-- VN: Checkbox đánh dấu đang học, nếu isAttend == 't' thì check lại
                            JP: 在学中フラグ用チェックボックス。isAttendが't'ならチェック状態にする --%>

                        <label class="form-check-label">在学中</label>
                        <%-- VN: Nhãn "在学中" cho checkbox
                            JP: チェックボックスのラベル「在学中」 --%>
                    </div>

                    <div class="mt-4 text-center">
                        <button formaction="StudentAddTemp.action" class="btn btn-warning w-25 me-2">追加</button>
                        <%-- VN: Nút "追加" gửi form tới StudentAddTemp.action để thêm vào danh sách tạm
                            JP: 一時リストに学生を追加するため、StudentAddTemp.actionへ送信する「追加」ボタン --%>

                        <button formaction="StudentMultiInsertExecute.action" class="btn btn-primary w-25">登録</button>
                        <%-- VN: Nút "登録" gửi form tới StudentMultiInsertExecute.action để đăng ký vào DB
                            JP: DBへ一括登録するため、StudentMultiInsertExecute.actionへ送信する「登録」ボタン --%>
                    </div>

                    <div class="mt-3 text-center">
                        <a href="StudentList.action" class="btn btn-outline-secondary ms-1 px-4">戻る</a>
                        <%-- VN: Nút quay lại danh sách học sinh, style viền xám, căn giữa
                            JP: 学生一覧画面へ戻るためのアウトラインセカンダリボタン --%>
                    </div>

                </form>

                <c:if test="${not empty temp_students}">
                <%-- VN: Nếu danh sách học sinh tạm (temp_students) không rỗng thì hiển thị bảng
                    JP: 一時リストtemp_studentsが空でない場合、追加済み学生一覧を表示 --%>

                    <div class="mx-5 mt-4">
                        <h4>追加済みの学生一覧</h4>
                        <%-- VN: Tiêu đề "Danh sách học sinh đã thêm"
                            JP: 見出し「追加済みの学生一覧」 --%>

                        <table class="table table-bordered mt-2">
                            <tr>
                                <th>学籍番号</th>
                                <th>氏名</th>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>在学中</th>
                                <th>削除</th>
                            </tr>
                            <%-- VN: Header bảng hiển thị thông tin học sinh trong danh sách tạm
                                JP: 一時リスト内の学生情報を表示するテーブルヘッダー --%>

                            <c:forEach var="s" items="${temp_students}" varStatus="st">
                            <%-- VN: Lặp qua từng học sinh trong danh sách tạm, st giữ index
                                JP: 一時リストtemp_studentsの各学生をループし、stでインデックスを管理 --%>

                                <tr>
                                    <td>${s.no}</td>
                                    <%-- VN: Cột mã số sinh viên
                                        JP: 学籍番号列 --%>

                                    <td>${s.name}</td>
                                    <%-- VN: Cột họ tên
                                        JP: 氏名列 --%>

                                    <td>${s.entYear}</td>
                                    <%-- VN: Cột năm nhập học
                                        JP: 入学年度列 --%>

                                    <td>${s.classNum}</td>
                                    <%-- VN: Cột lớp
                                        JP: クラス列 --%>

                                    <td><c:if test="${s.attend}">○</c:if></td>
                                    <%-- VN: Nếu attend == true thì hiển thị "○" ở cột đang học
                                        JP: attendがtrueの場合、「在学中」を○印で表示 --%>

                                    <td>
                                        <form method="post" action="StudentTempDelete.action" style="display:inline;">
                                            <input type="hidden" name="index" value="${st.index}">
                                            <%-- VN: Truyền index của phần tử trong danh sách tạm để xóa đúng học sinh
                                                JP: 一時リスト内の対象学生を特定するため、インデックスをhiddenで送信 --%>

                                            <button class="btn btn-danger btn-sm">削除</button>
                                            <%-- VN: Nút "削除" xóa học sinh khỏi danh sách tạm
                                                JP: 一時リストから学生を削除するための「削除」ボタン --%>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>

            </c:if>

        </section>

    </c:param>

</c:import>
