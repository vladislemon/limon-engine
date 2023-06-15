package test;

import limonengine.Engine;
import limonengine.render.Render;
import limonengine.util.math.MathUtil;
import test.rabbitisland.World2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * slimon
 * 09.08.2014
 */
public class App {

    public static void main(String[] args) {
        byteTest(3);
    }

    private static void byteTest(int i) {
        if(i > 2) {
            System.out.println(i);
        }
    }

    boolean get() {
        return false;
    }
}