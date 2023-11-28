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
    var boardCheck = function (){
        if(document.getElementById("subject").value === ""){
            alert("제목을 입력하세요")
            return false;
        }
        // 값이 있으면 true 없으면 false
        if(document.getElementById("content").value === ""){
            alert("글 내용을 입력하세요");
            return false;
        }
        return true;
    }
</script>
<main>
    <h1>글쓰기 폼</h1>
    <form action="${pageContext.servletContext.contextPath}/board/writeOk" method="post" onsubmit="return boardCheck()">
        <ul>
            <li>제목</li>
            <li><input type="text" name="subject" id="subject"></li>
            <li>글 내용</li>
            <li><textarea name="content" id="content"></textarea></li>
            <li>
                <input type="submit" value="글등록">
                <input type="reset" value="다시쓰기">
            </li>
        </ul>
    </form>
</main>