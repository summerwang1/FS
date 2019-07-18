package com.sar.fs.app.preference;

import android.content.Context;

import com.sar.fs.config.FSGlobal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.lang.reflect.Field;

/**
 * @auth: sarWang
 * @date: 2019-07-04 19:56
 * @describe
 */

public class FSConfigToProperties implements FSConfigImpl {
    private static FSConfigToProperties mPropertiesConfig;
    private Properties mProperties;
    private Context mContext;

    private FSConfigToProperties(Context context) {
        this.mContext = context;
    }

    public static FSConfigToProperties getPropertiesConfig(Context context) {
        if (mPropertiesConfig == null) {
            mPropertiesConfig = new FSConfigToProperties(context);
        }

        return mPropertiesConfig;
    }

    public void loadConfig() {
        this.setBoolean("is_load", true);
    }

    public void setBoolean(String key, Boolean value) {
        this.setString(key, String.valueOf(value));
    }

    public void setString(String key, String value) {
        this.setConfig(key, value);
    }

    public void setConfig(String key, String value) {
        if (value != null) {
            Properties props = this.getProperties();
            props.setProperty(key, value);
            this.setProperties(props);
        }

    }

    private Properties getProperties() {
        if (this.mProperties == null) {
            this.mProperties = this.getPro();
        }

        return this.mProperties;
    }

    private void setProperties(Properties p) {
        try {
            OutputStream out = this.mContext.openFileOutput(FSGlobal.application_properties, 0);
            p.store(out, (String)null);
            out.flush();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    private Properties getPro() {
        Properties props = new Properties();

        try {
            InputStream in = this.mContext.openFileInput(FSGlobal.application_properties);
            props.load(in);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return props;
    }

    public Boolean isLoadConfig() {
        return this.getBoolean("is_load", false);
    }

    public boolean getBoolean(String key, Boolean defaultValue) {
        try {
            return Boolean.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public String getString(String key, String defaultValue) {
        return this.getConfig(key, defaultValue);
    }

    public String getConfig(String key, String defaultValue) {
        return this.getProperties().getProperty(key, defaultValue);
    }

    public String getString(int resID, String defaultValue) {
        return this.getString(this.mContext.getString(resID), defaultValue);
    }

    public int getInt(int resID, int defaultValue) {
        return this.getInt(this.mContext.getString(resID), defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public boolean getBoolean(int resID, Boolean defaultValue) {
        return this.getBoolean(this.mContext.getString(resID), defaultValue);
    }

    public byte[] getByte(int resID, byte[] defaultValue) {
        return this.getByte(this.mContext.getString(resID), defaultValue);
    }

    public byte[] getByte(String key, byte[] defaultValue) {
        try {
            return this.getString(key, "").getBytes();
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public short getShort(int resID, Short defaultValue) {
        return this.getShort(this.mContext.getString(resID), defaultValue);
    }

    public short getShort(String key, Short defaultValue) {
        try {
            return Short.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public long getLong(int resID, Long defaultValue) {
        return this.getLong(this.mContext.getString(resID), defaultValue);
    }

    public long getLong(String key, Long defaultValue) {
        try {
            return Long.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public float getFloat(int resID, Float defaultValue) {
        return this.getFloat(this.mContext.getString(resID), defaultValue);
    }

    public float getFloat(String key, Float defaultValue) {
        try {
            return Float.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public double getDouble(int resID, Double defaultValue) {
        return this.getDouble(this.mContext.getString(resID), defaultValue);
    }

    public double getDouble(String key, Double defaultValue) {
        try {
            return Double.valueOf(this.getString(key, ""));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public void setString(int resID, String value) {
        this.setString(this.mContext.getString(resID), value);
    }

    public void setInt(int resID, int value) {
        this.setInt(this.mContext.getString(resID), value);
    }

    public void setInt(String key, int value) {
        this.setString(key, String.valueOf(value));
    }

    public void setBoolean(int resID, Boolean value) {
        this.setBoolean(this.mContext.getString(resID), value);
    }

    public void setByte(int resID, byte[] value) {
        this.setByte(this.mContext.getString(resID), value);
    }

    public void setByte(String key, byte[] value) {
        this.setString(key, String.valueOf(value));
    }

    public void setShort(int resID, short value) {
        this.setShort(this.mContext.getString(resID), value);
    }

    public void setShort(String key, short value) {
        this.setString(key, String.valueOf(value));
    }

    public void setLong(int resID, long value) {
        this.setLong(this.mContext.getString(resID), value);
    }

    public void setLong(String key, long value) {
        this.setString(key, String.valueOf(value));
    }

    public void setFloat(int resID, float value) {
        this.setFloat(this.mContext.getString(resID), value);
    }

    public void setFloat(String key, float value) {
        this.setString(key, String.valueOf(value));
    }

    public void setDouble(int resID, double value) {
        this.setDouble(this.mContext.getString(resID), value);
    }

    public void setDouble(String key, double value) {
        this.setString(key, String.valueOf(value));
    }

    public <T> T getConfig(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Object entity = null;

        try {
            entity = clazz.newInstance();
            Field[] var4 = fields;
            int var5 = fields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field field = var4[var6];
                field.setAccessible(true);
                if (!FSReflectUtils.isTransient(field) && FSReflectUtils.isBaseDateType(field)) {
                    String columnName = FSReflectUtils.getFieldName(field);
                    field.setAccessible(true);
                    this.getValue(field, columnName, entity);
                }
            }
        } catch (InstantiationException var9) {
            var9.printStackTrace();
        } catch (IllegalAccessException var10) {
            var10.printStackTrace();
        }

        return (T) entity;
    }

    public void setConfig(Object entity) {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            if (!FSReflectUtils.isTransient(field) && FSReflectUtils.isBaseDateType(field)) {
                String columnName = FSReflectUtils.getFieldName(field);
                field.setAccessible(true);
                this.setValue(field, columnName, entity);
            }
        }

    }

    private <T> void getValue(Field field, String columnName, T entity) {
        try {
            Class<?> clazz = field.getType();
            if (clazz.equals(String.class)) {
                field.set(entity, this.getString(columnName, ""));
            } else if (!clazz.equals(Integer.class) && !clazz.equals(Integer.TYPE)) {
                if (!clazz.equals(Float.class) && !clazz.equals(Float.TYPE)) {
                    if (!clazz.equals(Double.class) && !clazz.equals(Double.TYPE)) {
                        if (!clazz.equals(Short.class) && !clazz.equals(Short.TYPE)) {
                            if (!clazz.equals(Long.class) && !clazz.equals(Long.TYPE)) {
                                if (!clazz.equals(Byte.class) && !clazz.equals(Byte.TYPE)) {
                                    if (clazz.equals(Boolean.class) || clazz.equals(Boolean.TYPE)) {
                                        field.set(entity, this.getBoolean(columnName, false));
                                    }
                                } else {
                                    field.set(entity, this.getByte(columnName, new byte[8]));
                                }
                            } else {
                                field.set(entity, this.getLong(columnName, 0L));
                            }
                        } else {
                            field.set(entity, this.getShort(columnName, Short.valueOf((short)0)));
                        }
                    } else {
                        field.set(entity, this.getDouble(columnName, 0.0D));
                    }
                } else {
                    field.set(entity, this.getFloat(columnName, 0.0F));
                }
            } else {
                field.set(entity, this.getInt(columnName, 0));
            }
        } catch (IllegalArgumentException var5) {
            var5.printStackTrace();
        } catch (IllegalAccessException var6) {
            var6.printStackTrace();
        }

    }

    private void setValue(Field field, String columnName, Object entity) {
        try {
            Class<?> clazz = field.getType();
            if (clazz.equals(String.class)) {
                this.setString(columnName, (String)field.get(entity));
            } else if (!clazz.equals(Integer.class) && !clazz.equals(Integer.TYPE)) {
                if (!clazz.equals(Float.class) && !clazz.equals(Float.TYPE)) {
                    if (!clazz.equals(Double.class) && !clazz.equals(Double.TYPE)) {
                        if (!clazz.equals(Short.class) && !clazz.equals(Short.TYPE)) {
                            if (!clazz.equals(Long.class) && !clazz.equals(Long.TYPE)) {
                                if (clazz.equals(Boolean.class) || clazz.equals(Boolean.TYPE)) {
                                    this.setBoolean(columnName, (Boolean)field.get(entity));
                                }
                            } else {
                                this.setLong(columnName, (Long)field.get(entity));
                            }
                        } else {
                            this.setShort(columnName, (Short)field.get(entity));
                        }
                    } else {
                        this.setDouble(columnName, (Double)field.get(entity));
                    }
                } else {
                    this.setFloat(columnName, (Float)field.get(entity));
                }
            } else {
                this.setInt(columnName, (Integer)field.get(entity));
            }
        } catch (IllegalArgumentException var5) {
            var5.printStackTrace();
        } catch (IllegalAccessException var6) {
            var6.printStackTrace();
        }

    }

    public void remove(String key) {
        Properties props = this.getProperties();
        props.remove(key);
        this.setProperties(props);
    }

    public void remove(String... keys) {
        Properties props = this.getProperties();
        String[] var3 = keys;
        int var4 = keys.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String key = var3[var5];
            props.remove(key);
        }

        this.setProperties(props);
    }

    public void clear() {
        Properties props = this.getProperties();
        props.clear();
        this.setProperties(props);
    }

    public void open() {
    }

    public void close() {
    }

    public boolean isClosed() {
        return false;
    }
}

