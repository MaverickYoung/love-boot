package com.yuan.loveboot.common.utils;

import com.yuan.loveboot.common.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Base64;
import java.util.Set;

/**
 * 文件工具
 *
 * @author Maverick
 */
@Component
@Slf4j
public class FileUtil {

    // 从 application.yml 中读取上传目录
    @Value("${storage.dir.reward}")
    private String rewardDir;
    @Value("${storage.dir.avatar}")
    private String avatarDir;
    @Value("${storage.dir.background}")
    private String background;

    // 支持的图片格式
    private static final Set<String> SUPPORTED_IMAGE_TYPES = Set.of("jpg", "jpeg", "png", "gif", "svg", "webp", "avif", "apng", "bmp", "ico", "tif");

    /**
     * 保存奖励图片
     *
     * @param file   MultipartFile 文件
     * @param month  月份
     * @param userId 用户ID
     * @return 文件路径
     */
    public String saveRewardImage(MultipartFile file, String month, int userId) {
        // 保存文件
        return saveUserImage(file, userId, month, rewardDir);
    }

    /**
     * 保存头像图片
     *
     * @param file   MultipartFile 文件
     * @param userId 用户ID
     * @return 文件路径
     */
    public String saveAvatarImage(MultipartFile file, int userId) {
        // 保存文件
        return saveUserImage(file, userId, null, avatarDir);
    }

    /**
     * 保存背景图片
     *
     * @param file   MultipartFile 文件
     * @param userId 用户ID
     * @return 文件路径
     */
    public String saveBackgroundImage(MultipartFile file, int userId) {
        // 保存文件
        return saveUserImage(file, userId, null, background);
    }

    /**
     * 保存用户图片
     *
     * @param file   MultipartFile 文件
     * @param userId 用户ID
     * @param prefix 加入文件名的前缀
     * @param dir    保存目录
     * @return 文件路径
     */
    public String saveUserImage(MultipartFile file, int userId, String prefix, String dir) {
        long timestamp = Instant.now().getEpochSecond();
        String fileName = String.format(
                "%s%s_%d.%s",
                prefix != null && !prefix.isEmpty() ? prefix + "_" : "",
                userId,
                timestamp,
                getFileExtension(file.getOriginalFilename())
        );

        String path;
        try {
            path = saveFile(file, fileName, dir, SUPPORTED_IMAGE_TYPES);
        } catch (IllegalArgumentException e) {
            throw new ServerException(e.getMessage());
        }
        return path;
    }

    /**
     * 保存文件到指定路径
     *
     * @param file  MultipartFile 文件
     * @param name  文件名
     * @param dir   文件目录
     * @param types 支持的文件格式
     * @return 文件的完整路径
     */
    public static String saveFile(MultipartFile file, String name, String dir, Set<String> types) {
        if (isInvalidFileType(file, types)) {
            throw new IllegalArgumentException("不支持的文件格式");
        }

        Path uploadPath = Path.of(dir);
        // 构建文件保存路径
        Path filePath = uploadPath.resolve(name);
        try {
            // 检查文件夹是否存在，如果不存在则创建
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            log.error("保存{}文件失败", filePath);
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return filePath.toString();
    }

    /**
     * 获取本地图片并转换为 Base64
     *
     * @param imagePath 图片的本地路径
     * @return Base64 编码的图片字符串
     */
    @Named("imageAsBase64")
    public static String getImageAsBase64(String imagePath) {
        if (StringUtils.isBlank(imagePath)) {
            return null;
        }
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                log.error("图片文件不存在：{}", imagePath);
                throw new ServerException("图片文件不存在");
            }
            // 读取文件并转换为 Base64 编码
            return encodeImageToBase64(imageFile);

        } catch (IOException e) {
            log.error("读取图片文件失败：{}", e.getMessage());
            throw new ServerException("读取图片文件失败");
        }
    }

    /**
     * 将图片文件转换为 Base64 编码
     *
     * @param imageFile 图片文件
     * @return 带有 data 前缀的 Base64 编码字符串
     * @throws IOException 读取文件失败时抛出异常
     */
    private static String encodeImageToBase64(File imageFile) throws IOException {
        try (FileInputStream imageStream = new FileInputStream(imageFile)) {
            byte[] imageBytes = imageStream.readAllBytes();
            String base64String = Base64.getEncoder().encodeToString(imageBytes);
            // 根据图片文件的扩展名来决定前缀
            String mime = getMime(imageFile.getName());
            return "data:image/" + mime + ";base64," + base64String;
        }
    }

    /**
     * 根据图片名得到 MIME 类型
     *
     * @param imageName 图片名
     * @return MIME String
     */
    private static String getMime(String imageName) {
        String extension = getFileExtension(imageName);
        if (extension == null) {
            throw new IllegalStateException("无法解析图片格式: " + imageName);
        }

        return switch (extension) {
            case "jpg", "jpeg" -> "jpeg";
            case "png" -> "png";
            case "gif" -> "gif";
            case "svg" -> "svg+xml";
            case "webp" -> "webp";
            case "avif" -> "avif";
            case "apng" -> "apng";
            case "bmp" -> "bmp";
            case "ico" -> "x-icon"; // ico 一般用 "image/x-icon"
            case "tif", "tiff" -> "tiff";
            default -> throw new IllegalStateException("图片格式不支持: " + imageName);
        };
    }


    /**
     * 获取文件扩展名
     *
     * @param fileName 文件路径
     * @return 扩展名
     */
    private static String getFileExtension(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        int lastIndex = fileName.lastIndexOf(".");
        String extension = (lastIndex == -1) ? "" : fileName.substring(lastIndex + 1);
        return extension.toLowerCase();
    }

    /**
     * 检查文件格式是否**不合法**
     *
     * @param file  MultipartFile 文件
     * @param types 支持的文件格式
     * @return 是否为**不支持**的文件格式
     */
    public static boolean isInvalidFileType(MultipartFile file, Set<String> types) {
        String extension = getFileExtension(file.getOriginalFilename());
        if (extension == null) {
            return true;
        }
        return !types.contains(extension);
    }
}
