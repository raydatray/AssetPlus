package main.java.ca.mcgill.ecse.assetplus.persistence;

import ca.mcgill.ecse.biketourplus.application.AssetPlusApplication;
import ca.mcgill.ecse.biketourplus.model.AssetPlus;

public class AssetPlusPersistence {

    private static String filename = "ap.data";
    private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.assetplus");

    public static void setFilename(String filename) {
        AssetPlusPersistence.filename = filename;
    }

    public static void save() {
        save(AssetPlusApplication.getAssetPlus());
    }

    public static void save(AssetPlus ap) {
        serializer.serialize(ap, filename);
    }

    public static AssetPlus load() {
        var assetPlus = (AssetPlus) serializer.deserialize(filename);
        // model cannot be loaded - create empty AssetPlus
        if (assetPlus == null) {
            assetPlus = new AssetPlus();
        } else {
            assetPlus.reinitialize();
        }
        return assetPlus;
    }

}
