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

package com.tortillum.salome.recognizer;

import com.tortillum.salome.other.Pair;

public class Score {
	private String name;
	private int type_mode = -1;
	private int type_frequencies = -1;
	private int score_FFT;
	private int score_SFFT;
	private int score_allDuration;
	private int score_maxDuration;

	public Score(String pName, int pTypeMode, int pTypeFrequencies){
		name = pName;
		type_mode = pTypeMode;
		type_frequencies = pTypeFrequencies;
		score_FFT = 0;
		score_SFFT = 0;
		score_allDuration = 0;
		score_maxDuration = 0;
	}

	public void setName(String pName){
		name = pName;
	}

	public void addScore_FFT(int pScore_FFT){
		score_FFT += pScore_FFT;
	}

	public void addScore_SFFT(int pScore_SFFT){
		score_SFFT += pScore_SFFT;
	}

	public void addScore_allDuration(int pScore_allDuration) {
		score_allDuration += pScore_allDuration;
	}
	
	public void addScore_maxDuration(int pScore_maxDuration) {
		score_maxDuration += pScore_maxDuration;
	}

	public void setScore_FFT(int pScore_FFT){
		score_FFT = pScore_FFT;
	}

	public void setScore_SFFT(int pScore_SFFT){
		score_SFFT = pScore_SFFT;
	}

	public void setScore_allDuration(int pScore_allDuration) {
		score_allDuration = pScore_allDuration;
	}
	
	public void setScore_maxDuration(int pScore_maxDuration) {
		score_maxDuration = pScore_maxDuration;
	}

	public String getName(){
		return name;
	}
	
	public int getTypeMode() {
		return type_mode;
	}
	
	public int getTypeFrequencies() {
		return type_frequencies;
	}

	public int getScore_FFT() {
		return score_FFT;
	}

	public int getScore_SFFT(){
		return score_SFFT;
	}

	public int getScore_allDuration() {
		return score_allDuration;
	}

	public int getScore_maxDuration() {
		return score_maxDuration;
	}
}
