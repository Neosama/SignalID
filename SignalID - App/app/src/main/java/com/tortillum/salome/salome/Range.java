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

package com.tortillum.salome.salome;

public class Range {
	
	private int[] x = {0};

	// mode : 0 = pos & neg | 1 = pos only | 2 = neg only
	public Range(int number, int margin, int mode) {
		x = new int[margin*2];

		if(mode == 0 || mode == 1 || mode == 2) {
			// For positive margin
			if(mode == 0 || mode == 1) {
				for(int i = 0; i < margin; i++) {
					x[i] = number + i;
				}
			}

			// For negative margin
			if(mode == 0 || mode == 2) {
				int j = 1;
				for(int i = margin; i < margin * 2; i++) {
					x[i] = number - j;
					j++;
				}
			}
		}
	}

	public int[] get() {
		return x;
	}
}
