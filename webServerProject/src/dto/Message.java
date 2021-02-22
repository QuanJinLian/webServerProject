package dto;


public class Message {
	private String signal;
	private String fromId;
	private String toId;
	private String msg;
	private String time;
    private  String roomId;
    private  String roomName;
	
	// 친구 추가 시 fromId의 인포 필요
	private String photo;
	private String nikName;
	
	public void setAllNull() {
		this.signal = null;
		this.fromId = null;
		this.toId = null;
		this.msg = null;
		this.time = null;
		this.photo = null;
		this.nikName = null;
        this.roomId = null;
        this.roomName = null;
		
	}
	
	
	public String getRoomName() {
		return roomName;
	}


	public Message setRoomName(String roomName) {
		this.roomName = roomName;
		return this;
	}


	public String getRoomId() {
		return roomId;
	}


	public Message setRoomId(String roomId) {
		this.roomId = roomId;
		return this;
	}


	public String getSignal() {
		return signal;
	}
	public Message setSignal(String signal) {
		this.signal = signal;
		return this;
	}
	public String getFromId() {
		return fromId;
	}
	public Message setFromId(String fromId) {
		this.fromId = fromId;
		return this;
	}
	public String getToId() {
		return toId;
	}
	public Message setToId(String toId) {
		this.toId = toId;
		return this;
	}
	public String getMsg() {
		return msg;
	}
	public Message setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public String getTime() {
		return time;
	}
	public Message setTime(String time) {
		this.time = time;
		return this;
	}
	public String getPhoto() {
		return photo;
	}
	public Message setPhoto(String photo) {
		this.photo = photo;
		return this;
	}
	public String getNikName() {
		return nikName;
	}
	public Message setNikName(String nikName) {
		this.nikName = nikName;
		return this;
	}
	
	
}
