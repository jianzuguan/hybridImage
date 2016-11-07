package uk.ac.soton.ecs.jg17g13;

import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.resize.BilinearInterpolation;

import java.io.File;
import java.io.IOException;

public class HybridImage {
    MyConvolution conv;

    public HybridImage(MyConvolution conv) {
        this.conv = conv;
    }

    public MBFImage getLowPassImage(String path) {
        MBFImage img = null;
        try {
            img = ImageUtilities.readMBF(new File(path));
            img = img.process(conv);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public MBFImage getHighPassImage(String path) {
        // image lowpass filtered and highpass filtered
        MBFImage imgLPF = null, imgHPF = null;

        try {
            imgLPF = ImageUtilities.readMBF(new File(path));
            // make a copy of the original image
            imgHPF = imgLPF.clone();
            // create lowpass filtered image
            imgLPF =imgLPF.process(conv);

            System.out.print(imgLPF.getWidth()+"\t"+imgLPF.getHeight()+"\t"+imgHPF.getWidth()+"\t"+imgHPF.getHeight());

            // original iamge - lowpass image
            imgHPF = imgHPF.subtract(imgLPF);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgHPF;
    }

    public MBFImage getHybridImage(String pathL, String pathH) {
        MBFImage imgL = getLowPassImage(pathL);
        MBFImage imgH = getHighPassImage(pathH);

        return imgL.add(imgH);
    }

    public MBFImage getAllSize(MBFImage img) {
        // new image to store image in different size
        MBFImage allSizeImg = img.newInstance((int)(1.5*img.getWidth()), img.getHeight());

        int width = img.getWidth();
        int height = img.getHeight();
        // scale from old to new
        float scaleO2N = 0.5f;
        // scale from new to old
        float scaleN2O = 2f;
        // scale goes FROM the NEW image TO the ORIGINAL
        BilinearInterpolation blInterpolation = new BilinearInterpolation(width, height, scaleN2O);

        // top left corner where new image will draw
        int pointX=0;
        int pointY=0;

        int widthLimit = 32;
        int heightLimit = 32;
        while(width>widthLimit && height>heightLimit) {
            allSizeImg.drawImage(img, pointX, pointY);

            pointX += width;

            img.processInplace(blInterpolation);
            width *= scaleO2N;
            height *= scaleO2N;

            allSizeImg.drawImage(img, pointX, pointY);

            pointY += height;

            img.processInplace(blInterpolation);
            width *= scaleO2N;
            height *= scaleO2N;
        }

        return allSizeImg;
    }


}
