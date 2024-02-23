package satisfy.dragonflame.util;

public class TrackedPlayerState {
    static boolean isHoldingShift = false;

    public static boolean wasHoldingShiftWhenLastCached() {
        return isHoldingShift;
    }

    public static boolean setHoldingShift(boolean setTo) {
        boolean wasHoldingShift = isHoldingShift;
        isHoldingShift = setTo;
        return wasHoldingShift;
    }
}
