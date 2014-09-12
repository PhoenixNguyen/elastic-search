<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${wallet_account_logined !=null}">	
<div class="personal_bar">
	<a href="<%=request.getContextPath() %>/nha-phat-trien.html" class="btn_apk">SDK</a>
	<a href="<%=request.getContextPath() %>/nha-phat-trien.html" class="btn_api">APIs</a>
	<div class="sodu_box">
		<div class="sodu_holder">
			<fmt:formatNumber value="${wallet_account_logined.balance}" var="totalWalletBalanceFmt" currencyCode="vnd" />
			Số dư: <span><c:out value="${totalWalletBalanceFmt}" />đ</span><a href="<%=request.getContextPath() %>/protected/wallet.html" class="btn_ruttien">Rút tiền</a>
		</div>
	</div>
</div>
<div class="clear"></div>
</c:if>