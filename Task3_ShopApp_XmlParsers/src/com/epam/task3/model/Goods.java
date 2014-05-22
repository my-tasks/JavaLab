package com.epam.task3.model;

public class Goods implements Comparable<Goods> {
	private String category;
	private String subcategory;
	private String name;
	private String producer;
	private String model;
	private String dateOfIssue;
	private String color;
	private long price;
	private boolean notInStock = false;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public boolean isNotInStock() {
		return notInStock;
	}

	public void setNotInStock(boolean notInStock) {
		this.notInStock = notInStock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result
				+ ((dateOfIssue == null) ? 0 : dateOfIssue.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (notInStock ? 1231 : 1237);
		result = prime * result + (int) (price ^ (price >>> 32));
		result = prime * result
				+ ((producer == null) ? 0 : producer.hashCode());
		result = prime * result
				+ ((subcategory == null) ? 0 : subcategory.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Goods other = (Goods) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (dateOfIssue == null) {
			if (other.dateOfIssue != null)
				return false;
		} else if (!dateOfIssue.equals(other.dateOfIssue))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notInStock != other.notInStock)
			return false;
		if (price != other.price)
			return false;
		if (producer == null) {
			if (other.producer != null)
				return false;
		} else if (!producer.equals(other.producer))
			return false;
		if (subcategory == null) {
			if (other.subcategory != null)
				return false;
		} else if (!subcategory.equals(other.subcategory))
			return false;
		return true;
	}

	@Override
	public int compareTo(Goods that) {
		int result;
		result = this.getCategory().compareTo(that.getCategory());
		if (result == 0) {
			result = this.getSubcategory().compareTo(that.getSubcategory());
		}
		if (result == 0) {
			result = this.getName().compareTo(that.getName());
		}
		if (result == 0) {
			long dif = this.getPrice() - that.getPrice();
			if (dif > 0) {
				result = 1;
			} else if (dif < 0) {
				result = -1;
			}
		}
		if (result == 0) {
			result = this.getProducer().compareTo(that.getProducer());
		}
		if (result == 0) {
			result = this.getModel().compareTo(that.getModel());
		}
		if (result == 0) {
			result = this.getDateOfIssue().compareTo(that.getDateOfIssue());
		}
		if (result == 0) {
			result = this.getColor().compareTo(that.getColor());
		}
		return result;
	}
}
