package cn.efunding.fund.appliacation;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import cn.efunding.fund.R;
import cn.efunding.fund.common.JSON;
import cn.efunding.fund.common.Value;
import cn.efunding.fund.url.CURL;
import cn.efunding.fund.url.IPost;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MyApplication extends Application {

	private String userInfo ;

	private String walletInfo ;

	private String DID = "";
	
	private String TOKEN = "";

	private boolean isLogin = false;

	private boolean isFirstIn = true;

	private SharedPreferences sp;

	SharedPreferences.Editor editor ;

	public String getWalletInfo() {
		return walletInfo;
	}

	public void setWalletInfo(String walletInfo) {
		this.walletInfo = walletInfo;
		editor.putString("walletInfo", walletInfo);
		editor.commit();
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
		editor.putString("userInfo", userInfo);
		editor.commit();
	}

	private String cacheDirPath = "imageloader/Cache";

	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
		editor.putBoolean("isLogin", isLogin);
		editor.commit();
	}

	@Override
	public void onCreate() {

		super.onCreate();

		sp = getSharedPreferences("baseinfo", Activity.MODE_PRIVATE);
		editor = sp.edit();

		this.DID = sp.getString("DID", null);
		this.TOKEN = sp.getString("TOKEN", null);
		this.isLogin = sp.getBoolean("isLogin", false);
		this.userInfo = sp.getString("userInfo", null);
		this.walletInfo = sp.getString("walletInfo",null);


		if(DID == null) {
			new Thread(networkTask).start();
		}
		initImageLoader();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		ImageLoader.getInstance().clearMemoryCache();
	}

	public String getDid() {
		return DID;
	}

	private void setDid(String did) {
		this.DID = did;

		editor.putString("DID", this.DID);
		editor.commit();
	}
	
	public String getToken() {
		return TOKEN;
	}

	public void setToken(String token) {
		this.TOKEN = token;
		editor.putString("token", token);
		editor.commit();
	}
	
	/** 
     * 网络操作相关的子线程 
     */  
    Runnable networkTask = new Runnable() {  
      
        @Override  
        public void run() {  
        	
    		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
    		
    		final String tmDevice, tmSerial, tmPhone, androidId;
    		
    		tmDevice = "" + tm.getDeviceId();
    		
    		tmSerial = "" + tm.getSimSerialNumber();
    		
    		androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
    		
    		UUID deviceUuid = new UUID(androidId.hashCode(),((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
    			
    		StringBuilder url = new StringBuilder();
        	
    		url.append(getResources().getString(R.string.domain)).append(getString(R.string.base_url_device_join));
    		
    		IPost post;
    		
    		try {
    			post = CURL.post(new URL(url.toString()));
    			
    			post.addValue("uuid", deviceUuid.toString());
    			post.addValue("platform", "Android");
    			post.addValue("name", Build.MODEL);
    			post.addValue("model", "Android Phone");
    	    	post.addValue("systemName","Android");
    			post.addValue("systemVersion",Build.VERSION.RELEASE);

    	    	Object ob = JSON.decodeString(post.exec());
	        	    	    	
    	    	setDid(Value.stringValueForKey(Value.objectValueForKey(ob, "device", null), "objectId", "0")); // 初始化全局变量
    	    	
    	    	Log.i("did=",getDid());
    	    	
    		} catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (Throwable e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
        }  
    };


	private void initImageLoader() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), cacheDirPath);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(this)
				.memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(1)//线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache()) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.diskCacheFileCount(100)
				.diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
						// .writeDebugLogs() // Remove for release app
				.build();//开始构建
//        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);

		ImageLoader.getInstance().init(config);
	}

	public DisplayImageOptions getDisplayImageOptions(){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.cacheOnDisk(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.build();
		return options;
	}

	public DisplayImageOptions getDisplayImageOptions(boolean useCache){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.cacheOnDisk(useCache)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.build();
		return options;
	}

	public boolean isLogin(){
		return this.isLogin;
	}

	public String getFullUrl(int domainId,int urlId){

		return getString(domainId)+getString(urlId);
	}

	public void logout(){
		setIsLogin(false);
		setUserInfo(null);
		setWalletInfo(null);
		setToken(null);
	}

}
