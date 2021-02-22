package constant;

public enum Signals {
	LOGIN("로그인",140),
	LOGOUT("로그아웃",150),
	MSG("문자 메세지",100),
	CHECK_IN("입장",110),
	CHECK_OUT("퇴장",120),
	MSG_IMG("이미지 메세지",130),
	GROUP_CHATTING("그룹채팅",200),
	OPEN_GROUPROOM("채팅방 개설",210),
	CHECK_IN_GROUPROOM("채팅방 입장",220),
	CHECK_OUT_GROUPROOM("채팅방 퇴장",230),
	FILE_UPLOAD("파일 업로드",300),
	FILE_DOWNLOAD("파일 다운로드",310),
	ADD_FRIEND("친구 추가",400),
    AGREE_FRIEND("친추 동의",410),
    REMOVE_FRIEND("친구 삭제",420),
    ErrorMsg("에러",800);


    private  String types;
    private  int  signal;

	private Signals(String types, int signal) {
		this.types =types;
		this.signal = signal;
	}
	 @Override
     public  String toString() {
         return  this.signal +  "_"  +  this.types;
     }
	 
	 
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public int getSigal() {
		return signal;
	}
	public void setSignal(int signal) {
		this.signal = signal;
	}
	

	public static Signals getSignals(int type) {
		if(type == 0) {
			return null;
		}
		switch (type) {
		case 140:
			return LOGIN;
		case 150:
			return LOGOUT;
		case 100:
			return MSG;
		case 110:
			return CHECK_IN;
		case 120:
			return CHECK_OUT;
		case 130:
			return MSG_IMG;
		case 200:
			return GROUP_CHATTING;
		case 210:
			return OPEN_GROUPROOM;
		case 220:
			return CHECK_IN_GROUPROOM;
		case 230:
			return CHECK_OUT_GROUPROOM;
		case 300:
			return FILE_UPLOAD;
		case 310:
			return FILE_DOWNLOAD;
		case 400:
			return ADD_FRIEND;
		case 410:
			return AGREE_FRIEND;
		case 420:
			return REMOVE_FRIEND;
		case 800:
			return ErrorMsg;
		default:
			return null;
		}
	}
	
	 
    
}
