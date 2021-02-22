package socketServer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import com.example.wetalkclient.bean.ChatData;

import DBAction.DBAction;
import constant.Signals;
import dto.Message;


public class ClientHandler extends Thread {
	private Socket socket;
	private BufferedReader i;
	private PrintWriter o;
	private ChatServer server;
	private String userId;
	private String roomId;
	private String roomName;
	private String toId;
	private String photo;
	private String nikName;
	private String message;
	private ObjectInputStream in;
	private ObjectOutputStream os;
	private volatile boolean run = true;
	private Message msg = new Message();
	private String delim2 = "/@";
	private String delim3 = "/&-";
	private String secondData[];
	
	
	
	
//	public ClientHandler (String userId, Socket socket, ObjectInputStream in) throws IOException {
//		this.userId = userId;
//		this.socket = socket;
//		this.in = in;
//		os = new ObjectOutputStream(socket.getOutputStream());
////		o = new PrintWriter(socket.getOutputStream());
//	}
	
	public ClientHandler (ChatServer server, String userId, Socket socket, BufferedReader i) throws IOException {
		this.server = server;
		this.userId = userId;
		this.socket = socket;
		this.i = i;
		o = new PrintWriter(socket.getOutputStream());
	}
	
	public void run() {
		System.out.println("핸들러 스레드 시작---"+i.toString());
		while (run) {
			try {
				String data = i.readLine();
				System.out.println("data 읽기 ---"+data);

				if(data == null) {
					break;
				}
				msg.setAllNull();

//				다음 신호 캐치 시  받는 데이터 양식 --> 신호번호/@1.fromId/&-2.toId/&-3.message/&-4.time/&-5.photoName/&-6.nikName
//				StringTokenizer datas = new StringTokenizer(data,delim2);
				String [] datas  = data.split(delim2);
				int type = 0;
				String str = "";
				
				for(int i = 0; i < datas.length; i++) {
					str = datas[i];
					System.out.println(i+"번째 - > " +str);
					if(i == 0) {
						type = Integer.parseInt(str);
					}else {
						data = str;
					}
				}
				msg.setSignal(type+"");
				
				
				if(datas.length > 1 ) {
					secondData = data.split(delim3);
				}
			
				switch (Signals.getSignals(type)) {
					case LOGOUT:
						server.removeConnHandler(userId);
						break;
					case CHECK_IN:
						for(int i = 0; i < secondData.length; i++) {
							str = secondData[i];
							if(0 == i) {
								roomId =str;
							}else if(1 == i) {
								toId = str;
							}else if(2 == i) {
								photo = str;
							}else if(3 == i) {
								nikName = str;
							}
						}
						break;
					case CHECK_OUT:
						roomId = "";
						toId = "";
						photo = "";
						nikName = "";
						break;
					case MSG:
						for(int i = 0; i < secondData.length; i++) {
							str = secondData[i];
							if(0 == i) {
								message =str;
							}
						}
						msg.setFromId(userId);
						msg.setToId(toId);
						msg.setRoomId(roomId);
						msg.setPhoto(photo);
						msg.setNikName(nikName);
						msg.setMsg(message);
						server.sendMsg(msg);
						break;
					case MSG_IMG:
						break;
					case GROUP_CHATTING:
						for(int i = 0; i < secondData.length; i++) {
							str = secondData[i];
							if(0 == i) {
								message =str;
							}
						}
						msg.setFromId(userId);
						msg.setToId(toId);
						msg.setRoomId(roomId);
						msg.setPhoto(photo);
						msg.setNikName(nikName);
						msg.setMsg(message);
						msg.setRoomName(roomName);
						server.sendMsg(msg);
						break;
					case OPEN_GROUPROOM:
						break;
					case CHECK_IN_GROUPROOM:
						for(int i = 0; i < secondData.length; i++) {
							str = secondData[i];
							if(0 == i) {
								roomId =str;
							}else if(1 == i) {
								toId = str;
							}else if(2 == i) {
								photo = str;
							}else if(3 == i) {
								nikName = str;
							}else if(4 == i) {
								roomName = str;
							}
						}
						break;
					case CHECK_OUT_GROUPROOM:
						roomId = "";
						toId = "";
						photo = "";
						nikName = "";
						break;
					case FILE_UPLOAD:
						break;
					case FILE_DOWNLOAD:
						break;
					case ADD_FRIEND:
						msg.setSignal(Signals.ADD_FRIEND.getSigal()+"");
						for(int i = 0; i < secondData.length; i++) {
							str = secondData[i];
							if(0 == i) {
								msg.setFromId(str);
							}else if(1 == i) {
								msg.setToId(str);
							}else if(2 == i) {
								msg.setPhoto(str);
							}else if(3 == i) {
								msg.setNikName(str);
							}
						}
						server.sendMsg(msg);
						break;
					case AGREE_FRIEND:
						msg.setSignal(Signals.AGREE_FRIEND.getSigal()+"");
						for(int i = 0; i < secondData.length; i++) {
							str = secondData[i];
							if(0 == i) {
								msg.setFromId(str);
							}else if(1 == i) {
								msg.setToId(str);
							}else if(2 == i) {
								msg.setPhoto(str);
							}else if(3 == i) {
								msg.setNikName(str);
							}
						}
						server.agreeAddFriend(msg);
						
						break;
					case REMOVE_FRIEND:
						break;
					default:
						System.out.println("디폴트");
						break;
				}
//				if(obj instanceof ChatData.MSG) {
//					ChatData.MSG msg = (ChatData.MSG)obj;
//					System.out.println(msg.getFromId()+"에게서 받은 메세지--"+msg.getMsg());
//					System.out.println("타입--"+msg.getType());
//					switch (msg.getType()) {
//					case CHATTING_MSG:
//						System.out.println("send");
//						server.sendMsg(msg);
//						break;
//					case CHATTING_IMG:
//						// 이미지 보낸다
//						break;
//					case OFFLINE_MSG:
//						// 오프라인 메세지 보낸다
//						break;
//					case ADD_FRIEND:
//						// 친구 추가 신호를 보낸다
//						break;
//					case ADD_AGREE:
//						// 친구 추가 동의 신호를 보낸다
//						break;
//					case LOGOUT:
//						// 로그아웃
//						break;
//
//					default:
//						break;
//					}
//				}
			}catch(SocketException e) {
				System.out.println("소켓 예외--"+e.toString());
//				sendError(Signals.ErrorMsg.getSigal()+"/@소켓예외");
				break;
			}catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		System.out.println("while 문 밖에 ");
//        System.out.println("isBound---"+socket.isBound()); // 是否邦定  
//        System.out.println("isClosed---"+socket.isClosed()); // 是否关闭  
//        System.out.println("isConnected---"+socket.isConnected()); // 是否连接  
//        System.out.println("isInputShutdown---"+socket.isInputShutdown()); // 是否关闭输入流  
//        System.out.println("isOutputShutdown---"+socket.isOutputShutdown()); // 是否关闭输出流  
		server.removeConnHandler(userId);
		closeConn();
		closeSocket();
		System.out.println("연결 닫음 // 소켓 닫음");
	}
	
	public void sendError(String error) {
		try {
			o.println(error);
			o.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("에러메세지 발송");
	}
	
	public void sendMsg(Message msg) {
		try {
			String delim1 = "/@";
			String delim2 = "/&-";
			String sendMsg ="";
			int signal = Integer.parseInt(msg.getSignal());
			switch (Signals.getSignals(signal)) {
				case MSG:
					sendMsg = msg.getSignal()+delim1
							+msg.getRoomId()+delim2
							+msg.getFromId()+delim2
							+msg.getToId()+delim2
							+msg.getPhoto()+delim2
							+msg.getNikName()+delim2
							+msg.getMsg();
					break;
				case GROUP_CHATTING:
					sendMsg = msg.getSignal()+delim1
							+msg.getRoomId()+delim2
							+msg.getFromId()+delim2
							+msg.getToId()+delim2
							+msg.getPhoto()+delim2
							+msg.getNikName()+delim2
							+msg.getMsg()+delim2
							+msg.getRoomName();
					break;
				case ADD_FRIEND:
					sendMsg = msg.getSignal()+delim1
							+msg.getFromId()+delim2
							+msg.getToId()+delim2
							+msg.getPhoto()+delim2
							+msg.getNikName();
					break;
//				case AGREE_FRIEND:
//					sendMsg = msg.getSignal()+delim1
//							+msg.getFromId()+delim2
//							+msg.getToId()+delim2
//							+msg.getPhoto()+delim2
//							+msg.getNikName();
//					agreeAddFriend(msg);
//					break;
				default:
					break;
			}
			System.out.println(sendMsg);
			o.println(sendMsg);
			o.flush();
//			os.writeObject(msg);
//			os.flush();
//			os.reset();
////			byte [] bytes = toByteArray(msg);
//			o.println(toByteArray(msg));
//			o.flush();
//			System.out.println("toByteArray(msg)---- "+toByteArray(msg));
			System.out.println("FROM "+msg.getFromId()+"- To->"+msg.getToId()+" 내용은 :"+ msg.getMsg()+"메세지 보냄");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void closeConn() {
		run = false;
	}
	
	public void closeSocket() {
		try {
//			in.close();
//			os.close();
			i.close();
			o.close();
			socket.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public boolean isOnline() {
		boolean online = true;
		try {
			socket.sendUrgentData(44);
		}catch (Exception e) {
			online = false;
		}
		return online;
	}
	

	

}
