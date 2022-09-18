package galleryService.implementation;

import galleryService.exception.EmptyImageException;
import galleryService.interfaces.ImageService;
import galleryService.pojo.ImageInfoHolder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistence.dao.interfaces.FileManager;
import persistence.dao.interfaces.ImageDAO;
import persistence.exception.ValidationException;
import persistence.struct.Image;
import persistence.struct.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: agnidash
 * Date: 4/24/13
 * Time: 6:03 PM
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Value(value = "${persistence.fileManager.imagePreviewWidth}")
    private int width;

    @Value(value = "${persistence.fileManager.imagePreviewHeight}")
    private int height;

    @Value(value = "${persistence.fileManager.formatName}")
    private String formatName;

    private ImageDAO imageDAO;
    private FileManager fileManager;
    private ExecutorService executorService;
    private Map<Integer, Object> cache;

    private static final Logger LOGGER = Logger.getLogger(ImageServiceImpl.class);
    //
    private static final boolean ORIGINAL = true;
    private static final boolean PREVIEW = false;
    private static final Object NONE = new Object();

    @Value(value = "${service.imageCacheSize}")
    private float loadFactor;

    @Required
    public void setImageDAO(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    @Required
    public void setCache(Map<Integer, Object> cache) {
        this.cache = cache;
    }

    @Required
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Required
    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Transactional
    @Override
    public void addImage(User user, String name, String comment, byte[] data) throws ValidationException, IOException {

        if (data == null || data.length == 0) {
            throw new EmptyImageException("param data must not be null");
        }

        Image image = new Image();
        image.setName(name);
        image.setComment(comment);
        image.setUserId(user.getId());

        ImageDAO dao = getImageDAO();
        dao.insert(image);

        int id = image.getId();

        asyncCreatePreviewImage(id, data);

        try {
            getFileManager().createFile(data, id, ORIGINAL);
        } catch (IOException e) {
            dao.delete(id);

            throw e;
        }
    }

    private void asyncCreatePreviewImage(final int id, final byte[] data) {
        // This thread register image in cache
        cache.put(id, NONE);

        try {
            addTask(id, data);
        } catch (RejectedExecutionException e) {
            LOGGER.error("Task for creating preview image by id=" + id + " was rejected. ", e);

            cache.remove(id);
        }
    }

    private void addTask(final int id, final byte[] data) {
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    //expensive operation
                    createPreviewCopy(id, data);
                } catch (IOException e) {
                    LOGGER.error("Can't create preview copy of image. id=" + id + ". Size of image=" + data.length, e);

                } finally {
                    // unregister image from cache
                    cache.remove(id);
                }
            }
        });
    }

    @Override
    public InputStream getImageById(int id, boolean isUserChooseOriginal) throws ValidationException, IOException {
        boolean type = getTypeOfFile(id, isUserChooseOriginal);

        File file = getFile(id, type);

        long length = file.length();

        if (length == 0) {
            throw new IOException("File not found by id=" + id);
        }

        // calculate size of cache
        int cacheSize = (int) (length * loadFactor);

        return new BufferedInputStream(new FileInputStream(file), cacheSize);
    }

    private File getFile(int id, boolean type) {
        File file = getFileManager().getFile(id, type);

        if (type == PREVIEW) {
            // SOME EXCEPTIONS OCCURS
            // WHEN WE SCALE ORIGINAL IMAGE
            // AND COPY IT TO FILESYSTEM
            if (!file.exists()) {
                LOGGER.warn("Preview copy not found for image. id=" + id);

                // JUST RETURN ORIGINAL IMAGE
                file = getFileManager().getFile(id, ORIGINAL);
            }
        }

        return file;
    }

    private boolean getTypeOfFile(int id, boolean isUserChooseOriginal) {
        if (isUserChooseOriginal) {
            return ORIGINAL;
        } else {
            return cache.containsKey(id) ? ORIGINAL : PREVIEW;
        }
    }

    @Transactional
    @Override
    public ImageInfoHolder getImages(int offset, int limit) throws ValidationException {
        ImageDAO dao = getImageDAO();

        int count = dao.getCount();
        List<Image> daoResult = dao.fetchWithOffset(offset, limit);

        List<Image> bindResult = (daoResult == null) ? Collections.EMPTY_LIST : daoResult;

        return new ImageInfoHolder(count, bindResult);
    }

    @Override
    public void createPreviewCopy(int id, byte[] originalBytes) throws IOException {
        scale(id, ImageIO.read(new ByteArrayInputStream(originalBytes)));
    }

    private void scale(int id, BufferedImage original) throws IOException {
        File file = getFileManager().getFile(id, PREVIEW);

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        java.awt.Image scaledInstance = original.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        img.createGraphics().drawImage(scaledInstance, 0, 0, null);
        ImageIO.write(img, formatName, file);
    }

    private FileManager getFileManager() {
        return fileManager;
    }

    private ImageDAO getImageDAO() {
        return imageDAO;
    }
}
