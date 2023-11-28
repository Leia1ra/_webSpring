<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    #subject{
        width: 100%;
    }
    #file_list b {
        cursor: pointer;
    }
</style>
<%--ckeditor--%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ckeditor.css">
<script src="https://cdn.ckeditor.com/ckeditor5/39.0.1/super-build/ckeditor.js"></script>
<script src="<%=request.getContextPath()%>/js/ckeditor.js"></script>
<script>
    window.onload = function(){
        CKEDITOR.ClassicEditor.create(document.getElementById("content"), option);
    }
    $(function (){
        let fCount = ${fileCount};
        // 파일 첨부 추가하기
        $(document).on('click', "input[value= '  +  ']", function (){
            $(this).parent().parent().append('<div><input type="file" name="filename"><input type="button" value="  +  "></div>');
            // 이벤트가 발생한 + 버튼의 value를 - 로 변경
            $(this).val('  -  ');
        });
        // 첨부 파일 삭제하기
        $(document).on('click', "input[value='  -  ']", function (){
            $(this).parent().remove();
        });

        // 제목과 첨부파일의 수
        $("#dataForm").submit(function (){
            if($("#subject").val() === ""){
                alert("제목을 입력하세요");
                return false;
            }
            // 첨부파일의 수 구하기 반드시 하나 이상
            let fileCount = 0;
            $("input[name=filename]").each(function (){
                if($(this).val() !== ""){
                    fileCount++;
                }
            });
            // console.log(fileCount);
            if(fileCount + fCount < 1){
                alert("1개 이상의 파일을 첨부하여야 합니다");
                return false;
            }
            return true;
        });

        // 이미 첨부된 파일 삭제
        $("#file_list b").click(function (){
            // 부모 DIV는 숨김 -> 다음 input name = delFile
            $(this).parent().css("display", "none");
            $(this).parent().next().attr("name", "delFile");

            // 파일의 갯수 감소시키기
            fCount--;
        });
    });
</script>
<main>
    <h1>자료올리기 폼</h1>
    <!-- file 첨부가 있는 form 태그는 시작태그에 enctype속성이 반드시 있어야 서버로 파일을 보낼 수 있다. -->
    <form action="${pageContext.servletContext.contextPath}/data/editOk"
          method="post"
          id="dataForm"
          enctype="multipart/form-data">
        <input type="hidden" name="no" value="${vo.no}">
        <ul>
            <li>제목</li>
            <li><input type="text" name="subject" id="subject" value="${vo.subject}"></li>
            <li>글 내용</li>
            <li><textarea name="content" id="content">${vo.content}</textarea></li>
            <li>첨부파일</li>
            <li id="file_list">
                <!-- 기존 업로드한 파일 목록 -->
                <c:forEach var="fvo" items="${fList}">
                    <div>${fvo.filename} <b>[X]</b></div>
                    <input type="hidden" name="" value="${fvo.filename}">
                </c:forEach>
                <div></div>
                <div><input type="file" name="filename"><input type="button" value="  +  "></div>
            </li>
            <li style="padding-top: 10px;">
                <input type="submit" value="글수정">
                <input type="reset" value="다시쓰기">
            </li>
        </ul>
    </form>
</main>