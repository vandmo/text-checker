package se.vandmo.textchecker.maven;

import static java.util.Objects.requireNonNull;

public final class ComplaintWithFileInfo {

  private final Complaint complaint;
  private final String fileName;

  public ComplaintWithFileInfo(Complaint complaint, String fileName) {
    requireNonNull(complaint);
    requireNonNull(fileName);
    this.complaint = complaint;
    this.fileName = fileName;
  }

  public Complaint getComplaint() {
    return complaint;
  }

  public String getFileName() {
    return fileName;
  }
}
