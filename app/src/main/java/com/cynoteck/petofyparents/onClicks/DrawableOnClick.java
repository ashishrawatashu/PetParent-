package com.cynoteck.petofyparents.onClicks;

public interface DrawableOnClick {
    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    public void onClick(DrawablePosition target);

}
