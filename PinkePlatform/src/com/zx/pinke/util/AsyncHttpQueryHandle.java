package com.zx.pinke.util;

import java.net.URI;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class AsyncHttpQueryHandle extends Handler{

	private static final Log sLog = LogFactory.getLog(AsyncHttpQueryHandle.class);

//	public static final String PARAM_TOTAL = "total";
	public static final String PARAM_ROWS = "shareList";
	
    private static final int EVENT_ARG_POST = 1;
    private static final int EVENT_ARG_GET  = 2;
    private static final int EVENT_ARG_JSON_PARSE = 3;
    
    private static final int EVENT_ARG_DOWNLOAD = 4;
    private static final int EVENT_ARG_DOWNLOAD_BYTE_SIZES = 5;
    
    private static final String DEFAULT_QERUY_FORMAT = "json";
    
    private static Looper sLooper = null;
    private Context mContext;
    private Handler mWorkerThreadHandler;
    
    public AsyncHttpQueryHandle(Context context) {
        super();
        mContext = context;
        synchronized (AsyncHttpQueryHandle.class) {
            if (sLooper == null) {
                HandlerThread thread = new HandlerThread("AsyncHttpQueryHandle");
                thread.start();
                sLooper = thread.getLooper();
            }
        }
        mWorkerThreadHandler = createHandler(sLooper);
    }
    
    protected Handler createHandler(Looper looper) {
        return new WorkerHandler(looper);
    }
    
    protected static final class WorkerArgs {
        public URI uri;
        public Handler handler;
        public Map<String,Object> params;
        public Object result;
        public Object result2;
        public Object cookie;
        public String sesseionId;
    }
    
    protected class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            WorkerArgs args = (WorkerArgs) msg.obj;

            int token = msg.what;
            int event = msg.arg1;

            switch (event) {
                case EVENT_ARG_POST:
                	args.result = HttpHelper.post(mContext, args.uri, args.params,args.sesseionId);
                    break;
                case EVENT_ARG_DOWNLOAD:
                	final Handler innerHandler = args.handler;
                	args.result = DownloadHelper.download(mContext,args.uri,args.sesseionId,new DownloadHelper.ByteSizesHandleListener() {
						
						@Override
						public void execute(long length,long size) {
							
							Message bytesMsg = innerHandler.obtainMessage();
							WorkerArgs args = new WorkerArgs();
					        args.handler = innerHandler;
					        args.result = length;
					        args.result2 = size;
					        bytesMsg.obj = args;
					        bytesMsg.arg1 = EVENT_ARG_DOWNLOAD_BYTE_SIZES;
					        bytesMsg.sendToTarget();
						}
					},new DownloadHelper.InterruptHandleListener() {
						
						@Override
						public boolean interrupt() {
							return fileDownloadInterrupt;
						}
					});
                	break;
            }

            // passing the original token value back to the caller
            // on top of the event values in arg1.
            Message reply = args.handler.obtainMessage(token);
            reply.obj = args;
            reply.arg1 = msg.arg1;

            sLog.debug("WorkerHandler.handleMsg: msg.arg1=" + msg.arg1
                    + ", reply.what=" + reply.what);

            reply.sendToTarget();
        }
    }
    
    private boolean fileDownloadInterrupt = false;
	public void interruptFileDownload() {
		this.fileDownloadInterrupt = true;
	}

	public void startPost(int token, Object cookie, URI uri,
    		Map<String,Object> params) {
        // Use the token as what so cancelOperations works properly
        Message msg = mWorkerThreadHandler.obtainMessage(token);
        msg.arg1 = EVENT_ARG_POST;

        WorkerArgs args = new WorkerArgs();
        args.handler = this;
        args.uri = uri;
        args.params = params;
        args.cookie = cookie;
        msg.obj = args;

        mWorkerThreadHandler.sendMessage(msg);
    }
    
	public void startPost(int token, Object cookie, URI uri,
    		Map<String,Object> params,String sessionId) {
        // Use the token as what so cancelOperations works properly
        Message msg = mWorkerThreadHandler.obtainMessage(token);
        msg.arg1 = EVENT_ARG_POST;

        WorkerArgs args = new WorkerArgs();
        args.handler = this;
        args.uri = uri;
        args.params = params;
        args.sesseionId = sessionId;
        args.cookie = cookie;
        msg.obj = args;

        mWorkerThreadHandler.sendMessage(msg);
    }
	
    public void startDownload(int token, Object cookie, URI uri){
    	
    	 this.fileDownloadInterrupt = false;
    	 
    	 Message msg = mWorkerThreadHandler.obtainMessage(token);
         msg.arg1 = EVENT_ARG_DOWNLOAD;

         WorkerArgs args = new WorkerArgs();
         args.handler = this;
         args.uri = uri;
         args.cookie = cookie;
         msg.obj = args;

         mWorkerThreadHandler.sendMessage(msg);
    }
    
	
    public void startDownload(int token, Object cookie, URI uri,String sessionId){
    	
    	 this.fileDownloadInterrupt = false;
    	 
    	 Message msg = mWorkerThreadHandler.obtainMessage(token);
         msg.arg1 = EVENT_ARG_DOWNLOAD;

         WorkerArgs args = new WorkerArgs();
         args.handler = this;
         args.uri = uri;
         args.cookie = cookie;
         args.sesseionId = sessionId;
         msg.obj = args;

         mWorkerThreadHandler.sendMessage(msg);
    }
    
    
    public final void cancelOperation(int token) {
        mWorkerThreadHandler.removeMessages(token);
    }
    
    protected void onPostComplete(int token, Object cookie, HttpJsonResult result) {
        // Empty
    }
    
    protected void onDownloadComplete(int token, Object cookie, HttpFileResult result){
    	
    }
    
    protected void onDownloadByteSizes(Object cookie,long totalLength,long size){
    	
    }
    
    @Override
    public void handleMessage(Message msg) {
        WorkerArgs args = (WorkerArgs) msg.obj;
        
        sLog.debug("AsyncHttpQueryHandle.handleMessage: msg.what=" + msg.what
                + ", msg.arg1=" + msg.arg1);
        int token = msg.what;
        int event = msg.arg1;
        System.out.println("进入httpHelper");
        switch (event) {
	        case EVENT_ARG_POST:
	        	System.out.println("提交的请求");
	        	onPostComplete(token, args.cookie, (HttpJsonResult) args.result);
	        	System.out.println("请求响应成功");
	            break;
	        case EVENT_ARG_DOWNLOAD:
	        	onDownloadComplete(token,args.cookie,(HttpFileResult)args.result);
	        	break;
	        case EVENT_ARG_DOWNLOAD_BYTE_SIZES:
	        	onDownloadByteSizes(args.cookie,(Long)args.result,(Long)args.result2);
	        	break;
        }
    }
}
