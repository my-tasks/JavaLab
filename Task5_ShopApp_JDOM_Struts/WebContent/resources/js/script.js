function submitForm() {
	document.forms[0].submit();
}
function set(field, value) {
	if (value != null) {
		document.getElementById(field).value = value;
	}
}
function changePriceTD(num, categoryIndex, subcategoryIndex) {
	var id = "priceTD" + num;
	if (document.getElementById("cbox" + num).checked) {
		document.getElementById(id).innerHTML = "<div class='not-in-stock'>NOT IN STOCK</div>";
	} else {
		var hiddenPriceId = "hiddenPrice" + num;
		var hiddenPriceTD = document.getElementById(hiddenPriceId);
		if (hiddenPriceTD != null) {
			var value = hiddenPriceTD.value.toString();
			document.getElementById(id).innerHTML = "<input type='text' name='document.rootElement.children["
					+ categoryIndex
					+ "].children["
					+ subcategoryIndex
					+ "].children["
					+ num
					+ "].children[4].text' value='"
					+ value + "' size='10' id='price" + num + "'/>";
		} else {
			document.getElementById(id).innerHTML = "<input type='text' name='document.rootElement.children["
					+ categoryIndex
					+ "].children["
					+ subcategoryIndex
					+ "].children["
					+ num
					+ "].children[4].text' size='10' id='price" + num + "'/>";
		}
	}
}

function disablePrice() {
	var price = document.getElementById('price');
	price.disabled = !price.disabled;
}
function setColor(select, color) {
	var selectInput = document.getElementById(select);
	var options = selectInput.options;
	var opt;
	for ( var i = 0; i < options.length; i++) {
		opt = options[i];
		if (opt.value == color) {
			opt.selected = true;
			return;
		}
	}
}

function setNotInStock() {
	var num = 0;
	while (document.getElementById("price" + num) != null
			|| document.getElementById("notInStock" + num) != null) {
		if (document.getElementById("price" + num) == null) {
			document.getElementById("cbox" + num).checked = true;
		}
		num++;
	}
}

function fill() {
	document.getElementById("name").value = "New Item"
	document.getElementById("producer").value = "Зара"
	document.getElementById("model").value = "za000"
	document.getElementById("color").value = "green"
	document.getElementById("price").value = "109"
	document.getElementById("date").value = "12-12-2000"
	return false;
}

function validateProductForm() {
	var valid = true;
	var fill = "Fill the field";
	var name = document.getElementById("name").value.toString();
	if (name.length == 0) {
		document.getElementById("err_name").innerHTML = fill;
		valid = false;
	} else {
		document.getElementById("err_name").innerHTML = '';
	}
	var producer = document.getElementById("producer").value.toString();
	if (producer.length == 0) {
		document.getElementById("err_producer").innerHTML = fill;
		valid = false;
	} else {
		document.getElementById("err_producer").innerHTML = '';
	}
	var model = document.getElementById("model").value.toString();
	if (model.length == 0) {
		document.getElementById("err_model").innerHTML = fill;
		valid = false;
	} else if (model.length > 5
			|| model
					.match('[\u0430-\u044F\u0410-\u044Fa-zA-Z][\u0430-\u044F\u0410-\u044Fa-zA-Z][0-9][0-9][0-9]') == null) {
		document.getElementById("err_model").innerHTML = "Wrong model format. Must match: llDDD";
		valid = false;
	} else {
		document.getElementById("err_model").innerHTML = '';
	}
	var color = document.getElementById("color").value.toString();
	if (color.length == 0) {
		document.getElementById("err_color").innerHTML = "Choose the color";
		valid = false;
	} else {
		document.getElementById("err_color").innerHTML = '';
	}
	var date = document.getElementById("date").value.toString();
	if (date.length == 0) {
		document.getElementById("err_date").innerHTML = fill;
		valid = false;
	} else if (date.length > 10
			|| date
					.match('(((0)[1-9]|[1,2][0-9]|(3)[0-1])-((0)[1,3,5,7,8]|(1)[0,2])-((20)[0-9][0-9]))|(((0)[1-9]|[1,2][0-9]|(30))-((0)[4,6,9]|(11))-((20)[0-9][0-9]))|(((0)[1-9]|[1,2][0-9])-(02)-((20)[0-9][0-9]))') == null) {
		document.getElementById("err_date").innerHTML = "Wrong date format. Must match: dd-MM-yyyy";
		valid = false;
	} else {
		document.getElementById("err_date").innerHTML = '';
	}
	var notInStock = document.getElementById("notInStock");
	if (!notInStock.checked) {
		var price = document.getElementById("price").value.toString();
		if (price.length == 0) {
			document.getElementById("err_price").innerHTML = fill;
			valid = false;
		} else if (price.match(/^\d+$/) == null) {
			document.getElementById("err_price").innerHTML = "Not valid price";
			valid = false;
		} else {
			document.getElementById("err_price").innerHTML = '';
		}
	} else {
		document.getElementById("err_price").innerHTML = "";
	}
	return valid;
}

function validateProducts() {
	var valid = true;
	var fill = "Fill the field";
	var num = 0;
	var field;
	while ((field = document.getElementById("name" + num)) != null) {
		field = field.value.toString();
		if (field.length == 0) {
			document.getElementById("err_name" + num).innerHTML = fill;
			valid = false;
		} else {
			document.getElementById("err_name" + num).innerHTML = '';
		}
		num++;
	}
	num = 0;
	while ((field = document.getElementById("producer" + num)) != null) {
		field = field.value.toString();
		if (field.length == 0) {
			document.getElementById("err_producer" + num).innerHTML = fill;
			valid = false;
		} else {
			document.getElementById("err_producer" + num).innerHTML = '';
		}
		num++;
	}
	var num = 0;
	while ((field = document.getElementById("model" + num)) != null) {
		field = field.value.toString();
		if (field.length == 0) {
			document.getElementById("err_model" + num).innerHTML = fill;
			valid = false;
		} else if (field.length > 5
				|| field
						.match('[\u0430-\u044F\u0410-\u044Fa-zA-Z][\u0430-\u044F\u0410-\u044Fa-zA-Z][0-9][0-9][0-9]') == null) {
			document.getElementById("err_model" + num).innerHTML = "Wrong model format. Must match: llDDD";
			valid = false;
		} else {
			document.getElementById("err_model" + num).innerHTML = '';
		}
		num++;
	}
	var num = 0;
	while ((field = document.getElementById("color" + num)) != null) {
		field = field.value.toString();
		if (field.length == 0) {
			document.getElementById("err_color" + num).innerHTML = fill;
			valid = false;
		} else {
			document.getElementById("err_color" + num).innerHTML = '';
		}
		num++;
	}
	var num = 0;
	while ((field = document.getElementById("date" + num)) != null) {
		field = field.value.toString();
		if (field.length == 0) {
			document.getElementById("err_date" + num).innerHTML = fill;
			valid = false;
		} else if (field.length > 10
				|| field
						.match('(((0)[1-9]|[1,2][0-9]|(3)[0-1])-((0)[1,3,5,7,8]|(1)[0,2])-((20)[0-9][0-9]))|(((0)[1-9]|[1,2][0-9]|(30))-((0)[4,6,9]|(11))-((20)[0-9][0-9]))|(((0)[1-9]|[1,2][0-9])-(02)-((20)[0-9][0-9]))') == null) {
			document.getElementById("err_date" + num).innerHTML = "Wrong date format. Must match: dd-MM-yyyy";
			valid = false;
		} else {
			document.getElementById("err_date" + num).innerHTML = '';
		}
		num++;
	}
	var num = 0;
	var notInStock;
	while ((notInStock = document.getElementById("cbox" + num)) != null) {
		if (!notInStock.checked) {
			field = document.getElementById("price" + num).value.toString();
			if (field.length == 0) {
				document.getElementById("err_price" + num).innerHTML = fill;
				valid = false;
			} else if (field.match(/^\d+$/) == null) {
				document.getElementById("err_price" + num).innerHTML = "Not valid price";
				valid = false;
			} else {
				document.getElementById("err_price" + num).innerHTML = '';
			}
		} else {
			document.getElementById("err_price" + num).innerHTML = "";
		}
		num++;
	}
	return valid;

}