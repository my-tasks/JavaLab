	function deleteSelected() {

		var elements = document.getElementsByName("selectedToDelete");
		var count = 0;
		for ( var i = 0; i < elements.length; i++) {
			if (elements[i].checked) {
				count++;
			}
		}
		if (count == 0) {
			alert(ERROR_DELETE_SELECTED);

			return false;
		} else {
			var valid = confirm(CONFIRM_DELETE_SELECTED);
			if (valid) {
				document.forms["newsForm"].submit();
			} else {
				return false;
			}
		}

	}
	
	function deleteNews() {
		var confirmed = confirm(CONFIRM_DELETE);
		if (confirmed) {
			document.getElementById("method").value="delete";
			document.forms["newsForm"].submit();
		};
	}
	function editNews() {
			document.getElementById("method").value="edit";
			document.forms["newsForm"].submit();
		}
	