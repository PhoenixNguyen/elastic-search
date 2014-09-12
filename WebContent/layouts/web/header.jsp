<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="vn.onepay.common.SharedConstants, vn.onepay.account.model.Account, java.lang.String"%>
<!-- Header -->
<div id="w_header">
	<a href="<%=request.getContextPath()%>/protected/home.html" class="logo_1pay"></a>

	<%-- <div id="nav">
		<ul id="qm0" class="qmmc">
			<li><a class="qmparent nav_menu${menuTrangChu?'_slc':''}" href="<%=request.getContextPath()%>/index.html">Trang chủ</a></li>
		</ul>
	</div> --%>
	<div id="account-header">
		<ul class="nav">
			<c:choose>
			<c:when test="${account_logined != null}">
				<li class="ui-button">
					<a href="#" class="header-nav-link" onclick="return false;">
						<span id="emsnippet-100eb3184c1c497f"><c:out value="${account_logined.username}" /></span>
						<span class="lbl_gray">L${account_logined_level}</span>
					</a>
					<div class="sub-nav chat-bubble" align="left">
						<ul>
							<li>
								<a href="<%= request.getContextPath()%>/protected/profile.html">
									<div class="ava_box">
										<img src="<%= request.getContextPath()%>/images/avatar.png" border="0" />
									</div>
									<div>
										<strong>${account_logined.username}</strong><br/>
									</div>
								</a>
							</li>
							<div class="clear"></div>
							<li><a href="<%= request.getContextPath()%>/protected/profile.html">Cấu hình</a></li>
							<li><a href="<%= request.getContextPath()%>/logout.html">Sign out</a></li>
						</ul>
						<div class="chat-bubble-arrow-border"></div>
						<div class="chat-bubble-arrow"></div>
					</div>
				</li>
				
			<%-- <li class="ui-button">
				<a class="header-nav-link" href="<%= request.getContextPath()%>/protected/dashboard.html">
					<span id="emsnippet-100eb3184c1c497f"><c:out value="${_const_cas_assertion_.principal.name}" /></span>
				</a>
			</li> --%>
			</c:when>
			<c:otherwise>
			<li>
				<div class="down" id="top-login-wrapper" style="padding-top:10px;">
				<a href="<%= request.getContextPath()%>/protected/card-report.html"><img src="<%=request.getContextPath()%>/images/door-open-in.png"
						border="0" align="absmiddle" hspace="5" /> Đăng nhập</a>
				</div>
			</li>
			</c:otherwise>
			</c:choose>
			<script type="text/javascript">
				$(document).ready(function() {
					$('.ui-button').live('click', function() {
						var newClass= $(this).attr('class') + '';
						if(newClass.indexOf(' active') > 0) {
							newClass = newClass.replace(/ active/gi,'');
						} else {
							newClass = newClass.replace(/ active/gi,'') + ' active';
						}
						$(this).attr('class',newClass);
					});
				});
			</script>
		</ul>
	</div>
	<div class="clear"></div>
</div>
<!-- / Header -->