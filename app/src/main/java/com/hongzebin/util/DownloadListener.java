package com.hongzebin.util;

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();
}
