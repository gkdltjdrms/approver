<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인 화면</title>
    <style>
        body {
            background-color: #fafafa;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            color: #262626;
        }

        #main-container {
            max-width: 600px;
            width: 100%;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            overflow: auto; /* Make the main container scrollable */
            max-height: 80vh; /* Set a maximum height for the container */
        }
        .welcome-message {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 20px;
            color: #8e44ad;
        }

        #logout-btn,
        #write-post-btn,
        #delegate-approval-btn {
            background-color: #8e44ad;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            letter-spacing: 1px;
            margin-right: 10px;
            transition: background-color 0.3s;
        }

        #search-container {
            margin-top: 20px;
            display: flex;
            align-items: center;
        }

        #search-options,
        #search-status-options {
            margin-right: 10px;
        }

        #search-keyword,
        #search-status,
        #start-date,
        #end-date {
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #dbdbdb;
            border-radius: 4px;
            background-color: #fafafa;
            margin-right: 10px;
        }

        #search-btn {
            background-color: #8e44ad;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            letter-spacing: 1px;
            transition: background-color 0.3s;
        }

        #search-btn:hover {
            background-color: #6c3483;
        }

        #post-list {
            width: 100%;
            border-collapse: collapse;
        }

        #post-list th,
        #post-list td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }

        #post-list th {
            background-color: #8e44ad;
            color: #fff;
        }

        #post-list td {
            background-color: #fff;
            color: #262626;
        }

        #post-list tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #post-list tr:hover {
            background-color: #e0e0e0;
        }
    </style>
</head>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
    $(document).ready(function () {
    	
    	// 대리 결재 버튼에 대한 조건 기능 처리
    	// '사원', '대리'의 경우 버튼 숨김
        var rank = "${RANK}";

        var delegateButton = $("#delegate-approval-btn");

        if (delegateButton.length > 0) {
            if (rank === "사원" || rank === "대리") {
                delegateButton.hide();
            } else {
                delegateButton.show();
            }
        }
        
		// 글쓰기 버튼 기능 처리
        $("#write-post-btn").click(function () {
            window.location.href = "/myapp/signWrite";
        });
        
        // 각 행을 클릭했을 때 이벤트 처리
        $("#post-list tbody tr").click(function() {
            // 선택한 행의 seq 값 가져오기
            var seq = $(this).find("td:first-child").text();

            // seq 값으로 상세보기 페이지로 이동
            window.location.href = "${pageContext.request.contextPath}/signdatail?seq=" + seq;
        });
        
        // 검색 동기식으로 게시글 목록 출력
        $("#search-btn").click(function () {
            $("#searchfrm").submit();
        });
        
        // 결재상태(sign_status)에 따라 비동기식으로 게시글 목록 출력
        /////////////////////////////////////////////////////////////////////////////////////////////
		$("#search-status-options").change(function() {
		    // 선택된 값을 가져옵니다.
		    var selectedStatus = $(this).val();
		
		    $.ajax({
		        type: "GET",
		        url: "statuslist", 
		        data: { signStatus: selectedStatus }, 
		        dataType: "json",
		        success: function(response) {
		            // 받은 데이터로 테이블 업데이트
		        	StatusList(response.statusData);
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		            console.error("AJAX Error:", textStatus, errorThrown);
		            console.error("Response:", jqXHR.responseText);
		        }
		    });
		});

		function StatusList(data) {
		    var tbody = $("#post-list tbody");
		    tbody.empty();

		    for (var i = 0; i < data.length; i++) {
		        var post = data[i];
		        var row = $("<tr>");

		        row.append($("<td>").text(post.seq));
		        row.append($("<td>").text(post.name));
		        row.append($("<td>").text(post.subject));
		        row.append($("<td>").text(formatDate(post.regDate)));
		        row.append($("<td>").text(formatDate(post.signDate)));
		        row.append($("<td>").text(post.signer));
		        row.append($("<td>").text(post.signStatus));

		        tbody.append(row);
		    }
		}

		// 날짜 포맷 함수
		function formatDate(dateString) {
		    var options = { year: 'numeric', month: '2-digit', day: '2-digit' };
		    return new Date(dateString).toLocaleDateString(undefined, options);
		}
        /////////////////////////////////////////////////////////////////////////////////////////////
    });
</script>

<body>
    <div id="main-container">
        <div class="welcome-message">환영합니다, ${NAME}(${RANK})님!</div>
        <form id="logoutfrm" name="logoutfrm" action="logout" method="post">
            <input type="submit" id="logout-btn" value="로그아웃">
        </form>
        <form id="writefrm" name="writefrm" action="signWrite" method="post">
            <input type="submit" id="write-post-btn" value="글쓰기">
        </form>
        <form id="delegatefrm" name="delegatefrm" action="delegate" method="post">
            <button id="delegate-approval-btn">대리결재</button>
        </form>

        <div id="search-container">
        <form id="searchfrm" name="searchfrm" action="search" method="post">
            <select id="search-options" name="search-options">
                <option value="선택">선택</option>
                <option value="작성자">작성자</option>
                <option value="결재자">결재자</option>
                <option value="제목/내용">제목/내용</option>
            </select>

            <input type="text" id="search-keyword" name="search-keyword" placeholder="검색어를 입력하세요">

            <select id="search-status-options" name="search-status-options">
                <option value="결재상태">결재상태</option>
                <option value="임시저장">임시저장</option>
                <option value="결재요청">결재요청</option>
                <option value="결재중">결재중</option>
                <option value="결재완료">결재완료</option>
                <option value="반려">반려</option>
            </select>
		    <input type="date" id="start-date" name="start-date">
		    <input type="date" id="end-date" name="end-date">

            <button id="search-btn">검색</button>
        </form>
        </div>

        <table id="post-list">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>작성자</th>
                    <th>제목</th>
                    <th>작성일</th>
                    <th>결재일</th>
                    <th>결재자</th>
                    <th>결재상태</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="post" items="${sList}">
                    <tr>
                        <td>${post.seq}</td>
                        <td>${post.name}</td>
                        <td>${post.subject}</td>
                        <td><fmt:formatDate value="${post.regDate}" pattern="yyyy.MM.dd" /></td>
                        <td><fmt:formatDate value="${post.signDate}" pattern="yyyy.MM.dd" /></td>
                        <td>${post.name}</td>
                        <td>${post.signStatus}</td>
                    </tr>
                </c:forEach>
            </tbody>
	        <tbody>
		        <c:forEach var="post" items="${searchResults}">
		            <tr>
		                <td>${post.seq}</td>
		                <td>${post.name}</td>
		                <td>${post.subject}</td>
		                <td><fmt:formatDate value="${post.regDate}" pattern="yyyy.MM.dd" /></td>
		                <td><fmt:formatDate value="${post.signDate}" pattern="yyyy.MM.dd" /></td>
		                <td>${post.name}</td>
		                <td>${post.signStatus}</td>
		            </tr>
		        </c:forEach>
		    </tbody>
        </table>
    </div>
</body>

</html>
