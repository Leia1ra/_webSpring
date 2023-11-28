<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    #subject{
        width: 100%;
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
            console.log(fileCount);
            return true;
        })
    });
</script>
<main>
    <h1>자료올리기 폼</h1>
    <!-- file 첨부가 있는 form 태그는 시작태그에 enctype속성이 반드시 있어야 서버로 파일을 보낼 수 있다. -->
    <form action="${pageContext.servletContext.contextPath}/data/writeOk"
          method="post"
          id="dataForm"
          enctype="multipart/form-data">
        <ul>
            <li>제목</li>
            <li><input type="text" name="subject" id="subject"></li>
            <li>글 내용</li>
            <li><textarea name="content" id="content"></textarea></li>
            <li>첨부파일</li>
            <li>
                <div><input type="file" name="filename"><input type="button" value="  +  "></div>
            </li>
            <li style="padding-top: 10px;">
                <input type="submit" value="글등록">
                <input type="reset" value="다시쓰기">
            </li>
        </ul>
    </form>
</main>