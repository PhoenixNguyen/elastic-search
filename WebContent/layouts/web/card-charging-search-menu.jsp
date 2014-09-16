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
	
	.scope_search .slc_link{
		font-size: 14px;
		color: #FFA200;
		
	}
</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		var keys = [];
		<c:forEach items="${model.fieldMaps }" var="map">
			keys.push('<c:out value="${map.key}"/>');
		</c:forEach>
		
		$.each(keys, function(k , v){
			//alert(v);
			$('.scope_search .'+v+'').live('click', function(){
				
				var item = $(this).attr(v);
				var itemChild = $('form[name=cardReport] input[name='+v+']');
				
				if(typeof itemChild.val() === 'undefined'){
					 $('form[name=cardReport]').append('<input type="hidden" name="'+v+'" value="'+item+'"/>');
				}
				
				itemChild.val(item);
				
				$('form[name=cardReport]').submit();
			});
			
		});
		
		//Delete filter
		$('.scope_search .filter').live('click', function(){
			var child = $(this).attr('filter');
			
			$('form[name=cardReport] input[name='+child+']').remove();
			
			$('form[name=cardReport]').submit();
		});
	});
</script>

<div class="left_cat">
	<ul class="sub_cat">
		<c:forEach items="${model.fieldMaps }" var="map">
		
			<li class="scope_search"><a href="#" class="search"><b>${map.value }:</b>  <br/></a>
				<ul>
					<c:forEach items="${model.facetsMap[map.key] }" var="item">
					
						<li>
							<c:if test="${item.getCount() != 0}">
								<a href="javascript:{}" class="${map.key } ${param[map.key] == item.getTerm()?'slc_link':''}" ${map.key }="${item.getTerm() }" >${item.getTerm() } 
									<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>	
								 </a>
							</c:if>
							<c:if test="${item.getCount() == 0}">
								<span class="option_selected ${param[map.key] == item.getTerm()?'slc_link':''}" " >${item.getTerm() } 
									<c:if test="${item.getCount() != 0}">&nbsp(${item.getCount()})</c:if>
									&nbsp<c:if test="${item.getCount() == 0}"><a href="javascript:{}" class="filter" filter="${map.key }"><img src="<%=request.getContextPath() %>/images/cross.png" title="Xóa lọc" /></a></c:if>	
								 </span>
							</c:if>
						 
						</li>
					</c:forEach>
					
				</ul>
			</li>
		</c:forEach>
	</ul>
</div>