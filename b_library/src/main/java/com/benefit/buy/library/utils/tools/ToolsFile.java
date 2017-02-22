/* 
 * 文件名：ToolsFile.java
 * 版权：Copyright 2009-2010 companyName MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.benefit.buy.library.utils.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * sd卡配置信息
 * @author qinyulun
 * @version HDMNV100R001, 2014-7-9
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ToolsFile {

    private static final String TAG = ToolsFile.class.getName();

    /**
     * 判断SD是否可以
     * @return
     */
    public static boolean isSdcardExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 创建根目录
     * @param path 目录路径
     * @return
     */
    public static boolean createDirFile(String path) {
        File dir = new File(path);
        return createDir(dir);
    }

    /**
     * 创建文件夹
     * @param dir
     * @return
     */
    public static boolean createDir(File dir) {
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return true;
        }
        catch (Exception e) {
            Log.e(TAG, "create dir error", e);
            return false;
        }
    }

    /**
     * 创建文件
     * @param path 文件路径
     * @return 创建的文件
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        return createNewFile(file);
    }

    /**
     * 创建文件
     * @param file
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static File createNewFile(File file) {
        try {
            if (file.exists()) {
                return file;
            }
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        catch (IOException e) {
            Log.e(TAG, "", e);
            return null;
        }
        return file;
    }

    /**
     * 删除文件夹
     * @param folderPath 文件夹的路径
     */
    public static void delFolder(String folderPath) {
        delAllFile(folderPath);
        String filePath = folderPath;
        filePath = filePath.toString();
        java.io.File myFilePath = new java.io.File(filePath);
        myFilePath.delete();
    }

    /**
     * 删除文件
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    /**
     * 删除文件
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        }
        else if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
        }
        file.delete();
    }

    /**
     * 删除文件
     * @param path 文件的路径
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            }
            else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
            }
        }
    }

    /**
     * 获取文件的Uri
     * @param path 文件的路径
     * @return
     */
    public static Uri getUriFromFile(String path) {
        File file = new File(path);
        return Uri.fromFile(file);
    }

    /**
     * 换算文件大小
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "未知大小";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        }
        else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        }
        else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        }
        else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 拷贝文件
     * @param fromFile
     * @param toFile
     * @throws IOException
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static void copyFile(File fromFile, String toFile) throws IOException {
        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write
            }
        }
        finally {
            if (from != null) {
                try {
                    from.close();
                }
                catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            }
            if (to != null) {
                try {
                    to.close();
                }
                catch (IOException e) {
                    Log.e(TAG, "", e);
                }
            }
        }
    }

    /**
     * 向Text文件中写入内容
     * @param path
     * @param content
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static boolean write(String path, String content) {
        return write(path, content, false);
    }

    public static boolean write(String path, String content, boolean append) {
        return write(new File(path), content, append);
    }

    public static boolean write(File file, String content) {
        return write(file, content, false);
    }

    public static boolean write(File file, String content, boolean append) {
        if ((file == null) || ToolsKit.isEmpty(content)) {
            return false;
        }
        if (!file.exists()) {
            file = createNewFile(file);
        }
        FileOutputStream ops = null;
        try {
            ops = new FileOutputStream(file, append);
            ops.write(content.getBytes());
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
            return false;
        }
        finally {
            try {
                ops.close();
            }
            catch (IOException e) {
                Log.e(TAG, "", e);
            }
            ops = null;
        }
        return true;
    }

    /**
     * 获得文件名
     * @param path
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getFileName(String path) {
        if (ToolsKit.isEmpty(path)) {
            return null;
        }
        File f = new File(path);
        String name = f.getName();
        f = null;
        return name;
    }

    /**
     * 读取文件内容，从第startLine行开始，读取lineCount行
     * @param file
     * @param startLine
     * @param lineCount
     * @return 读到文字的list,如果list.size<lineCount则说明读到文件末尾了
     */
    public static List<String> readFile(File file, int startLine, int lineCount) {
        if ((file == null) || (startLine < 1) || (lineCount < 1)) {
            return null;
        }
        if (!file.exists()) {
            return null;
        }
        FileReader fileReader = null;
        List<String> list = null;
        try {
            list = new ArrayList<String>();
            fileReader = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fileReader);
            boolean end = false;
            for (int i = 1; i < startLine; i++) {
                if (lnr.readLine() == null) {
                    end = true;
                    break;
                }
            }
            if (end == false) {
                for (int i = startLine; i < (startLine + lineCount); i++) {
                    String line = lnr.readLine();
                    if (line == null) {
                        break;
                    }
                    list.add(line);
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "read log error!", e);
        }
        finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 在SD卡上创建目录
     * @param dirName
     */
    public static File creatSDDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断SD卡上的文件是否存在
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public static File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = createNewFile(path + "/" + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            int len = -1;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                output.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 从文件中一行一行的读取文件
     * @param file
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String readFile(File file) {
        Reader read = null;
        String content = "";
        String string = "";
        BufferedReader br = null;
        try {
            read = new FileReader(file);
            br = new BufferedReader(read);
            while ((content = br.readLine().toString().trim()) != null) {
                string += content + "\r\n";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                read.close();
                br.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("string=" + string);
        return string.toString();
    }

    /**
     * 取得文件大小
     * @param f
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        }
        else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }

    /**
     * 取得文件夹大小 递归
     * @param f
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            }
            else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 转换文件大小
     * @param fileS
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS <= 0) {
            fileSizeString = "0M";
        }
        else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        }
        else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        }
        else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 递归求取目录文件个数
     * @param f
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static long getlist(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }
}
