<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>글목록</title>

    <style>
        /* 이전 스타일 유지 */
        .pagination {
            display: flex;
            list-style: none;
            padding: 0;
        }

        .pagination-item {
            margin: 0 5px;
        }

        .pagination-item a, .pagination-item span {
            text-decoration: none;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            color: #333;
            background-color: #fff;
            transition: background-color 0.3s ease;
        }

        .pagination-item span {
            color: red;
            background-color: #f0f0f0;
        }

        .pagination-item a:hover {
            background-color: #f0f0f0;
        }

        /* 글 목록 테이블 스타일 */
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px; /* 셀 내용과 경계 사이의 여백 늘림 */
            text-align: center; /* 텍스트 가운데 정렬 */
        }

        th {
            background-color: #f2f2f2;
        }

        /* 날짜 형식 스타일 */
        .date-cell {
            white-space: nowrap;
        }

        /* 추가: 검색 결과 메시지 스타일 */
        .no-data-message {
            font-size: 16px;
            color: #777;
            margin-top: 20px;
        }
    </style>

</head>
<body>
    <h1>글목록</h1>

    <!-- 글쓰기 버튼 -->
    <form action="goToWrite" method="get">
        <input type="submit" value="글쓰기">
    </form>

    <form action="goDelete" method="get">
        <input type="submit" value="삭제">
    </form>

    <!-- 검색 기능 구현 -->
    <form id="searchForm" action="searchgo" method="get">
        <input type="text" id="keyword" name="keyword" placeholder="검색어를 입력하세요..." value="${param.keyword}">

        <!-- 검색 옵션 추가 -->
        <select id="searchOption" name="searchOption">
            <option value="all" <c:if test="${param.searchOption == 'all'}">selected</c:if>>전체</option>
            <option value="title" <c:if test="${param.searchOption == 'title'}">selected</c:if>>제목</option>
            <option value="titleAndAuthor" <c:if test="${param.searchOption == 'titleAndAuthor'}">selected</c:if>>제목+작성자</option>
            <option value="id" <c:if test="${param.searchOption == 'id'}">selected</c:if>>id</option>
        </select>

        <!-- Additional input fields for date range -->
        <label for="startDate">시작 날짜:</label>
        <input type="date" id="startDate" name="startDate" placeholder="시작 날짜" value="${param.startDate}">
        <label for="endDate">종료 날짜:</label>
        <input type="date" id="endDate" name="endDate" placeholder="종료 날짜" value="${param.endDate}">
        <input type="hidden" id="page" name="page" value="1">
    </form>
    <button id="search_go">검색</button>

    <table border="1">
        <thead>
            <tr>
                <th>글 번호</th>
                <th>작성자 (id)</th>
                <th>작성자 이름</th>
                <th>제목</th>
                <th>작성일</th>
                <th>수정일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${listOfPosts}" var="post">
                <tr>
                    <td>${post.seq}</td>
                    <td>${post.mem_id}</td>
                    <td>${post.mem_name}</td>
                    <td>
                        <!-- 글 제목에 링크 추가 -->
                        <a href="listinfo?seq=${post.seq}">${post.board_subject}</a>
                    </td>
                    <td class="date-cell">${post.reg_date}</td>
                    <td class="date-cell">${post.upt_date}</td>
                    <td>${post.view_cnt}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 페이지 번호 표시 -->
    <ul class="pagination" id="pagination-container">
        <!-- 맨 처음 버튼 -->
        <li class="pagination-item"><a href="#" class="page-link" data-page="1">처음</a></li>
        <!-- 이전 버튼 -->
        <li class="pagination-item"><a href="#" class="page-link" data-page="${currentPage - 1}">이전</a></li>
        <!-- 페이지 번호는 AJAX로 동적으로 추가될 예정입니다. -->
        <!-- 다음 버튼 -->
        <li class="pagination-item"><a href="#" class="page-link" data-page="${currentPage + 1}">다음</a></li>
        <!-- 맨 끝 버튼 -->
        <li class="pagination-item"><a href="#" class="page-link" data-page="${totalPages}">맨끝</a></li>
    </ul>

    <c:choose>
        <c:when test="${empty listOfPosts}">
            <!-- 데이터가 없을 경우 -->
            <p class="no-data-message">데이터가 없습니다.</p>
        </c:when>
    </c:choose>

    <!-- jQuery CDN 추가 -->
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>

    <script>
    $(document).ready(function () {
        // 초기 로딩 시에 페이지 정보를 받아오도록 수정
        loadData(1);

        // 검색 버튼 클릭 시
        $("#search_go").on("click", function () {
        	  var formData = {
        		        keyword: $("#keyword").val(),
        		        searchOption: $("#searchOption").val(),
        		        startDate: $("#startDate").val(),
        		        endDate: $("#endDate").val()
        		    };

        		    // loadData 함수 호출 시 직접 데이터를 JSON 문자열로 변환해서 전달
        		    loadData(1, JSON.stringify(formData));
        });

        // 페이지 번호 클릭 시
        $(document).on("click", ".page-link", function (e) {
            e.preventDefault();
            var clickedPage = $(this).data("page");
            loadData(clickedPage);
        });
        
            

        function loadData(page) {
            // 페이지 정보를 서버에 전달
            $("#page").val(page);

            $.ajax({
                type: "GET",
                url: "searchgo",
                data: $("#searchForm").serialize(),
                success: function (data) {
                    console.log("AJAX Success:", data);

                    // 서버에서 받은 데이터를 활용하여 동적으로 테이블에 데이터 추가
                    var tableBody = $("table tbody");
                    tableBody.empty(); // 이전 데이터 삭제

                    if (data.listOfPosts && data.listOfPosts.length > 0) {
                        // 테이블 헤더를 추가
                        var tableHeader = $("table thead");
                        tableHeader.empty(); // 이전 데이터 삭제
                        tableHeader.append("<tr><th>글 번호</th><th>작성자 (id)</th><th>작성자 이름</th><th>제목</th><th>작성일</th><th>수정일</th><th>조회수</th></tr>");

                        $.each(data.listOfPosts, function (index, post) {
                            var row = $("<tr>");
                            row.append($("<td>").text(post.seq));
                            row.append($("<td>").text(post.mem_id));
                            row.append($("<td>").text(post.mem_name));
                            row.append($("<td>").append($("<a>").attr("href", "listinfo?seq=" + post.seq).text(post.board_subject)));
                            // 테이블에 날짜 데이터 출력 시 날짜 형식 변환 적용
                            row.append($("<td>").text(formatDate(post.reg_date)));
                            row.append($("<td>").text(formatDate(post.upt_date)));
                            row.append($("<td>").text(post.view_cnt));
                            tableBody.append(row);
                        });
                    } else {
                        // 데이터가 없는 경우 메시지 추가
                        var noDataMessage = $("<tr>").append($("<td colspan='7'>").text("데이터가 없습니다."));
                        tableBody.append(noDataMessage);

                        // 테이블 헤더를 추가
                        var tableHeader = $("table thead");
                        tableHeader.empty(); // 이전 데이터 삭제
                        tableHeader.append("<tr><th>글 번호</th><th>작성자 (id)</th><th>작성자 이름</th><th>제목</th><th>작성일</th><th>수정일</th><th>조회수</th></tr>");
                    }

                    // 페이지 번호 동적으로 추가
                    var paginationContainer = $("#pagination-container");
                    paginationContainer.empty();

                    // 맨 처음 버튼
                    var firstPageLink = $("<a>").attr("href", "#").addClass("page-link").data("page", 1).text("처음");
                    var firstPageListItem = $("<li>").addClass("pagination-item").append(firstPageLink);
                    paginationContainer.append(firstPageListItem);

                    // 이전 버튼
                    var prevPageLink = $("<a>").attr("href", "#").addClass("page-link").data("page", data.currentPage - 1).text("이전");
                    var prevPageListItem = $("<li>").addClass("pagination-item").append(prevPageLink);
                    paginationContainer.append(prevPageListItem);
					
                 // 페이지 번호 표시 현재페이지 일수 빨간색
                    if (data.currentPage === 1 || data.currentPage === 2) {
                        // 현재 페이지가 1 또는 2인 경우 항상 5개의 페이지 번호를 표시
                        for (var i = 1; i <= 5; i++) {
                            var pageLink = $("<a>").attr("href", "#").addClass("page-link").data("page", i);
                            if (i === data.currentPage) {
                                pageLink.append($("<span>").css("color", "red").text(i));
                            } else {
                                pageLink.text(i);
                            }
                            var listItem = $("<li>").addClass("pagination-item").append(pageLink);
                            paginationContainer.append(listItem);
                        }
                    } else {
                        // 현재 페이지가 1 또는 2가 아닌 경우 일반적인 표시 방식 사용
                        for (var i = Math.max(1, data.currentPage - 2); i <= Math.min(data.totalPages, data.currentPage + 2); i++) {
                            var pageLink = $("<a>").attr("href", "#").addClass("page-link").data("page", i);
                            if (i === data.currentPage) {
                                pageLink.append($("<span>").css("color", "red").text(i));
                            } else {
                                pageLink.text(i);
                            }
                            var listItem = $("<li>").addClass("pagination-item").append(pageLink);
                            paginationContainer.append(listItem);
                        }
                    }



                    // 다음 버튼
                    // 현재 페이지가 마지막 페이지라면 다음 버튼을 비활성화
					if (data.currentPage === data.totalPages) {
					    nextPageListItem.addClass("disabled");
					    nextPageLink.off("click"); // 클릭 이벤트 제거
					}
                    
                    var nextPageLink = $("<a>").attr("href", "#").addClass("page-link").data("page", data.currentPage + 1).text("다음");
                    var nextPageListItem = $("<li>").addClass("pagination-item").append(nextPageLink);
                    paginationContainer.append(nextPageListItem);

                    // 맨 끝 버튼
                    var lastPageLink = $("<a>").attr("href", "#").addClass("page-link").data("page", data.totalPages).text("맨끝");
                    var lastPageListItem = $("<li>").addClass("pagination-item").append(lastPageLink);
                    paginationContainer.append(lastPageListItem);

                    // TODO: 응답 처리 (페이지 번호 등)
                },
                error: function (error) {
                    console.error("Error fetching data:", error);
                }
            });
        }
    });

 // 날짜 형식 변환 함수
    function formatDate(dateString) {
        // null 체크
        if (dateString === null) {
            return "없음";
        }

        // 받은 날짜 문자열을 Date 객체로 변환
        var date = new Date(dateString);

        // 포맷을 지정해서 반환 (예: YYYY-MM-DD HH:mm:ss)
        var formattedDate = date.toLocaleString('en-US', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });

        return formattedDate;
    }


    </script>
</body>
</html>
