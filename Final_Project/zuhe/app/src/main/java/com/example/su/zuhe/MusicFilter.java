package com.example.su.zuhe;

import java.io.File;
import java.io.FilenameFilter;
/**
 * Created by su on 2016/1/9.
 */
public class MusicFilter implements FilenameFilter{

    @Override
    public boolean accept(File dir, String filename) {
        // TODO Auto-generated method stub
        return (filename.endsWith(".mp3"));
    }


}
