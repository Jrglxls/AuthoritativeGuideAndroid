package com.bignerdranch.android.criminalintent.Model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Jrglxls on 2017/1/18.
 */

public class CriminalIntentJsonSerializer {
    //上下文
    private Context context;
    //文件名
    private String fileName;

    /**
     * 构造方法
     */
    public CriminalIntentJsonSerializer(Context context , String fileName){
        this.context = context;
        this.fileName = fileName;
    }

    /**
     * 保存数据 将ArrayList数据写入到文件中
     */
    public void saveCrimes(ArrayList<Crime> crimes) throws IOException {
        //创建JSONArray数组
        JSONArray jsonArray = new JSONArray();
        //遍历Crimes数组
        for (int i = 0;i<crimes.size();i++){
            //将Crime数据添加到JSONArray
            jsonArray.put(crimes.get(i).toJson());
        }

        //创建Writer对象
        Writer writer = null;
        try {
            //创建OutputStream,打开文件并写入数据
            //参数一 文件名 参数二 文件操作模式
            OutputStream outPutStream = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            //创建Writer对象
            writer = new OutputStreamWriter(outPutStream);
            //Writer对象写入数据
            writer.write(jsonArray.toString());
            System.out.println("jjjjjjjj 保存jsonArray  "+jsonArray.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    /**
     * 加载数据
     */
    public ArrayList<Crime> loadCrimes() throws IOException {
        //创建Crimes对象
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        //创建bufferedReader对象
        BufferedReader bufferedReader = null;
        try {
            //获取文件中的inputStream
            InputStream inputStream = context.openFileInput(fileName);
            //将inputStream转化为bufferedReader
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //创建StringBuilder对象
            StringBuilder stringBuilder = new StringBuilder();
            //创建每条数据
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                //将bufferedReader每条数据以此添加至stringBuilder
                stringBuilder.append(line);
            }
            //解析stringBuilder数据为jsonArray数据
            JSONArray jsonArray = (JSONArray) new JSONTokener(stringBuilder.toString()).nextValue();
            //将jsonArray数据转化为ArrayList数据
            for(int i = 0;i<jsonArray.length();i++){
                crimes.add(new Crime(jsonArray.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            //完成底层文件句柄的释放
            if (bufferedReader != null){
                bufferedReader.close();
            }
        }
        System.out.println("jjjjjjj  加载crimes"+crimes.toString());
        return crimes;
    }
}
