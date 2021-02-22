package socketServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.text.StyleConstants.CharacterConstants;

import com.example.wetalkclient.bean.ChatData;
import com.example.wetalkclient.bean.ChatData.MSG;

import DBAction.DBAction;
import constant.Constants;
import constant.Signals;
import dto.Message;

public class ChatServer {
	private static Map<String, ClientHandler> connHandler = new HashMap<String, ClientHandler>();
	
	private boolean run = true ;
	private ServerSocket server;
	private Socket socket = null;
	private ObjectInputStream in = null;
	private BufferedReader i ;
	private String data="";
	private ClientHandler clientHandler;
	private ClientHandler SendToClientHandler;
	private String delim1 = "/@";
	private String delim3 = "/#";
	private DBAction db ;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String friendList;
	
	public ChatServer(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("서버 준비 완료");
		}catch (Exception e) {
			e.printStackTrace();
		}
			while(run) {
				try {
					socket = server.accept();
					System.out.println("accepte 신호 받음 socket = "+socket.toString());
					
					InputStream ins = socket.getInputStream();
					System.out.println(ins+"InputStream 생성");
//					
//					in = new ObjectInputStream(ins);
					i = new BufferedReader(new InputStreamReader(ins));
					
					data = i.readLine();
					System.out.println("data -- "+data);
					
					String datas[] = data.split(delim1);

					int type = 0;
					String str = "";
					
					for(int i = 0; i < datas.length; i++) {
						str = datas[i];
						if(i == 0) {
							type = Integer.parseInt(str);
						}else {
							data = str;
						}
					}
					
					if(type == Signals.LOGIN.getSigal()) {
						String userId = data;
						System.out.println("아이디 -- "+userId);
						ClientHandler ch = new ClientHandler(this,userId, socket, i);
						connHandler.put(userId, ch);
						ch.start();
					}
					
//					Object obj = in.readObject();
//					System.out.println("obj 읽음");
//					if(obj instanceof ChatData.ID) {
//						ChatData.ID user = (ChatData.ID)obj;
//						String userId = user.getUserId();
//						System.out.println("아이디 -- "+userId);
//						ClientHandler ch = new ClientHandler(userId, socket,in);
//						connHandler.put(userId, ch);
//						ch.start();
//					}
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		
	}
	public void removeConnHandler(String id) {
		clientHandler = connHandler.remove(id);
		if(clientHandler != null) {
			clientHandler.closeConn();
			clientHandler.closeSocket();
		}
	}
	
	public void sendMsg(Message msg) {
			String toId = msg.getToId();
			String fromId = msg.getFromId();
			if(toId != null && fromId != null) {
				String [] toIds = toId.split(delim3);
				for(int i = 0; i < toIds.length; i++) {
					toId = toIds[i];
					System.out.println("ToId ---->"+ toId);
					findAndSend(toId, msg);
				}
//				findAndSend(fromId, msg);
			}else {
				ClientHandler sendErrorHanlder = connHandler.get(fromId);
				sendErrorHanlder.sendError(Signals.ErrorMsg.getSigal()+"/@toId없어서 에러");
				System.out.println("toId Or FromId 가 없어서 에러");
			}
		
//		ClientHandler clientHandler1 = connHandler.get(toId);
//		ClientHandler clientHandler2 = connHandler.get(fromId);
//		if(clientHandler1 != null && clientHandler1.isOnline()) {
//			clientHandler1.sendMsg(msg);
//		}else if(clientHandler1 != null && !clientHandler1.isOnline()) {
//			connHandler.remove(toId);
//			// 오프라인 메세지 저장소로 간다
//		}else if(clientHandler2 != null && clientHandler2.isOnline()) {
//			clientHandler2.sendMsg(msg);
//		}else if(clientHandler2 != null && !clientHandler2.isOnline()) {
//			connHandler.remove(fromId);
//			// 오프라인 메세지 저장소로 간다
//		}else {  // 핸들러 자체가 없을때 
//			// 오프라인 메세지 저장소로 간다
//		}
		
	}
	public void findAndSend(String id,Message msg) {
		SendToClientHandler = connHandler.get(id);
		if(SendToClientHandler != null) {
			synchronized (SendToClientHandler) {
			if(SendToClientHandler != null && SendToClientHandler.isOnline()) {
				SendToClientHandler.sendMsg(msg);
			}else if(SendToClientHandler != null && !SendToClientHandler.isOnline()) {
				connHandler.remove(id);
				System.out.println("toId 핸들러는 있는데 오프라인 상태");
			}else {
				System.out.println("toId의 핸들러가 없음 == 로그인을 안했음");
			}
		}
		}
	}
	
	
	public void agreeAddFriend(Message msg) {
		System.out.println("agreeAddFriend 들어옴");
		try {
			db = DBAction.getInstance();
			conn = db.getConnection();
			stmt = conn.createStatement();
			System.out.println("DB 연결 됨");
			String sql = "SELECT friendlist FROM wetalkdb.friendlist WHERE ID='"+msg.getFromId()+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				friendList = rs.getString(1);
				if(friendList.equals("")|| friendList == null) {
					friendList = msg.getToId();
				}else {
					friendList = friendList+"&"+msg.getToId();
				}
				sql =  "UPDATE wetalkdb.friendlist SET friendlist='"+friendList+"'  WHERE id='"+msg.getFromId()+"'";
				stmt.executeUpdate(sql);
				System.out.println("ToID DB 저장완료");
			}
			sql  = "SELECT friendlist FROM wetalkdb.friendlist WHERE ID='"+msg.getToId()+"'";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				friendList = rs.getString(1);
				if(friendList.equals("")|| friendList == null) {
					friendList = msg.getFromId();
				}else {
					friendList = friendList+"&"+msg.getFromId();
				}
				sql =  "UPDATE wetalkdb.friendlist SET friendlist='"+friendList+"'  WHERE id='"+msg.getToId()+"'";
				stmt.executeUpdate(sql);
				System.out.println("FromID DB 저장완료");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null ) stmt.close();
//				if(conn != null ) conn.close();
			}catch(SQLException e) {}
		}
	}
	
	public static void main(String[] args) {
		new ChatServer(Constants.SERVER_PORT);
	}
	
}
