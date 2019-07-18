package com.sar.fs.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import com.sar.fs.config.FSGlobal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:54
 * @describe
 */

@SuppressLint({"InlinedApi"})
public class FSInstallation {
    private static final String INSTALLATION = "androidkit-" + UUID.nameUUIDFromBytes("androidkit".getBytes());

    public FSInstallation() {
    }

    public static synchronized String getID(Context context) {
        if (FSGlobal.device_installation_id == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);

            try {
                if (!installation.exists()) {
                    writeInstallationFile(context, installation);
                }

                FSGlobal.device_installation_id = readInstallationFile(installation);
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

        return FSGlobal.device_installation_id;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(installation, "r");
        byte[] bs = new byte[(int)accessFile.length()];
        accessFile.readFully(bs);
        accessFile.close();
        return new String(bs);
    }

    private static void writeInstallationFile(Context context, File installation) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String uuid = UUID.nameUUIDFromBytes(Settings.Secure.getString(context.getContentResolver(), "android_id").getBytes()).toString();
        out.write(uuid.getBytes());
        out.close();
    }
}
