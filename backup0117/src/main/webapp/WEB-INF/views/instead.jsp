<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>�븮 ���� ����</title>
    <style>
        /* CSS ��Ÿ�ϸ��� �ʿ信 ���� �����Ͻʽÿ�. */
        .container {
            width: 80%;
            margin: 20px auto;
            text-align: center;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }

        #buttons {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        button {
            margin: 0 10px;
            padding: 10px;
            cursor: pointer;
        }

        select {
            width: 150px;
        }
    </style>
</head>
<body>

	<%
    // ���ǿ��� �α����� ������� ������ �����ɴϴ�.
    String loggedInUserId = (String) session.getAttribute("loggedInUserId");
    String loggedInUserName = (String) session.getAttribute("loggedInUserName");
    String loggedInUserRank = (String) session.getAttribute("loggedInUserRank");
%>

<div class="container">
    <h1>�븮 ����</h1>
    
    
    
<form>
    <!-- ���̺�� �븮 ������ ���� ǥ�� -->
    <table>
        <tr>
            <th>�븮 ������</th>
            <td>
                <select id="delegateSelector" >
                   <option value="chooseinstead">���� </option>
                    <option value="">�ҷ��� �̸�</option>
                    <!-- �ʿ信 ���� �븮 ������ �ɼ� �߰� -->
                </select>
            </td>
        </tr>
        <tr>
            <th>����</th>
            <td id="rank">����</td>
        </tr>
        <tr>
            <th>�븮�� ����</th>
            <td id="delegateInfo">${loggedInUserName}(${loggedInUserRank})</td>
            <input type="hidden" id="LoginId" value="${loggedInUserId}" />
             <input type="hidden" id="LoginRank" value="${loggedInUserRank}" >
              <input type="hidden" id="LoginName" value="${loggedInUserName}" >
            
        </tr>
    </table>
    </form>

    <!-- ��ư ���� -->
    <div id="buttons">
        <button onclick="cancel()">���</button>
        <button onclick="approve()">����</button>
    </div>
</div>

<!-- �ʿ��� JavaScript �ڵ� -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
$(document).ready(function () {
    // ������ �ε� �� �븮�� ��� �ʱ�ȭ
    updateDelegateInfo();

    // �븮�� ������ ����� ������ rank ������Ʈ
    $('#delegateSelector').change(function() {
        updateRank();
    });
});

function updateDelegateInfo() {
    // ���ǿ��� ������ ����
    var loggedInUserId = $('#LoginId').val();
    var loggedInUserName = $('#LoginName').val();
    var loggedInUserRank = $('#LoginRank').val();

    $.ajax({
        url: 'delegateList', // ������ �ۼ��� Controller �޼ҵ��� URL
        type: 'GET',
        dataType: 'json',
        data: {
            loggedInUserId: loggedInUserId,
            loggedInUserName: loggedInUserName,
            loggedInUserRank: loggedInUserRank
        },
        success: function (data) {
        	 console.log(data); // ������ Ȯ�ο� �α�
            // ���������� �����͸� �޾ƿ��� �� ����Ǵ� �Լ�
            var delegateSelector = $('#delegateSelector');
            delegateSelector.empty(); // ���� �ɼ� ����

            // '����' �ɼ� �߰�
            delegateSelector.append('<option value="chooseinstead">����</option>');

            // �븮�� ����� �ݺ��Ͽ� �ɼ� �߰�
       for (var i = 0; i < data.length; i++) {
    var delegate = data[i];
    delegateSelector.append('<option value="' + delegate.ID + '">' + delegate.MEM_NAME + '</option>');
}

        },
        error: function () {
            // �������� �� ����Ǵ� �Լ�
            alert('�븮�� ����� �������µ� �����߽��ϴ�.');
        }
    });
}

function updateRank() {
    var selectedDelegateId = $('#delegateSelector').val();

    // 'chooseinstead'�� ������ ��쿡�� '���� ����'���� ǥ��
    if (selectedDelegateId === 'chooseinstead') {
        $('#rank').text('����');
    } else {
        // ������ �븮�� ID�� ������ ������ �������� ����
        $.ajax({
            url: 'getDelegateRank', // ���� ��Ʈ�ѷ��� URL�� �°� ����
            type: 'GET',
            dataType: 'json',
            data: {
                delegateId: selectedDelegateId
            },
            success: function (data) {
                // ���������� �����͸� �޾ƿ��� �� ����Ǵ� �Լ�
                // delegateInfoList�� ������Ʈ�ϰų� ���� �ʿ��� ó���� ����
                delegateInfoList = data;
                $('#rank').text(data.rank); // 'rank' Ű�� ����
            },
            error: function () {
                alert('�븮�� ������ �������µ� �����߽��ϴ�.');
            }
        });
    }
}

// ��� ��ư Ŭ�� �� ����
function cancel() {
    alert('���');
    // �˾� â �ݱ�
    window.close();
}

// ���� ��ư Ŭ�� �� ����
function approve() {
    alert('���� ��ư Ŭ��');
    // �ʿ��� ���� �߰�
}

</script>


</body>
</html>
