/*
 *  Copyright (C) 2015 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *import static android.os.BatteryManager.BATTERY_HEALTH_UNKNOWN;
 */
package org.omnirom.omnigears.utils;
import static android.os.BatteryManager.BATTERY_STATUS_FULL;
import static android.os.BatteryManager.BATTERY_STATUS_UNKNOWN;
import static android.os.BatteryManager.EXTRA_HEALTH;
import static android.os.BatteryManager.EXTRA_LEVEL;
import static android.os.BatteryManager.EXTRA_MAX_CHARGING_CURRENT;
import static android.os.BatteryManager.EXTRA_MAX_CHARGING_VOLTAGE;
import static android.os.BatteryManager.EXTRA_PLUGGED;
import static android.os.BatteryManager.EXTRA_STATUS;
import android.os.BatteryManager;

public class OmniBatteryStatus {
        public static final int CHARGING_UNKNOWN = -1;
        public static final int CHARGING_SLOWLY = 0;
        public static final int CHARGING_REGULAR = 1;
        public static final int CHARGING_FAST = 2;
        private static final int LOW_BATTERY_THRESHOLD = 20;

        public final int status;
        public final int level;
        public final int plugged;
        public final int health;
        public final int maxChargingWattage;

        public OmniBatteryStatus(int status, int level, int plugged, int health,
                int maxChargingWattage) {
            this.status = status;
            this.level = level;
            this.plugged = plugged;
            this.health = health;
            this.maxChargingWattage = maxChargingWattage;
        }

        /**
         * Determine whether the device is plugged in (USB, power, or wireless).
         * @return true if the device is plugged in.
         */
        public boolean isPluggedIn() {
            return plugged == BatteryManager.BATTERY_PLUGGED_AC
                    || plugged == BatteryManager.BATTERY_PLUGGED_USB
                    || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS;
        }

        /**
         * Whether or not the device is charged. Note that some devices never return 100% for
         * battery level, so this allows either battery level or status to determine if the
         * battery is charged.
         * @return true if the device is charged
         */
        public boolean isCharged() {
            return status == BATTERY_STATUS_FULL || level >= 100;
        }

        /**
         * Whether battery is low and needs to be charged.
         * @return true if battery is low
         */
        public boolean isBatteryLow() {
            return level < LOW_BATTERY_THRESHOLD;
        }

        public final int getChargingSpeed(int slowThreshold, int fastThreshold) {
            return maxChargingWattage <= 0 ? CHARGING_UNKNOWN :
                    maxChargingWattage < slowThreshold ? CHARGING_SLOWLY :
                    maxChargingWattage > fastThreshold ? CHARGING_FAST :
                    CHARGING_REGULAR;
        }
    }
