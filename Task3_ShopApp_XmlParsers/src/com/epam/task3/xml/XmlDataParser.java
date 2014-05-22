package com.epam.task3.xml;

import java.util.Collection;

import com.epam.task3.model.Goods;

public interface XmlDataParser {
	public Collection<Goods> getProductCollection(String file, Collection<Goods> target);
}
