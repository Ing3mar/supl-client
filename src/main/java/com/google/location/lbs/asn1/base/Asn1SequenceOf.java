// Copyright 2008 Google Inc. All Rights Reserved.

package com.google.location.lbs.asn1.base;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Implements ASN.1 functionality.
 *
 * @author tobe@google.com (Torbjorn Gannholm)
 */
public abstract class Asn1SequenceOf<T extends Asn1Object> extends Asn1Object {
  private static final Collection<Asn1Tag> possibleFirstTags =
      ImmutableList.of(Asn1Tag.SEQUENCE);

  // TODO(tobe): Protected field is not ideal.
  protected LinkedList<T> sequence = new LinkedList<T>();
  private int minimumSize = 0;
  private Integer maximumSize = null; // Null is unbounded.

  public static Collection<Asn1Tag> getPossibleFirstTags() {
    return possibleFirstTags;
  }

  protected void setMinSize(int min) {
    minimumSize = min;
  }

  protected void setMaxSize(int max) {
    maximumSize = max;
  }

  public void add(T component) {
    sequence.addLast(component);
  }

  public Iterable<T> getValues() {
    return sequence;
  }

  public abstract T createAndAddValue();

  @Override Asn1Tag getDefaultTag() {
    return Asn1Tag.SEQUENCE;
  }

  @Override boolean isConstructed() {
    return true;
  }

  @Override int getBerValueLength() {
    int length = 0;
    for (Asn1Object component : sequence) {
      length += component.getBerLength();
    }
    return length;
  }

  @Override void encodeBerValue(ByteBuffer buf) {
    for (Asn1Object component : sequence) {
      component.encodeBer(buf);
    }
  }

  @Override void decodeBerValue(ByteBuffer buf) {
    while (buf.hasRemaining()) {
      Asn1Tag tag = Asn1Tag.readTag(buf);
      int valueLength = Asn1Tag.readLength(buf);
      T value = createAndAddValue();
      if (value.getTag() != null) {
        checkTag(tag, value.getTag());
        if (!value.isTagImplicit()) {
          // read inner tag + length
          checkTag(value.getDefaultTag(), Asn1Tag.readTag(buf));
          valueLength = Asn1Tag.readLength(buf);
        }
      } else {
        checkTag(tag, value.getDefaultTag());
      }
      ByteBuffer subBuf = ByteBuffer.wrap(buf.array(), buf.position(), valueLength);
      value.decodeBerValue(subBuf);
      if (subBuf.hasRemaining()) {
        throw new IllegalArgumentException("child failed to consume all input");
      }
      buf.position(buf.position() + valueLength);
    }
  }

  private Iterable<BitStream> encodePerImpl(boolean aligned) {
    Preconditions.checkState(sequence.size() >= minimumSize,
                             "Too few components.");
    Preconditions.checkState(maximumSize == null
                             || sequence.size() <= maximumSize,
                             "Too many components.");
    ImmutableList.Builder<BitStream> listBuilder = ImmutableList.builder();
    // TODO(tobe) when needed: handle size extensibility.
    if (maximumSize == null || maximumSize >= PerAlignedUtils.SIXTYFOUR_K) {
      // TODO(tobe): refactor to encodeSemiConstrainedLengthOfObjects.
      if (aligned) {
        listBuilder.add(PerAlignedUtils.encodeSemiConstrainedLength(sequence.size()));
      } else {
        listBuilder.add(PerUnalignedUtils.encodeSemiConstrainedLength(sequence.size()));
      }
    } else if (maximumSize != minimumSize) {
      if (aligned) {
        listBuilder.add(
            PerAlignedUtils.encodeSmallConstrainedWholeNumber(
                sequence.size(), minimumSize, maximumSize));
      } else {
        listBuilder.add(
            PerUnalignedUtils.encodeConstrainedWholeNumber(
                sequence.size(), minimumSize, maximumSize));
      }
    }
    for (Asn1Object component : sequence) {
      if (aligned) {
        listBuilder.addAll(component.encodePerAligned());
      } else {
        listBuilder.addAll(component.encodePerUnaligned());
      }
    }
    return listBuilder.build();
  }

  @Override public Iterable<BitStream> encodePerUnaligned() {
    return encodePerImpl(false);
  }

  @Override public Iterable<BitStream> encodePerAligned() {
    return encodePerImpl(true);
  }

  private void decodePerImpl(BitStreamReader reader, boolean aligned) {
    // TODO(tobe) when needed: handle size extensibility.
    int size = minimumSize;
    if (maximumSize == null || maximumSize >= PerAlignedUtils.SIXTYFOUR_K) {
      // TODO(tobe): refactor to decodeSemiConstrainedLengthOfObjects.
      if (aligned) {
        size = PerAlignedUtils.decodeSemiConstrainedLength(reader);
      } else {
        size = PerUnalignedUtils.decodeSemiConstrainedLength(reader);
      }
    } else if (maximumSize != minimumSize) {
      if (aligned) {
        size = PerAlignedUtils.decodeSmallConstrainedWholeNumber(
            reader, minimumSize, maximumSize);
      } else {
        size = PerUnalignedUtils.decodeConstrainedWholeNumber(
            reader, minimumSize, maximumSize);
      }
    }
    for (int i = 0; i < size; i++) {
      T value = createAndAddValue();
      if (aligned) {
        value.decodePerAligned(reader);
      } else {
        value.decodePerUnaligned(reader);
      }
    }
  }

  @Override public void decodePerUnaligned(BitStreamReader reader) {
    decodePerImpl(reader, false);
  }

  @Override public void decodePerAligned(BitStreamReader reader) {
    decodePerImpl(reader, true);
  }
}