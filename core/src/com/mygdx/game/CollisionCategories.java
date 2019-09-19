package com.mygdx.game;

public class CollisionCategories {
    public static final short CATEGORY_PLAYER = 0x0001;
    public static final short CATEGORY_SCENERY = 0x0002;
    public static final short CATEGORY_COLLECTIBLES = 0x0004;

    public static final short MASK_PLAYER = CATEGORY_SCENERY | CATEGORY_COLLECTIBLES;
    public static final short MASK_COLLECTIBLE = CATEGORY_PLAYER | CATEGORY_SCENERY;
    public static final short MASK_SCENERY = -1;

}