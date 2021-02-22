package socketServer;

import java.awt.BorderLayout;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.example.wetalkclient.bean.ChatData;

public class CattingTest extends JFrame implements Runnable, ActionListener {
//	private BufferedReader i; // 통신 입출력 장치 (문자 데이터)
//	private PrintWriter o; // 통신 입출력 장치 (문자 데이터) Interface Serializable
	private JTextArea output;
	private JTextField input;
	private JLabel label;
	private Thread listener; // 스레드 - 데이터 입출력을 실행하는 실행단위로 쓰는 스레드
	private String host;
	private JScrollPane jp;
	private JScrollBar jb;
	private List userList;
	private ObjectInputStream in = null ;
	private ObjectOutputStream out = null;
	int no;

	public CattingTest(String server) {
		super("채팅 프로그램"); // 제목 달기
		host = server; // 서버 IP
		listener = new Thread(this);
		listener.start(); // 스레드 시작
		output = new JTextArea();
		jp = new JScrollPane(output);
		jb = jp.getVerticalScrollBar();
		output.setEditable(false); // TextArea입력할수 있는 부분 비 활성화
		Panel bottom = new Panel(new BorderLayout());
		label = new JLabel("사용자 이름");
		bottom.add(label, "West");
		input = new JTextField();
		bottom.add(input, "Center");
		userList = new List(6);
		add(userList, "East");
		add(jp, "Center");
		add(bottom, "South");

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
//		input.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		input.addActionListener(this); // TextField에 액션리슨어 이벤트 적용

	}

	@Override
	public void run() {
		try {
			System.out.println("스레드 시작");
			Socket s = new Socket(host, 8888);
			System.out.println("Socket 생성");
			InputStream ins = s.getInputStream();
			OutputStream os = s.getOutputStream();
			System.out.println("InputStream & OutputStream 생성");
//			i = new BufferedReader(new InputStreamReader(ins));
//			o = new PrintWriter(new OutputStreamWriter(os), true);
            out = new ObjectOutputStream(os);
            ChatData.ID user = new ChatData.ID();
            user.setUserId("ss");
//          user.setUserId("dd");
            out.writeObject(user);
            out.flush();
            in = new ObjectInputStream(ins);
			while (true) {
				System.out.println("무한 루푸  시작");
//				String line = i.readLine();    // 여기서 블로킹

                Object obj = in.readObject();
                if(obj instanceof ChatData.MSG){
                    ChatData.MSG msg = (ChatData.MSG)obj;
                    switch (msg.getType()){
                        case CHATTING_MSG:
            				output.append(msg.getFromId()+" -- " + msg.getMsg()+"\n");
                            break;
                    }
                }
				jb.setValue(jb.getMaximum());
			}
		} catch (IOException e) {e.printStackTrace();
		}catch (Exception e) {e.printStackTrace();}
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object c = e.getSource();
		if (c == input) {   // c == enter일때 실행함
			label.setText("메시지");
	        ChatData.MSG message = new ChatData.MSG().setFromId("ss")
                    .setMsg(input.getText())
                    .setToId("dd")
                   .setNikName("im ss")
                   .setType(ChatData.Type.CHATTING_MSG);
//	        ChatData.MSG message = new ChatData.MSG().setFromId("dd")
//	                .setMsg(input.getText())
//	                .setToId("ss")
//	                .setNikName("나 dd야")
//	                .setType(ChatData.Type.CHATTING_MSG);
			try {
				out.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // TextField의 내용을 서버에 OutputStream을 한다
			input.setText("");
		}

	}

	public static void main(String[] args) {
		if (args.length > 0) {
			new CattingTest(args[0]);
		} else {
			new CattingTest("192.168.0.52");
		}
	}

}
