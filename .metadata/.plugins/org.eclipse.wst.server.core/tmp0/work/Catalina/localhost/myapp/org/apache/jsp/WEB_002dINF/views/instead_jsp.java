/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.82
 * Generated at: 2024-01-24 05:12:15 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class instead_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("    <title>대리 결재 내용</title>\r\n");
      out.write("   <style>\r\n");
      out.write("        /* 수정된 CSS 스타일 */\r\n");
      out.write("        body {\r\n");
      out.write("            font-family: 'Arial', sans-serif;\r\n");
      out.write("            background-color: #f4f4f4;\r\n");
      out.write("            margin: 0;\r\n");
      out.write("            padding: 0;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        .container {\r\n");
      out.write("            width: 80%;\r\n");
      out.write("            margin: 20px auto;\r\n");
      out.write("            text-align: center;\r\n");
      out.write("            background-color: #fff;\r\n");
      out.write("            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\r\n");
      out.write("            padding: 20px;\r\n");
      out.write("            border-radius: 5px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        table {\r\n");
      out.write("            border-collapse: collapse;\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            margin-top: 20px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        th, td {\r\n");
      out.write("            border: 1px solid #dddddd;\r\n");
      out.write("            text-align: left;\r\n");
      out.write("            padding: 8px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        th {\r\n");
      out.write("            background-color: #f2f2f2;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        #buttons {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            margin-top: 20px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        button {\r\n");
      out.write("            margin: 0 10px;\r\n");
      out.write("            padding: 10px;\r\n");
      out.write("            cursor: pointer;\r\n");
      out.write("            background-color: #3498db;\r\n");
      out.write("            color: #fff;\r\n");
      out.write("            border: none;\r\n");
      out.write("            border-radius: 3px;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        select {\r\n");
      out.write("            width: 150px;\r\n");
      out.write("            padding: 8px;\r\n");
      out.write("            margin-right: 10px;\r\n");
      out.write("        }\r\n");
      out.write("    </style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("	");

    // 세션에서 로그인한 사용자의 정보를 가져옵니다.
    String loggedInUserId = (String) session.getAttribute("loggedInUserId");
    String loggedInUserName = (String) session.getAttribute("loggedInUserName");
    String loggedInUserRank = (String) session.getAttribute("loggedInUserRank");

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"container\">\r\n");
      out.write("    <h1>대리 결재</h1>\r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("<form>\r\n");
      out.write("    <!-- 테이블로 대리 결재자 정보 표시 -->\r\n");
      out.write("    <table>\r\n");
      out.write("        <tr>\r\n");
      out.write("            <th>대리 결재자</th>\r\n");
      out.write("            <td>\r\n");
      out.write("                <select id=\"delegateSelector\" >\r\n");
      out.write("                   <option value=\"chooseinstead\">선택 </option>\r\n");
      out.write("                    <option value=\"\">불러온 이름</option>\r\n");
      out.write("                    <!-- 필요에 따라 대리 결재자 옵션 추가 -->\r\n");
      out.write("                </select>\r\n");
      out.write("            </td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("            <th>직급</th>\r\n");
      out.write("            <td id=\"rank\">직급</td>\r\n");
      out.write("        </tr>\r\n");
      out.write("        <tr>\r\n");
      out.write("            <th>대리자 정보</th>\r\n");
      out.write("            <td id=\"delegateInfo\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loggedInUserName}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write('(');
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loggedInUserRank}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(")</td>\r\n");
      out.write("            <input type=\"hidden\" id=\"LoginId\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loggedInUserId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" />\r\n");
      out.write("             <input type=\"hidden\" id=\"LoginRank\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loggedInUserRank}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" >\r\n");
      out.write("              <input type=\"hidden\" id=\"LoginName\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loggedInUserName}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" >\r\n");
      out.write("            \r\n");
      out.write("        </tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    </form>\r\n");
      out.write("\r\n");
      out.write("    <!-- 버튼 영역 -->\r\n");
      out.write("    <div id=\"buttons\">\r\n");
      out.write("        <button onclick=\"cancel()\">취소</button>\r\n");
      out.write("        <button onclick=\"approve()\">승인</button>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<!-- 필요한 JavaScript 코드 -->\r\n");
      out.write("<script src=\"https://code.jquery.com/jquery-3.6.4.min.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("$(document).ready(function () {\r\n");
      out.write("    // 페이지 로드 시 대리자 목록 초기화\r\n");
      out.write("    updateDelegateInfo();\r\n");
      out.write("\r\n");
      out.write("    // 대리자 선택이 변경될 때마다 rank 업데이트\r\n");
      out.write("    $('#delegateSelector').change(function() {\r\n");
      out.write("        updateRank();\r\n");
      out.write("    });\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("function updateDelegateInfo() {\r\n");
      out.write("    // 세션에서 가져온 값들\r\n");
      out.write("    var loggedInUserId = $('#LoginId').val();\r\n");
      out.write("    var loggedInUserName = $('#LoginName').val();\r\n");
      out.write("    var loggedInUserRank = $('#LoginRank').val();\r\n");
      out.write("\r\n");
      out.write("    $.ajax({\r\n");
      out.write("        url: 'delegateList', // 위에서 작성한 Controller 메소드의 URL\r\n");
      out.write("        type: 'GET',\r\n");
      out.write("        dataType: 'json',\r\n");
      out.write("        data: {\r\n");
      out.write("            loggedInUserId: loggedInUserId,\r\n");
      out.write("            loggedInUserName: loggedInUserName,\r\n");
      out.write("            loggedInUserRank: loggedInUserRank\r\n");
      out.write("        },\r\n");
      out.write("        success: function (data) {\r\n");
      out.write("        	 console.log(data); // 데이터 확인용 로깅\r\n");
      out.write("            // 성공적으로 데이터를 받아왔을 때 실행되는 함수\r\n");
      out.write("            var delegateSelector = $('#delegateSelector');\r\n");
      out.write("            delegateSelector.empty(); // 기존 옵션 제거\r\n");
      out.write("\r\n");
      out.write("            // '선택' 옵션 추가\r\n");
      out.write("            delegateSelector.append('<option value=\"chooseinstead\">선택</option>');\r\n");
      out.write("\r\n");
      out.write("            // 대리자 목록을 반복하여 옵션 추가\r\n");
      out.write("       for (var i = 0; i < data.length; i++) {\r\n");
      out.write("    var delegate = data[i];\r\n");
      out.write("    delegateSelector.append('<option value=\"' + delegate.ID + '\">' + delegate.MEM_NAME + '</option>');\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("        },\r\n");
      out.write("        error: function () {\r\n");
      out.write("            // 실패했을 때 실행되는 함수\r\n");
      out.write("            alert('대리자 목록을 가져오는데 실패했습니다.');\r\n");
      out.write("        }\r\n");
      out.write("    });\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function updateRank() {\r\n");
      out.write("    var selectedDelegateId = $('#delegateSelector').val();\r\n");
      out.write("\r\n");
      out.write("    // 'chooseinstead'를 선택한 경우에는 '직급 없음'으로 표시\r\n");
      out.write("    if (selectedDelegateId === 'chooseinstead') {\r\n");
      out.write("        $('#rank').text('직급');\r\n");
      out.write("    } else {\r\n");
      out.write("        // 서버에 대리자 ID를 보내어 정보를 가져오는 예시\r\n");
      out.write("        $.ajax({\r\n");
      out.write("            url: 'getDelegateRank', // 실제 컨트롤러의 URL에 맞게 수정\r\n");
      out.write("            type: 'GET',\r\n");
      out.write("            dataType: 'json',\r\n");
      out.write("            data: {\r\n");
      out.write("                delegateId: selectedDelegateId\r\n");
      out.write("            },\r\n");
      out.write("            success: function (data) {\r\n");
      out.write("                // 성공적으로 데이터를 받아왔을 때 실행되는 함수\r\n");
      out.write("                // delegateInfoList를 업데이트하거나 직접 필요한 처리를 수행\r\n");
      out.write("                delegateInfoList = data;\r\n");
      out.write("                $('#rank').text(data.rank); // 'rank' 키로 접근\r\n");
      out.write("            },\r\n");
      out.write("            error: function () {\r\n");
      out.write("                alert('대리자 정보를 가져오는데 실패했습니다.');\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    }\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// 취소 버튼 클릭 시 동작\r\n");
      out.write("function cancel() {\r\n");
      out.write("    alert('취소');\r\n");
      out.write("    // 팝업 창 닫기\r\n");
      out.write("    window.close();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("// 승인 버튼 클릭 시 동작\r\n");
      out.write("function approve() {\r\n");
      out.write("	 var selectedDelegateId = $('#delegateSelector').val();\r\n");
      out.write("\r\n");
      out.write("	    if (selectedDelegateId === 'chooseinstead') {\r\n");
      out.write("	        alert('대리자를 선택하세요.');\r\n");
      out.write("	        return;\r\n");
      out.write("	    }\r\n");
      out.write("\r\n");
      out.write("	    // Make an AJAX request to the approveDelegate endpoint\r\n");
      out.write("	    $.ajax({\r\n");
      out.write("	        url: 'approveDelegate',\r\n");
      out.write("	        type: 'GET',\r\n");
      out.write("	        dataType: 'text',\r\n");
      out.write("	        data: {\r\n");
      out.write("	            loggedInUserId: $('#LoginId').val(),\r\n");
      out.write("	            delegateId: selectedDelegateId\r\n");
      out.write("	        },\r\n");
      out.write("	        contentType: \"application/x-www-form-urlencoded; charset=UTF-8\",\r\n");
      out.write("	        success: function (response) {\r\n");
      out.write("	            alert(response); // Show success or failure message\r\n");
      out.write("	            // Perform additional actions if needed\r\n");
      out.write("	            // 팝업 창 닫기\r\n");
      out.write("	            window.close();\r\n");
      out.write("	        },\r\n");
      out.write("	        error: function () {\r\n");
      out.write("	            alert('승인에 실패하였습니다.');\r\n");
      out.write("	        }\r\n");
      out.write("	    });\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
