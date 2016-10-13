package me.itache.utils.imagesource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public interface ImageSource {
    void delete(String name) throws IOException;

    void save(byte[] binary, String name) throws IOException;

    InputStream get(String name) throws IOException;

    default void createThumb(byte[] originalImageContent,
                             String thumbnailFile,
                             int thumbWidth,
                             int thumbHeight) throws IOException {
        Image image = ImageIO.read(new ByteArrayInputStream(originalImageContent));

        double thumbRatio = (double) thumbWidth / (double) thumbHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double imageRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < imageRatio) {
            thumbHeight = (int) (thumbWidth / imageRatio);
        } else {
            thumbWidth = (int) (thumbHeight * imageRatio);
        }

        if (imageWidth < thumbWidth && imageHeight < thumbHeight) {
            thumbWidth = imageWidth;
            thumbHeight = imageHeight;
        } else if (imageWidth < thumbWidth)
            thumbWidth = imageWidth;
        else if (imageHeight < thumbHeight)
            thumbHeight = imageHeight;

        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setBackground(Color.WHITE);
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fillRect(0, 0, thumbWidth, thumbHeight);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(thumbImage, "jpg", baos);
        save(baos.toByteArray(), thumbnailFile);
        IOUtils.closeQuietly(baos);
    }

    /**
     * Get uploaded image as input stream from request.
     *
     * @param request
     * @throws Exception
     */
    static byte[] getUploadedImageAsStream(HttpServletRequest request) throws Exception {
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());

        List<FileItem> items = uploadHandler.parseRequest(request);
        for(FileItem item : items) {
            if(!item.isFormField()) {
                return IOUtils.toByteArray(item.getInputStream());
            }
        }
        return null;

    }
}
