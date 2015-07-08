package se.vandmo.textchecker.maven;

import static com.google.common.base.Preconditions.checkNotNull;


public final class ComplaintWithFileInfo {

    private final Complaint complaint;
    private final String fileName;

    public ComplaintWithFileInfo(Complaint complaint, String fileName) {
        checkNotNull(complaint);
        checkNotNull(fileName);
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
