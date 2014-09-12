<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el"%>
<display:setProperty name="paging.banner.placement" value="bottom" />
<display:setProperty name="paging.banner.group_size">4</display:setProperty>
<display:setProperty name="paging.banner.page.separator"> </display:setProperty>
<display:setProperty name="paging.banner.item_name">
	<span class="pagebanner"> kết quả </span>
</display:setProperty>
<display:setProperty name="paging.banner.items_name">
	<span class="pagebanner"> kết quả </span>
</display:setProperty>
<display:setProperty name="paging.banner.no_items_found">
	<span class="pagebanner"> No {0} tìm thấy. </span>
</display:setProperty>
<display:setProperty name="paging.banner.one_item_found">
	<span class="pagebanner"> Một {0} tìm thấy. </span>
</display:setProperty>
<display:setProperty name="paging.banner.all_items_found">
	<!-- <span class="pagebanner"> {0} {1} tìm thấy, hiển thị tất cả {2}. </span> --> 
	<span class="pagebanner"></span> 
</display:setProperty>
<display:setProperty name="paging.banner.some_items_found">
	<!-- <span class="pagebanner"> {0} {1} tìm thấy, hiển thị từ {2} tới {3}. </span> -->
	<span class="pagebanner"></span> 
</display:setProperty>
<display:setProperty name="paging.banner.full">
	<span class="pagelinks">
		{0}  
		<a href="{1}" class="link_green" style="margin-left: 5px;background: none;margin: 0;padding: 0;font-size: 9pt;">&lt;&lt; Đầu</a>
		<a href="{2}" class="link_green" style="margin-left: 5px;background: none;margin: 0;padding: 0;font-size: 9pt;">&lt; Trước</a>&nbsp;| 
		<a href="{3}" class="link_green" style="margin-left: 5px;background: none;margin: 0;padding: 0;font-size: 9pt;">Tiếp &gt;</a>
		<a href="{4}" class="link_green" style="margin-left: 5px;background: none;margin: 0;padding: 0;font-size: 9pt;">Cuối &gt;&gt;</a>
	</span> 
</display:setProperty>
<display:setProperty name="paging.banner.first">
	<span class="pagelinks"> 
		{0} 
		<a href="{3}" class="link_green" style="margin-left: 5px;background: none;margin: 0;padding: 0;font-size: 9pt;">Tiếp &gt;</a>
		<a href="{4}" class="link_green" style="margin-left: 5px;background: none;margin: 0;padding: 0;font-size: 9pt;">Cuối &gt;&gt;</a>
	</span>
</display:setProperty>
<display:setProperty name="paging.banner.last">
	<span class="pagelinks"> 
		{0} 
		<a href="{1}" class="link_green" style="margin-left: 5px;background: none;margin: 0;padding: 0;font-size: 9pt;">&lt;&lt; Đầu</a>
		<a href="{2}" class="link_green" style="margin-left: 5px;background: none;margin: 0;padding: 0;font-size: 9pt;">&lt; Trước</a> 
	</span>
</display:setProperty>
<display:setProperty name="paging.banner.page.selected"><strong style="margin-right: 10px;">{0}</strong></display:setProperty>
<display:setProperty name="paging.banner.onepage">
	<span class="pagelinks">{0}</span>
</display:setProperty>
<display:setProperty name="export.banner">
	<div class="exportlinks"> Kết xuất dữ liệu: {0} </div>
</display:setProperty>
<display:setProperty name="basic.msg.empty_list_row">
		<tr class="empty"><td colspan="{0}">Không tìm thấy kết quả để hiển thị.</td></tr> 
</display:setProperty>
<display:setProperty name="basic.msg.empty_list">
		Không tìm thấy kết quả để hiển thị. 
</display:setProperty>
