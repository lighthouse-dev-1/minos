package action;

public class ActionFactory {

	private static ActionFactory instance = new ActionFactory();

	private ActionFactory() {
		super();
	}

	public static ActionFactory getInstance() {
		return instance;
	}
	
	public Action getAction(String command) {
		Action action = null;
		
		if (command.equals("main")) {
			action = new InputTaskAction();
		} else if(command.equals("connect")) {
			action = new ConnectAction();
		}
		return action;
	}
}
