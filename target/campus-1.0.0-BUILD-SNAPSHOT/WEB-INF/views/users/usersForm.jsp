<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    main{
        width: 600px;
        margin: 0 auto;
    }
    form>ul{
        overflow: auto;
    }
    form>ul>li{
        float: left;
        height: 40px;
        line-height: 40px;
        border-bottom: 1px solid #ddd;
        width: 20%;
    }
    form>ul>li:nth-child(2n){
        width: 80%;
    }
    form>ul>li:last-of-type{
        width: 100%;
        text-align: center;
    }
</style>
<!--우편번호 찾기 API-->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    $(function (){
        $("input[value='아이디 중복검사']").click(function (){
            if($("#userid").val() == ""){{
                alert("아이디를 입력후 중복검사하세요.");
                return;
            }}
            window.open("/campus/users/idCheck?userid="+$("#userid").val(),"idCheck","width=500px, height:300px");
        });
        $("#userid").keyup(function () {
            $("#chk").val("N");
        });
        //우편번호 찾기
        $("input[value=우편번호찾기]").click(function (){
            new daum.Postcode({
                oncomplete: function(data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var addr = ''; // 주소 변수
                    var extraAddr = ''; // 참고항목 변수

                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById('zipcode').value = data.zonecode;
                    // document.getElementById("sample6_address").value = addr;
                    document.getElementById("addr").value = addr;
                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("addrdetail").focus();
                }
            }).open();
        });
        // 유효성 검사 - 아이디, 비밀번호, 이름, 연락처
        $("#userfrm").submit(function (){
            // 영어, 숫자, _만 허용
            var reg = /^\w{6,14}$/
            if(!reg.test($("#userid").val())){
                alert("아이디는 영어 대소문자, 숫자, _만 허용, 길이는 6~14글자를 사이여야 한다.");
                return false;
            }
            if($('#chk').val() === "N"){
                alert("아이디 중복검사를 하세요");
                return false;
            }
            if($("#userpwd")=="" || $("#userpwd2").val()==""){
                alert("비밀번호를 입력하세요");
                return false;
            }
            if($("#userpwd").val() != $("#userpwd2").val()){
                alert("비밀번호가 다릅니다.");
                return false;
            }

            reg = /^[가-힣]{2,10}$/
            if(!reg.test($("#username").val())){
                alert("이름을 잘못입력하였습니다.");
                return false;
            }

            reg = /^[0-9]{3,4}-[0-9]{4}$/
            let tel = $("#tel2").val()+"-"+$("#tel3").val();
            if(!reg.test( tel )){
                alert("전화번호를 잘못입력하셨습니다.");
                return false;
            }
            return true;
        });
    });
</script>
<main>
    <h1>회원가입 폼</h1>
                                <%--<%=request.getContextPath()%>--%>
    <form method="post" action="${pageContext.servletContext.contextPath}/users/userFormOk" id="userfrm">
        <ul>
            <li>아이디</li>
            <li>
                <input type="text" name="userid" id="userid">
                <input type="button" value="아이디 중복검사"/>
                <input type="hidden" name="chk" id="chk" value="N">
            </li>
            <li>비밀번호</li>
            <li><input type="password" name="userpwd" id="userpwd"></li>
            <li>비밀번호 확인</li>
            <li><input type="password" name="userpwd2" id="userpwd2"></li>
            <li>이름</li>
            <li><input type="text" name="username" id="username"></li>
            <li>연락처</li>
            <li>
                <select name="tel1" id="tel1">
                    <option value="010">010</option>
                    <option value="011">011</option>
                    <option value="02">02</option>
                    <option value="031">031</option>
                    <option value="042">042</option>
                </select>-
                <input type="number" name="tel2" id="tel2">-
                <input type="number" name="tel3" id="tel3">
            </li>
            <li>이메일</li>
            <li><input type="text" name="email" id="email"></li>
            <li>우편번호</li>
            <li>
                <input type="text" name="zipcode" id="zipcode">
                <input type="button" value="우편번호찾기">
            </li>
            <li>주소</li>
            <li><input type="text" name="addr" id="addr" style="width: 100%"></li>
            <li>상세주소</li>
            <li><input type="text" name="addrdetail" id="addrdetail" style="width: 100%"></li>
            <li>취미</li>
            <li>
                <input type="checkbox" name="hobby" value="야구">야구
                <input type="checkbox" name="hobby" value="바이크">바이크
                <input type="checkbox" name="hobby" value="등산">등산
                <input type="checkbox" name="hobby" value="쇼핑">쇼핑
                <input type="checkbox" name="hobby" value="자전거">자전거
                <input type="checkbox" name="hobby" value="걷기">걷기
                <input type="checkbox" name="hobby" value="영화감상">영화감상
            </li>
            <li><input type="submit" value="회원가입하기"></li>
        </ul>
    </form>
</main>