<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="util.BoardStatusConverter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>

<style>
    /* CSS for mouseover effect on table rows */
    tbody tr:hover {
        background-color: #f5f5f5; /* Change this to your desired hover color */
    }
</style>



<head>

<meta charset="EUC-KR">
<title>Login Success</title>
</head>
<body>
	<%-- Check if session attributes are present --%>
	<c:if test="${empty sessionScope.loggedInUserId}">
		<%-- Redirect to payLogin.jsp if session attributes are not present --%>
		<% response.sendRedirect(request.getContextPath() + "/payLogin.jsp"); %>
	</c:if>

	<h1>�α��� ����</h1>
	<%
    // ���ǿ��� �α����� ������� ������ �����ɴϴ�.
    String loggedInUserId = (String) session.getAttribute("loggedInUserId");
    String loggedInUserName = (String) session.getAttribute("loggedInUserName");
    String loggedInUserRank = (String) session.getAttribute("loggedInUserRank");
%>



	<div id="head">
		<h3><%= loggedInUserName %>(<%= loggedInUserRank %>) �� ȯ���մϴ�
		</h3>
		<form action="logout" method="post">
			<button type="submit">�α׾ƿ�</button>
		</form>

		<c:choose>
			<c:when
				test="${loggedInUserRank eq '����' or loggedInUserRank eq '����'}">
				<button>�븮 ����</button>
			</c:when>
			<c:otherwise>
				<%-- Do nothing or display alternative content for other ranks --%>
			</c:otherwise>
		</c:choose>

		<div>
			<form action="payWriteForm" method="get">
				<input type="hidden" name="id" value="${loggedInUserId}"> <input
					type="hidden" name="userName" value="${loggedInUserName}">
				<input type="hidden" name="userRank" value="${loggedInUserRank}">
				<button type="submit">�۾���</button>
			</form>

		</div>
	</div>

	<!-- �˻�â �κ� -->
	<form id="searchForm" action="search" method="get" accept-charset="UTF-8">
	<input type="text" name="id" value="${loggedInUserId}" readonly>
		<select name="searchType">
			<option value="all">����</option>
			<option value="writer">�ۼ���</option>
			<option value="approver">������</option>
			<option value="titleContent">����+����</option>
		</select> <input type="text" name="searchKeyword" placeholder="�˻��� �Է�">

		<!-- �߰�: ������� ��Ӵٿ� �޴� -->
		<select name="approveStatus" id="approveStatus">
			<option value="">�������</option>
			<option value="save">�ӽ�����</option>
			<option value="wait">������</option>
			<option value="checking">������</option>
			<option value="finish">����Ϸ�</option>
			<option value="rejected">�ݷ�</option>
		</select>
		
		  <!-- �߰�: �����ϰ� ������ �Է� -->
		   <br><label  for="startDate">������:</label>
		    <input type="date" name="startDate" id="startDate">
		
		    <label for="endDate">������:</label>
		    <input type="date" id="endDate" name="endDate">

		<button type="submit">�˻�</button>
	</form>


	<!-- ���� ����Ʈ �κ� -->
	<table border="1">
		<thead>
			<tr>
				<th>��ȣ</th>
				<th>�ۼ���</th>
				<th>����</th>
				<th>�ۼ���</th>
				<th>������</th>
				<th>������</th>
				<th>�������</th>
			
				
			</tr>
		</thead>
		<tbody>
	

		<%-- ���⿡ �������� ������ ���� ����Ʈ �����͸� �ݺ��ؼ� ����ϴ� �κ��� ���ϴ�. --%>
			<c:forEach var="board" items="${boardList}">
		    <tr>
		        <td>${board.seq}</td>
		        <td>${board.memName}</td>
		        <td>${board.subject}</td>
		        <td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td><fmt:formatDate value="${board.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		  <%--       <td>${board.checkName}</td> --%>
		         <td>${board.checkBoardName}</td>
		        <td>${BoardStatusConverter.getKoreanStatus(board.payOption)}</td>
		    </tr>
		</c:forEach>


			
		</tbody>
	</table>
	<!-- jQuery�� �ε��ϴ� ��ũ��Ʈ �߰� -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
    $(document).ready(function() {

    	// ������ �ε� �� �˻�� �ҷ��ͼ� �˻� �ʵ忡 ����
        var searchKeyword = localStorage.getItem('searchKeyword');
        if (searchKeyword) {
            $('#searchKeyword').val(searchKeyword);
        }
        
     // approveStatus�� ���� ����� ������ �̺�Ʈ ó��
        $('#approveStatus').on('change', function() {
            // ���⿡�� AJAX ��û ����
            updateSearch();
        });


     // ���̺� �� Ŭ�� �̺�Ʈ ó��
        $('tbody').on('click', 'tr', function() {
            // ������ �࿡�� seq ���� ������
            var seq = $(this).find('td:first').text(); // ù ��° td ���� �ִ� �� (seq ��)
            
            // ������ �̵��ϴ� form ���� ����
            var form = $('<form></form>');
            form.attr('method', 'GET');
            form.attr('action', 'detail');

            // seq ���� form�� �߰�
            var seqField = $('<input></input>');
            seqField.attr('type', 'hidden');
            seqField.attr('name', 'seq');
            seqField.attr('value', seq);

            var userRankField = $('<input></input>');
            userRankField.attr('type', 'hidden');
            userRankField.attr('name', 'userRank');
            userRankField.attr('value', '<%= loggedInUserRank %>'); // �������� ������ userRank ��

            // form�� body�� �߰�
            form.append(seqField);
            $('body').append(form);

            // form ����
            form.submit();
        });


        
        
    });

    function updateSearch() {
        var form = document.getElementById('searchForm');

        // AJAX ��û�� ���� ������ ����
        var formData = {
            id: form.id.value,
            searchType: form.searchType.value,
            searchKeyword: form.searchKeyword.value,
            approveStatus: form.approveStatus.value,
            startDate: form.startDate.value,
            endDate: form.endDate.value
        };

        // AJAX ȣ���Ͽ� �˻� ��� ������Ʈ
        $.ajax({
            url: "searchoption",
            type: 'GET',
            async: true,
            data: formData,
            dataType: "json", // JSON ������ �����
            contentType: "application/json",
            success: function(data) {
                // �����κ��� ���� JSON �����͸� ����Ͽ� �ʿ��� �۾� ����
                console.log(data); // ������ ������ �ֿܼ� �α� ���
                console.log(JSON.stringify(data));

                // ���� �����͸� �̿��Ͽ� ���̺� ������Ʈ
                var tbody = $('tbody');
                tbody.empty(); // ���̺� ���� �ʱ�ȭ

                // ���� �����͸� �̿��Ͽ� ���̺� �� �߰�
                for (var i = 0; i < data.length; i++) {
                    var checkDate = data[i].checkDate ? new Date(data[i].checkDate).toLocaleString() : ''; // �������� �ִ� ��쿡�� ���� ����
                    var checkName = data[i].checkName || ''; // �����ڰ� �ִ� ��쿡�� ���, ������ �� ���ڿ�

                    var row = '<tr><td>' + data[i].seq +
                        '</td><td>' + data[i].memName +
                        '</td><td>' + data[i].subject +
                        '</td><td>' + new Date(data[i].regDate).toLocaleString() +
                        '</td><td>' + checkDate +
                        '</td><td>' + checkName +
                        '</td><td>' + data[i].payOption +
                        '</td></tr>';
                    tbody.append(row);
                }
            },
            error: function(request, status, error) {
                console.log(error); // ������ ������ �ֿܼ� ���� �α� ���
                alert('����3');
            }
        });

        return false;
    }
</script>
	

</body>
</html>
