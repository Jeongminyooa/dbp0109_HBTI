package model.service.exception;

/*
 * �׷� ������ ��û���� ��, �ο��� �����̶��
 * �������� ���Ѵٴ� ���� �߻�
 */
public class OverTheLimitException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OverTheLimitException() {
		super();
	}

	public OverTheLimitException(String arg0) {
		super(arg0);
	}
}
