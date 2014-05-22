function list(category, subcategory) {
	if (category != null) {
		document.getElementById("category").value = category;
	}
	if (subcategory != null) {
		document.getElementById("subcategory").value = subcategory;
	}
	document.getElementById("selectCategory").submit();
}