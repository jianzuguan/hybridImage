package uk.ac.soton.ecs.jg17g13;

import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {
    protected float[][] template;

    public MyConvolution(float[][] template) {
        // both dimensions are odd
        this.template = template;
    }

    @Override
    public void processImage(FImage image) {
        // convolve image with template and store result back in image
        //
        // hint: use FImage#internalAssign(FImage) to set the contents
        // of your temporary buffer image to the image.

        FImage bufferImage = image.clone().fill(0);

        int templateHeight = template.length;
        int templateWidth = template[0].length;
        int templateHeightMid = templateHeight/2+1;
        int templateWidthMid = templateWidth/2+1;

        // from 0 to middle of template height will be black
        for (int row=templateHeightMid; row<image.height-templateHeightMid; row++) {
            for(int col=templateWidthMid; col<image.width-templateWidthMid; col++) {
                // walk through every pixels within template's range
                for (int tRow=0; tRow<templateHeight; tRow++) {
                    for (int tCol=0; tCol<templateWidth; tCol++) {
                        int y = row - templateHeightMid + tRow;
                        int x = col - templateWidthMid + tCol;
                        // new pixel will be accumulated
                        bufferImage.pixels[row][col] += image.pixels[y][x] * template[tRow][tCol];
                    }
                }
            }
        }

        image.internalAssign(bufferImage);
    }

}
