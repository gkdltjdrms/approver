<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#head {
    margin-bottom: 30px;
    height: 75px;
    border-bottom: 1px solid #000;
    display: flex;
    justify-content: flex-start;
    align-items: center;
}
footer {
    margin-bottom: 50px;
    height: 60px;
    border-top: 1px solid #000; /* 상단에 가로선 스타일 및 색상 설정 */
    display: flex;
    justify-content: flex-end;
    align-items: center;
}

footer button {
    margin-left: 10px; /* 버튼 사이의 간격 설정 (선택사항) */
}

</style>

</head>
<body>
	 <div id="head">회원가입</div>
	
	<form>
		<label for="id">ID:</label> <input type="text" id="id" name="id" style="width: 120px;"
			required>
		<button type="submit">중복체크</button>
		<br>
		<br> <label for="password">비밀번호:</label> <input type="password"
			id="password" name="password" required> <br>
		<br> <label for="confirm_password">비밀번호 확인:</label> <input
			type="password" id="confirm_password" name="confirm_password"
			required><br>
		<br> <label for="name">이름:</label>
<input type="text" id="name" name="name" required maxlength="5"><br>

		<br> <label for="email">이메일:</label> <input type="text"
			id="email" name="email" required>@
			<input type="text"
			id="email2" name="email2" required>
			
			
			<br> <br> 
			<label for="phone">휴대폰:</label>
				<select id="phone" name="phone" required>
				  <option value="010">010</option>
				  <option value="011">011</option>
				  <option value="016">016</option>
				  <option value="017">017</option>
				  <option value="019">019</option>
</select>
-
<input type="text" id="phone2" name="phone2" required maxlength="4" style="width: 70px;">
-
<input type="text" id="phone3" name="phone3" required maxlength="4" style="width: 70px;">
			

			
		<br><br>
		<label>성별:</label> <input type="radio" id="male"
			name="gender" value="남자"> <label for="male">남자</label> <input
			type="radio" id="female" name="gender" value="여자"> <label
			for="female">여자</label><br>
			
			
			
		<br> <label for="ssn">주민번호:</label> <input type="text" id="ssn"
			name="ssn"required> -
			<input type="text" id="ssn2"
			name="ssn2"required>
			
			
			
			
			
			<br>
		<br> <label for="address">주소:</label> <input type="text"
			id="address" name="address" required><br>
			*주소는 (시/도)만 입력해주세요 (예: 경기도, 서울특별시, 경상남도 등)
		<br><br>
		
		<footer>
		<button type="submit">가입신청</button>
		<button type="reset">다시입력</button>
		<button type="button">취소</button>
		</footer>
		
	</form>




<script>
const nameInput = document.getElementById("name");
nameInput.addEventListener("input", function (e) {
    const inputValue = nameInput.value;
    const namePattern = /^[A-Za-z가-힣]{0,5}$/; // 한글, 영어로 5글자까지 입력 가능
    if (!namePattern.test(inputValue)) {
        nameInput.setCustomValidity("이름은 한글 또는 영어로 5글자까지 입력 가능합니다.");
    } else {
        nameInput.setCustomValidity("");
    }
});


const phone2 = document.getElementById("phone2");
const phone3 = document.getElementById("phone3");

phone2.addEventListener("input", function (e) {
    const inputValue = phone2.value;
    const numericValue = inputValue.replace(/\D/g, ''); // 숫자 이외의 문자를 제거

    if (numericValue.length === 4) {
        phone2.value = numericValue; // 숫자만 입력한 값으로 변경
        phone3.focus();
    } else {
        phone2.value = numericValue; // 숫자만 입력한 값으로 변경
    }
});

phone3.addEventListener("input", function (e) {
    const inputValue = phone3.value;
    const numericValue = inputValue.replace(/\D/g, ''); // 숫자 이외의 문자를 제거
    phone3.value = numericValue; // 숫자만 입력한 값으로 변경
});

const ssnInput = document.getElementById("ssn");
const ssn2Input = document.getElementById("ssn2");

ssnInput.addEventListener("input", function (e) {
    const inputValue = ssnInput.value;
    const numericValue = inputValue.replace(/\D/g, ''); // 숫자 이외의 문자를 제거
    const sanitizedValue = numericValue.substr(0, 6); // 앞 6자리만 남김
    ssnInput.value = sanitizedValue; // 앞 6자리만 입력한 값으로 변경

    if (sanitizedValue.length === 6) {
        ssn2Input.focus();
    }
});

ssn2Input.addEventListener("input", function (e) {
    const inputValue = ssn2Input.value;
    const numericValue = inputValue.replace(/\D/g, ''); // 숫자 이외의 문자를 제거
    const sanitizedValue = numericValue.charAt(0); // 뒷자리 맨 첫 자리만 남김
    ssn2Input.value = sanitizedValue; // 뒷자리 맨 첫 자리만 입력한 값으로 변경
});


</script>

</body>
</html>