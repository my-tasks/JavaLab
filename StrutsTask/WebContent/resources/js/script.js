function submitForm() {
	document.forms[0].submit();
}
function set(field, value) {
	if (value != null) {
		document.getElementById(field).value = value;
	}
}
function changePriceTD(num) {
	var id = "priceTD" + num;
	if (document.getElementById("cbox" + num).checked) {
		document.getElementById(id).innerHTML = "<div class='not-in-stock'>NOT IN STOCK</div>";
	} else {
		document.getElementById(id).innerHTML = "<input type='text' name='dd' size='10' />";
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

function setNotInStock(checkbox, notInStock) {
	if (notInStock == "true") {
		document.getElementById(checkbox).checked = true;
		document.getElementById('price').disabled = true;
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
	} else if (model.length>5||model
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
	} else if (date.length>10||date
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
	} else{
		document.getElementById("err_price").innerHTML = "";
	}
	return valid;

}