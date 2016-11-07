package uk.ac.soton.ecs.jg17g13;

import org.openimaj.image.FImage;

public class FullImageConvolution extends MyConvolution {

    public FullImageConvolution(float[][] template) {
        super(template);
    }

    @Override
    public void processImage(FImage image) {
        // convolve image with template and store result back in image
        //
        // hint: use FImage#internalAssign(FImage) to set the contents
        // of your temporary buffer image to the image.

        System.out.println(template);

        int templateHeight = template.length;
        int templateWidth = template[0].length;
        int templateHeightMid = templateHeight/2;
        int templateWidthMid = templateWidth/2;

        FImage bufferImage = image.newInstance(image.getWidth()+templateWidth-1, image.getHeight()+templateHeight-1);
        FImage resultImage = bufferImage.clone().fill(0);

        // duplicate border pixels
        for (int row=0; row<bufferImage.height; row++) {
            for(int col=0; col<bufferImage.width; col++) {
                if(row<templateHeightMid) {
                    if(col<=templateWidthMid)
                        bufferImage.pixels[row][col] = image.pixels[0][0];

                    else if(col>templateWidthMid && col<bufferImage.width-templateWidthMid)
                        bufferImage.pixels[row][col] = image.pixels[0][col - templateWidthMid];

                    else
                        bufferImage.pixels[row][col] = image.pixels[0][image.width-1];


                } else if(row>=templateHeightMid && row<bufferImage.height-templateHeightMid) {
                    if(col<=templateWidthMid)
                        bufferImage.pixels[row][col] = image.pixels[row-templateHeightMid][0];

                    else if(col>templateWidthMid && col<bufferImage.width-templateWidthMid)
                        bufferImage.pixels[row][col] = image.pixels[row-templateHeightMid][col-templateWidthMid];

                    else
                        bufferImage.pixels[row][col] = image.pixels[row-templateHeightMid][image.width-1];


                } else {
                    if(col<=templateWidthMid)
                        bufferImage.pixels[row][col] = image.pixels[bufferImage.height-templateHeight][0];

                    else if(col>templateWidthMid && col<bufferImage.width-templateWidthMid)
                        bufferImage.pixels[row][col] = image.pixels[bufferImage.height-templateHeight][col-templateWidthMid];

                    else
                        bufferImage.pixels[row][col] = image.pixels[bufferImage.height-templateHeight][image.width-1];

                }

            }
        }

        // from 0 to middle of template height will be black
        for (int row=templateHeightMid; row<bufferImage.height-templateHeightMid; row++) {
            for(int col=templateWidthMid; col<bufferImage.width-templateWidthMid; col++) {
                // walk through every pixels within template's range
                for (int tRow=0; tRow<templateHeight; tRow++) {
                    for (int tCol=0; tCol<templateWidth; tCol++) {
                        int y = row - templateHeightMid + tRow;
                        int x = col - templateWidthMid + tCol;
                        // new pixel will be accumulated
                        resultImage.pixels[row][col] += bufferImage.pixels[y][x] * template[tRow][tCol];
                    }
                }
            }
        }

        image.internalAssign(resultImage.trim());
    }
}
