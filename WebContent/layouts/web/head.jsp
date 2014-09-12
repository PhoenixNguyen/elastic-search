<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=no;" />

<title>1Pay.vn - Kết nối nhanh, thanh toán linh hoạt | Ket noi nhanh, thanh toan linh hoat</title>

<link rel="icon" href="<%=request.getContextPath()%>/favicon.ico" type="image/x-icon">
<link href="<%=request.getContextPath()%>/css/daterangepicker.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/home_slider.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/1pay_style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/reveal.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath()%>/js/jquery.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/date.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/daterangepicker.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.slides.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/core_menu.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.leanModal.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.reveal.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.blockUI.js" type="text/javascript"></script>

<!-- Add-On Core Code (Remove when not using any add-on's) -->

<style type="text/css">.qmfv{visibility:visible !important;}.qmfh{visibility:hidden !important;}</style>

<script type="text/JavaScript">var qmad = new Object();qmad.bvis="";qmad.bhide="";</script>

<!-- Add-On Settings -->
<script type="text/JavaScript">

	/*******  Menu 0 Add-On Settings *******/
	var a = qmad.qm0 = new Object();

	// IE Over Select Fix Add On
	a.overselects_active = true;

</script>
<script type="text/javascript">
	var Constants = { DO_NOT_ADD_A_TRAILING_COMMA: true};
    function global_report_exception (e, f, l, tb, force) {
        if (!window.reported_exception || force) {
            var stack_str = "";
            try {
                if (!tb) {
                    var stack = get_stack_rep();
                    stack.pop(); // remove global_report_exception
                    stack.pop(); // remove onerror handler
                    stack_str = stack.join("\n");
                }
            } catch (e) { }
            var log = function() {
                var parameters = {
                    'e': e,
                    'f': f || window.location.href,
                    'l': l,
                    'loc': window.location.href,
                    'ref': Constants.referrer,
                    'tb': tb || stack_str,
                    'trace': Trace && Trace.get()
                };
                new Ajax.DBRequest("/jse", {
                    parameters: parameters,
                    noAutonotify: true,
                    no_watch: true
                });
            };
            if (typeof(Ajax) == 'undefined') {
                document.observe('script:loaded', log);
            } else {
                log();
            }
            window.reported_exception = e;
       }
    }

    // this constant gets set to true at the bottom of dropbox.js
    window.LoadedJsSuccessfully = false;
    window.onerror = function (e, f, l) {
        global_report_exception(e, f, l);
    };

    var old_onload = window.onload;
    window.onload = function() {
        if (!window.LoadedJsSuccessfully) {
            var url = encodeURIComponent(window.location.href);
            new Image().src = '/jse?e=failed+to+load+script&loc=' + url + '&f=' + url;
        }
        old_onload && old_onload();
    };
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$('.filter_label').live('click', function() {
			var lbClass = $(this).attr('class') + '';
			if(lbClass.indexOf('box_locketqua_hide')>0) {
				lbClass = lbClass.replace(/ close/gi,' open');
				$(this).attr('class', lbClass.replace(/ box_locketqua_hide/gi,''));
				$('.box_locketqua').fadeIn(300);
			} else {
				lbClass = lbClass.replace(/ open/gi,' close');
				$(this).attr('class', lbClass + ' box_locketqua_hide');
				$('.box_locketqua').fadeOut(300);
			}
		});
	});
</script>
<script type="text/javascript">
	$(function() {
  			$('a[rel*=leanModal]').leanModal({ top : 200, closeButton: ".modal_close" });		
	});
</script>

<!--Start of Zopim Live Chat Script-->
<script type="text/javascript">
window.$zopim||(function(d,s){var z=$zopim=function(c){z._.push(c)},$=z.s=
d.createElement(s),e=d.getElementsByTagName(s)[0];z.set=function(o){z.set.
_.push(o)};z._=[];z.set._=[];$.async=!0;$.setAttribute('charset','utf-8');
$.src='//v2.zopim.com/?1Px2VMF7I6b2Sl4YNzp1R0AuCPcTQ54r';z.t=+new Date;$.
type='text/javascript';e.parentNode.insertBefore($,e)})(document,'script');
</script>
<!--End of Zopim Live Chat Script-->

<script type="text/javascript"
	src="<%=request.getContextPath() %>/js/jquery.qtip-1.0.0-rc3.js"
	language="javascript"></script>