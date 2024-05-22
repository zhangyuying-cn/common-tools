package com.zyy.tools.util;


import cn.hutool.core.util.RandomUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtils {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "PNG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 500;
    // LOGO宽度
    private static final int WIDTH = 100;
    // LOGO高度
    private static final int HEIGHT = 100;


    /**
     * 创建二维码
     * @param content
     * @param needCompress
     * @return
     * @throws Exception
     */
    private static BufferedImage createImage(String content,
                                             boolean needCompress) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 使用的纠错程度
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置字符编码
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        // 指定生成条形码时要使用的边距（以像素为单位）
        hints.put(EncodeHintType.MARGIN, 1);
        // 设置二维码的四个参数   需要生成的字符串，类型设置为二维码，二维码宽度，二维码高度，字符串字符集
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        // 二维码像素，也就是上面设置的 宽度和高度
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        // 创建二维码对象
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 按照上面定义好的二维码颜色编码生成二维码
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        // 在二维码中插入图片
        QRCodeUtils.insertImage(image, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source,
                                    boolean needCompress) throws Exception {
        Image src = ImageIO.read(new ClassPathResource("static/qrLogo.png").getInputStream());
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();

        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.fillOval(x, y, width, height);
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, height, 6, 6);
        graph.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        graph.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static String encode(String content, String destPath,
                                boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content,
                needCompress);
        mkdirs(destPath);
        String file = RandomUtil.randomInt(99999999) + ".jpg";
        ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));
        return file;
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 存放目录
     * @date 2013-12-11 上午10:16:36
     */
    private static void mkdirs(String destPath) {
        File file = new File(destPath);
        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content 内容
     * @param output  输出流
     * @throws Exception
     */
    public static void encode(String content,
                              OutputStream output) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content,
                true);
        ImageIO.write(image, FORMAT_NAME, output);
    }



}
