package com.onthegomap.flatmap.reader;

import com.onthegomap.flatmap.geo.GeoUtils;
import com.onthegomap.flatmap.reader.osm.OsmReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Lineal;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.Puntal;

public class ReaderFeature extends SourceFeature {

  private final Geometry latLonGeometry;
  private final Map<String, Object> tags;

  public ReaderFeature(Geometry latLonGeometry, Map<String, Object> tags, long id) {
    this(latLonGeometry, tags, null, null, id);
  }

  public ReaderFeature(Geometry latLonGeometry, Map<String, Object> tags, String source, String sourceLayer,
    long id) {
    this(latLonGeometry, tags, source, sourceLayer, id, null);
  }

  public ReaderFeature(Geometry latLonGeometry, int numTags, String source, String sourceLayer, long id) {
    this(latLonGeometry, new HashMap<>(numTags), source, sourceLayer, id);
  }

  public ReaderFeature(Geometry latLonGeometry, Map<String, Object> tags, String source, String sourceLayer,
    long id, List<OsmReader.RelationMember<OsmReader.RelationInfo>> relations) {
    super(tags, source, sourceLayer, relations, id);
    this.latLonGeometry = latLonGeometry;
    this.tags = tags;
  }

  @Override
  public Geometry latLonGeometry() {
    return latLonGeometry;
  }

  private Geometry worldGeometry;

  @Override
  public Geometry worldGeometry() {
    return worldGeometry != null ? worldGeometry
      : (worldGeometry = GeoUtils.ensureDescendingPolygonsSizes(GeoUtils.latLonToWorldCoords(latLonGeometry)));
  }

  public Map<String, Object> tags() {
    return tags;
  }

  @Override
  public boolean isPoint() {
    return latLonGeometry instanceof Puntal;
  }

  @Override
  public boolean canBePolygon() {
    return latLonGeometry instanceof Polygonal;
  }

  @Override
  public boolean canBeLine() {
    return latLonGeometry instanceof Lineal;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (ReaderFeature) obj;
    return Objects.equals(this.latLonGeometry, that.latLonGeometry) &&
      Objects.equals(this.tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(latLonGeometry, tags);
  }

  @Override
  public String toString() {
    return "ReaderFeature[" +
      "geometry=" + latLonGeometry + ", " +
      "tags=" + tags + ']';
  }
}
