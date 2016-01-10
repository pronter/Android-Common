package com.example.su.zuhe;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by su on 2015/11/27.
 */
public class FileUtils {
    public void saveContent(Context context, String fileName, String fileText) {
        String file_Name = fileName+".txt";
        File dir;
        dir = new File(Environment.getExternalStorageDirectory().getPath());
        if (dir.exists() && dir.canWrite()) {
            File newFile = new File(dir.getAbsolutePath() + "/lizi/" + file_Name);
            FileOutputStream fos = null;
            try {
                newFile.createNewFile();
                if (newFile.exists() && newFile.canWrite()) {
                    fos = new FileOutputStream(newFile);
                    fos.write(fileText.getBytes());
                    Toast.makeText(context, "Save Content Success", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    }
                    catch (IOException e) {}
                }
            }
        }
    }
    public String getContent(Context context, String fileName) {
        try {
            String file_Name = fileName+".txt";
            File dir;
            dir = new File(Environment.getExternalStorageDirectory().getPath());
            FileInputStream fis;
            fis = new FileInputStream(dir.getAbsolutePath() + "/lizi/" + file_Name);
            byte[] contents = new byte[fis.available()];
            fis.read(contents);
            fis.close();
            Toast.makeText(context, "Read Content Success", Toast.LENGTH_SHORT).show();
            return new String(contents);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Read Content Failed", Toast.LENGTH_SHORT).show();
            return new String("");
        }
    }
    public void deleteFile(Context context, String fileName) {
        String file_Name = fileName+".txt";
        File dir;
        dir = new File(Environment.getExternalStorageDirectory().getPath());
        File newFile = new File(dir.getAbsolutePath() + "/lizi/" + file_Name);
        newFile.delete();
        Toast.makeText(context, "Delete File Success", Toast.LENGTH_SHORT).show();
    }
}