package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_2105 {
	
	static int N;
	static int[][] map;
	static boolean[] disserts;
	static int[][] directions = {
			//ø¿æ∆, øﬁæ∆, øﬁ¿ß, ø¿¿ß
			{1, 1, -1, -1},
			{1, -1, -1, 1}
	};
	
	static int[] startPosition;
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t<=testCase) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			int max = 0;
			for(int i=0; i<N; ++i) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");
				for(int j=0; j<N; ++j) {
					map[i][j] = Integer.parseInt(st.nextToken());
					max = Math.max(max, map[i][j]);
				}
			}
						
			answer = 0;
			startPosition = new int[2];
			disserts = new boolean[max+1];
			for(int i=0; i<N; ++i) {
				for(int j=0; j<N; ++j) {
					int count=1;
					int dir = 0;					
										
					startPosition[0] = i;
					startPosition[1] = j;					
					
					disserts[map[i][j]] = true;
					doSolution(i, j, count, dir);
					disserts[map[i][j]] = false;					
				}
			}
			
			answer = answer == 0 ? -1 : answer;
			System.out.println("#" + t + " " + answer);
			t++;
		}
		
	}

	private static void doSolution(int y, int x, int count, int dir) {
		if(dir == 4) return;
		if(dir == 3) {
			if(startPosition[0]+1 == y && startPosition[1]-1 == x) {
				answer = Math.max(answer, count);
				return;
			}						
		}		
		
		
		int moveY = y + directions[0][dir];
		int moveX = x + directions[1][dir];
		
		if(isRanged(moveY, moveX) && !disserts[map[moveY][moveX]]) {
			disserts[map[moveY][moveX]] = true;
			doSolution(moveY, moveX, count+1, dir);
			doSolution(moveY, moveX, count+1, dir+1);
			disserts[map[moveY][moveX]] = false;
		}		
	}

	private static boolean isRanged(int y, int x) {
		if(y>=0 && y<N && x>=0 && x<N) return true;
		return false;
	}

}
