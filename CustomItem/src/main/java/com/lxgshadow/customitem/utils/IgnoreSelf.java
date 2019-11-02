package com.lxgshadow.customitem.utils;

import org.bukkit.entity.Entity;

import java.util.function.Predicate;

public class IgnoreSelf implements Predicate<Entity> {
    private Entity self;
    public IgnoreSelf(Entity e){self=e;}
    @Override
    public boolean test(Entity e) {
        return e!=self;
    }
}