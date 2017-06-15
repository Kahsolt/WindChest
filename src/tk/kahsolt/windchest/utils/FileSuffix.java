package tk.kahsolt.windchest.utils;

import java.util.ArrayList;

/**
 * Created by kahsolt on 17-6-13.
 */
public class FileSuffix {

    private static final ArrayList ImageSuffix=new ArrayList();
    private static final ArrayList AudioSuffix=new ArrayList();
    private static final ArrayList VideoSuffix=new ArrayList();

    static {
        ImageSuffix.add("jpg");
        ImageSuffix.add("jpeg");
        ImageSuffix.add("bmp");
        ImageSuffix.add("png");
        ImageSuffix.add("gif");

        AudioSuffix.add("mp3");
        AudioSuffix.add("wav");
        AudioSuffix.add("wmv");

        VideoSuffix.add("mp4");
        VideoSuffix.add("webm");
        VideoSuffix.add("mpg");
        VideoSuffix.add("ogg");
        VideoSuffix.add("flv");
        VideoSuffix.add("f4v");
    }

    public static String getType(String filename) {
        String suffix=getExtension(filename);
        if(ImageSuffix.contains(suffix)) {
            return "image";
        } else if (AudioSuffix.contains(suffix)) {
            return "audio";
        } else if (VideoSuffix.contains(suffix)) {
            return "video";
        } else {
            return "file";
        }
    }

    private static String getExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
