<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
    #replyArea{
        padding: 10px 0px;
    }
    #coment{
        width: 600px;
        height: 80px;
    }
    #replyList>li{
        border-bottom: 1px solid #ddd;
    }
</style>
<script>
    function boardDel() {
        if(confirm("글을 정말로 삭제하시겠습니까?")){
            location.href = "<%=request.getContextPath()%>/board/delete?no=${vo.no}"
        }
    }

    // ajax를 이용한 댓글 등록, 수정, 삭제, 목록
    $(function (){
        // 목록
        function replyList(){
            $.ajax({
                url:"<%=request.getContextPath()%>/boardReply/list",
                data: {no : ${vo.no}},
                type: "GET",
                success : function (result) {
                    console.log(result);
                    var tag = ""; // 댓글 목록 태그 + 수정 삭제
                    $(result).each(function(i, rVO){
                        tag += "<li><div><b>"+ rVO.userid +"</b>(" + rVO.writedate +")";
                        if('${logId}' === rVO.userid){
                            // 변수로 취급해서 ''처리 해주어서 String 데이터로 만들어 주어야 함
                            tag += "<input type='button' value='Edit'/>";
                            tag += "<input type='button' value='Del' title='" + rVO.replyno + "'/>";
                            // 댓글 내용
                            tag += "<p>"+rVO.coment+"</p></div>";

                            // 수정폼 -> 댓글 번호 ,댓글 내용이 적혀 잇음
                            tag += "<div style='display: none'><form method='post'>";
                            tag += "<input type='hidden' name='no' value='"+ rVO.no +"'>";
                            tag += "<input type='hidden' name='replyno' value='" + rVO.replyno + "'>"
                            tag += "<textarea name='coment' style='width: 400px;height: 80px'> " + rVO.coment + " </textarea>";
                            tag += "<input type='submit' value='댓글수정하기'>"
                            tag += "</div></form>";

                        } else {
                            tag += "<p>"+rVO.coment+"</p></div>";
                        }
                        tag += "</li>"
                    });
                    // document.getElementById("replyList").innerHTML = tag;
                    $("#replyList").html(tag);
                },error : function (e) {
                    console.log(e.responseText)
                }

            });
        }
        // 등록
        $("#replyForm").submit(function(){
            //form태그의 action을 중지한다.
            event.preventDefault();

            //coment 입력 확인
            if($("#coment").val() == ""){
                alert("댓글을 입력후 등록하세요");
                return false;
            }

            // form의 데이터를 query로 만들기
            var param = $(this).serialize();// 쿼리로 만들어줌
                   // = "no=" + $("#no").val() + "&coment=" + $("#coment").val();

            $.ajax({
                url : "<%=request.getContextPath()%>/boardReply/write",
                data:param,
                type:"POST",
                success:function(result){
                    console.log(result);
                    if(result == 0){
                        alert("댓글 작성이 실패하였습니다");
                    } else {
                        replyList();
                    }
                    $("#coment").val("");
                }, error:function(e){
                    console.log(e.responseText);
                }
            });
        });
        // 수정
        $(document).on('click', '#replyList input[value=Edit]', function () {
            $(this).parent().css('display', 'none');// 부모 숨기기 : 댓글 내용
            $(this).parent().next().css('display', 'block');
        });
        $(document).on('submit', '#replyList form', function () {
            event.preventDefault();
            var params = $(this).serialize();
            $.ajax({
                url : "<%=request.getContextPath()%>/boardReply/editOk",
                data:params,
                type:"POST",
                success:function(result){
                    if(result == 0){
                        alert("댓글 수정이 실패하였습니다.");
                    } else {
                        replyList();
                    }
                    console.log(result);
                }, error:function(e){
                    console.log(e.responseText);
                }
            });
        });
        // 삭제
        $(document).on('click','#replyList input[value=Del]', function(){
            if(confirm("삭제하시겠습니까?")){
                var replyno = $(this).attr("title");
                $.ajax({
                    url:"<%=request.getContextPath()%>/boardReply/delete",
                    data:{
                        replyno:replyno
                    }, type : "GET",
                    success:function(result){
                        replyList();
                    }, error:function(e){
                        console.log(e.responseText);
                    }
                });
            }
        });


        /*목록 함수 호출*/
        replyList();
    })
</script>
<main>
    <h1>글 내용 보기</h1>
    <ul>
        <li>번호 : ${vo.no}, 글쓴이 : ${vo.userid}, 조회수 : ${vo.hit}, 등록일 : ${vo.hit}</li>
        <li>제목 : ${vo.subject}</li>
        <li>${vo.content}</li>
    </ul>
    <div>
        <a href="<%=request.getContextPath()%>/board/list?nowPage=${pVO.nowPage}<c:if test="${pVO.searchWord != null}">&searchKey=${pVO.searchKey}&searchWord=${pVO.searchWord}</c:if>">목록</a>
        <c:if test="${logId == vo.userid}">
            <a href="<%=request.getContextPath()%>/board/edit?no=${vo.no}">수정</a>
            <a href="<%=request.getContextPath()%>/board/delete?no=${vo.no}" onclick="boardDel()">삭제</a>
        </c:if>
    </div>

    <!-- 댓글 -->
    <div id="replyArea">
        <div style="background-color: #FFF3FE">댓글 목록</div>
        <!-- 로그인 상태일때 댓글 쓰기 -->
        <c:if test="${logStatus == 'Y'}">
            <form method="post" id="replyForm">
                <!-- 원글 글번호 -->
                <input type="hidden" name="no" value="${vo.no}">
                <textarea name="coment" id="coment"></textarea>
                <button>댓글등록</button>
            </form>
        </c:if>
        <!-- 댓글목록 -->
        <ul id="replyList">
            <li>
                <div>
                    <b>lllll</b>(2023-10-10 12:12:23)
                    <input type="button" value="Edit"/>
                    <input type="button" value="del"/>
                    <p>댓글 공부중...</p>
                </div>
            </li>
        </ul>
    </div>
</main>