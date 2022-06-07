package com.onthegomap.planetiler.examples;

import com.onthegomap.planetiler.FeatureCollector;
import com.onthegomap.planetiler.Planetiler;
import com.onthegomap.planetiler.Profile;
import com.onthegomap.planetiler.reader.SourceFeature;
import java.nio.file.Path;

public class DeeplateProfile implements Profile {

  @Override
  public void processFeature(SourceFeature sourceFeature, FeatureCollector features) {
    if (sourceFeature.hasTag("highway")) {
      features.line("lines").setMinZoom(0);
    }

  }

  @Override
  public String name() {
    return "Deeplate Profile";
  }

  public static void main(String... args) throws Exception {
    Planetiler.create(args)
      .setProfile(new DeeplateProfile())
      .addOsmSource("osm", Path.of("data", "sources", "latvia.pbf"), "geofabrik:monaco")
      .overwriteOutput("mbtiles", Path.of("data", "latvia-roads.mbtiles"))
      .run();
  }
}
