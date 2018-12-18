package com.google.location.lbs.asn1.supl2.ver2_ulp_components;

// Copyright 2008 Google Inc. All Rights Reserved.
/*
 * This class is AUTOMATICALLY GENERATED. Do NOT EDIT.
 */


//
//
import com.google.location.lbs.asn1.base.Asn1Integer;
import com.google.location.lbs.asn1.base.Asn1Null;
import com.google.location.lbs.asn1.base.Asn1Object;
import com.google.location.lbs.asn1.base.Asn1Sequence;
import com.google.location.lbs.asn1.base.Asn1Tag;
import com.google.location.lbs.asn1.base.BitStream;
import com.google.location.lbs.asn1.base.BitStreamReader;
import com.google.location.lbs.asn1.base.SequenceComponent;
import com.google.common.collect.ImmutableList;
import java.util.Collection;
import javax.annotation.Nullable;


/**
* @author Torbjorn Gannholm (tobe@google.com)
*/
public  class PolygonArea extends Asn1Sequence {
  //

  private static final Asn1Tag TAG_PolygonArea
      = Asn1Tag.fromClassAndNumber(-1, -1);

  public PolygonArea() {
    super();
  }

  @Override
  @Nullable
  protected Asn1Tag getTag() {
    return TAG_PolygonArea;
  }

  @Override
  protected boolean isTagImplicit() {
    return true;
  }

  public static Collection<Asn1Tag> getPossibleFirstTags() {
    if (TAG_PolygonArea != null) {
      return ImmutableList.of(TAG_PolygonArea);
    } else {
      return Asn1Sequence.getPossibleFirstTags();
    }
  }

  /**
   * Creates a new PolygonArea from encoded stream.
   */
  public static PolygonArea fromPerUnaligned(byte[] encodedBytes) {
    PolygonArea result = new PolygonArea();
    result.decodePerUnaligned(new BitStreamReader(encodedBytes));
    return result;
  }

  /**
   * Creates a new PolygonArea from encoded stream.
   */
  public static PolygonArea fromPerAligned(byte[] encodedBytes) {
    PolygonArea result = new PolygonArea();
    result.decodePerAligned(new BitStreamReader(encodedBytes));
    return result;
  }



  @Override protected boolean isExtensible() {
    return false;
  }

  @Override public boolean containsExtensionValues() {
    for (SequenceComponent extensionComponent : getExtensionComponents()) {
      if (extensionComponent.isExplicitlySet()) return true;
    }
    return false;
  }

  
  private PolygonDescription polygonDescription_;
  public PolygonDescription getPolygonDescription() {
    return polygonDescription_;
  }
  /**
   * @throws ClassCastException if value is not a PolygonDescription
   */
  public void setPolygonDescription(Asn1Object value) {
    this.polygonDescription_ = (PolygonDescription) value;
  }
  public PolygonDescription setPolygonDescriptionToNewInstance() {
    polygonDescription_ = new PolygonDescription();
    return polygonDescription_;
  }
  
  private PolygonArea.polygonHysteresisType polygonHysteresis_;
  public PolygonArea.polygonHysteresisType getPolygonHysteresis() {
    return polygonHysteresis_;
  }
  /**
   * @throws ClassCastException if value is not a PolygonArea.polygonHysteresisType
   */
  public void setPolygonHysteresis(Asn1Object value) {
    this.polygonHysteresis_ = (PolygonArea.polygonHysteresisType) value;
  }
  public PolygonArea.polygonHysteresisType setPolygonHysteresisToNewInstance() {
    polygonHysteresis_ = new PolygonArea.polygonHysteresisType();
    return polygonHysteresis_;
  }
  

  

  

  @Override public Iterable<? extends SequenceComponent> getComponents() {
    ImmutableList.Builder<SequenceComponent> builder = ImmutableList.builder();
    
    builder.add(new SequenceComponent() {
          Asn1Tag tag = Asn1Tag.fromClassAndNumber(2, 0);

          @Override public boolean isExplicitlySet() {
            return getPolygonDescription() != null;
          }

          @Override public boolean hasDefaultValue() {
            return false;
          }

          @Override public boolean isOptional() {
            return false;
          }

          @Override public Asn1Object getComponentValue() {
            return getPolygonDescription();
          }

          @Override public void setToNewInstance() {
            setPolygonDescriptionToNewInstance();
          }

          @Override public Collection<Asn1Tag> getPossibleFirstTags() {
            return tag == null ? PolygonDescription.getPossibleFirstTags() : ImmutableList.of(tag);
          }

          @Override
          public Asn1Tag getTag() {
            return tag;
          }

          @Override
          public boolean isImplicitTagging() {
            return true;
          }

          @Override public String toIndentedString(String indent) {
                return "polygonDescription : "
                    + getPolygonDescription().toIndentedString(indent);
              }
        });
    
    builder.add(new SequenceComponent() {
          Asn1Tag tag = Asn1Tag.fromClassAndNumber(2, 1);

          @Override public boolean isExplicitlySet() {
            return getPolygonHysteresis() != null;
          }

          @Override public boolean hasDefaultValue() {
            return false;
          }

          @Override public boolean isOptional() {
            return true;
          }

          @Override public Asn1Object getComponentValue() {
            return getPolygonHysteresis();
          }

          @Override public void setToNewInstance() {
            setPolygonHysteresisToNewInstance();
          }

          @Override public Collection<Asn1Tag> getPossibleFirstTags() {
            return tag == null ? PolygonArea.polygonHysteresisType.getPossibleFirstTags() : ImmutableList.of(tag);
          }

          @Override
          public Asn1Tag getTag() {
            return tag;
          }

          @Override
          public boolean isImplicitTagging() {
            return true;
          }

          @Override public String toIndentedString(String indent) {
                return "polygonHysteresis : "
                    + getPolygonHysteresis().toIndentedString(indent);
              }
        });
    
    return builder.build();
  }

  @Override public Iterable<? extends SequenceComponent>
                                                    getExtensionComponents() {
    ImmutableList.Builder<SequenceComponent> builder = ImmutableList.builder();
      
      return builder.build();
    }

  
  
  
  // Copyright 2008 Google Inc. All Rights Reserved.
/*
 * This class is AUTOMATICALLY GENERATED. Do NOT EDIT.
 */


//

/**
 * @author Torbjorn Gannholm (tobe@google.com)
 */
public static class polygonHysteresisType extends Asn1Integer {
  //

  private static final Asn1Tag TAG_polygonHysteresisType
      = Asn1Tag.fromClassAndNumber(-1, -1);

  public polygonHysteresisType() {
    super();
    setValueRange(new java.math.BigInteger("1"), new java.math.BigInteger("100000"));

  }

  @Override
  @Nullable
  protected Asn1Tag getTag() {
    return TAG_polygonHysteresisType;
  }

  @Override
  protected boolean isTagImplicit() {
    return true;
  }

  public static Collection<Asn1Tag> getPossibleFirstTags() {
    if (TAG_polygonHysteresisType != null) {
      return ImmutableList.of(TAG_polygonHysteresisType);
    } else {
      return Asn1Integer.getPossibleFirstTags();
    }
  }

  /**
   * Creates a new polygonHysteresisType from encoded stream.
   */
  public static polygonHysteresisType fromPerUnaligned(byte[] encodedBytes) {
    polygonHysteresisType result = new polygonHysteresisType();
    result.decodePerUnaligned(new BitStreamReader(encodedBytes));
    return result;
  }

  /**
   * Creates a new polygonHysteresisType from encoded stream.
   */
  public static polygonHysteresisType fromPerAligned(byte[] encodedBytes) {
    polygonHysteresisType result = new polygonHysteresisType();
    result.decodePerAligned(new BitStreamReader(encodedBytes));
    return result;
  }

  @Override public Iterable<BitStream> encodePerUnaligned() {
    return super.encodePerUnaligned();
  }

  @Override public Iterable<BitStream> encodePerAligned() {
    return super.encodePerAligned();
  }

  @Override public void decodePerUnaligned(BitStreamReader reader) {
    super.decodePerUnaligned(reader);
  }

  @Override public void decodePerAligned(BitStreamReader reader) {
    super.decodePerAligned(reader);
  }

  @Override public String toString() {
    return toIndentedString("");
  }

  public String toIndentedString(String indent) {
    return "polygonHysteresisType = " + getInteger() + ";\n";
  }
}

  

    

  @Override public Iterable<BitStream> encodePerUnaligned() {
    return super.encodePerUnaligned();
  }

  @Override public Iterable<BitStream> encodePerAligned() {
    return super.encodePerAligned();
  }

  @Override public void decodePerUnaligned(BitStreamReader reader) {
    super.decodePerUnaligned(reader);
  }

  @Override public void decodePerAligned(BitStreamReader reader) {
    super.decodePerAligned(reader);
  }

  @Override public String toString() {
    return toIndentedString("");
  }

  public String toIndentedString(String indent) {
    StringBuilder builder = new StringBuilder();
    builder.append("PolygonArea = {\n");
    final String internalIndent = indent + "  ";
    for (SequenceComponent component : getComponents()) {
      if (component.isExplicitlySet()) {
        builder.append(internalIndent)
            .append(component.toIndentedString(internalIndent));
      }
    }
    if (isExtensible()) {
      builder.append(internalIndent).append("...\n");
      for (SequenceComponent component : getExtensionComponents()) {
        if (component.isExplicitlySet()) {
          builder.append(internalIndent)
              .append(component.toIndentedString(internalIndent));
        }
      }
    }
    builder.append(indent).append("};\n");
    return builder.toString();
  }
}