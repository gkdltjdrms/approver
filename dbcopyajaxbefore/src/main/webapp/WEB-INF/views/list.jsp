<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>글목록</title>

    <style>
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

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #f2f2f2;
        }

        .date-cell {
            white-space: nowrap;
        }

        .no-data-message {
            font-size: 16px;
            color: #777;
            margin-top: 20px;
        }
    </style>

</head>
<body>
    <h1>글목록</h1>

    <form action="goToWrite" method="get">
        <input type="submit" value="글쓰기">
    </form>

    <form action="goDelete" method="get">
        <input type="submit" value="삭제">
    </form>

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

    <div id="table-container">
        <%@include file="table.jsp" %>
    </div>

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


    <!-- jQuery CDN 추가 -->
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>

    <script>
    $(document).ready(function () {
        loadData(1);

        $("#search_go").on("click", function () {
            loadData(1);
        });

        $(document).on("click", ".page-link", function (e) {
            e.preventDefault();
            var clickedPage = $(this).data("page");
            loadData(clickedPage);
        });

        function loadData(page) {
            $("#page").val(page);

            $.ajax({
                type: "GET",
                url: "searchgo",
                data: $("#searchForm").serialize(),
                dataType: "html",
                success: function (html) {
                    console.log("AJAX Success");
                    console.log(html);
                    var tableContainer = $("#table-container");
                    tableContainer.empty();
                    tableContainer.append(html);
                    
                    
                },
                error: function (error) {
                    console.error("Error fetching data:", error);
                }
            });
        }
    });

    </script>
</body>
</html>
