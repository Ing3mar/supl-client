// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.location.lbs.asn1.base;

import static com.google.location.lbs.asn1.base.PerAlignedUtils.SIXTYFOUR_K;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.nio.ByteBuffer;
import java.util.Collection;

/**
 * A general string is any ISO 646 related 8-bit encoding, presumably agreed on
 * by other means. TODO(tobe): verify.
 *
 * Implements ASN.1 functionality.
 *
 * @author tobe@google.com (Torbjorn Gannholm)
 */
public class Asn1GeneralString extends Asn1Object {
  private static final Collection<Asn1Tag> possibleFirstTags =
      ImmutableList.of(Asn1Tag.GENERAL_STRING);

  private byte[] value;
  private int minimumSize = 0;
  private Integer maximumSize = null; // Null == unconstrained.

  public static Collection<Asn1Tag> getPossibleFirstTags() {
    return possibleFirstTags;
  }

  @Override Asn1Tag getDefaultTag() {
    return Asn1Tag.GENERAL_STRING;
  }

  @Override int getBerValueLength() {
    throw new UnsupportedOperationException();
  }

  @Override void encodeBerValue(ByteBuffer buf) {
    throw new UnsupportedOperationException();
  }

  @Override void decodeBerValue(ByteBuffer buf) {
    throw new UnsupportedOperationException();
  }

  protected void setMinSize(int min) {
    minimumSize = min;
  }

  protected void setMaxSize(int max) {
    maximumSize = max;
  }

  public byte[] getValue() {
    return value;
  }

  public void setValue(byte[] value) {
    this.value = value;
  }

  private Iterable<BitStream> encodePerImpl(boolean aligned) {
    Preconditions.checkNotNull(value, "No value set.");
    Preconditions.checkState(value.length >= minimumSize, "Value too short.");
    Preconditions.checkState(maximumSize == null || value.length <= maximumSize,
                             "Value too long.");
    // TODO(tobe): handle size extensibility if needed.
    int characterBitCount = 8;
    if (maximumSize == null) {
      throw new UnsupportedOperationException("unconstrained unimplemented");
    }

    BitStream result = new BitStream();
    for (byte b : value) {
      result.appendByte(b);
    }
    if (aligned && maximumSize * characterBitCount > 16) {
      result.setBeginByteAligned();
    }

    if (minimumSize == maximumSize
        && maximumSize < SIXTYFOUR_K) {
      return ImmutableList.of(result);
    }

    if (maximumSize >= SIXTYFOUR_K) {
      throw new UnsupportedOperationException("large string unimplemented");
    }

    // A little oddity when maximumSize != minimumSize.
    if (aligned && maximumSize * characterBitCount == 16) {
      result.setBeginByteAligned();
    }

    // Must be preceded by a count. The count and the bit field may be
    // independently aligned.
    BitStream count = null;
    if (aligned) {
      count = PerAlignedUtils.encodeSmallConstrainedWholeNumber(
          value.length, minimumSize, maximumSize);
    } else {
      count = PerUnalignedUtils.encodeConstrainedWholeNumber(
          value.length, minimumSize, maximumSize);
    }
    return ImmutableList.of(count, result);
  }

  @Override public Iterable<BitStream> encodePerUnaligned() {
    return encodePerImpl(false);
  }

  @Override public Iterable<BitStream> encodePerAligned() {
    return encodePerImpl(true);
  }

  private void decodePerImpl(BitStreamReader reader, boolean aligned) {
    // TODO(tobe): handle size extensibility if needed.
    // TODO(tobe): handle alphabet constraints when and if needed.
    int characterBitCount = 8;
    if (maximumSize == null) {
      throw new UnsupportedOperationException("unconstrained unimplemented");
    }

    if (minimumSize == maximumSize
        && maximumSize < SIXTYFOUR_K) {
      if (aligned && maximumSize * characterBitCount > 16) {
        reader.spoolToByteBoundary();
      }
      value = new byte[maximumSize];
      for (int i = 0; i < maximumSize; i++) {
        value[i] = reader.readByte();
      }
      return;
    }

    if (maximumSize >= SIXTYFOUR_K) {
      throw new UnsupportedOperationException("large string unimplemented");
    }

    // Value is preceded by a count.
    int count = 0;
    if (aligned) {
      count = PerAlignedUtils.decodeSmallConstrainedWholeNumber(
          reader, minimumSize, maximumSize);
    } else {
      count = PerUnalignedUtils.decodeConstrainedWholeNumber(
          reader, minimumSize, maximumSize);
    }

    if (aligned && maximumSize * characterBitCount >= 16) {
      reader.spoolToByteBoundary();
    }

    value = new byte[count];
    for (int i = 0; i < count; i++) {
      value[i] = reader.readByte();
    }
  }

  @Override public void decodePerUnaligned(BitStreamReader reader) {
    decodePerImpl(reader, false);
  }

  @Override public void decodePerAligned(BitStreamReader reader) {
    decodePerImpl(reader, true);
  }
}