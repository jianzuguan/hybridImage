
package uk.ac.soton.ecs.jg17g13;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.MBFImage;

import java.util.ArrayList;
import java.util.HashMap;


public class App {
    public static void main( String[] args ) {
        HashMap<String, String> paths = new HashMap<String, String>();
        paths.put("cat", "data/cat.bmp");
        paths.put("dog", "data/dog.bmp");
        paths.put("einstein", "data/einstein.bmp");
        paths.put("marilyn", "data/marilyn.bmp");
        paths.put("bicycle", "data/bicycle.bmp");
        paths.put("motorcycle", "data/motorcycle.bmp");
        paths.put("brid", "data/brid.bmp");
        paths.put("plane", "data/plane.bmp");
        paths.put("fish", "data/fish.bmp");
        paths.put("submarine", "data/submarine.bmp");

        HybridImage hi = new HybridImage(5f);

        MBFImage imgDog = hi.getLowPassImage(paths.get("dog"));
        DisplayUtilities.display(imgDog);

/*
        MBFImage imgDogMultiFiltered = hi.getMultiLowPassImage(paths.get("dog"));
        DisplayUtilities.display(imgDogMultiFiltered);
*/

/*
        MBFImage imgCat = hi.getHighPassImage(paths.get("cat"));
        DisplayUtilities.display(imgCat);
*/

        MBFImage hybridImg = hi.getHybridImage(paths.get("dog"), paths.get("cat"));
//        DisplayUtilities.display(hybridImg);

/*
        System.out.print("processing all size...");
        MBFImage allSizeImg = hi.getAllSize(hybridImg);
        DisplayUtilities.display(allSizeImg);
        System.out.println("done");
*/

    }
}
