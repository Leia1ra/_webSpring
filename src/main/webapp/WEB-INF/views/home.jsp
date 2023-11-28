<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<main>
	<h1>Hello world!(웰컴)</h1>
	<h2><a href="<%=request.getContextPath()%>/chatForm">채팅하러 가기(WebSocket -> 1:N)</a></h2>
	
	<p>
		jsp페이지에서 외부파일인 js, css, 이미지, 동영상파일 등을 사용하기 위해서는 프로젝트 webapp폴더 하위에 폴더를
		생성하고 servlet-context.xml에
		<!--
			<resources mapping="/img/**" location="/img/" />
		-->
		resources를 추가하여야 한다.
	</p>
	<p>
		${serverTime}
	</p>
	
	<img src="img/cute-cat-11.png" class="h"/><img src="img/cute-cat-12.png" class="h"/>
	<div style="border: 1px solid #ddd; padding: 20px; margin:20px 0;">
		<a href="/campus/test1?name=홍&num=12">접속하기(get)</a>
		<a href="/campus/test2?name=이순신&num=55">접속하기(get)</a>
		<a href="/campus/test3?name=안중근&num=100">접속하기(get)</a>
		<p>
			data1 : ${data1}<br/>
			data1 : ${data2}<br/>
		</p>
		<form action="/campus/test4" method="post">
			상품명 : <input type="text" name="productName">
			가격 : <input type="text" name="price">
			<input type="submit">
		</form>
		<form action="<%=request.getContextPath()%>/test5">
			<input type="number" name="testNo1">
			<input type="submit" value="전송">
		</form>
		<p>
			상품명 : ${pName}<br/>
			가격 : ${sales}<br/>
		</p>
	</div>
</main>
