<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="head.jsp"></jsp:include>
</head>
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
					<div class="r_01">
						<div class="col_01">
							<div class="mod_vieclammoi">
								<div id="hot_productslides">
									<div class="slides_container">
										<div>
											<iframe width="500" height="375" src="//www.youtube.com/embed/LcZ0zVNsfF4" frameborder="0" allowfullscreen></iframe>
										</div>
										<div>
											<a href="#"> <img src="images/slide_img_01.jpg" border="0" /></a>
										</div>

										<div>
											<a href="#"> <img src="images/slide_img_02.jpg" border="0" /></a>
										</div>

										<div>
											<a href="#"> <img src="images/slide_img_03.jpg" border="0" /></a>
										</div>
									</div>
									<a href="#" class="prev"></a> <a href="#" class="next"></a>
								</div>
							</div>
						</div>

						<div class="col_02">
							<div class="mod_dangkysrv">
								<h1>Kết nối nhanh, thanh toán linh hoạt</h1>
								<a href="<%=request.getContextPath() %>/dich-vu.html" class="btn_khamphasrv">Khám phá ngay</a>  hoặc <a href="<%= request.getContextPath() %>/dang-ky.html">Đăng ký tài khoản</a> | <a href="<%= request.getContextPath() %>/password_recovery.html">Lấy lại mật khẩu</a>
							</div>
						</div>
					</div>

					<div class="r_02">
						<div class="col_3">
							<div class="ad_title">Đơn kết nối</div>
							<div class="ad_des">Một kết nối duy nhất, đơn giản nhất cho mọi dịch vụ thanh toán.</div>
						</div>
						<div class="col_3">
							<div class="ad_title">Đa dịch vụ, đủ an toàn</div>
							<div class="ad_des">
								Hỗ trợ mọi phương thức thanh toán, cho
								mọi nền tảng thiết bị, ứng dụng, với công nghệ bảo mật và an
								toàn nhất.
							</div>
						</div>
						<div class="col_4">
							<div class="ad_title">Hoàn toàn minh bạch và uy tín</div>
							<div class="ad_des">Công cụ thống kê chi tiết minh bạch nhất với uy tín cao nhất.</div>
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
	<jsp:include page="include_java_script_footer.jsp"></jsp:include>
</body>
</html>
