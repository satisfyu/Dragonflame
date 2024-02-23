package satisfyu.dragonflame.util;

import java.util.ArrayList;
import java.util.Collection;

public interface MathUtil {
    /**
     * @author cph101
     * @param angle The FOV angle, should be the sum of the angles on both sides of the player's orientation
     * @param playerOrientation Player's original orientation. This will be used as the center of the FOV
     * @return All the degrees in the specified FOV, rotated by a specified angle
     */
    static Collection<Double> getFOVDegreeCollection(double angle, double playerOrientation) {
        Collection<Double> fovDegrees = new ArrayList<>();
        if (angle > 360) angle = 360;
        angle = Math.abs(angle) / 2;
        playerOrientation = -validateAngle(playerOrientation);
        for (double fovOffset = 0; fovOffset < angle; fovOffset++) {
            fovDegrees.add(validateAngle(playerOrientation + fovOffset));
        }
        for (double fovOffset = 0; fovOffset < angle; fovOffset++) {
            fovDegrees.add(validateAngle(playerOrientation - fovOffset));
        }

        /*for (int i = 1; i <= 360; i++) {
            fovDegrees.add((double) i);
        }*/

        return fovDegrees;

    }

    /** Validates the angle to be within 0 to 360 degrees
     * @author cph101
     * @param angle The angle to validate
     * @return The validated angle
     */
    static double validateAngle(double angle) {
        if (angle > 360) angle = angle % 360;
        if (Math.abs(angle) != angle) {
            angle = 360 - Math.abs(angle); // rotation
        }
        return angle;
    }


    /**
     * Converts seconds to Minecraft ticks
     * @param s Time in seconds
     * @return Time in Minecraft ticks
     */
    static int secondsToTicks(double s) {
        return (int) (20 * s);
    }

    /**
     * Simple utility to round a value to the nearest multiple
     * @param value The value to round
     * @param multiple The multiple to round to
     * @return The rounded value
     */
    static double roundToMultiple(double value, double multiple) {
        return Math.round(value / multiple) * multiple;
    }
}
