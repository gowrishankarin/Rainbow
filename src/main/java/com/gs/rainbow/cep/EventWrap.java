package com.gs.rainbow.cep;


public class EventWrap<T> {
	T object;
	ComplexEvent complexEvent;

	private EventWrap() {

	}

	public EventWrap(T object, ComplexEvent complexEvent) {
		this.object = object;
		this.complexEvent = complexEvent;
	}

	public T getObject() {
		return object;
	}

	public ComplexEvent getComplexEvent() {
		return complexEvent;
	}
}