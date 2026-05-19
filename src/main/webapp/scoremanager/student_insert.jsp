<!-- VI: Hiển thị thông báo hoàn tất đăng ký
     JP: 登録完了メッセージの表示 -->
<c:if test="${not empty message}">
    <div class="glass-success mx-5 mt-2">
        ${message}
    </div>

    <div class="mt-3 text-center">
        <a href="StudentList.action" class="btn btn-secondary w-25">戻る</a>
    </div>
</c:if>

<!-- VI: Nếu chưa hoàn tất đăng ký thì hiển thị form nhập
     JP: 完了メッセージがない場合、入力フォームを表示 -->
<c:if test="${empty message}">

    <!-- VI: Form nhập thông tin 1 học sinh
         JP: 学生情報の入力フォーム -->
    <form method="post" class="glass-block mx-5">

        <!-- VI: Chọn năm nhập học
             JP: 入学年度の選択 -->
        <div class="form-floating mt-3">
            <select class="form-select" id="entYear" name="entYear">
                <option value=""></option>
                <c:forEach var="year" items="${ent_year_set}">
                    <option value="${year}"
                        <c:if test="${year == entYear}">selected</c:if>>
                        ${year}
                    </option>
                </c:forEach>
            </select>
            <label>入学年度</label>

            <c:if test="${not empty errors.entYear}">
                <div class="input-error">${errors.entYear}</div>
            </c:if>
        </div>

        <!-- VI: Nhập mã số sinh viên
             JP: 学生番号の入力 -->
        <div class="form-floating mt-3">
            <input class="form-control" type="text" name="no" maxlength="10"
                   value="${no}" placeholder="学生番号を入力してください" />
            <label>学生番号</label>

            <c:if test="${not empty errors.no}">
                <div class="input-error">${errors.no}</div>
            </c:if>
        </div>

        <!-- VI: Nhập họ tên sinh viên
             JP: 氏名の入力 -->
        <div class="form-floating mt-3">
            <input class="form-control" type="text" name="name" maxlength="20"
                   value="${name}" placeholder="氏名を入力してください" />
            <label>氏名</label>

            <c:if test="${not empty errors.name}">
                <div class="input-error">${errors.name}</div>
            </c:if>
        </div>

        <!-- VI: Chọn lớp
             JP: クラスの選択 -->
        <div class="form-floating mt-3">
            <select class="form-select" name="classNum">
                <option value=""></option>
                <c:forEach var="num" items="${class_num_set}">
                    <option value="${num}"
                        <c:if test="${num == classNum}">selected</c:if>>
                        ${num}
                    </option>
                </c:forEach>
            </select>
            <label>クラス</label>

            <c:if test="${not empty errors.classNum}">
                <div class="input-error">${errors.classNum}</div>
            </c:if>
        </div>

        <!-- VI: Đánh dấu trạng thái đang học
             JP: 在学中フラグの設定 -->
        <div class="form-check mt-3">
            <input class="form-check-input" type="checkbox" name="isAttend" value="t"
                   <c:if test="${isAttend == 't'}">checked</c:if> />
            <label class="form-check-label">在学中</label>
        </div>

        <!-- VI: Nút thêm vào danh sách tạm + nút đăng ký vào DB
             JP: 一時リストへ追加ボタン + DB登録ボタン -->
        <div class="mt-4 text-center">
            <button formaction="StudentAddTemp.action" class="btn btn-warning w-25 me-2">追加</button>
            <button formaction="StudentMultiInsertExecute.action" class="btn btn-primary w-25">登録</button>
        </div>

        <div class="mt-3 text-center">
            <a href="StudentList.action" class="btn btn-outline-secondary ms-1 px-4">戻る</a>
        </div>

    </form>

    <!-- VI: Danh sách học sinh đã thêm vào danh sách tạm
         JP: 追加済みの学生一覧（セッション一時リスト） -->
    <c:if test="${not empty temp_students}">
        <div class="mx-5 mt-4">
            <h4>追加済みの学生一覧</h4>
            <table class="table table-bordered mt-2">
                <tr>
                    <th>学籍番号</th>
                    <th>氏名</th>
                    <th>入学年度</th>
                    <th>クラス</th>
                    <th>在学中</th>
                    <th>削除</th>
                </tr>

                <c:forEach var="s" items="${temp_students}" varStatus="st">
                    <tr>
                        <td>${s.no}</td>
                        <td>${s.name}</td>
                        <td>${s.entYear}</td>
                        <td>${s.classNum}</td>
                        <td><c:if test="${s.attend}">○</c:if></td>

                        <!-- VI: Nút xóa học sinh khỏi danh sách tạm
                             JP: 一時リストから学生を削除するボタン -->
                        <td>
                           <form method="post" action="StudentTempDelete.action" style="display:inline;">
                                <input type="hidden" name="index" value="${st.index}">
                                <button class="btn btn-danger btn-sm">削除</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>

</c:if>
