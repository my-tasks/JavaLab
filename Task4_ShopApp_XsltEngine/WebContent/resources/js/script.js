function submit(formName) {
	document.getElementById(formName).submit();
}

function set(field, value) {
	if (value != null) {
		document.getElementById(field).value = value;
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
	if (notInStock=="true") {
		document.getElementById(checkbox).checked = true;
		document.getElementById('price').disabled=true;
	}
}

function fill(){
	document.getElementById("name").value="New Item"
	document.getElementById("provider").value="Зара"
	document.getElementById("model").value="za000"
	document.getElementById("color").value="green"
	document.getElementById("price").value="109"
	document.getElementById("date").value="12-12-2000"

	}