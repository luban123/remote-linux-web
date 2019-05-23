package com.puwell.searchfind.searchfind.utils;

import ch.ethz.ssh2.*;
import com.puwell.searchfind.searchfind.enu.FileTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

public class ConnnectRemoteComputerUtil {
    private static String  DEFAULTCHART="UTF-8";
    public static Connection connectRemoteLinux(String ip,String name,String pwd,int port) throws Exception {
            Connection conn=null;
            conn=new Connection(ip,port);
            conn.connect();
            boolean b = conn.authenticateWithPassword(name, pwd);
            if(b){
                System.out.println("连接成功");
                return conn;
            }else {
                throw new Exception("连接失败");
            }
    }
    //执行cmd命令
    public static String execute(Connection connection,String cmd){
        String result="";
        try {
            Session session = connection.openSession();
            session.execCommand(cmd);
            result=processStdout(session.getStdout(),DEFAULTCHART);
            //如果为得到标准输出为空，说明脚本执行出错了
            if(StringUtils.isBlank(result)){
                result=processStdout(session.getStderr(),DEFAULTCHART);
            }else{
                System.out.println("执行命令成功,链接conn:"+connection+",执行的命令："+cmd);
            }

            connection.close();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    //处理返回execute结果
    public static String processStdout(InputStream in,String charset){
        InputStream stdout =new StreamGobbler(in);
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(stdout,charset));
            String line=null;
            while ((line=br.readLine())!=null){
                stringBuffer.append(line+"\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
        } finally {
            try {
                stdout.close();
                 br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
    //获取各个文件的属性
    public static Map getproperties(Connection connection, String path){
        Map<String,Integer> map=new HashMap();
        try {
            SFTPv3Client sftPv3Client = new SFTPv3Client(connection);
            Vector<?> ls = sftPv3Client.ls(path);
            for(int i=0;i<ls.size();i++){
                SFTPv3DirectoryEntry s = new SFTPv3DirectoryEntry();
                s= (SFTPv3DirectoryEntry) ls.get(i);
                //文件名
                String filename=s.filename;
                if(!s.attributes.isDirectory()){//判断是不是目录
                    map.put(filename, FileTypeEnum.NODERICTORY.getCode());
                }else {
                    map.put(filename, FileTypeEnum.DERICTORY.getCode());
                }
            }
            sftPv3Client.close();
            connection.close();
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getFileContent(Connection connection,String path){
        Session ss = null;
        try {
            ss = connection.openSession();
            ss.execCommand("cat ".concat(path));
            InputStream is = new StreamGobbler(ss.getStdout());
            BufferedReader brs = new BufferedReader(new InputStreamReader(is));
            String str="";
            String line=null;
            while ((line = brs.readLine())!=null) {
                str+=line+"\n";
            }
            brs.close();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 连接的Session和Connection对象都需要关闭
            if (ss!= null) {
                ss.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        }
    public static void main(String[] args) throws Exception {
      Connection connection = connectRemoteLinux("", "", "",);
        String cmd="ls /var/log";
        String path="cat /AWSSES.pid";
        //String execute = execute(connection, cmd);
        //System.out.println(execute);
  /*      Map<String,Integer> getproperties = getproperties(connection, path);
        for (String key:getproperties.keySet()){
            System.out.println("key:"+key+"  "+"code:"+getproperties.get(key));
        }*/
        String fileContent = execute(connection, path);
        System.out.println(fileContent);
    }
}
