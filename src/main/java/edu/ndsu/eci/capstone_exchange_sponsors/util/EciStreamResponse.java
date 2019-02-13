package edu.ndsu.eci.capstone_exchange_sponsors.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

/**
 * Handles most of the setup for streaming back responses.
 * By default returns content as an attachment.
 *
 */
public class EciStreamResponse implements StreamResponse {
  
  /** Attachment disposition type for popping up the save as dialog */
  public static final String ATTACHMENT_DISPOSITION = "attachment";
  /** Inline disposition type for displaying content in the browser */
  public static final String INLINE_DISPOSITION = "inline";
  
  /** default filename */
  protected String filename = "default";
  /** input stream */
  protected InputStream stream = null;
  /** default content type */
  private String contentType = "text/plain";
  /** default file extension */
  private String extension = "txt";
  /** set download */
  private String disposition = ATTACHMENT_DISPOSITION;
  
  /**
   * EciStreamResponse
   * Configure the response with the given input stream and arguments
   * @param stream input stream
   * @param args parameters
   */
  public EciStreamResponse(InputStream stream, String... args) {
    this.stream = stream;
    if (args.length > 0) {
      this.filename = args[0];
    }
  }
  
  /**
   * getExtension()
   *
   * @return file extension
   */
  public String getExtension() {
    return extension;
  }

  /**
   * getFilename()
   *
   * @return file name
   */
  public String getFilename() {
    return filename;
  }

  /**
   * setContentType()
   *
   * @param type file content type
   */
  public void setContentType(String type) {
    contentType = type;
  }

  /**
   * setExtension()
   *
   * @param extension file extension
   */
  public void setExtension(String extension) {
    this.extension = extension;
  }

  /**
   * setFilename()
   *
   * @param filename file name
   */
  public void setFilename(String filename) {
    this.filename = filename;
  }
  
  /**
   * The Content-Disposition header type.
   * @param disposition typically attachment or inline.
   */
  public void setDisposition(String disposition) {
    this.disposition = disposition;
  }

  /**
   * Content-Disposition header type
   * @return header type, typically attachment or inline.
   */
  public String getDisposition() {
    return this.disposition;
  }
  
  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public InputStream getStream() throws IOException {
    return stream;
  }

  @Override
  public void prepareResponse(Response arg0) {
    arg0.setHeader("Content-Disposition", disposition + "; filename=" + filename 
        + ((getExtension() == null) ? "" : ("." + getExtension())));

  }

}
