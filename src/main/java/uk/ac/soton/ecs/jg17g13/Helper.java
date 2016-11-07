package uk.ac.soton.ecs.jg17g13;

import org.openimaj.image.FImage;
import org.openimaj.image.processing.convolution.Gaussian2D;


public class Helper {

    public static int getSize(float sigma) {
        // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
        int size = (int) (8.0f * sigma + 1.0f);
        // size must be odd
        if (size % 2 == 0) size++;
        return size;
    }

    public static FImage getTemplate(float sigma) {
        FImage template = Gaussian2D.createKernelImage(getSize(sigma), sigma);
        return template;
    }
}
