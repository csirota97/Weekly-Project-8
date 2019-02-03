package res;

import java.io.Serializable;

public class Message implements Serializable{
	private String subject, message;
	public Message(String sub, String mess) {
		subject = sub;
		message = mess;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
