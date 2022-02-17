package util;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-13:16
 * @version: 1.0
 */
public class FileUtil {
    /**
     * 用于从给定文件名中获取其扩展名
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        String[] fileNameSplatted = fileName.split("\\.");
        return fileNameSplatted[fileNameSplatted.length - 1];
    }
}
