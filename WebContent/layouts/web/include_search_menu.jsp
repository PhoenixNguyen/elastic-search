<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
	.sub_cat a{
		display: inline;
	}
	
	.option_selected{
		font-size: 14px;
	}
	
	.scope_search{
		padding-bottom: 10px;
	}
	
	.scope_search .search{
		background: url(../images/filter.png) no-repeat left;
		padding-left: 22px;
	}
	
	::-webkit-input-placeholder { font-style: italic; }
	::-moz-placeholder { font-style: italic; }
	::-ms-input-placeholder { font-style: italic; }
</style>

<div class="left_cat">
	<ul class="sub_cat">
		<c:set var="providerTotal" value="0"/>
		<c:forEach items="${model.providerFacets }" var="item">
			<c:set var="providerTotal" value="${providerTotal + item.getCount()}"/>
		</c:forEach>
		
		<li class="scope_search"><a href="#" class="search"><b>Lọc theo Providers:</b> ${providerTotal!=0?'':'<img src="../images/tick.png" title="Đang lọc theo provider" alt="" />' } <br/></a>
			<ul>
				<c:forEach items="${model.providerFacets }" var="item">
					<li>
						<c:if test="${item.getCount() != 0}">
							<a href="javascript:{}" class="provider ${param.provider == item.getTerm()?'slc_link':''}" provider="${item.getTerm() }" >${item.getTerm() } 
								<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>	
							 </a>
						</c:if>
						<c:if test="${item.getCount() == 0}">
							<span class="option_selected ${param.provider == item.getTerm()?'slc_link':''}" " >${item.getTerm() } 
								<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>
								&nbsp<c:if test="${item.getCount() == 0}"><a href="javascript:{}" class="filter" filter="provider"><img src="<%=request.getContextPath() %>/images/cross.png" title="Xóa lọc" /></a></c:if>	
							 </span>
						</c:if>
					 
					</li>
				</c:forEach>
				
			</ul>
		</li>
		
		<c:set var="typeTotal" value="0"/>
		<c:forEach items="${model.typeFacets }" var="item">
			<c:set var="typeTotal" value="${typeTotal + item.getCount()}"/>
		</c:forEach>
		
		<li class="scope_search"><a href="#" class="search"><b>Loại thẻ: ${typeTotal!=0?'':'<img src="../images/tick.png" title="Đang lọc theo loại thẻ" alt="" />' }</b> <br/></a>
			<ul>
				<c:forEach items="${model.typeFacets }" var="item">
					<li>
						<c:if test="${item.getCount() != 0}">
							<a href="javascript:{}" class="type ${param.type == item.getTerm()?'slc_link':''}" type="${item.getTerm() }" >${item.getTerm() } 
							<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>	
						 </a>
						</c:if>
						<c:if test="${item.getCount() == 0}">
							<span class="option_selected ${param.type == item.getTerm()?'slc_link':''}" type="${item.getTerm() }" >${item.getTerm() } 
								<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>	
							 </span>
							&nbsp<c:if test="${item.getCount() == 0}"><a href="javascript:{}" class="filter" filter="type"><img src="<%=request.getContextPath() %>/images/cross.png" title="Xóa lọc" /></a></c:if>
						</c:if>
						
						
						 
						
					</li>
				</c:forEach>
				
			</ul>
		</li>
		
		<c:set var="amountTotal" value="0"/>
		<c:forEach items="${model.amountFacets }" var="item">
			<c:set var="amountTotal" value="${amountTotal + item.getCount()}"/>
		</c:forEach>
		
		<li class="scope_search"><a href="#" class="search"><b>Mệnh giá: ${amountTotal!=0?'':'<img src="../images/tick.png" title="Đang lọc theo mệnh giá" alt="" />' }</b> <br/></a>
			<ul>
				<c:forEach items="${model.amountFacets }" var="item">
					<li>
						<c:if test="${item.getCount() != 0}">
							<a href="javascript:{}" class="amount ${param.amount == item.getTerm()?'slc_link':''}" amount="${item.getTerm() }" >${item.getTerm() } 
							<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>	
						 </a>
						</c:if>
						<c:if test="${item.getCount() == 0}">
							<span class="option_selected ${param.type == item.getTerm()?'slc_link':''}" type="${item.getTerm() }" >${item.getTerm() } 
								<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>	
							 </span>
							&nbsp<c:if test="${item.getCount() == 0}"><a href="javascript:{}" class="filter" filter="amount"><img src="<%=request.getContextPath() %>/images/cross.png" title="Xóa lọc" /></a></c:if>
						</c:if>
						
						
						 
						
					</li>
				</c:forEach>
				
			</ul>
		</li>
		
		<c:set var="statusTotal" value="0"/>
		<c:forEach items="${model.statusFacets }" var="item">
			<c:set var="statusTotal" value="${statusTotal + item.getCount()}"/>
		</c:forEach>
		
		<li class="scope_search"><a href="#" class="search"><b>Trạng thái: ${statusTotal!=0?'':'<img src="../images/tick.png" title="Đang lọc theo trạng thái" alt="" />' }</b> <br/></a>
			<ul>
				<c:forEach items="${model.statusFacets }" var="item">
					<li>
						<c:if test="${item.getCount() != 0}">
							<a href="javascript:{}" class="status ${param.status == item.getTerm()?'slc_link':''}" status="${item.getTerm() }" >${item.getTerm() } 
							<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>	
						 </a>
						</c:if>
						<c:if test="${item.getCount() == 0}">
							<span class="option_selected ${param.type == item.getTerm()?'slc_link':''}" type="${item.getTerm() }" >${item.getTerm() } 
								<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>	
							 </span>
							&nbsp<c:if test="${item.getCount() == 0}"><a href="javascript:{}" class="filter" filter="status"><img src="<%=request.getContextPath() %>/images/cross.png" title="Xóa lọc" /></a></c:if>
						</c:if>
						
						
						 
						
					</li>
				</c:forEach>
				
			</ul>
		</li>
	</ul>
</div>