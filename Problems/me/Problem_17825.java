package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_17825 {

	static class Horse_17825 {
		public int currentIdx; // 말의 현재 위치
		public int currentScore; // 말의 현재 위치의 칸의 점수
		public int dir; // 말이 현재 진행중인 경로

		public Horse_17825(int currentIdx, int currentScore, int dir) {
			super();
			this.currentIdx = currentIdx;
			this.currentScore = currentScore;
			this.dir = dir;
		}
	}

	static int[] dice = new int[10]; // 주사위
	static boolean[][] checked = new boolean[4][25]; // 경로 번호, 해당 경로에서 말의 index
	static int answer = 0;

	// 갈 수 있는 길은 총 4가지 경우
	static int[][] routes = {
			// 파란색 칸에서 시작하지 않는 경우
			{ 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40 },

			// 10에서 시작하는 경우
			{ 0, 2, 4, 6, 8, 10, 13, 16, 19, 25, 30, 35, 40 },

			// 20에서 시작하는 경우
			{ 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 25, 30, 35, 40 },

			// 30에서 시작하는 경우
			{ 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 28, 27, 26, 25, 30, 35, 40 }

	};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		String input = br.readLine();
		st = new StringTokenizer(input, " ");
		for (int i = 0; i < dice.length; ++i) {
			dice[i] = Integer.parseInt(st.nextToken());
		}

		int count = 0;
		int dir = 0;
		int acc = 0;
		Horse_17825[] horse = new Horse_17825[4];
		for (int i = 0; i < 4; ++i) {
			horse[i] = new Horse_17825(0, 0, dir);
		}
		playGame(horse, count, acc);

		System.out.println(answer);
	}

	private static void playGame(Horse_17825[] horse, int count, int acc) {
		if (count == dice.length) {		
			System.out.println(acc);
			answer = Math.max(answer, acc);
			return;
		}
		
		Horse_17825[] tmp = new Horse_17825[4];
		for (int i = 0; i < 4; ++i) {
			tmp[i] = new Horse_17825(horse[i].currentIdx, horse[i].currentScore, horse[i].dir);
		}		

		for (int i = 0; i < 4; ++i) {					
			// 말의 현재 위치가 도착지라면 => 다음 말로 경우의 수 고려
			if (tmp[i].currentIdx < routes[tmp[i].dir].length) {

				// 말의 시작 위치에 따라 경로 변경
				if (tmp[i].currentScore == 10) {					
					int dir = 1;
					tmp[i].dir = dir;
					
				} else if (tmp[i].currentScore == 20) {
					int dir = 2;
					tmp[i].dir = dir;

				} else if (tmp[i].currentScore == 30) {
					int dir = 3;
					tmp[i].dir = dir;
				}
				
				//해당 말이 움직일 위치가 범위를 벗어난다면 => 목적지에 놓기
				int movePosition = tmp[i].currentIdx + dice[count];
				if(movePosition >= routes[tmp[i].dir].length) {
					movePosition = routes[tmp[i].dir].length;
					tmp[i].currentIdx = movePosition;
					tmp[i].currentScore = -1;
					
					//움직일 때 이전 좌표는 방문 체크 해제
					checked[horse[i].dir][horse[i].currentIdx] = false;
					playGame(tmp, count+1, acc);
					
					tmp[i].currentIdx = horse[i].currentIdx;
					tmp[i].currentScore = horse[i].currentScore;
					checked[horse[i].dir][horse[i].currentIdx] = true;
				}
				
				else {
					//만약 이동할 곳에 다른 말이 존재한다면 => 이동x
					if(!checked[tmp[i].dir][movePosition]) {						
						
						//움직일 때 이전 좌표는 방문 체크 해제, 움직일 곳에 방문 체크
						checked[horse[i].dir][horse[i].currentIdx] = false;
						checked[tmp[i].dir][movePosition] = true;
						
						tmp[i].currentIdx = movePosition;
						tmp[i].currentScore = routes[tmp[i].dir][movePosition];
						
						System.out.println("이동가능!");
						System.out.println("말: " + i + ", " + tmp[i].currentIdx + ", " + tmp[i].currentScore + ", " + tmp[i].dir);
						System.out.println();
						
						playGame(tmp, count+1, acc + tmp[i].currentScore);
						
						tmp[i].currentIdx = horse[i].currentIdx;
						tmp[i].currentScore = horse[i].currentScore;
						
						checked[horse[i].dir][horse[i].currentIdx] = true;
						checked[tmp[i].dir][movePosition] = false;
					}
				}
			}
		}

	}
}
