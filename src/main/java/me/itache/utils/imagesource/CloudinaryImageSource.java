package me.itache.utils.imagesource;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CloudinaryImageSource implements ImageSource {
    private Cloudinary cloudinary;

    public CloudinaryImageSource() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dexwggeql",
                "api_key", "333414822513587",
                "api_secret", "0g4ht_qn3gpWz5P6Q4JA59XlMuw"));
    }

    @Override
    public void delete(String name) throws IOException {
        cloudinary.uploader().destroy(name.substring(0, name.indexOf(".")), ObjectUtils.emptyMap());
    }

    @Override
    public void save(byte[] binary, String name) throws IOException {
        cloudinary.uploader().upload(binary, ObjectUtils.asMap(
                "public_id", name.substring(0, name.indexOf(".")),
                "invalidate", true));
    }

    @Override
    public InputStream get(String name) throws IOException {
        return new URL(cloudinary.url().generate(name)).openStream();
    }
}
