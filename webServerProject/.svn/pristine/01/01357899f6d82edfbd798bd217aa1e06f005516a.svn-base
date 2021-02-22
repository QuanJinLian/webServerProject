package prepare;


import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;


public class ApplicationContext {
		Hashtable<String, Object> objTable = new Hashtable<String, Object>();
		public Object getBean(String key) {
			return objTable.get(key);

		}
		public ApplicationContext(String propertiesPath) throws Exception{
			Properties props = new Properties();
			props.load(new FileReader(propertiesPath));
			prepareObjects(props);
			prepareAnnotationObjects();
			injectDependency();
			
		}
		private void prepareAnnotationObjects() throws Exception{
			Reflections reflector = new Reflections("");
			Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
			String key = null;
			for(Class<?> clazz : list) {
				key = clazz.getAnnotation(Component.class).value();
				objTable.put(key, clazz.newInstance()); 
			}
		}
		
		private void prepareObjects(Properties props) throws Exception {
			Context ctx = new InitialContext();    // context 초기화
			String key = null;
			String value = null;
			for(Object item : props.keySet()) {
				key = (String) item;    // 프로포티의 키값들을 읽어옴

				value = props.getProperty(key);  // 프로포티안의 위에서 구한 키값에 해당하는 값을 읽어옴

				if(key.startsWith("jndi.")) {  // value값이 jndi.으로
					objTable.put(key, ctx.lookup(value));  //데이터소스를 담는다
				}else {
					objTable.put(key, Class.forName(value).newInstance()); // 컨트롤러 생성자를 생성한다
				}
			}
		}
		private void injectDependency() throws Exception{ //依赖注入
			for(String key: objTable.keySet()) {
				if(!key.startsWith("jndi.")) {
					callSetter(objTable.get(key)); //key값에 해당하는 클래스들로 callSetter메소드를 실행
															// 해당 클래스중 set이 들어간 메소드들을 꺼내서 쓰겠다
					
				}
			}
		}
		private void callSetter(Object obj)throws Exception{
			Object dependency = null;
			for(Method m : obj.getClass().getMethods()) { //클래스들이 갖고 있는 메소드들을 소환하여 반복문
				if(m.getName().startsWith("set")) {  //메소드이름중 set이 들어간 메소일때
					dependency = findObjectByType(m.getParameterTypes()[0]); // set이 들어간 메소드의 타입을 반환한다.

					if(dependency != null) {
						m.invoke(obj, dependency);
					}
				}
			}
		}
		private Object findObjectByType(Class<?> type) {
			for(Object obj: objTable.values()) {
				if(type.isInstance(obj)) {
					return obj;
				}
			}
			return null;
		}
}
