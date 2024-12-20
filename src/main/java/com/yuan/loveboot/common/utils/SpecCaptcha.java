package com.yuan.loveboot.common.utils;

import com.wf.captcha.base.Captcha;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 验证码工具类
 * 修改为透明背景
 *
 * @author Maverick
 */
@NoArgsConstructor
@Slf4j
public class SpecCaptcha extends Captcha {
    public SpecCaptcha(int width, int height) {
        this();
        this.setWidth(width);
        this.setHeight(height);
    }

    public SpecCaptcha(int width, int height, int len) {
        this(width, height);
        this.setLen(len);
    }

    public SpecCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        this.setFont(font);
    }

    public boolean out(OutputStream out) {
        return this.graphicsImage(this.textChar(), out);
    }

    public String toBase64() {
        return this.toBase64("data:image/png;base64,");
    }

    private boolean graphicsImage(char[] strs, OutputStream out) {
        try {
            BufferedImage bi = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) bi.getGraphics();
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, this.width, this.height);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            this.drawOval(2, g2d);
            g2d.setStroke(new BasicStroke(2.0F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            this.drawBesselLine(1, g2d);
            g2d.setFont(this.getFont());
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int fW = this.width / strs.length;
            int fSp = (fW - (int) fontMetrics.getStringBounds("W", g2d).getWidth()) / 2;

            for (int i = 0; i < strs.length; ++i) {
                g2d.setColor(this.color());
                int fY = this.height - (this.height - (int) fontMetrics.getStringBounds(String.valueOf(strs[i]), g2d).getHeight() >> 1);
                g2d.drawString(String.valueOf(strs[i]), i * fW + fSp + 3, fY - 3);
            }

            g2d.dispose();
            ImageIO.write(bi, "png", out);
            out.flush();
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }

        }

        return false;
    }
}

