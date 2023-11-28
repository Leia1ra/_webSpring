<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/Login/Login.css">
<script>
    function loginCheck(){
        if(document.getElementById("userid").value === ""){
            alert("아이디를 입력후 로그인 하세요..");
            return false;
        }
        if(document.getElementById("userpwd").value === ""){
            alert("비밀번호를 입력후 로그인 하세요..");
            return false;
        }
        return true;
    }
</script>
<!--로그인 페이지 만듦-->
<!--Contents-->
<article id="Login">
    <!--LogIn-->
    <h2>로그인</h2>
    <form action="<%=request.getContextPath()%>/users/loginOk" method="post" onsubmit="return loginCheck()">
        <div id="formInput">
            <input type="text" name="userid" id="userid" placeholder="아이디를 입력하세요">
            <input type="password" name="userpwd" id="userpw" placeholder="비밀번호를 입력하세요">
        </div>
        <div id="logfrm">
            <div>
                <a href="<%=request.getContextPath()%>/users/userForm">회원가입</a><br>
                <a href="#">아이디/비밀번호 찾기</a>
            </div>
            <label>
                <span>로그인 정보 기억</span>
                <input type="checkbox" name="AutoLogin" id="Auto" value="">
            </label>
        </div>
        <input type="submit" value="로그인">
    </form>
</article>