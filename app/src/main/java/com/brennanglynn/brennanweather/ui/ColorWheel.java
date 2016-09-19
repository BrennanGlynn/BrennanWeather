package com.brennanglynn.brennanweather.ui;

import android.graphics.Color;

import java.util.Random;

public class ColorWheel {

    private String mColors[] = {
            "#39add1", // light blue [0]
            "#3079ab", // dark blue [1]
            "#c25975", // mauve [2]
            "#e15258", // red [3]
            "#f9845b", // orange [4]
            "#838cc7", // lavender [5]
            "#7d669e", // purple [6]
            "#53bbb4", // aqua [7]
            "#e0ab18", // mustard [8]
            "#637a91", // dark gray [9]
            "#f092b0", // pink [10]
            "#b7c0c7", // light gray [11]
            "#3a7bd5", // Reef [12]
            "#f9d34c", // yellow [13]
            "#B3FFAB", // Neon Life [14]
            "#AAFFA9", // Teal Love [15]
            "#e74c3c", // Red Mist [16]
            "#ADD100", // Parklife [17]
            "#FBD3E9", // Cherryblossoms [18]
            "#53346D", // Shadow Night [19]
            "#8CA6DB"  // Dirty Fog [20]
    };

    private String mColors2[] = {
            "#3079ab", //dark blue  [0]
            "#39add1", // light blue [1]
            "#e0ab18", // mustard [2]
            "#637a91", // dark gray [3]
            "#f092b0", // pink [4]
            "#b7c0c7", // light gray [5]
            "#c25975", // mauve [6]
            "#51b46d", // green [7]
            "#f9845b", // orange [8]
            "#838cc7", // lavender [9]
            "#7d669e", // purple [10]
            "#53bbb4", // aqua [11]
            "#00d2ff", // Reef [12]
            "#f25019", // orange [13]
            "#12FFF7", // Neon Life [14]
            "#11FFBD", // Teal Love [15]
            "#000000", // Red Mist [16]
            "#7B920A", // Parklife [17]
            "#BB377D", // Cherryblossoms [18]
            "#000000", // Shadow Night [19]
            "#B993DC"  // Dirty Fog [20]
    };

    public int[] getColors() {
        String color;
        String color2;

        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(mColors.length);

        color = mColors[randomNumber];
        color2 = mColors2[randomNumber];
        int colorAsInt = Color.parseColor(color);
        int colorAsInt2 = Color.parseColor(color2);

        return new int[]{colorAsInt, colorAsInt2};
    }

    public int[] getSpecificColor(int number) {
        String color;
        String color2;

        Random randomGenerator = new Random();
        int randomNumber = number;

        color = mColors[randomNumber];
        color2 = mColors2[randomNumber];
        int colorAsInt = Color.parseColor(color);
        int colorAsInt2 = Color.parseColor(color2);

        return new int[]{colorAsInt, colorAsInt2};

    }

}
