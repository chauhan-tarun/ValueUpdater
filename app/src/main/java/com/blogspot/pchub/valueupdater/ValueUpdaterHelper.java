package com.blogspot.pchub.valueupdater;

/**
 * Value Updater Helper will helps you to get regular updates on
 * some value whenever an event occurs(say a button clicked).
 *
 * <p>
 *     Basically with help of this class you can simply set a min value,
 *     a max value and an value value and you can get the updated value by passing
 *     the event type that is occurred.
 * </p>
 */

public class ValueUpdaterHelper {


    /**
     * Interface for {@link ValueUpdaterHelper} which will return the value updates
     */
    public interface ValueUpdaterInterface{

        /**
         * Interface method to get updated value
         */
        void onValueUpdate(int updatedValue);
    }

    // Min, Max and Initial values
    private int min = 0, max = Integer.MAX_VALUE, value = min;

    /**
     * Empty constructor
     */
    public ValueUpdaterHelper() {}

    /**
     * ValueUpdaterHelper constructor to set min, max and value value
     *
     * @param min     Minimum value that you wants to set. By default its zero
     * @param max     Maximum value that you wants to set. By default its Integer.MAX_VALUE
     * @param initialValue Initial value that you wants to set. By default its equal to minimum value
     */
    public ValueUpdaterHelper(int min, int max, int initialValue) {
        setMin(min);
        setMax(max);
        setValue(initialValue);
    }

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

    /**
     * Sets Initial value
     *
     * @param initialValue initial value
     */
    public void setValue(int initialValue) {
        if (initialValue < min || initialValue > max)
            this.value = min;
        else
            this.value = initialValue;
    }


    /**
     * This method will perform all calculations and checks to return you
     * the updated value based on the parameter sent.
     *
     * @param isIncreased boolean isIncreased.
     *                    Send true if you want increased value and
     *                    send false if you wants decreased value
     *
     * @return The updated value within the min or max limit
     */
    public int getUpdatedValue(boolean isIncreased){

        if(isIncreased){
            // Return increased value by checking if value is less than max
            if(value < max)
                return ++value;

        }else{
            // Return decreased value by checking if value is greater than min
            if(value > min)
                return --value;
        }

        return value;
    }
}
