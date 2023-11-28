<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    $(function () {
        //header태그와 footer태그의 내용을 지움
        $("header").html = "";
        $("header").css("display", "none");
        $("footer").css("display", "none");

        $("form").submit(function (){
            if($("#userid").val() == ""){
                alert("아이디를 입력 후에 중복검사를 실시하세요.");
                return false;
            }
            return true;
        });
        $("input[value=사용하기]").click(function () {
            opener.document.getElementById("userid").value = '${userid1}';
            opener.$("#chk").val("Y");
            self.close();
        });
    });
</script>
<div>
    아이디 중복검사결과
    ${result1}
    <div>
        <c:if test="${result1 == 0}">
            <strong>${userid1}</strong>은 사용 가능한 아이디입니다.
            <input type="button" value="사용하기">
        </c:if>
        <c:if test="${result1 == 1}">
            <strong>${userid1}</strong>은 사용 불가능합니다.
        </c:if>
    </div>
    <hr>
    <div>
        <form action="/campus/users/idCheck" onsubmit="return check()">
            아이디: <input type="text" name="userid" id="userid">
            <input type="submit" value="아이디 중복검사">
        </form>
    </div>
</div>