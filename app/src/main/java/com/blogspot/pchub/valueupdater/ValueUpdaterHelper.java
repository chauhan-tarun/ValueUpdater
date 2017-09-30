package com.blogspot.pchub.valueupdater;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Value Updater Helper will helps you to get regular updates on
 * some value whenever an event occurs(say a button clicked).
 * <p>
 * <p>
 * Basically with help of this class you can simply set a min value,
 * a max value and an initial value and you can get the updated value by passing
 * the event type that is occurred.
 * </p>
 */

public class ValueUpdaterHelper {

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Interface for {@link ValueUpdaterHelper} which will return the value updates
     */
    public interface ValueUpdaterInterface {

        /**
         * Interface method to get updated value
         */
        void onValueUpdate(View view, int updatedValue);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    // Min, Max and Initial values
    private int min = 0, max = Integer.MAX_VALUE, value = min;

    // View which is long pressed
    private View view;

    // This boolean will tell us that the view that is long pressed will increase the value or decrease the value
    private boolean isIncreasing = false;

    // ValueUpdaterInterface to return the updated values on long pres of View
    private ValueUpdaterInterface updaterInterface;

    // Speed counter to return updated values with increasing speed
    private int updatingSpeed = 350;

    // This counter will help our updateSpeed to get reduced quickly as the updated values are sent.
    // This will result in nice effect to user where he can see that the speed of his updates
    // on long pressing the button is keep on increasing
    private int counter = 0;

    private Handler handler = new Handler();

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Empty constructor
     */
    public ValueUpdaterHelper() {
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * ValueUpdaterHelper constructor to set min, max and value value
     *
     * @param min          Minimum value that you wants to set. By default its zero
     * @param max          Maximum value that you wants to set. By default its Integer.MAX_VALUE
     * @param initialValue Initial value that you wants to set. By default its equal to minimum value
     */
    public ValueUpdaterHelper(int min, int max, int initialValue) {
        setMin(min);
        setMax(max);
        setValue(initialValue);
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Sets minimum value
     *
     * @param min Minimum value
     */
    public void setMin(int min) {
        if (min < 0 || min >= max)
            this.min = 0;
        else
            this.min = min;

        if (value < this.min) {
            value = this.min;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Sets maximum value
     *
     * @param max Maximum value
     */
    public void setMax(int max) {
        if (max < 1 || max <= min)
            this.max = Integer.MAX_VALUE;
        else
            this.max = max;

        if (value > this.max) {
            value = this.min;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Use this method to update value directly.
     *
     * @param value initial value
     * @return Returns the correct updated value within the defined min and max values
     */
    public int setValue(int value) {
        if (value < min)
            this.value = min;
        else if (value <= max) {
            this.value = value;
        }

        return this.value;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method will perform all calculations and checks to return you
     * the updated value based on the parameter sent.
     *
     * @param isIncreased boolean isIncreased.
     *                    Send true if you want increased value and
     *                    send false if you wants decreased value
     * @return The updated value within the min or max limit
     */
    public int getUpdatedValue(boolean isIncreased) {

        if (isIncreased) {
            // Return increased value by checking if value is less than max
            return setValue(value + 1);

        } else {
            // Return decreased value by checking if value is greater than min
            return setValue(value - 1);
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Call this method to get the updated values when a view is long pressed
     *
     * @param view             View which is long pressed
     * @param updaterInterface The ValueUpdaterInterface to receive the value updates
     *                         until the view is pressed or value is reached
     * @param isIncreasing     boolean to define whether the value should increase or decrease.
     *                         The value will get updated on basis of this boolean
     */
    public void getLongPressUpdates(@NonNull View view, @NonNull ValueUpdaterInterface updaterInterface, boolean isIncreasing) {
        this.view = view;
        this.updaterInterface = updaterInterface;
        this.isIncreasing = isIncreasing;

        sendLongPressUpdates();
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    /**
     * This method will recall itself until the limit is reached
     * or user releases the long pressed view.
     * <p>
     * This method will also send the updates using the interface with an increasing speed.
     * As soon as user releases the long pressed view or as soon as value update limit is reached
     * all values will get back to their default stage so that when a view is long pressed next time
     * it gets same effect from the starting.
     */
    private void sendLongPressUpdates() {

        if (view.isPressed()) {

            // Here we returns the updated value
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    // Just to get the reference of previous value
                    int valueBeforeUpdate = value;

                    // Call setValue() to get the updatedValue
                    if (isIncreasing) {
                        setValue(value + 1);
                    } else {
                        setValue(value - 1);
                    }

                    // If we gets the updated value then call the method again to get further values
                    // Otherwise don't call the method again and end this cycle here.
                    if (valueBeforeUpdate != value) {
                        updaterInterface.onValueUpdate(view, value);

                        // This will result in increased speed in value updates
                        if (updatingSpeed > 10) {
                            updatingSpeed -= counter;
                            counter++;
                        }

                        sendLongPressUpdates();
                    } else {

                        // So the limit is reached. Reset all long pressed values and stop sending regular updates
                        counter = 10;
                        updatingSpeed = 350;

                    }

                }
            }, updatingSpeed);

        } else {

            // View is no longer pressed. Reset all long pressed values to default and stop sending regular updates
            counter = 10;
            updatingSpeed = 350;

        }

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

}
