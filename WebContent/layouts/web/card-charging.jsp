<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<%@ page import="vn.onepay.common.SharedConstants, vn.onepay.account.model.Account, java.lang.String"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="head.jsp"></jsp:include>
<link href="${pageContext.request.contextPath }/css/personal.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jquery-ui.min.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.multiselect.js"></script>


<style>
	.filter_row .fieldset_filter {border: solid 1px #ccc;margin-left:40px; width: auto;}
</style>


<script type="text/javascript">
$(function(){

	$("#filter_merchant").multiselect({
		selectedList: 4,
		noneSelectedText: "Tất cả"
	});
	$("#filter_provider").multiselect({
		selectedList: 4,
		noneSelectedText: "Tất cả"
	});
	$("#filter_card_type").multiselect({
		selectedList: 4,
		noneSelectedText: "Tất cả"
	});
	$("#filter_card_amount").multiselect({
		selectedList: 4,
		noneSelectedText: "Tất cả"
	});
});
</script>

<style >
	.box_locketqua table a{
		font-size: 14px;
		color: #2c8f39;
		padding-top: 2px;
		
	}
	
	.box_locketqua table a.slc_link {
		color: #FFA200;
	}
</style>
</head>
<%
	Account account = (Account) request.getSession().getAttribute(SharedConstants.ACCOUNT_LOGINED);
	boolean isAdmin = account.isAdmin();
	boolean mbiz =  SharedConstants.MBIZ && isAdmin;
	request.setAttribute("MBIZ", mbiz);
%>
<body>
	<!-- 1PAY WEB -->
	<div id="wrapper">
		<!-- Web Top -->
		<div id="w_top">
			<div class="layout_1000">
				<!-- Header -->
				<%request.setAttribute("menuTrangChu", true); %>
				<jsp:include page="header.jsp"></jsp:include>
				<!-- / Header -->

				<!-- Body -->
				<div id="w_body">
					<div class="row_sub">
						<!-- Persanal Bar -->
						<jsp:include page="include_personal_bar.jsp" />
						<!-- Left Menu -->
						<%request.setAttribute("cardReportElastic", true);%>
						<jsp:include page="card-charging-search-menu.jsp" />
						
						<div class="right_content">
							<h1 class="srv_title">Tìm kiếm Card Charging</h1>
							<div>
								<form id="cardReport" name="cardReport" action="card-charging.html" method="get">
									
									
									<c:forEach items="${model.fieldMaps }" var="map">
									
										<c:if test="${param[map.key] != null && param[map.key] != ''  }">
											<input type="hidden" name="${map.key }" value="${param[map.key] }"/>
										</c:if>
									</c:forEach>	
									
									<div id="filter">
										<div class="filter_row">
												
							                 	<table width="80%" align="center">
							                 		<tr>
							                 		
							                 			<td width="70%"><input style="width: 97%" type="text" name="key" list="listForSearchMC"  maxlength="100" value="${param.key}" class="txt_filter" placeholder="Nhập từ khóa" /></td>
							                 			<td width="30%" align="center"><input style="margin-top: 0px;"  class="btn_greensmall" type="submit" value="Tìm kiếm" /></td>
							                 			
							                 		</tr>
							                 	</table>
							               		
							               		<datalist id = "listForSearchMC">
							               			<c:forEach var="merchant" items="${model.merchants}">
														<option value="${merchant }">
													</c:forEach>
							               		</datalist>
												
							                </div>
									</div>
									
									
								</form>
								
							</div>
							<div class="srv_row">
								<%
								if(!account.checkRole(Account.ACCOUNT_CUSTOMER_CARE_ROLE)){
								%>
								<c:if test="${!MBIZ}">
               					<strong>Sản lượng:</strong> <fmt:formatNumber var="money" value="${model.sumary[1]}" currencyCode="vnd" /><c:out value="${money}"/> đ &nbsp;<img id="myTip" src="<%=request.getContextPath() %>/images/question.png" title="Chú thích" style="vertical-align: bottom; margin-right: 5px;" width="16px"/>|
               					<strong>Giao dịch:</strong> <fmt:formatNumber var="totalItems" value="${model.sumary[0]}" currencyCode="vnd" /><c:out value="${totalItems}" />
               					</c:if>
               					<%} %>
               					<script type="text/javascript" language="javascript">
									$(document).ready(function() {
							
										$("#myTip").qtip({
											content: 'Sản lượng: Tổng số tiền mệnh giá nhà mạng thu khách hàng đầu cuối.',
											position : {
												corner : {
													target : 'topRight',
													tooltip : 'bottomLeft'
												}
											},
											style : {
												name : 'cream',
												tip : 'bottomLeft',
												border : {
													width : 1,
													radius : 12,
													color : '#F49105'
												},
												color : '#fff',
												background : '#F49105'
											}
										});
									});
								</script>
               				</div>
               				
							<div class="srv_row">
								<script>var rownum = 1;</script>
								<c:if test="${model.total > 0}">
									<span class="pagebanner"> ${model.total} kết quả tìm thấy, hiển thị từ ${model.offset + 1  } tới ${(model.offset + model.pagesize) > model.total ? model.total : (model.offset + model.pagesize) }. 
										&nbsp(Thời gian tìm kiếm ${model.timeHandleTotal /1000.0} giây)
									</span>
								</c:if>
								<br/>
								
								<display:table name="model.list" id="list" 
												requestURI="card-charging.html" 
												pagesize="${model.pagesize}" partialList="true" size="model.total"
												style="width:100%;cellspacing:0;cellpadding:0;border: 1px solid #CCC;table-layout:fixed;" 
												sort="list">
									<%@ include file="display_table.jsp" %>
									<display:column title="Stt" headerClass="transhead fit_to_content" class="transdata" style="text-align:right;border: 1px solid #CCC;">
										<span id="row${list.id}" class="rowid">
											<!-- <script>document.write(rownum++);</script> -->
											<c:out value="${model.offset + list_rowNum }" />
										</span>
								    </display:column>
								    <% if(account.checkRoles(new String[]{Account.ACCOUNT_ADMIN_ROLE, Account.ACCOUNT_OPERATION_MANAGER_ROLE, Account.ACCOUNT_REPORTER_ROLE, Account.ACCOUNT_SHARE_HOLDER_ROLE, Account.ACCOUNT_CUSTOMER_CARE_ROLE})){%>
								    <display:column title="Payment Provider" headerClass="transhead" class="transdata" property="paymentProvider" style="border: 1px solid #CCC;" >
								    	<%-- <c:out value="${onepay:providerCode2Text(list.paymentProvider)}"/> --%>
								    </display:column>
								    <%} %>
								    <display:column title="Merchant" headerClass="transhead" class="transdata" property="merchant" style="border: 1px solid #CCC;" />
								    <display:column title="Timestamp" headerClass="transhead" class="transdata" property="timestamp" format="{0,date,dd/MM/yyyy HH:mm:ss}" style="border: 1px solid #CCC;" />
								    <display:column title="Card Type" headerClass="transhead" class="transdata" property="type" style="border: 1px solid #CCC;" />
								    <display:column title="Serial" headerClass="transhead" class="transdata" property="serial" style="border: 1px solid #CCC;" />
								    <display:column title="Amount" headerClass="transhead" class="transdata" property="amount" style="border: 1px solid #CCC;text-align:right;" format="{0,number,0,000} đ" />
								    <display:column title="Status" headerClass="transhead" class="transdata" sortProperty="status" style="border: 1px solid #CCC;text-align:center;">
										<c:choose>
											<c:when test="${list.status == '00' }">
												<img border="0" src="<%=request.getContextPath()%>/images/tick.png" title="Thành công" />
											</c:when>
											<c:when test="${list.status == '02' }">
												<img border="0" src="<%=request.getContextPath()%>/images/invalid.png" title="Thẻ không hợp lệ hoặc đã được sử dụng" />
											</c:when>
											<c:otherwise>
												<img border="0" src="<%=request.getContextPath()%>/images/error.png" title="Không thành công" />
											</c:otherwise>
										</c:choose>
								    </display:column>
								    <%-- <display:column title="Message" headerClass="transhead" class="transdata" property="message" maxLength="20" style="border: 1px solid #CCC;" /> --%>
								    <%-- <display:column title="Pin" property="pin" /> --%>
								</display:table>
							</div>
						</div>
					</div>
				</div>
				<!-- / Body -->
			</div>
		</div>
		<!-- / Web Top -->

		<!-- Web Foot -->
		<jsp:include page="footer.jsp"></jsp:include>
		<!-- / Web Foot -->
	</div>
	<!-- / 1PAY WEB -->
	<script type="text/javascript">
		/*         */
		jQuery(function($) {

			$(function() {
				$('#hot_productslides').slides({
					preload : true,
					preloadImage : 'images/loading.gif',
					play : 5000,
					pause : 2500,
					hoverPause : true
				});
			});

		});
		/*   */
	</script>
	<!-- Create Menu Settings: (Menu ID, Is Vertical, Show Timer, Hide Timer, On Click ('all' or 'lev2'), Right to Left, Horizontal Subs, Flush Left, Flush Top) -->
	<script type="text/javascript">
		qm_create(0, false, 0, 250, false, false, false, false, false);
	</script>
	<!--[if IE]><iframe onload="on_script_loaded(function() { HashKeeper.reloading=false; });" style="display: none" name="hashkeeper" src="/blank" height="1" width="1" id="hashkeeper"></iframe><![endif]-->

</body>
</html>
