package com.wap.tools.service;

import jodd.io.FileUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;



public class Cropper {


    /**
     * 变更图像为指定大小
     *
     * @param bufferedimage
     *            目标图像
     * @param w
     *            宽
     * @param h
     *            高
     * @return
     */
//    public static BufferedImage resizeImage(final BufferedImage bufferedimage,
//                                            final int w, final int h) {
//        int type = bufferedimage.getColorModel().getTransparency();
//        BufferedImage img;
//        Graphics2D graphics2d;
//        (graphics2d = (img = createImage(w, h, type))
//                .createGraphics()).setRenderingHint(
//                RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0, bufferedimage
//                .getWidth(), bufferedimage.getHeight(), null);
//        graphics2d.dispose();
//        return img;
//    }

    public void fromBase64(){
        String data= null;
        try {
            data = FileUtil.readString(System.getProperty("user.dir") + "/img.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64Image = data.split(",")[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            File outputfile = new File("/home/zuo_ji/img.png");
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转图片为指定角度
     *
     * @param bufferedimage
     *            目标图像
     * @param degree
     *            旋转角度
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    public void crop(){
        try {
            BufferedImage originalImgage = ImageIO.read(new File("C:/Test/pool.jpg"));

            System.out.println("Original Image Dimension: "+originalImgage.getWidth()+"x"+originalImgage.getHeight());

            BufferedImage SubImgage = originalImgage.getSubimage(300, 150, 200, 200);
            System.out.println("Cropped Image Dimension: "+SubImgage.getWidth()+"x"+SubImgage.getHeight());

            File outputfile = new File("C:/Test/croppedImage.jpg");
            ImageIO.write(SubImgage, "jpg", outputfile);

            System.out.println("Image cropped successfully: "+outputfile.getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Cropper().fromBase64();
    }
}
