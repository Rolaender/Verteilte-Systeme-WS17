package com.example.demo;

import static org.mockito.Mockito.times;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ToDo {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String todo;
	private boolean done;
	private String timestamp;
	
	public ToDo(String tod) {
		this.todo = tod;
		this.done = false;
		this.timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	}
	
	public ToDo(){}
	
	@Override
	public String toString() {
		return String.format("Todo[id=%d, todo='%s', done='%s', erstellungszeit='%s' ]", id, todo, done, timestamp);
	}

	public long getId() {
		return id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public boolean getDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
