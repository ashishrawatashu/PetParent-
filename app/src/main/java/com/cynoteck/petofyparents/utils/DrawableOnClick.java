package com.cynoteck.petofyparents.utils;

public interface DrawableOnClick {
    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);

}
