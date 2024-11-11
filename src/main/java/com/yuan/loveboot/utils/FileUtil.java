package com.yuan.loveboot.utils;

import com.yuan.loveboot.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashSet;
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

    // 支持的图片格式
    private static final Set<String> SUPPORTED_IMAGE_EXTENSIONS = new HashSet<>();

    static {
        SUPPORTED_IMAGE_EXTENSIONS.add("jpg");
        SUPPORTED_IMAGE_EXTENSIONS.add("jpeg");
        SUPPORTED_IMAGE_EXTENSIONS.add("png");
        SUPPORTED_IMAGE_EXTENSIONS.add("gif");
        SUPPORTED_IMAGE_EXTENSIONS.add("bmp");
        SUPPORTED_IMAGE_EXTENSIONS.add("webp");
        SUPPORTED_IMAGE_EXTENSIONS.add("svg");
    }

    /**
     * 生成文件名
     *
     * @param month     月份
     * @param userId    用户ID
     * @param extension 文件扩展名
     * @return 包含时间戳的文件名
     */
    public String generateFileName(String month, int userId, String extension) {
        long timestamp = Instant.now().getEpochSecond();
        return String.format("%s_%d_%d.%s", month, userId, timestamp, extension);
    }

    /**
     * 保存文件
     *
     * @param fileBytes 文件字节数组
     * @param name      文件名
     * @param dir       文件目录
     * @return 文件路径
     */
    public String saveFile(byte[] fileBytes, String name, String dir) {
        // 创建保存目录
        Path uploadPath = Path.of(dir);

        try {
            // 检查文件夹是否存在，如果不存在则创建
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            log.error("创建{}文件夹失败", uploadPath);
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // 构建文件保存路径
        Path filePath = uploadPath.resolve(name);

        try {
            // 保存文件
            Files.write(filePath, fileBytes);
        } catch (IOException e) {
            log.error("保存{}文件失败", filePath);
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // 返回文件路径
        return filePath.toString();
    }

    /**
     * 保存奖励图片
     *
     * @param imageBytes 图片字节数组
     * @param month      月份
     * @param userId     用户ID
     * @return 文件路径
     */
    public String saveRewardImage(byte[] imageBytes, String month, int userId) {
        // 获取文件扩展名
        String extension = getFileExtensionFromBytes(imageBytes);

        // 校验文件扩展名是否是支持的图片格式
        if (!isSupportedImageExtension(extension)) {
            throw new ServerException("图片格式错误，支持的格式为：jpg、jpeg、png、gif、bmp、webp、svg");
        }

        // 保存文件
        return saveFile(imageBytes, generateFileName(month, userId, extension), rewardDir);
    }

    /**
     * 根据字节数组获取文件扩展名
     *
     * @param fileBytes 文件字节数组
     * @return 扩展名（不带点）
     */
    public String getFileExtensionFromBytes(byte[] fileBytes) {
        // 使用 ByteArrayInputStream 读取字节流
        try (ByteArrayInputStream bais = new ByteArrayInputStream(fileBytes)) {
            // 使用 ImageIO 来读取图像
            BufferedImage bufferedImage = ImageIO.read(bais);

            // 如果无法读取为图片，抛出异常
            if (bufferedImage == null) {
                throw new ServerException("无法识别文件格式");
            }

            // 使用 ImageIO 获取所有支持的图片格式
            for (String format : SUPPORTED_IMAGE_EXTENSIONS) {
                // 检查是否可以通过该格式来写入图片
                if (ImageIO.getImageWritersBySuffix(format).hasNext()) {
                    return format;
                }
            }
            throw new ServerException("不支持的图片格式");
        } catch (IOException e) {
            throw new ServerException("图片读取失败");
        }
    }

    /**
     * 校验文件扩展名是否为支持的图片格式
     *
     * @param extension 文件扩展名
     * @return 是否为支持的图片格式
     */
    private boolean isSupportedImageExtension(String extension) {
        return SUPPORTED_IMAGE_EXTENSIONS.contains(extension.toLowerCase());
    }
}
