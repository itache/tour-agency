package me.itache.utils.imagesource;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Contains convenient methods for image processing
 */
public class LocalImageSource implements ImageSource {
    private static final Logger LOG = Logger.getLogger(ImageSource.class);
    private static String PATH = "D:/images";

    public static void init(String path) {
        PATH = path;
    }

    @Override
    public void delete(String name) {
        File file = new File(PATH, name);
        file.delete();
    }

    @Override
    public void save(byte[] binary, String name) throws IOException {
        File file = new File(PATH, name);
        Files.copy(new ByteArrayInputStream(binary), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public InputStream get(String name) throws IOException {
        File file = new File(PATH, name);
        if (!file.exists()) {
            LOG.debug("Image not exists. Path: " + name);
            file = new File(PATH, "404.jpg");
        }
        return new FileInputStream(file);
    }
}
