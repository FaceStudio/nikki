package net;

/**
 * 网络任务执行 情况 接口
 */
public interface NetCallBack {
    /**
     * 执行前
     */
    public void onCallPre();

    /**
     * 执行中
     */
    public void onCallPro();

    /**
     * 执行成功，拿到返回结果
     * @param s
     */
    public void onCallSuccess(String s);

    /**
     * 执行失败
     */
    public void onCallFailed();
}