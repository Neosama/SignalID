/*
 *  Copyright (C) 2020 Tortillum / Neosama
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tortillum.salome.other;

import com.tortillum.salome.signal.INFO_SIGNAL;

public class Duration {
	boolean state = false;

	String name;
	double resolution_part = 38.75; // 5/129 = 0,0387596899224806
	double duration_parts = 0;
	int max_true_parts = 0;
	int all_true_parts = 0;
	int current_true_parts = 0;
	int current_false_parts = 0;
	int timeout_parts = 0; // 2

	public Duration(String pName, int pTimeoutParts) {
		name = pName;
		timeout_parts = pTimeoutParts;
	}

	public Duration() {

	}

	public void setState(boolean pState) {
		state = pState;

		if(state) {
			current_true_parts++;
			all_true_parts++;

			if(current_true_parts > max_true_parts)
				max_true_parts = current_true_parts;

			// reset
			current_false_parts = 0;
		} else {
			current_false_parts++;

			// reset
			if(current_false_parts >= timeout_parts)
				current_true_parts = 0;
		}
	}

	public double getAllDuration() {
		return all_true_parts * resolution_part; // in ms
	}

	public double getMaxDuration() {
		return max_true_parts * resolution_part; // in ms
	}

	public String getName() {
		return name;
	}

	public void info() {
		System.out.println("[" + name + "] AllDuration = " + getAllDuration() + " / MaxDuration = " + getMaxDuration());
	}
	
	// SIGNAL duration

	public int FT8_check(Duration[] durations) {
		int score = 0;
		for(int i = 0; i <= 46; i++) { // RANGE DURATION ID : 0 -> 46
			if(durations[i].getMaxDuration() > 10000-1000 && durations[i].getMaxDuration() < 12100 && durations[i].getName().contentEquals(INFO_SIGNAL.NAME_FT8)) {
				//System.out.println("FT8 found! (" + i + ")");
				score++;
			}
		}

		return score;
	}
	
	public int FT4_check(Duration[] durations) {
		int score = 0;
		for(int i = 47; i <= 100; i++) { // RANGE DURATION ID : 47 -> 100
			if(durations[i].getMaxDuration() > 4480-1000 && durations[i].getMaxDuration() < 5050 & durations[i].getName().contentEquals(INFO_SIGNAL.NAME_FT4)) {
				//System.out.println("FT4 found! (" + i + ")");
				score++;
			}
		}

		return score;
	}
}
