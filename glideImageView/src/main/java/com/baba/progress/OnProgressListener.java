package com.baba.progress;

public interface OnProgressListener {
    void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes);
}
