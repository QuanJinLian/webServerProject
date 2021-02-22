package constant;

import java.util.StringTokenizer;

public class test {

	public static void main(String[] args) {
//		System.out.println(Signals.MSG.getSigal());
		String text = "85585/@4444/8888";
		StringTokenizer st = new StringTokenizer(text, "/@");
		while(st.hasMoreElements()) {
			String token = st.nextToken();
			System.out.println(token);
		}
		String sts [] = text.split("/@--");
		for(int i = 0 ; i < sts.length; i++) {
			System.out.println(i+"번째"+sts[i]);
		}
	}

}
