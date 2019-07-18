package com.sar.fs.app.adapter.annotations;

/**
 * @auth: sarWang
 * @date: 2019-07-05 17:55
 * @describe
 */
public class LayoutIdMissingException extends RuntimeException {
    public LayoutIdMissingException() {
        super("EasyItemViewHolder children classes must be annotated with a layout id, please add @LayoutId(someLayoutId) ");
    }
}