package com.subtitlor.model;

public class Subliter {

	private int id;
	private String periode;
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Subliter() {
		super();
	}

	public Subliter(int id, String periode, String message) {
		super();
		this.id = id;
		this.periode = periode;
		this.message = message;
	}

}