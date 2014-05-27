function validateToSave() {

	var news_title = document.getElementById("news_title").value.toString();
	if (news_title.length == 0) {
		document.getElementById("title_error").innerHTML = ERROR_FIELD_REQUIRED;
	} else {
		if (news_title.length > 100) {
			document.getElementById("title_error").innerHTML = ERROR_TITLE_LENGTH;
		} else {
			document.getElementById("title_error").innerHTML = '';
		}
	}
	var date = document.getElementById("news_date").value.toString();
	var pattern = document.getElementById("pattern").value.toString();
	var year;
	var month;
	var day;
	if (date.length != pattern.length) {
		document.getElementById("date_error").innerHTML = errorDateFormat(pattern);
	} else {
		if (pattern.split("/").length == 3) {
			dateArr = date.split("/");
			day = dateArr[1];
			month = dateArr[0];
		} else {
			dateArr = date.split(".");
			day = dateArr[0];
			month = dateArr[1];
		}
		year = dateArr[2];
		if (year.length != 4 || year.match('^\\d+$') == null) {
			document.getElementById("date_error").innerHTML = errorDateFormat(pattern);
		} else if (month.length != 2 || month.match('^\\d+$') == null) {
			document.getElementById("date_error").innerHTML = errorDateFormat(pattern);
		} else if (month < 1 || month > 12) {
			document.getElementById("date_error").innerHTML = errorDateIncorrect(pattern);
		} else if (day.length != 2) {
			document.getElementById("date_error").innerHTML = errorDateFormat(pattern);
		} else {
			year = parseInt(year);
			month = parseInt(month);
			day = parseInt(day);
			if (day < 1) {
				document.getElementById("date_error").innerHTML = errorDateFormat(pattern);
			} else if (year % 4 == 0 && month == 2 && day > 29) {
				document.getElementById("date_error").innerHTML = errorDateIncorrect(pattern);
			} else if (year % 4 != 0 && month == 2 && day > 28) {
				document.getElementById("date_error").innerHTML = errorDateIncorrect(pattern);
			} else if ((month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12)
					&& day > 31) {
				document.getElementById("date_error").innerHTML = errorDateIncorrect(pattern);
			} else if ((month == 4 || month == 6 || month == 9 || month == 11)
					&& day > 30) {
				document.getElementById("date_error").innerHTML = errorDateIncorrect(pattern);
			} else {
				document.getElementById("date_error").innerHTML = '';
			}
		}
	}

	var news_brief = document.getElementById("news_brief").value.toString();
	if (news_brief.length == 0) {
		document.getElementById("brief_error").innerHTML = ERROR_FIELD_REQUIRED;
	} else {
		document.getElementById("brief_error").innerHTML = '';
		if (news_brief.length > 500) {
			document.getElementById("brief_error").innerHTML = ERROR_BRIEF_LENGTH;
		} else {
			document.getElementById("brief_error").innerHTML = '';
		}
	}
	var news_content = document.getElementById("news_content").value.toString();
	if (news_content.length == 0) {
		document.getElementById("content_error").innerHTML = ERROR_FIELD_REQUIRED;
	} else {
		document.getElementById("content_error").innerHTML = '';
		if (news_content.length > 2048) {
			document.getElementById("content_error").innerHTML = ERROR_CONTENT_LENGTH;
		} else {
			document.getElementById("content_error").innerHTML = '';
		}
	}
	if (document.getElementById("title_error").innerHTML == ''
			&& document.getElementById("date_error").innerHTML == ''
			&& document.getElementById("brief_error").innerHTML == ''
			&& document.getElementById("content_error").innerHTML == '') {
		document.forms["newsForm"].submit();
		document.getElementById("saveButton").disabled = true;
	}
}

function errorDateFormat(pattern) {
	return ERROR_DATE_FORMAT + pattern;
}
function errorDateIncorrect(pattern) {
	return ERROR_DATE_INCORRECT + pattern;
}