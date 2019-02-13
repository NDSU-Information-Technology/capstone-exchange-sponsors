package edu.ndsu.eci.capstone_exchange_sponsors.util;

import java.io.InputStream;

public class JPEGInline extends EciStreamResponse{

  /**
   * JPEGInline()
   * Set up a JPG (photo) inline stream
   *
   * @param stream for the photo
   * @param args arguments
   */
  public JPEGInline(InputStream stream, String... args) {
    super(stream, args);
    setContentType("image/jpeg");
    setExtension("jpg");
    setDisposition(INLINE_DISPOSITION);
  }
}
