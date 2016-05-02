package cn.efunding.fund.msg;

public class AppMessage {
	
	/**
	 * 操作成功
	 */
	public final static int ACTION_SUCCESS  = 0x0001;
	
	/**
	 * 操作失败
	 */
	public final static int ACTION_FAIL = 0x0002;

	/**
	 * 刷新完成
	 */
	public final static int REFRESH_COMPLETE = 0x0003;

	/**
	 * 生成二维码
	 */
	public final static int DRAW_CODE_MSG  = 0x0004;	
	
}
