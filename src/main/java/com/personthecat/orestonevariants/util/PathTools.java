package com.personthecat.orestonevariants.util;

import net.minecraft.util.ResourceLocation;

import java.util.Iterator;

import static com.personthecat.orestonevariants.util.CommonMethods.startsWithAny;

/** A collection of tools used for interacting with OSV texture paths. */
public class PathTools {
    /** Converts the input path into a ResourceLocation */
    public static ResourceLocation getResourceLocation(String path) {
        final String[] split = path
            .split("[/\\\\]");
        final String namespace = startsWithAny(path, "assets", "data")
            ? split[1] : split[0];
        final String result = path
            .replaceAll("assets[/\\\\]", "")
            .replace(namespace + "/", "");
        return new ResourceLocation(namespace, result);
    }

    /** Ensures that the input path does not refer to a special *OSV* texture. */
    public static String ensureNormal(String path) {
        return path.replaceAll("_shaded|dense_", "");
    }

    /** Ensures that the input path refers to a dense texture. */
    public static String ensureDense(String path) {
        final String name = filename(path = ensureNormal(path));
        return path.replace(name, "dense_" + name);
    }

    /** Variant of #ensureDense which accepts a ResourceLocation. */
    public static ResourceLocation ensureDense(ResourceLocation location) {
        return new ResourceLocation(location.getNamespace(), ensureDense(location.getPath()));
    }

    /** Ensures that the input path refers to a shaded texture. */
    public static String ensureShaded(String path) {
        final String name = filename(path = ensureNormal(path));
        return path.replace(name, name + "_shaded");
    }

    /** Returns the end of the input path. */
    public static String filename(String path) {
        final String[] split = path.split("[/\\\\]");
        return split[split.length - 1];
    }

    public static class PathSet implements Iterable<String> {
        public final String normal;
        public final String shaded;
        public final String dense;

        public PathSet(String path, String ext) {
            this.normal = ensureNormal(path) + ext;
            this.shaded = ensureShaded(path) + ext;
            this.dense = ensureDense(path) + ext;
        }

        public Iterator<String> iterator() {
            return new Iterator<String>() {
                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < 3;
                }

                @Override
                public String next() {
                    switch (i) {
                        case 0: return normal;
                        case 1: return shaded;
                        case 2: return dense;
                        default: return null;
                    }
                }
            };
        }
    }
}