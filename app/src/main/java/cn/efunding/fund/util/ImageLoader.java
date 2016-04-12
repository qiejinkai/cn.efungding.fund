package cn.efunding.fund.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qiejinkai on 16/4/12.
 */
public class ImageLoader {

    //最大内存
    final static int memClass = (int) Runtime.getRuntime().maxMemory();
    private Context context;

    private static String dirPath = "";

    // 是否缓存到硬盘
    private boolean  diskcache = true;

    // 定义一级 缓存的图片数
    private static final int catch_num = 10;

    // 定义二级缓存 容器  软引用
    private static ConcurrentHashMap<String, SoftReference<Bitmap>> current_hashmap = new ConcurrentHashMap<String, SoftReference<Bitmap>>();

    // 定义一级缓存容器  强引用       (catch_num ,0.75f,true） 默认参数                                                                                                                        2.加载因子默认        3.排序模式 true
    private static LinkedHashMap<String, Bitmap> link_hashmap = new LinkedHashMap<String, Bitmap>(catch_num ,0.75f,true) {

        // 必须实现的方法
        protected boolean removeEldestEntry(java.util.Map.Entry<String, Bitmap> eldest) {
            /** 当一级缓存中 图片数量大于 定义的数量 放入二级缓存中
             */
            if (this.size() > catch_num) {
                // 软连接的方法 存进二级缓存中
                current_hashmap.put(eldest.getKey(), new SoftReference<Bitmap>(
                        eldest.getValue()));
                //缓存到本地
                cancheToDisk(eldest.getKey(),eldest.getValue() );

                return true;
            }
            return false;
        };
    };

    public ImageLoader(Context context) {

        this.context = context;
        dirPath = this.context.getCacheDir().getAbsolutePath();

    }


    /**
     *  外部调用此方法   进行下载图片
     */
    public void downLoad(String key , ImageView imageView,Context context){
        // 先从缓存中找   。
        context = this.context;

        Bitmap bitmap = getBitmapFromCache(key);
        if( null!= bitmap){
            imageView.setImageBitmap(bitmap);
            cancleDownload(key, imageView);         //取消下载
            return ;
        }

        // 缓存中 没有  把当前的 imageView 给他 得到 task
        if(cancleDownload(key, imageView)){     //没有任务进行。，。。开始下载
            ImageDownloadTask task = new ImageDownloadTask(imageView);
            Zhanwei_Image  zhanwei_image = new Zhanwei_Image(task);
            //先把占位的图片放进去
            imageView.setImageDrawable(zhanwei_image);
            // task执行任务
            task.execute(key);
        }
    }


    /** 此方法 用于优化  ： 用户直接 翻到 哪个 就先加载 哪个、
     * @param key                - URL
     * @param imageView          - imageView
     *  core： 给当前的 imageView 得到给他下载的 task
     */

    private boolean cancleDownload(String key,ImageView imageView){
        // 给当前的 imageView 得到给他下载的 task
        ImageDownloadTask task = getImageDownloadTask(imageView);
        if(null != task){
            String down_key = task.key;
            if( null == down_key || !down_key.equals(key)){
                task.cancel(true);        // imageview 和 url 的key不一样       取消下载
            }else{
                return false;      //正在下载：
            }
        }
        return true;            //没有正在下载
    }



//  public void getThisProcessMemeryInfo() {
//        int pid = android.os.Process.myPid();
//        android.os.Debug.MemoryInfo[] memoryInfoArray = activityManager.getProcessMemoryInfo(new int[] {pid});
//        System.out.println("本应用当前使用了" + (float)memoryInfoArray[0].getTotalPrivateDirty() / 1024 + "mb的内存");
//    }



    /**
     * 从缓存中得到 图片的方法 1.先从一级 缓存找 linkhashmap 不是线程安全的 必须要加同步
     */
    public Bitmap getBitmapFromCache(String key) {
        //1.先在一级缓存中找
        synchronized (link_hashmap) {
            Bitmap bitmap = link_hashmap.get(key);
            if (null != bitmap) {
                link_hashmap.remove(key);
                // 按照 LRU是Least Recently Used 近期最少使用算法 内存算法 就近 就 原则 放到首位
                link_hashmap.put(key, bitmap);
                //System.out.println(" 在缓存1中找图片了 =" +key);
                return bitmap;
            }
        }

        // 2. 到二级 缓存找
        SoftReference<Bitmap> soft = current_hashmap.get(key);
        if (soft != null) {
            //得到 软连接 中的图片
            Bitmap soft_bitmap = soft.get();
            if (null != soft_bitmap) {
                //System.out.println(" 在缓存2中找图片了 =" +key);
                return soft_bitmap;
            }
        } else {
            // 没有图片的话 把这个key删除
            current_hashmap.remove(key);
        }


        //3.都没有的话去从外部缓存文件读取
        if(diskcache){
            Bitmap bitmap = getBitmapFromFile(key);
            if(bitmap!= null){
                link_hashmap.put(key, bitmap);   //将图片放到一级缓存首位
                return bitmap;
            }
        }

        return null;
    }


    /**
     * 缓存到本地文件
     * @param key
     * @param bitmap
     */
    public static void cancheToDisk(String key ,Bitmap bitmap ){
        //2.缓存bitmap至/data/data/packageName/cache/文件夹中
        try {
            String fileName = getMD5Str(key);
            String filePath = dirPath + "/" + fileName;
            System.out.println("缓存到本地===" + filePath);
            FileOutputStream fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

        } catch (Exception e) {

        }
    }


    /**
     * 从外部文件缓存中获取bitmap
     * @param url
     * @return
     */
    private Bitmap getBitmapFromFile(String url){
        Bitmap bitmap = null;
        String fileName = getMD5Str(url);
        if(fileName == null){
            return null;
        }
        String filePath = this.context.getCacheDir().getAbsolutePath() + "/" + fileName;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            bitmap = BitmapFactory.decodeStream(fis);
            System.out.println("在本地缓存中找到图片==="+ filePath);
        } catch (FileNotFoundException e) {
            System.out.println("getBitmapFromFile==="+ e.toString());
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }



    /**
     * 清理文件缓存
     * @param dirPath
     * @return
     */
    public static boolean removeCache(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if(files == null || files.length == 0) {
            return true;
        }
        int dirSize = 0;
        //这里删除所有的缓存
        int all_ = (int) ( 1 * files.length + 1);
        //对files 进行排序
        Arrays.sort(files, new FileLastModifiedSort());
        for (int i = 0; i < all_ ; i++) {
            files[i].delete();
        }
        return true;
    }


    /**
     * 根据文件最后修改时间进行排序
     */
    private static class FileLastModifiedSort implements Comparator<File> {
        @Override
        public int compare(File lhs, File rhs) {
            if(lhs.lastModified() > rhs.lastModified()) {
                return 1;
            } else if(lhs.lastModified() == rhs.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }


    /**
     * MD5 加密
     */
    private static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }


    // ------------------------ 异步加载----------------------------
    /**
     *  占位的 图片 或者 颜色      用来绑定 相应的图片
     */
    class Zhanwei_Image extends ColorDrawable {
        //里面存放 相应 的异步 处理时加载好的图片 ----- 相应的 task
        private final WeakReference<ImageDownloadTask> taskReference;
        public Zhanwei_Image(ImageDownloadTask task){
            super(Color.BLUE);
            taskReference = new WeakReference<ImageLoader.ImageDownloadTask>(task);
        }
        // 返回去这个 task 用于比较
        public ImageDownloadTask getImageDownloadTask(){
            return taskReference.get();
        }
    }


    // 根据 给 的 iamgeView、 得到里面的 task  用于和当前的 task比较是不是同1个
    private ImageDownloadTask getImageDownloadTask(ImageView imageView){
        if( null != imageView){
            Drawable drawable = imageView.getDrawable();
            if( drawable instanceof Zhanwei_Image)
                return ((Zhanwei_Image)drawable).getImageDownloadTask();

        }
        return null;
    }



    /**
     * 把图片 添加到缓存中
     */
    public void addBitmap(String key, Bitmap bitmap) {
        if (null != bitmap) {
            synchronized (link_hashmap) {         // 添加到一级 缓存中
                link_hashmap.put(key, bitmap);
            }
        }
    }


    /** 在后台 加载每个图片
     *  第一个参数 第2个要进度条不 第三个返回结果 bitmap
     */
    class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {

        private String key;
        private WeakReference<ImageView> imgViReference;

        public ImageDownloadTask(ImageView imageView) {
            //imageView 传进来 。。要给哪个iamgeView加载图片
            imgViReference = new WeakReference<ImageView>(
                    imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params){
            key = params[0];
            //调用下载函数 根据 url 下载
            return downloadBitmap(key);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if(isCancelled()){
                result = null;
            }

            System.out.println("result=="+ result.getByteCount()+"---memClassmemery="+memClass);

            if(null!= result){
                //保存到缓存中
                addBitmap(key, result);
                ImageView  imageView = imgViReference.get();
                if( null != imageView){
                    //向 imageView 里面放入 bitmap
                    ImageDownloadTask task = getImageDownloadTask(imageView);

                    /**
                     *  判断 是不是 同一个 task( )
                     *  如果当前这个 task  ==  imageView 里面的那个 task 就是同1个
                     */
                    if( this == task ){
                        imageView.setImageBitmap(result);

                    }
                }
            }
        }
    }


    /**
     * 连接网络 客户端 下载图片
     */
    private Bitmap downloadBitmap(String url) {
//
//        final HttpClient client = AndroidHttpClient.newInstance("Android");
//        final HttpGet getRequest = new HttpGet(url);
//        try {
//            HttpResponse response = client.execute(getRequest);
//            final int statusCode = response.getStatusLine().getStatusCode();
//
//            if (statusCode != HttpStatus.SC_OK) {
//
//                Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
//                return null;
//            }
//
//            final HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                InputStream inputStream = null;
//                try {
//
//                    inputStream = entity.getContent();
//                    /**
//                     *  1.没有压缩直接将生成的bitmap返回去
//                     */
////                  return BitmapFactory.decodeStream(inputStream);
//
//                    /**
//                     *  2.得到data后在这里把图片进行压缩
//                     */
//                    byte[] data = read(inputStream);
//                    return  BitmapManager.scaleBitmap(context, data, 0.3f);
////                   return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
//                } finally {
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//                    entity.consumeContent();
//                }
//            }
//        } catch (IOException e) {
//            getRequest.abort();
//        } catch (IllegalStateException e) {
//            getRequest.abort();
//        } catch (Exception e) {
//            getRequest.abort();
//        } finally {
//            if ((client instanceof AndroidHttpClient)) {
//                ((AndroidHttpClient) client).close();
//            }
//        }
        return null;
    }

    public static  byte[] read(InputStream in) throws Exception{
        ByteArrayOutputStream out_byte = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len=0;
        while((len = in.read(buff))!= -1){
            //写到内存中  字节流
            out_byte.write( buff, 0 , len);
        }
        out_byte.close();
        // 把内存数据返回
        return  out_byte.toByteArray();
    }

}
